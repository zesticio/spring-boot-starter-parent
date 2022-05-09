package com.zestic.springboot.common.activemq.config;

import com.zestic.springboot.common.activemq.model.JMSDeliveryMode;
import com.zestic.springboot.common.activemq.model.JMSPriority;
import lombok.Data;

@Data
public class ProducerProperties {

    private String queueName;
    private JMSDeliveryMode jmsDeliveryMode = JMSDeliveryMode.PERSISTENT;
    private Boolean enableJmsExpiration = false;
    private Long jmsExpiration;
    private Boolean autogenerateJmsMessageId = false;
    private Boolean disableJmsMessageId = false;
    private JMSPriority jmsPriority = JMSPriority.NORMAL;
    private Boolean disableMessageTimestamp = false;
}
