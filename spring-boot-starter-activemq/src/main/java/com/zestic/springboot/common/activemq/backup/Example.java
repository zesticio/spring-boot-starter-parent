package com.zestic.springboot.common.activemq.backup;

import com.zestic.common.entity.Message;

public class Example {

    public static void main(String[] args) {
        Channel kumar = new Channel();

        Subscriber deebendu = new Subscriber();
        Subscriber supriya = new Subscriber();

        kumar.subscribe(deebendu);
        kumar.subscribe(supriya);

        kumar.onMessage(new Message());
    }
}
