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

package io.zestic.activemq;

import com.google.common.util.concurrent.RateLimiter;
import io.zestic.core.pdu.Pdu;
import io.zestic.core.util.IBuilder;
import io.zestic.core.util.ProcessingThread;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Consumer extends ProcessingThread implements Observable {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Consumer.class);

    private final Integer THROUGHPUT = 2048;
    private RateLimiter rateLimiter;
    private String primaryUri;
    private String secondaryUri;
    private String username;
    private String password;
    private String queueName;
    private Integer throughput;
    private Integer instances;
    private Listener listener;

    private List<Observer> observers;

    public Consumer() {
    }

    public Consumer(Builder builder) {
        this.primaryUri = builder.primaryUri;
        this.secondaryUri = builder.secondaryUri;
        this.username = builder.username;
        this.password = builder.password;
        this.queueName = builder.queueName;
        this.throughput = builder.throughput;
        this.instances = builder.instances;
        this.listener = builder.listener;
    }

    public void register(Observer obj) {

    }

    public void unregister(Observer obj) {

    }

    public void notify(Pdu pdu) {

    }

    public void init() {
        this.rateLimiter = RateLimiter.create(THROUGHPUT);
    }

    @Override
    public void process() {
        rateLimiter.acquire();
    }

    public static class Builder implements IBuilder<Consumer> {

        private String primaryUri;
        private String secondaryUri;
        private String username;
        private String password;
        private String queueName;
        private Integer throughput;
        private Integer instances;
        private Listener listener;

        public Builder primaryUri(String primaryUri) {
            this.primaryUri = primaryUri;
            return this;
        }

        public Builder secondaryUri(String secondaryUri) {
            this.secondaryUri = secondaryUri;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder queueName(String queueName) {
            this.queueName = queueName;
            return this;
        }

        public Builder throughput(Integer throughput) {
            this.throughput = throughput;
            return this;
        }

        public Builder instances(Integer instances) {
            this.instances = instances;
            return this;
        }

        public Builder listener(Listener listener) {
            this.listener = listener;
            return this;
        }

        @Override
        public Consumer build() {
            Consumer consumer = new Consumer();
            return consumer;
        }
    }

    public interface Listener {

        public void onStart();
        public void onStop();
        public void onError(String description);
    }
}
