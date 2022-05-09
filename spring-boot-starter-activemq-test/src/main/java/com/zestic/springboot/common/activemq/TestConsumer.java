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
