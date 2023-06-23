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

import io.zestic.activemq.exception.ActiveMQRuntimeException;
import io.zestic.core.exception.ApplicationException;
import io.zestic.core.pdu.Pdu;
import io.zestic.core.util.IBuilder;
import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;

/**
 * Acquire a JMS destination
 * Create a JMS consumer
 * Create a JMS consumer
 * Optionally register a JMS message listener
 */
public class ActiveMQConsumer extends ActiveMQClient {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQConsumer.class.getSimpleName());

    private ActiveMQMessageConsumer consumer = null;

    public ActiveMQConsumer() {
    }

    public ActiveMQConsumer(Builder builder) {
        primaryUri = builder.primaryUri;
        secondaryUri = builder.secondaryUri;
        username = builder.username;
        password = builder.password;
        queueName = builder.queueName;
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
            destination = (ActiveMQQueue) session.createQueue(queueName);
            consumer = (ActiveMQMessageConsumer) session.createConsumer(destination);
            consumer.start();
        } catch (JMSException e) {
            throw new ActiveMQRuntimeException(ActiveMQError.RTE_UNABLE_CREATE_CONSUMER, e.getMessage());
        }
    }

    @SneakyThrows
    public void close() {
        consumer.close();
        super.close();
    }

    @SneakyThrows
    protected Pdu receive() {
        Pdu pdu = null;
        javax.jms.Message message = null;
        try {
            if (session.isRunning() && consumer.getMessageSize() >= 1) {
                message = consumer.receive(5);
            }
        } catch (JMSException ex) {
            logger.error("", ex);
        } finally {
            if (message != null) {
                message.acknowledge();
            }
        }
        return pdu;
    }

    @Override
    public void process() {
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
