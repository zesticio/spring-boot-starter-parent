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
