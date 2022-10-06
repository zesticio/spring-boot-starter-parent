package in.zestic.springboot.common.activemq.exception;

import in.zestic.springboot.common.exception.ApplicationError;
import in.zestic.springboot.common.exception.ApplicationException;

public class ActiveMQRuntimeException extends ApplicationException {

    public ActiveMQRuntimeException(ApplicationError errorType, String message) {
        super(errorType, message);
    }

}
