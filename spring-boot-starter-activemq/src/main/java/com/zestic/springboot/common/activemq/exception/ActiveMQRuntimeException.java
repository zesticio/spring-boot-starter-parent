package com.zestic.springboot.common.activemq.exception;

import com.zestic.springboot.common.exception.ApplicationError;
import com.zestic.springboot.common.exception.ApplicationException;

public class ActiveMQRuntimeException extends ApplicationException {

    public ActiveMQRuntimeException(ApplicationError errorType, String message) {
        super(errorType, message);
    }

}
