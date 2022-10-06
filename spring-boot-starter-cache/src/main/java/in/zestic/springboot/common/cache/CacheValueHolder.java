package in.zestic.springboot.common.cache;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public final class CacheValueHolder<V> implements Serializable {

    private static final long serialVersionUID = -7973743507831565203L;
    private V value;
    private long expireTime;
    private long accessTime;

    public CacheValueHolder(V value, long expireAfterWrite) {
        this.value = value;
        this.accessTime = System.currentTimeMillis();
        this.expireTime = accessTime + expireAfterWrite;
    }
}
