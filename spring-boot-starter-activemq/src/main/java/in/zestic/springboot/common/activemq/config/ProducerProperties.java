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

package in.zestic.springboot.common.activemq.config;

import in.zestic.springboot.common.activemq.model.JMSDeliveryMode;
import in.zestic.springboot.common.activemq.model.JMSPriority;
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
    private Integer throughput = 128;
}
