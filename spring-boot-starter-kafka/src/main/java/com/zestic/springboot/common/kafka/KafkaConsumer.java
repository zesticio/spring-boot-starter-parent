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
import com.zestic.common.entity.Message;
import com.zestic.common.exception.NotImplementedException;
import com.zestic.springboot.common.Consumer;
import com.zestic.springboot.common.kafka.config.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class KafkaConsumer extends Kafka implements Consumer {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(KafkaConsumer.class.getSimpleName());

    org.apache.kafka.clients.consumer.KafkaConsumer<String, Object> consumer = null;

    public KafkaConsumer(KafkaProperties properties) {
        super(properties);
    }

    @Override
    public void create() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.properties.getBootstrapServers());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, this.properties.getConsumer().getGroupId());
        /**
         * none - if no offset is found then do not even start
         * earliest - read from the very beginning of the topic
         * latest - read from latest offset
         */
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(config);
    }

    /**
     * poll for new data
     *
     * @return
     */
    @Override
    public Message receive() throws NotImplementedException {
        throw new NotImplementedException(com.zestic.common.Constants.RTE_METHOD_NOT_IMPL.getCode(), Constants.RTE_METHOD_NOT_IMPL.getMessage());
    }

    @Override
    public void listen() {
        /**
         * poll kafka as many records as possible, if we don't have any data then wait for 100 milliseconds
         */
        ConsumerRecords<String, Object> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, Object> record : records) {
            logger.info(record.key() + " " + record.value());
        }
        records.forEach(K -> System.out.println(K.key() + " " + K.value()));
    }

    private void subscribe() {
        //to listen to multiple topics
        //here topics is an array of topics
        //consumer.subscribe(Arrays.asList(topics));

        //to listen to single topic
        consumer.subscribe(Collections.singleton(this.properties.getConsumer().getTopic()));
    }
}
