/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <Deebendu Kumar>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.zestic.activemq.config;

import io.zestic.activemq.model.JMSDeliveryMode;
import io.zestic.activemq.model.JMSPriority;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@PropertySource("classpath:activemq.properties")
@ConfigurationProperties(prefix = "spring.activemq")
public class ActiveMQProperties {

    private String primary = "tcp://127.0.0.1:61616";
    private String secondary = "tcp://127.0.0.1:61616";
    private String username = "admin";
    private String password = "admin";

    private Integer instance = 1;
    private String queueName;
    private Integer prefetchLimit = 128;
    private Integer throughput = 128;
    private JMSDeliveryMode jmsDeliveryMode = JMSDeliveryMode.PERSISTENT;
    private Boolean enableJmsExpiration = false;
    private Long jmsExpiration = 1000L;
    private Boolean autogenerateJmsMessageId = false;
    private Boolean disableJmsMessageId = false;
    private JMSPriority jmsPriority = JMSPriority.NORMAL;
    private Boolean disableMessageTimestamp = false;
    private Boolean useTransaction = false;
}
