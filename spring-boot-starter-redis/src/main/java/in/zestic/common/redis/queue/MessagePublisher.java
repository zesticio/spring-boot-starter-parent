package in.zestic.common.redis.queue;

public interface MessagePublisher {

    void publish(final String message);
}