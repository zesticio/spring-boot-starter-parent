package io.zestic.activemq.test;

import io.zestic.core.queue.Queue;
import io.zestic.core.queue.QueueBuilder;
import io.zestic.core.util.ProcessingThread;

public class TestQueue {

    public static class Producer extends ProcessingThread {

        private final Queue queue;

        public Producer(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void process() {

        }
    }

    public static void main(String[] args) {
        Queue queue = new QueueBuilder().build();
    }
}
