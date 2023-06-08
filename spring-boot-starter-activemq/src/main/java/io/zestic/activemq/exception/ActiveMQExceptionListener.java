package io.zestic.activemq.exception;

import io.zestic.activemq.ActiveMQClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;

public class ActiveMQExceptionListener implements ExceptionListener {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQClient.class.getSimpleName());

    private ExceptionListener exceptionListener = null;

    public ActiveMQExceptionListener() {
    }

    public ActiveMQExceptionListener(Connection connection, ExceptionListener exceptionListener) {
        super();
        this.exceptionListener = exceptionListener;
    }

    @Override
    public void onException(JMSException e) {
        if (exceptionListener != null) {
            exceptionListener.onException(e);
        }
    }
}
