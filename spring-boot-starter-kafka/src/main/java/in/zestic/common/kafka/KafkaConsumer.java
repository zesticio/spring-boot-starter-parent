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

package in.zestic.common.kafka;

import in.zestic.common.entity.Message;
import in.zestic.common.exception.GenericError;
import in.zestic.common.exception.NotImplementedException;
import in.zestic.common.kafka.config.KafkaProperties;
import in.zestic.common.mq.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Consumer group are able to restart where they have last left off if the off set is correctly committed
 * If multiple consumer joins the same group then kafka will do a re-joining of group and it will allocated
 * partitions to each consumer and then set the offset from where it has to read the data
 * <p>
 * every time a new consumer joins the same group, other consumers will do a re-joining and partitions will be
 * allocated fresh
 */
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
         * RangeAssignor - assign partition on a per-topic basis (can lead to imbalance)
         * RoundRobin - assign partition across all topics in round robin fashion, optimal balance
         * StickyAssignor - balanced like RoundRobin and then minimises partition movements when consumer / join / leave the
         * group in order to minimize movements
         * CooperativeStickyAssignor - rebalance strategy is identical to StickyAssignor but supports cooperative rebalances and therefore
         * consumer can keep on consuming from the topic
         * The default assignor is [RangeAssignot, CooperativeStickyAssignor] which will be use the RangeAssignot by default, but allows
         * upgrading to the CooperativeStickyAssignor with just a single rolling bounce that removes the RangeAssignor from the list
         *
         *
         * in Kafka connect - cooperative rebalance is already implemented by default
         * in Kafka Streams - turned on by default using StreamsPartitionAssignor
         */
        //config.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, this.properties.getConsumer().getGroupId());
        //config.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CooperativeStickyAssignor.class.getName());
        //config.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, CooperativeStickyAssignor.class.getName());

        //auto.commit.interval.ms = 5000 and enable.auto.commit=true
        //config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        //config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, CooperativeStickyAssignor.class.getName());

        /**
         * none - if no offset is found then do not even start
         * earliest - read from the very beginning of the topic
         * latest - read from latest offset
         */
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        //upon leaving the consumer has up to session.timeout.ms to join back and get back its partition
        //else they will be re-assigned without triggering a rebalance
        //config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "earliest");

        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(config);
    }

    /**
     * poll for new data
     *
     * @return
     */
    @Override
    public Message receive() throws NotImplementedException {
        throw new NotImplementedException(GenericError.RTE_METHOD_NOT_IMPL);
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

    @Override
    public void close() throws NotImplementedException {
        /**
         //below code will generate an exception in listen
         final Thread thread = Thread.currentThread();
         Runtime.getRuntime().addShutdownHook(new Thread() {

         public void run() {
         logger.info("Detected a shutdown, lets exit by calling consumer,wakeup()...");
         consumer.wakeup();

         //join the main thread to allow the execution of the code in the main thread
         try {
         thread.join();
         } catch (InterruptedException ex) {
         logger.error("", ex);
         }
         }
         });**/
        consumer.close();
    }

    private void subscribe() {
        //to listen to multiple topics
        //here topics is an array of topics
        //consumer.subscribe(Arrays.asList(topics));

        //to listen to single topic
        consumer.subscribe(Collections.singleton(this.properties.getConsumer().getTopic()));
    }

    private void test(Optional<String> code) {

    }
}
