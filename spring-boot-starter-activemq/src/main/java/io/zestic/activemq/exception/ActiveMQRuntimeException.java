package io.zestic.activemq.exception;

import io.zestic.core.exception.ApplicationError;
import io.zestic.core.exception.ApplicationException;

public class ActiveMQRuntimeException extends ApplicationException {

    public ActiveMQRuntimeException(ApplicationError errorType, String message) {
        super(errorType, message);
    }

}
