/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <deebendu.kumar@zestic.in>
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

package com.zestic.springboot.common.kafka.config;

import lombok.Data;

@Data
public class ConsumerProperties {

    /**
     * if you set this value it will make the consumer a static member of the group
     * after disconnect if it joins back it will not be assigned a new "member id"
     *
     * upon leaving the consumer has up to session.timeout.ms to join back and get back its partition
     * else they will be re-assigned without triggering a rebalance
     */
    private Integer groupInstanceId;
    private String groupId = "default_group";
    private String topic = "demo";
    private Integer sessionTimeoutMs;
    /**
     * Offset will be commited when we call poll and if auto.commit.interval.ms has elapsed
     */
    private Integer autoCommitIntervalMs = 100000;
}
