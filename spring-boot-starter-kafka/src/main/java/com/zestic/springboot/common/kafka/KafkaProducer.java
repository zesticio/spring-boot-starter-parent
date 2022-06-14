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

package com.zestic.springboot.common.kafka;

import com.zestic.common.entity.Message;
import com.zestic.common.exception.ApplicationRuntimeException;
import com.zestic.common.exception.NotImplementedException;
import com.zestic.springboot.common.kafka.config.KafkaProperties;
import com.zestic.springboot.common.mq.Producer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.springframework.util.SerializationUtils;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducer extends Kafka implements Producer {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(KafkaProducer.class.getSimpleName());

    org.apache.kafka.clients.producer.KafkaProducer<String, Object> producer = null;

    public KafkaProducer(KafkaProperties properties) {
        super(properties);
    }

    @Override
    public void create() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.properties.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        //prop.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, "");
        config.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 1 * 1024 * 1024);
        this.producer = new org.apache.kafka.clients.producer.KafkaProducer<>(config);
    }

    @Override
    public void submit(Message message) throws ApplicationRuntimeException {
        this.producer.send(
                createProducerRecord(this.properties.getProducer().getTopic(),
                        SerializationUtils.serialize(message)), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        // executes every time a record is successfully sent or an exception is thrown
                        if (e == null) {
                            logger.info("Message successfully sent, received meta data");
                            logger.info("Topic " + recordMetadata.topic());
                            logger.info("Topic " + recordMetadata.partition());
                            if (recordMetadata.hasOffset())
                                logger.info("Topic " + recordMetadata.offset());
                            if (recordMetadata.hasTimestamp())
                                logger.info("Topic " + recordMetadata.timestamp());
                        }
                    }
                });
    }

    @Override
    public void submit(Message message, Map<String, Object> optional) throws ApplicationRuntimeException {
    }

    @Override
    public void close() throws NotImplementedException {
        this.producer.close();
    }

    /**
     * its a synchronous call
     * @throws ApplicationRuntimeException
     */
    @Override
    public void flush() throws ApplicationRuntimeException {
        this.producer.flush();
    }

    private ProducerRecord<String, Object> createProducerRecord(String topic, byte[] data) {
        return new ProducerRecord<>(topic, data);
    }

    /**
     * all messages that share the same key goes to the same partition
     * @param topic
     * @param key
     * @param data
     * @return
     */
    private ProducerRecord<String, Object> createProducerRecord(String topic, String key, byte[] data) {
        return new ProducerRecord<>(topic, key, data);
    }

}
