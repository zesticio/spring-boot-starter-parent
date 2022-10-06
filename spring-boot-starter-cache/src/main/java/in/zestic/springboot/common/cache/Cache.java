package in.zestic.springboot.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

public interface Cache<K, V> extends Closeable {

    Logger logger = LoggerFactory.getLogger(Cache.class);

    /**
     * Before Java 8, interfaces could have only abstract methods. The implementation of these methods has to be
     * provided in a separate class. So, if a new method is to be added in an interface, then its implementation
     * code has to be provided in the class implementing the same interface. To overcome this issue, Java 8 has
     * introduced the concept of default methods which allow the interfaces to have methods with implementation
     * without affecting the classes that implement the interface.
     *
     * @param key
     * @return
     * @throws CacheInvokeException
     */
    default V get(K key) throws CacheInvokeException {
        CacheGetResult<V> result = GET(key);
        if (result.isSuccess()) {
            return result.getValue();
        } else {
            return null;
        }
    }

    CacheGetResult<V> GET(K key);
}