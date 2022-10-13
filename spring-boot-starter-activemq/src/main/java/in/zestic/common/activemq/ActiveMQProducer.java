/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <Deebendu Kumar>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.zestic.common.activemq;

import com.google.common.util.concurrent.RateLimiter;
import in.zestic.common.activemq.config.ActiveMQProperties;
import in.zestic.common.activemq.exception.ActiveMQRuntimeException;
import in.zestic.common.collection.SynchronizedQueue;
import in.zestic.common.entity.Message;
import in.zestic.common.exception.ApplicationException;
import in.zestic.common.exception.GenericError;
import in.zestic.common.exception.NotImplementedException;
import in.zestic.common.mq.Producer;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.MessageId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import java.util.Map;

/**
 * 1. Acquire a JMS destination
 * 2. Create a JMS producer, OR
 * a. Create a JMS producer
 * b. Create a JMS message and address it to a destination
 */
@Getter
@Setter
public class ActiveMQProducer extends ActiveMQClient implements Producer {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQProducer.class.getSimpleName());

    private ActiveMQMessageProducer producer = null;

    private final SynchronizedQueue<Message> queue;

    private final RateLimiter rateLimiter;

    public ActiveMQProducer(ActiveMQProperties properties) {
        super(properties);
        queue = new SynchronizedQueue<>();
        rateLimiter = RateLimiter.create(properties.getProducer().getThroughput());
    }

    public void create() throws ApplicationException {
        try {
            super.create();
            logger.error("Creating destination");
            destination = (ActiveMQQueue) session.createQueue(this.properties.getConsumer().getQueueName());
            logger.error("Creating producer");
            producer = (ActiveMQMessageProducer) session.createProducer(destination);
            logger.error("Setting delivery mode to PERSISTENT");
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } catch (JMSException e) {
            logger.error("Exception " + e.getMessage());
            throw new ActiveMQRuntimeException(ActiveMQError.RTE_UNABLE_CREATE_PRODUCER, e.getMessage());
        }
    }

    @SneakyThrows
    public void close() {
        logger.error("Closing the producer");
        if (producer != null)
            producer.close();
        super.close();
    }

    @Override
    public void submit(Message message, Map<String, Object> optional) throws ApplicationException {
    }

    @Override
    @SneakyThrows
    public void flush() throws ApplicationException {
        throw new NotImplementedException(GenericError.RTE_METHOD_NOT_IMPL);
    }

    @Override
    public void submit(Message message) throws ApplicationException {
        if (session != null && session.isRunning() && producer != null) {
            ActiveMQObjectMessage object = prepare();
            if (object != null) {
                object.setJMSDeliveryMode(properties.getProducer().getJmsDeliveryMode().getIndex());
                setMessageId(object, message.getMessageId(), properties.getProducer().getDisableJmsMessageId());
                setJmsExpiration(properties.getProducer().getJmsExpiration());
                enableMessageTimestamp();
                try {
                    //The time that a message will expire, into milliseconds
                    producer.send(object);
                    if (session.getTransacted()) {
                        session.commit();
                    }
                } catch (JMSException e) {
                    logger.error("Exception " + e.getMessage());
                    throw new ActiveMQRuntimeException(ActiveMQError.RTE_PRODUCER_UNABLE_TO_SUBMIT, e.getMessage());
                }
            }
        } else {
            logger.error("Either session or producer is not created properly");
        }
    }

    private ActiveMQObjectMessage prepare() {
        ActiveMQObjectMessage object = null;
        if (session != null && session.isRunning()) {
            try {
                object = (ActiveMQObjectMessage) session.createObjectMessage();
            } catch (JMSException e) {
                logger.error("Unable to create object message");
                throw new ActiveMQRuntimeException(ActiveMQError.RTE_UNABLE_CREATE_OBJECT_MESSAGE, e.getMessage());
            }
        }
        return object;
    }

    private void setJmsExpiration(Long timeToLive) {
        if (properties.getProducer().getEnableJmsExpiration()) {
            try {
                producer.setTimeToLive(properties.getProducer().getJmsExpiration());
            } catch (JMSException e) {
                logger.error("Exception " + e.getMessage());
                throw new ActiveMQRuntimeException(ActiveMQError.RTE_JMS_EXPIRATION, e.getMessage());
            }
        }
    }

    private void setMessageId(ActiveMQObjectMessage object, String messageId, Boolean enableMessageId) {
        try {
            if (enableMessageId) {
                producer.setDisableMessageID(false);
                if (messageId != null && !messageId.isEmpty()) {
                    object.setMessageId(new MessageId(messageId));
                }
            } else {
                producer.setDisableMessageID(true);
            }
        } catch (JMSException e) {
            logger.error("Exception " + e.getMessage());
            throw new ActiveMQRuntimeException(ActiveMQError.RTE_UNABLE_SET_MESSAGE_ID, e.getMessage());
        }
    }

    private void enableMessageTimestamp() {
        try {
            producer.setDisableMessageTimestamp(false);
        } catch (JMSException e) {
            logger.error("Exception " + e.getMessage());
            throw new ActiveMQRuntimeException(ActiveMQError.RTE_UNABLE_SET_MESSAGE_ID, e.getMessage());
        }
    }

    @Override
    public void process() {

    }
}
