package io.zestic.redis;

public interface MessagePublisher {

    void publish(final String message);
}