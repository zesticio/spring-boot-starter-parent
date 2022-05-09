package com.zestic.springboot.common.activemq.backup;

import com.zestic.common.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class Channel implements Subject {

    private String title;
    private List<Observer> list = new ArrayList<>();

    @Override
    public void subscribe(Observer subscriber) {
        list.add(subscriber);
    }

    @Override
    public void unsubscribe(Observer subscriber) {
        list.remove(subscriber);
    }

    @Override
    public void notifySubscribers() {
        for (Observer subscriber : list) {
            subscriber.update();
        }
    }

    @Override
    public void onMessage(Message message) {
        notifySubscribers();
    }
}
