package com.zestic.springboot.common.activemq.model;

import lombok.Getter;

/**
 * JMSPriority—Used to assign a level of importance to a message. This header is
 * also set on the message producer. Once the priority is set on a producer, it
 * applies to all messages sent from that producer. The priority can be overridden
 * for individual messages. JMS defines 10 levels of message priority, ranging from
 * 0 (the lowest) to 9 (the highest):
 */
@Getter
public enum JMSPriority {

    NORMAL(0),
    EXPEDITED(5);

    private final Integer index;

    JMSPriority(int i) {
        this.index = i;
    }
}
