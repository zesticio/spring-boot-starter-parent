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
import io.zestic.core.io.txt.FileReader;
import io.zestic.core.util.ProcessingThread;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;
import java.io.File;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Consumer.class);

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

    @Value("${spring.activemq.instances}")
    private Integer instances;

    @Value("${spring.activemq.throughput}")
    private Integer throughput;

    @Value("${spring.trigger.path}")
    private String path;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws JMSException {

        Producer producer = new Producer.Builder()
                .primaryUri(primaryUri)
                .secondaryUri(secondaryUri)
                .username(username)
                .password(password)
                .queueName(queueName)
                .instances(instances)
                .throughput(throughput)
                .build();
        try {
            //If both classes are in the same package, the protected method can be called.
            //producer.create();
            producer.start(new ProcessingThread.Subscriber() {

                @Override
                public void onStart() {
                    System.out.println("producer initialized");
                }

                @Override
                public void onStop() {
                    System.out.println("producer stopped");
                }
            });
        } catch (InterruptedException e) {
            System.err.println("producer stopped");
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
