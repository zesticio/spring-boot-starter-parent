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

import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TestProducer extends ProcessingThread {

    private ActiveMQProducer producer;
    private Counter counter = null;
    private ThrottleImpl throttle = null;
    private final Integer THROUGHPUT = 2048;
    private ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
    private RateLimiter rateLimiter;

    public TestProducer(ActiveMQProducer producer) {
        this.producer = producer;
        this.counter = new Counter();
        this.throttle = new ThrottleImpl("Producer", THROUGHPUT, counter);
        this.rateLimiter = RateLimiter.create(THROUGHPUT);

        this.producer.create();
        this.throttle.start();
    }

    @Override
    public void process() {
        if (counter.get() <= THROUGHPUT) {
            TestMessage message = new TestMessage();
            message.setPayload("This is a test message");
            Message messageq1 = message;
            messageq1.setMessageId("");
            producer.submit(messageq1);
            counter.increment();
        }
        rateLimiter.acquire();
    }

    public synchronized int size() throws InterruptedException {
        try {
            return queue.size();
        } catch (Exception ex) {
            throw new InterruptedException("unable to lock for read");
        } finally {
            Thread.yield();
        }
    }

    public boolean isEmpty() throws InterruptedException {
        try {
            return queue.isEmpty();
        } catch (Exception ex) {
            throw new InterruptedException("unable to lock for read");
        } finally {
            Thread.yield();
        }
    }

    public Object dequeue() throws InterruptedException {
        try {
            Object first = null;
            if (size() > 0) {
                first = queue.poll();
            }
            return first;
        } catch (InterruptedException ex) {
            throw new InterruptedException("unable to lock for read");
        } finally {
            Thread.yield();
        }
    }

    public void enqueue(Object obj) throws IndexOutOfBoundsException, InterruptedException {
        try {
            queue.add(obj);
        } catch (Exception ex) {
            throw new InterruptedException("unable to lock for write");
        } finally {
            Thread.yield();
        }
    }

    public void remove(Object obj) throws InterruptedException {
        try {
            queue.remove(obj);
        } catch (Exception ex) {
            throw new InterruptedException("unable to lock for write");
        } finally {
            Thread.yield();
        }
    }
}
