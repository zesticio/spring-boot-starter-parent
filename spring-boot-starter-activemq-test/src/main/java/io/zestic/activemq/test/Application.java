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

package io.zestic.activemq.test;

import io.zestic.activemq.ActiveMQConsumer;
import io.zestic.activemq.ActiveMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestConsumer.class);

    @Value("${spring.activemq.primary}")
    private String primaryUri;

    @Value("${spring.activemq.secondary}")
    private String secondaryUri;

    @Value("${spring.activemq.username}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.queue-name}")
    private String queueName;

    @Value("${spring.activemq.prefetch-limit}")
    private String prefetchLimit;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws JMSException {
        try {
            ActiveMQProducer producer = new ActiveMQProducer.Builder()
                    .primaryUri(primaryUri)
                    .secondaryUri(secondaryUri)
                    .username(username)
                    .password(password)
                    .queueName(queueName)
                    .build();
            //If both classes are in the same package, the protected method can be called.
            //producer.create();
            producer.start();

            ActiveMQConsumer consumer = new ActiveMQConsumer.Builder()
                    .primaryUri(primaryUri)
                    .secondaryUri(secondaryUri)
                    .username(username)
                    .password(password)
                    .queueName(queueName)
                    .build();
            consumer.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
