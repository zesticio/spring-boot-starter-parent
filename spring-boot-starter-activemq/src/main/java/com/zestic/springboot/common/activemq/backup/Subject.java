package com.zestic.springboot.common.activemq.backup;

import com.zestic.common.entity.Message;

public interface Subject {

    void subscribe(Observer subscriber);

    void unsubscribe(Observer subscriber);

    void notifySubscribers();

    void onMessage(Message message);
}
