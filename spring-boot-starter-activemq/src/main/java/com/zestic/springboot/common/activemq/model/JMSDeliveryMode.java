package com.zestic.springboot.common.activemq.model;

import com.zestic.common.utils.Error;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum JMSDeliveryMode {

    PERSISTENT(1),
    NON_PERSISTENT(2);

    private final Integer index;

    JMSDeliveryMode(int i) {
        this.index = i;
    }
}
