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
import io.zestic.core.exception.ApplicationException;
import io.zestic.core.pdu.Pdu;
import io.zestic.core.queue.Queue;
import io.zestic.core.util.HexUtil;
import io.zestic.core.util.IBuilder;
import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.MessageId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. Acquire a JMS destination
 * 2. Create a JMS producer, OR
 * a. Create a JMS producer
 * b. Create a JMS message and address it to a destination
 */
public class ActiveMQProducer extends ActiveMQClient {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQProducer.class.getSimpleName());

    private AtomicInteger counter = new AtomicInteger();

    private ActiveMQMessageProducer producer;
    private Queue queue;
    private RateLimiter rateLimiter;

    public ActiveMQProducer(Builder builder) {
        this.primaryUri = builder.primaryUri;
        this.secondaryUri = builder.secondaryUri;
        this.username = builder.username;
        this.password = builder.password;
        this.queueName = builder.queueName;
        this.instanceId = (builder.instance != null) ? builder.instance : 0;
        id = "active-mq-producer" + "-" + (instanceId);
    }

    public void setListener(Listener listener) {
        Objects.requireNonNull(listener, "listener cannot be null");
        this.listener = listener;
    }

    public void connect() throws ApplicationException {
        try {
            super.connect();
            /**
             * lets create a internal queue for store and forward.
             */
            queue = new Queue.Builder().build();
            rateLimiter = RateLimiter.create(_THROUGHPUT);
            logger.info("creating destination");
            destination = (ActiveMQQueue) session.createQueue(queueName);
            logger.info("creating producer");
            producer = (ActiveMQMessageProducer) session.createProducer(destination);
            logger.info("if you are looking for good performance with messages, " +
                    "set delivery mode to be NON_PERSISTENT. " +
                    "Or set the useAsyncSend property on connection factory to be true");
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            listener.onConnect(id);
        } catch (JMSException ex) {
            if (listener != null) listener.onError(id, ex.getMessage());
        }
    }

    @SneakyThrows
    public void close() {
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
                if (listener != null) listener.onError(id, "unable to create object");
            }
        }
        return object;
    }

    public void enqueue(Pdu pdu) throws ApplicationException {
        queue.enqueue(pdu);
        counter.incrementAndGet();
    }

    private void setJmsExpiration(Long timeToLive) {
        if (enableJmsExpiration) {
            try {
                producer.setTimeToLive(timeToLive);
            } catch (JMSException e) {
                if (listener != null) listener.onError(id, e.getMessage());
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
            if (listener != null) listener.onError(id, e.getMessage());
        }
    }

    private void enableMessageTimestamp() {
        try {
            producer.setDisableMessageTimestamp(false);
        } catch (JMSException e) {
            if (listener != null) listener.onError(id, e.getMessage());
        }
    }

    /**
     * Process messages that are in queue
     */
    @Override
    public void process() {
        try {
            if (!queue.isEmpty()) {
                try {
                    if (session != null && session.isRunning()) {
                        ActiveMQObjectMessage object = (ActiveMQObjectMessage) session.createObjectMessage();
                        Pdu pdu = queue.dequeue();
                        object.setObject(queue.dequeue());
                        object.setJMSType(HexUtil.toHexString(pdu.getCommandId()));
                        producer.send(object);
                        counter.decrementAndGet();
                    }
                } catch (JMSException e) {
                    if (listener != null) listener.onError(id, e.getMessage());
                }
            }
        } catch (Exception ex) {
            if (listener != null) listener.onError(id, ex.getMessage());
        } finally {
            rateLimiter.acquire();
        }
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
        private Integer instance = 0;

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

        public Builder instance(Integer instance) {
            this.instance = instance;
            return this;
        }

        @Override
        public ActiveMQProducer build() {
            ActiveMQProducer producer = new ActiveMQProducer(this);
            producer.connect();
            return producer;
        }
    }
}
