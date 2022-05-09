package com.zestic.springboot.common.activemq.backup;

public interface Observer {

    void update();
    void subscribe(Channel channel);
}
