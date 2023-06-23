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

import com.google.common.util.concurrent.RateLimiter;
import io.zestic.activemq.ActiveMQClient;
import io.zestic.activemq.ActiveMQProducer;
import io.zestic.core.pdu.Pdu;
import io.zestic.core.queue.Queue;
import io.zestic.core.util.IBuilder;
import io.zestic.core.util.ProcessingThread;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class Producer extends ProcessingThread {

    private final Integer _THROUGHPUT = 128;
    private final Integer _INSTANCE = 1;

    private Integer throughput = _THROUGHPUT;
    private Integer instances = _INSTANCE;
    private String primaryUri;
    private String secondaryUri;
    private String username;
    private String password;
    private String queueName;
    private Listener listener;
    private RateLimiter rateLimiter;
    private Queue queue;

    private RoundRobinRouter router = new RoundRobinRouter();
    private AtomicInteger counter = new AtomicInteger();
    private List<ActiveMQProducer> producers = new ArrayList<>();

    public Producer() {
    }

    public Producer(Builder builder) {
        this.throughput = builder.throughput;
        this.instances = builder.instances;
        this.listener = builder.listener;
        this.primaryUri = builder.primaryUri;
        this.secondaryUri = builder.secondaryUri;
        this.username = builder.username;
        this.password = builder.password;
        this.queueName = builder.queueName;
        this.listener = builder.listener;
    }

    public void init() {
        this.rateLimiter = RateLimiter.create(this.throughput);
        queue = new Queue.Builder().build();
        for (Integer index = 0; index < instances; index++) {
            ActiveMQProducer producer = new ActiveMQProducer.Builder()
                    .primaryUri(primaryUri)
                    .secondaryUri(secondaryUri)
                    .username(username)
                    .password(password)
                    .queueName(queueName)
                    .throughput(throughput / instances)
                    .instance(index)
                    .build();

            producer.setListener(new ActiveMQClient.Listener() {
                @Override
                public void onConnect(String id) {
                    try {
                        producer.start(new ProcessingThread.Subscriber() {
                            @Override
                            public void onStart() {
                                //add to the list
                                producers.add(producer);
                            }

                            @Override
                            public void onStop() {
                                //remove from the list
                            }
                        });
                    } catch (Exception ex) {
                    }
                }

                @Override
                public void onClose(String id) {
                    //remove from the list
                }

                @Override
                public void onError(String id, String description) {
                    listener.onError(id + " " + description);
                }
            });
            producer.connect();
        }
    }

    @Override
    public void process() {
        try {
            if (!queue.isEmpty()) {
                ActiveMQProducer producer = router.selectPeer(producers);
                producer.enqueue(queue.dequeue());
                counter.decrementAndGet();
            }
        } catch (Exception ex) {
            if (listener != null) {
                listener.onError(ex.getMessage());
            }
        } finally {
            this.rateLimiter.acquire();
        }
    }

    public void enqueue(Pdu pdu) {
        queue.enqueue(pdu);
        counter.incrementAndGet();
    }

    public static class Builder implements IBuilder<Producer> {

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
        public Producer build() {

            Producer producer = new Producer(this);
            producer.init();
            try {
                producer.start(new ProcessingThread.Subscriber() {
                    @Override
                    public void onStart() {
                        if (listener != null) listener.onStart();
                    }

                    @Override
                    public void onStop() {
                        if (listener != null) listener.onStop();
                    }
                });
            } catch (InterruptedException ex) {
                if (listener != null) {
                    listener.onError(ex.getMessage());
                }
            }
            return producer;
        }
    }

    public interface Listener {

        public void onStart();
        public void onStop();
        public void onError(String description);
    }
}
