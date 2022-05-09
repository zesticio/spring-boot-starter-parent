package com.zestic.springboot.common.activemq;

import com.zestic.common.entity.Message;
import com.zestic.common.exception.ApplicationException;
import com.zestic.common.exception.ApplicationRuntimeException;
import com.zestic.springboot.common.Producer;
import com.zestic.springboot.common.activemq.config.ActiveMQProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.MessageId;

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
public class ActiveMQProducer extends ActiveMQ implements Producer {

    private ActiveMQMessageProducer producer = null;
    private ActiveMQObjectMessage object = null;

    public ActiveMQProducer(ActiveMQProperties properties) {
        super(properties);
    }

    public void create() throws ApplicationRuntimeException {
        super.create();
        try {
            destination = (ActiveMQQueue) session.createQueue(this.properties.getConsumer().getQueueName());
            producer = (ActiveMQMessageProducer) session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } catch (JMSException e) {
            throw new ApplicationRuntimeException(new ApplicationException(
                    Constants.RTE_UNABLE_CREATE_PRODUCER.getCode(),
                    Constants.RTE_UNABLE_CREATE_PRODUCER.getMessage()));
        }
    }

    @SneakyThrows
    public void close() {
        producer.close();
        super.close();
    }

    @Override
    public void submit(Message message, Map<String, Object> optional) throws ApplicationRuntimeException {
    }

    @Override
    public void submit(Message message) throws ApplicationRuntimeException {
        if (session != null && session.isRunning()) {
            try {
                object = (ActiveMQObjectMessage) session.createObjectMessage();
                object.setJMSDeliveryMode(properties.getProducer().getJmsDeliveryMode().getIndex());
                if (message.getMessageId() != null && !message.getMessageId().isEmpty()) {
                    object.setMessageId(new MessageId(message.getMessageId()));
                }
//                if(jmsProperties != null && !jmsProperties.isEmpty()) {
//                    object.setStringProperty();
//                }
            } catch (JMSException e) {
                throw new ApplicationRuntimeException(new ApplicationException(
                        Constants.RTE_SESSION_NULL.getCode(),
                        Constants.RTE_SESSION_NULL.getMessage()));
            }
            if (producer != null) {
                try {
                    //The time that a message will expire, into milliseconds
                    if (properties.getProducer().getEnableJmsExpiration()) {
                        producer.setTimeToLive(properties.getProducer().getJmsExpiration());
                    }
                    if (properties.getProducer().getDisableJmsMessageId()) {
                        producer.setDisableMessageID(true);
                    }
                    if (properties.getProducer().getDisableMessageTimestamp()) {
                        producer.setDisableMessageTimestamp(properties.getProducer().getDisableMessageTimestamp());
                    }
                    producer.send(object);
                    if (session.getTransacted()) {
                        session.commit();
                    }
                } catch (JMSException e) {
                    throw new ApplicationRuntimeException(new ApplicationException(
                            Constants.RTE_PRODUCER_UNABLE_TO_SUBMIT.getCode(),
                            Constants.RTE_PRODUCER_UNABLE_TO_SUBMIT.getMessage()));
                }
            } else {
                throw new ApplicationRuntimeException(new ApplicationException(
                        Constants.RTE_PRODUCER_NULL.getCode(),
                        Constants.RTE_PRODUCER_NULL.getMessage()));
            }
        }
    }
}
