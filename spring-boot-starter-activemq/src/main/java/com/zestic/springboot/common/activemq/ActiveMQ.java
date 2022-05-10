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

package com.zestic.springboot.common.activemq;

import com.zestic.common.exception.ApplicationException;
import com.zestic.common.exception.ApplicationRuntimeException;
import com.zestic.common.throttling.ThrottleImpl;
import com.zestic.springboot.common.Client;
import com.zestic.springboot.common.activemq.config.ActiveMQProperties;
import lombok.SneakyThrows;
import org.apache.activemq.*;
import org.apache.activemq.command.ActiveMQDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

/**
 * 1 Acquire a JMS connection factory
 * 2 Create a JMS connection using the connection factory
 * 3 Start the JMS connection
 * 4 Create a JMS session from the connection
 */
public abstract class ActiveMQ implements Client, ExceptionListener {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQ.class.getSimpleName());

    protected ActiveMQProperties properties = null;
    protected ActiveMQConnectionFactory factory = null;
    protected ActiveMQSession session = null;
    protected ActiveMQConnection connection = null;
    protected ActiveMQDestination destination = null;

    protected ExceptionListener connectionExceptionListener;

    public ActiveMQ(ActiveMQProperties properties) {
        this.properties = properties;
    }

    public void create() throws ApplicationRuntimeException {
        String uri = "failover:("
                + properties.getPrimary() + ","
                + properties.getSecondary() + ")?randomize=false";
        factory = new ActiveMQConnectionFactory(properties.getUsername(), properties.getPassword(), uri);
        factory.setUseAsyncSend(true);

        /**
         * https://activemq.apache.org/what-is-the-prefetch-limit-for.html
         * To limit the maximum number of messages that can be dispatched to consumer at once.
         * The consumer in turn uses the prefetch limit to size its prefetch message buffer it will not dispatch any more messages
         * to that consumer until the consumer acknowledged at least 50% of the prefetched messages.
         */
        ActiveMQPrefetchPolicy prefetch = new ActiveMQPrefetchPolicy();
        prefetch.setQueuePrefetch(properties.getConsumer().getPrefetchLimit());

        factory.setPrefetchPolicy(prefetch);
        try {
            connection = (ActiveMQConnection) factory.createConnection();
            connection.setExceptionListener(connectionExceptionListener);
            connection.setOptimizeAcknowledge(true);
            connection.setOptimizedMessageDispatch(true);
            /**
             * currently we setting it to client acknowledge
             */
            session = (ActiveMQSession) connection.createSession(properties.getUseTransaction(), ActiveMQSession.CLIENT_ACKNOWLEDGE);
            connection.start();
        } catch (JMSException ex) {
            throw new ApplicationRuntimeException(new ApplicationException(Constants.RTE_UNABLE_ESTABLISH_CONNECTION.getCode(), Constants.RTE_UNABLE_ESTABLISH_CONNECTION.getMessage()));
        }
    }

    @SneakyThrows
    public void close() {
        session.close();
        connection.close();
    }

    @Override
    public void onException(JMSException e) {
        logger.error("Exception ", e);
    }
}
