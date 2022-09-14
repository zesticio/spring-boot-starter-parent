package com.zestic.springboot.common.exception;

public interface ApplicationError {

    Integer getCode();

    String getMessage();
}
