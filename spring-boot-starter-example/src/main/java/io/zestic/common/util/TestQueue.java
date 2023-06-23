package io.zestic.common.util;

import io.zestic.core.pdu.EnquireLinkRequest;
import io.zestic.core.pdu.Pdu;
import io.zestic.core.queue.Queue;
import io.zestic.core.util.ProcessingThread;

public class TestQueue {

    public static void main(String[] args) {
        Queue queue = new Queue.Builder().build();
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        try {
            producer.start(new ProcessingThread.Subscriber() {
                @Override
                public void onStart() {
                }

                @Override
                public void onStop() {
                }
            });
            consumer.start(new ProcessingThread.Subscriber() {
                @Override
                public void onStart() {
                }

                @Override
                public void onStop() {
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class Producer extends ProcessingThread {

        private final Queue queue;

        public Producer(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void process() {
            EnquireLinkRequest request = new EnquireLinkRequest();
            queue.enqueue(request);
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Consumer extends ProcessingThread {

        private final Queue queue;

        public Consumer(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void process() {
            if (!queue.isEmpty()) {
                Pdu pdu = queue.dequeue();
                System.out.println(pdu.toString());
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
