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

package in.zestic.common.activemq;

import com.google.common.util.concurrent.RateLimiter;
import in.zestic.common.util.ProcessingThread;
import org.springframework.stereotype.Service;

@Service
public class TestConsumer extends ProcessingThread {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestConsumer.class);

    private ActiveMQConsumer consumer;
    private final Integer THROUGHPUT = 2048;
    private RateLimiter rateLimiter;

    public TestConsumer(ActiveMQConsumer consumer) {
        this.consumer = consumer;
        this.rateLimiter = RateLimiter.create(THROUGHPUT);
        this.consumer.create();
    }

    @Override
    public void process() {
        rateLimiter.acquire();
    }
}