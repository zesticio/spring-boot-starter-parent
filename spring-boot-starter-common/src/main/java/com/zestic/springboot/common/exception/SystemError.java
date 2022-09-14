package com.zestic.springboot.common.exception;

import lombok.Getter;

@Getter
public enum SystemError implements ApplicationError {

    SYSTEM_ERROR(1, "System exception");
//    SYSTEM_BUSY("000001", "The system is busy, please try again later"),
//
//    GATEWAY_NOT_FOUND_SERVICE("010404", "Service not found"),
//    GATEWAY_ERROR("010500", "Gateway exception"),
//    GATEWAY_CONNECT_TIME_OUT("010002", "Gateway timed out"),
//
//    ARGUMENT_NOT_VALID("020000", "Request parameter verification failed"),
//    INVALID_TOKEN("020001", "Invalid token"),
//    UPLOAD_FILE_SIZE_LIMIT("020010", "Upload file size exceeds limit"),
//
//    DUPLICATE_PRIMARY_KEY("030000", "Unique key conflict");

    private Integer code;
    private String message;

    SystemError(Integer code, String mesg) {
        this.code = code;
        this.message = mesg;
    }
}
