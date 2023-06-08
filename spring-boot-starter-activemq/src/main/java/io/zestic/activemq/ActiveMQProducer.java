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

package io.zestic.activemq;

import com.google.common.util.concurrent.RateLimiter;
import io.zestic.activemq.exception.ActiveMQRuntimeException;
import io.zestic.core.exception.ApplicationException;
import io.zestic.core.exception.GenericError;
import io.zestic.core.exception.NotImplementedException;
import io.zestic.core.pdu.Pdu;
import io.zestic.core.queue.Queue;
import io.zestic.core.queue.QueueBuilder;
import io.zestic.core.util.IBuilder;
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
public class ActiveMQProducer extends ActiveMQClient {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQProducer.class.getSimpleName());

    private ActiveMQMessageProducer producer = null;
    private Queue queue;
    private RateLimiter rateLimiter;

    public ActiveMQProducer(){}

    public ActiveMQProducer(Builder builder) {
        primaryUri = builder.primaryUri;
        secondaryUri = builder.secondaryUri;
        username = builder.username;
        password = builder.password;
        queueName = builder.queueName;

        queue = new QueueBuilder().build();
        rateLimiter = RateLimiter.create(_THROUGHPUT);
    }

    protected void create() throws ApplicationException {
        try {
            super.create();
            logger.info("Creating destination");
            destination = (ActiveMQQueue) session.createQueue(queueName);
            logger.info("Creating producer");
            producer = (ActiveMQMessageProducer) session.createProducer(destination);
            logger.info("If you are looking for good performance with messages, set delivery mode to be NON_PERSISTENT. " +
                    "Or set the useAsyncSend property on connection factory to be true");
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } catch (JMSException e) {
            logger.error("Exception " + e.getMessage());
            throw new ActiveMQRuntimeException(ActiveMQError.RTE_UNABLE_CREATE_PRODUCER, e.getMessage());
        }
    }

    public void submit(Pdu message, Map<String, Object> optional) throws ApplicationException {
    }

    public void submit(Pdu message) throws ApplicationException {
        if (session != null && session.isRunning() && producer != null) {
            ActiveMQObjectMessage object = prepare();
            if (object != null) {
                object.setJMSDeliveryMode(jmsDeliveryMode.getIndex());
                setJmsExpiration(jmsExpiration);
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

    @SneakyThrows
    public void flush() throws ApplicationException {
        throw new NotImplementedException(GenericError.RTE_METHOD_NOT_IMPL);
    }

    @SneakyThrows
    public void close() {
        logger.error("Closing the producer");
        if (producer != null)
            producer.close();
        super.close();
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
        if (enableJmsExpiration) {
            try {
                producer.setTimeToLive(timeToLive);
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

    public static class Builder implements IBuilder<ActiveMQProducer> {

        private String primaryUri;
        private String secondaryUri;
        private String queueName;
        private String username;
        private String password;
        private Integer prefetchLimit;
        private Boolean useTransaction;
        private Integer throughput;

        public Builder primaryUri(String primaryUri) {
            this.primaryUri = primaryUri;
            return this;
        }

        public Builder secondaryUri(String secondaryUri) {
            this.secondaryUri = secondaryUri;
            return this;
        }

        public Builder queueName(String queueName) {
            this.queueName = queueName;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder prefetchLimit(Integer prefetchLimit) {
            this.prefetchLimit = prefetchLimit;
            return this;
        }

        public Builder useTransaction(Boolean useTransaction) {
            this.useTransaction = useTransaction;
            return this;
        }

        public Builder throughput(Integer throughput) {
            this.throughput = throughput;
            return this;
        }

        @Override
        public ActiveMQProducer build() {
            ActiveMQProducer producer = new ActiveMQProducer(this);
            producer.create();
            return producer;
        }
    }
}
