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
import io.zestic.activemq.model.JMSDeliveryMode;
import io.zestic.core.exception.ApplicationException;
import io.zestic.core.util.ProcessingThread;
import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.ActiveMQSession;
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
public abstract class ActiveMQClient extends ProcessingThread implements ExceptionListener {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQClient.class.getSimpleName());

    protected final Integer _THROUGHPUT = 128;
    protected final Integer _PREFETCH_LIMIT = 512;

    protected ActiveMQConnectionFactory factory = null;
    protected ActiveMQSession session = null;
    protected ActiveMQConnection connection = null;
    protected ActiveMQDestination destination = null;

    protected Listener listener;

    protected String id;
    protected Integer instanceId;

    protected String primaryUri;
    protected String secondaryUri;
    protected String queueName;
    protected String username;
    protected String password;
    protected Integer prefetchLimit = _PREFETCH_LIMIT;
    protected Boolean useTransaction = false;
    protected Integer throughput;
    protected JMSDeliveryMode jmsDeliveryMode = JMSDeliveryMode.PERSISTENT;
    protected Boolean enableJmsExpiration = false;
    protected Long jmsExpiration;
    protected Boolean disableJmsMessageId = false;

    protected ExceptionListener connectionExceptionListener;

    public ActiveMQClient() {
    }

    public void connect() throws ApplicationException {
        String uri = "failover:("
                + primaryUri + ","
                + secondaryUri + ")?randomize=false";
        logger.info("Establishing connection with " + uri);
        factory = new ActiveMQConnectionFactory(username, password, uri);

        /**
         * When an ActiveMQ message producer sends a non-persistent message, its dispatched asynchronously
         * (fire and forget) - but for persistent messages, the publisher will block until it gets a notification that
         * the message has been processed (saved to the store - queued to be dispatched to any active consumers etc) by
         * the broker. messages are dispatched with delivery mode set to be persistent by default (which is required by
         * the JMS spec). So if you are sending messages on a Topic, the publisher will block by default (even if there
         * are no durable subscribers on the topic) until the broker has returned a notification.
         *
         * So if you looking for good performance with topic messages, either set the delivery mode on the publisher to
         * be non-persistent, or set the useAsyncSend property on the ActiveMQ ConnectionFactory to be true.
         */
        factory.setUseAsyncSend(false);

        /**
         * https://activemq.apache.org/what-is-the-prefetch-limit-for.html
         * To limit the maximum number of messages that can be dispatched to consumer at once.
         * The consumer in turn uses the prefetch limit to size its prefetch message buffer it will not dispatch any
         * more messages to that consumer until the consumer acknowledged at least 50% of the prefetched messages.
         *
         * ActiveMQ will push as many messages to the consumer as fast as possible, where they will be queued for
         * processing by an ActiveMQ Session. The maximum number of messages that ActiveMQ will push to a Consumer
         * without the Consumer processing a message is set by the pre-fetch size. You can improve throughput by
         * running ActiveMQ with larger pre-fetch sizes.
         */
        ActiveMQPrefetchPolicy prefetch = new ActiveMQPrefetchPolicy();
        logger.info("Setting up prefetch limit to : " + prefetchLimit);
        prefetch.setQueuePrefetch(prefetchLimit);
        logger.info("Setting up prefetch policy");
        factory.setPrefetchPolicy(prefetch);


        try {
            logger.info("Establishing connection");
            connection = (ActiveMQConnection) factory.createConnection();

            logger.info("Set connection exception listener");
            if (connectionExceptionListener != null)
                connection.setExceptionListener(connectionExceptionListener);

            /**
             * When consuming messages in auto acknowledge mode (set when creating the consumers’ session), ActiveMQ
             * can acknowledge receipt of messages back to the broker in batches (to improve performance). The batch
             * size is 65% of the prefetch limit for the Consumer. Also if message consumption is slow the batch will
             * be sent every 300ms. You switch batch acknowledgment on by setting optimizeAcknowledge=true
             */
            connection.setOptimizeAcknowledge(true);
            /**
             * By default, a Consumer’s session will dispatch messages to the consumer in a separate thread. If you are
             * using Consumers with auto acknowledge, you can increase throughput by passing messages straight through
             * the Session to the Consumer by setting alwaysSessionAsync=false
             */
            connection.setAlwaysSessionAsync(false);

            /**
             * currently we setting it to client acknowledge
             */
            logger.info("Creating a session with acknowledgement to : CLIENT_ACKNOWLEDGE");
            session = (ActiveMQSession) connection.createSession(useTransaction, ActiveMQSession.CLIENT_ACKNOWLEDGE);
            logger.info("Starting the connection");
            connection.start();
        } catch (JMSException ex) {
            if (listener != null) listener.onError(id, ex.getMessage());
            this.close();
        }
    }

    @SneakyThrows
    public void close() {
        logger.error("shutting down the session");
        if (session != null) session.close();
        logger.error("shutting down the connection");
        if (connection != null) connection.close();
        if (listener != null) listener.onClose("");
        if (listener != null) listener.onClose(id);
    }

    @Override
    public void onException(JMSException e) {
        logger.error("Exception ", e);
        throw new ActiveMQRuntimeException(ActiveMQError.RTE_JMS_EXCEPTION, e.getMessage());
    }

    public interface Listener {

        public void onConnect(String id);
        public void onClose(String id);
        public void onError(String id, String description);
    }

}
