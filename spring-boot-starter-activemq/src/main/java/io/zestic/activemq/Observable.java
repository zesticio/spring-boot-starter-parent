package io.zestic.activemq;

import io.zestic.core.pdu.Pdu;

public interface Observable {

    void register(Observer obj);
    void unregister(Observer obj);
    void notify(Pdu pdu);
}
