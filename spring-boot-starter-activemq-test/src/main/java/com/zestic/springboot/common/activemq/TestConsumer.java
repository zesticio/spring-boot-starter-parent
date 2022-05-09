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

package com.zestic.springboot.common.activemq;

import com.google.common.util.concurrent.RateLimiter;
import com.zestic.common.entity.Message;
import com.zestic.common.throttling.Counter;
import com.zestic.common.throttling.ThrottleImpl;
import com.zestic.common.utils.ProcessingThread;
import org.springframework.stereotype.Service;

@Service
public class TestConsumer extends ProcessingThread {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ThrottleImpl.class);

    private ActiveMQConsumer consumer;
    private Counter counter = null;
    private ThrottleImpl throttle = null;
    private final Integer THROUGHPUT = 2048;
    private RateLimiter rateLimiter;

    public TestConsumer(ActiveMQConsumer consumer) {
        this.consumer = consumer;
        this.counter = new Counter();
        this.throttle = new ThrottleImpl("Consumer", THROUGHPUT, counter);
        this.rateLimiter = RateLimiter.create(THROUGHPUT);
        this.consumer.create();
        this.throttle.start();
    }

    @Override
    public void process() {
        if (counter.get() <= THROUGHPUT) {
            Message message = consumer.receive();
            if (message != null) {
                counter.increment();
            }
        }
        rateLimiter.acquire();
    }
}
