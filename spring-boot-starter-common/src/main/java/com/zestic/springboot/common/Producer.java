package com.zestic.springboot.common;

import com.zestic.common.entity.Message;
import com.zestic.common.exception.ApplicationRuntimeException;

import java.util.Map;

public interface Producer {

    void submit(Message message) throws ApplicationRuntimeException;
    void submit(Message message, Map<String, Object> optional) throws ApplicationRuntimeException;
}
