package com.zestic.springboot.common.activemq.backup;

import lombok.Data;

@Data
public class Subscriber implements Observer {

    private String name;
    private Channel channel;

    public void update() {
        System.out.println("On consumer update");
    }

    public void subscribe(Channel channel) {
        this.channel = channel;
    }
}
