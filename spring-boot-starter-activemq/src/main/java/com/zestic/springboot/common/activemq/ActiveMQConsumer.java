package com.zestic.springboot.common.activemq;

import com.zestic.common.entity.Message;
import com.zestic.common.exception.ApplicationException;
import com.zestic.common.exception.ApplicationRuntimeException;
import com.zestic.springboot.common.Consumer;
import com.zestic.springboot.common.activemq.config.ActiveMQProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.JMSException;

/**
 * Acquire a JMS destination
 * Create a JMS consumer
 * Create a JMS consumer
 * Optionally register a JMS message listener
 */
@Getter
@Setter
public class ActiveMQConsumer extends ActiveMQ implements Consumer {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ActiveMQConsumer.class);

    private ActiveMQMessageConsumer consumer = null;

    public ActiveMQConsumer(ActiveMQProperties properties) {
        super(properties);
    }

    /**
     * The JMS API also provides the ability to asynchronously receive messages. The JMS
     * provider will push messages to the consumer.
     *
     * @throws ApplicationRuntimeException
     */
    public void create() throws ApplicationRuntimeException {
        super.create();
        try {
            destination = (ActiveMQQueue) session.createQueue(this.properties.getConsumer().getQueueName());
            consumer = (ActiveMQMessageConsumer) session.createConsumer(destination);
            consumer.start();
        } catch (JMSException e) {
            throw new ApplicationRuntimeException(new ApplicationException(
                    Constants.RTE_UNABLE_CREATE_CONSUMER.getCode(),
                    Constants.RTE_UNABLE_CREATE_CONSUMER.getMessage()));
        }
    }

    @SneakyThrows
    public void close() {
        consumer.close();
        super.close();
    }

    @SneakyThrows
    public Message receive() {
        Message message = null;
        javax.jms.Message jmsMessage = null;
        try {
            if (session.isRunning() && consumer.getMessageSize() >= 1) {
                jmsMessage = consumer.receive(5);
            }
        } catch (JMSException ex) {
            logger.error("", ex);
        } finally {
            if (jmsMessage != null) {
                jmsMessage.acknowledge();
            }
        }
        return message;
    }
}
