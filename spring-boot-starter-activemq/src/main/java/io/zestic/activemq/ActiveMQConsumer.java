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
import io.zestic.core.util.IBuilder;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Objects;

/**
 * Acquire a JMS destination
 * Create a JMS consumer
 * Create a JMS consumer
 * Optionally register a JMS message listener
 */
public class ActiveMQConsumer extends ActiveMQClient implements MessageListener{

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQConsumer.class.getSimpleName());

    private ExceptionListener listener;
    private ActiveMQMessageConsumer consumer;
    private Queue queue;
    private RateLimiter rateLimiter;

    private Integer max;

    public ActiveMQConsumer() {
    }

    public ActiveMQConsumer(Builder builder) {
        this.primaryUri = builder.primaryUri;
        this.secondaryUri = builder.secondaryUri;
        this.username = builder.username;
        this.password = builder.password;
        this.queueName = builder.queueName;
        this.throughput = builder.throughput;
        max = throughput * 10;
    }

    public void setExceptionListener(ExceptionListener listener) {
        Objects.requireNonNull(listener, "listener cannot be null");
        this.listener = listener;
    }

    /**
     * The JMS API also provides the ability to asynchronously receive messages. The JMS
     * provider will push messages to the consumer.
     *
     * @throws ApplicationException
     */
    public void connect() throws ApplicationException {
        try {
            super.connect();
            queue = new Queue.Builder().build();
            rateLimiter = RateLimiter.create(throughput);
            logger.info("creating destination");
            destination = (ActiveMQQueue) session.createQueue(queueName);
            logger.info("creating consumer");
            consumer = (ActiveMQMessageConsumer) session.createConsumer(destination);
            logger.info("setting up message listener");
            consumer.setMessageListener(this);
            logger.info("starting consumer");
            consumer.start();
        } catch (JMSException ex) {
            if (listener != null) listener.onException(ex);
        }
    }

    public void close() {
        try {
            if (consumer != null) consumer.close();
            super.close();
        } catch (JMSException e) {
            if (listener != null) listener.onException(e);
        }
    }

    @Override
    public void process() {
        while (queue.isEmpty()){
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        rateLimiter.acquire();
    }

    @Override
    public void onMessage(Message message) {
        while (queue.size()>= max) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ex) {
                if (listener != null) listener.onException(ex);
            }
        }
        if (message != null) {
            try {
                logger.info(message.getJMSMessageID());
                message.acknowledge();
            } catch (JMSException ex) {
                if (listener != null) listener.onException(ex);
            }
        }
    }

    public static class Builder implements IBuilder<ActiveMQConsumer> {
        private String primaryUri;
        private String secondaryUri;
        private String queueName;
        private String username;
        private String password;
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

        public Builder throughput(Integer throughput) {
            this.throughput = throughput;
            return this;
        }

        @Override
        public ActiveMQConsumer build() {
            ActiveMQConsumer consumer = new ActiveMQConsumer(this);
            consumer.connect();
            return consumer;
        }
    }
}
