package io.zestic.activemq.test;

import io.zestic.activemq.ActiveMQProducer;
import io.zestic.core.router.IRouter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinRouter implements IRouter<ActiveMQProducer> {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public ActiveMQProducer selectPeer(List<ActiveMQProducer> list) {

        int peerSize = list != null ? list.size() : 0;

        // Return none if empty, or first if only one member found
        if (peerSize <= 0) {
            return null;
        }

        // if there is only one producer then return
        if (peerSize == 1) {
            return list.iterator().next();
        }
        if (counter.get() >= Integer.MAX_VALUE) {
            counter.set(0);
        }
        return list.get(counter.getAndIncrement() % peerSize);
    }
}
