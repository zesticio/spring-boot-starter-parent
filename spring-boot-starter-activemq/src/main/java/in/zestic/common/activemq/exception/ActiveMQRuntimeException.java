package in.zestic.common.activemq.exception;

import in.zestic.common.exception.ApplicationError;
import in.zestic.common.exception.ApplicationException;

public class ActiveMQRuntimeException extends ApplicationException {

    public ActiveMQRuntimeException(ApplicationError errorType, String message) {
        super(errorType, message);
    }

}
