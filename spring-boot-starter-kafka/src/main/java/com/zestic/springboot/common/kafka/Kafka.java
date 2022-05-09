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
package com.zestic.springboot.common.kafka;

import com.zestic.common.Constants;
import com.zestic.common.exception.NotImplementedException;
import com.zestic.common.throttling.ThrottleImpl;
import com.zestic.springboot.common.Client;
import com.zestic.springboot.common.kafka.config.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Kafka implements Client {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Kafka.class.getSimpleName());

    protected KafkaProperties properties = null;

    public Kafka(KafkaProperties properties) {
        this.properties = properties;
    }

    @Override
    public void create() throws NotImplementedException {
        throw new NotImplementedException(Constants.RTE_METHOD_NOT_IMPL.getCode(), Constants.RTE_METHOD_NOT_IMPL.getMessage());
    }

    @Override
    public void close() throws NotImplementedException {
        throw new NotImplementedException(Constants.RTE_METHOD_NOT_IMPL.getCode(), Constants.RTE_METHOD_NOT_IMPL.getMessage());
    }
}
