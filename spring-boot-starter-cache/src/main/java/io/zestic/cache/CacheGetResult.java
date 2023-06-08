package io.zestic.cache;

import java.util.concurrent.*;

public class CacheGetResult<V> extends CacheResult {

    private volatile V value;
    private volatile CacheValueHolder<V> holder;

    public CacheGetResult(CacheResultCode resultCode, String message, CacheValueHolder<V> holder) {
        super(CompletableFuture.completedFuture(new ResultData(resultCode, message, holder)));
    }

    public V getValue() {
        waitForResult();
        return value;
    }

    protected CacheValueHolder<V> getHolder() {
        waitForResult();
        return holder;
    }
}
