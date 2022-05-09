package com.zestic.springboot.common.activemq;

import com.zestic.common.entity.Message;
import lombok.Data;

@Data
public class TestMessage extends Message {

    private String payload;
}
