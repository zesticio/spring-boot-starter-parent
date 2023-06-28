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

import com.google.common.io.Files;
import io.zestic.activemq.ActiveMQConsumer;
import io.zestic.activemq.ActiveMQProducer;
import io.zestic.activemq.Consumer;
import io.zestic.activemq.ExceptionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;
import java.io.File;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private ActiveMQProducer producer;
    @Autowired
    private ActiveMQConsumer consumer;

    @Value("${spring.trigger.path}")
    private String path;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws JMSException {
        try {
            producer.setExceptionListener(new ExceptionListener() {
                @Override
                public void onException(Exception ex) {
                    logger.error("", ex);
                }
            });

            producer.connect();
            producer.start();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }

    private void process() {
        for (File file : Files.fileTraverser().breadthFirst(new File(path))) {
            if (file.isFile()) {
                System.out.println(file);
            }
        }
//        new FileReader.Builder()
//                .path(path + File.separator + "msisdn.txt")
//                .subscriber(new FileReader.Subscriber() {
//
//                    @Override
//                    public void onNext(String line) {
//                        System.out.println(line);
//                    }
//
//                    @Override
//                    public void onError(String description) {
//                        System.out.println(description);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        System.out.println("complete");
//                    }
//                }).build();
    }
}
