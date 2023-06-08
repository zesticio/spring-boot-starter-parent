package io.zestic.web.client.handler;

import io.zestic.web.client.config.WebClientProperties;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class WebClientInvocationHandler implements InvocationHandler {

    private final Object source;
    private Object fallback;

    public WebClientInvocationHandler(Object source, Object fallback, WebClientProperties properties) {
        this.source = source;
        this.fallback = fallback;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(source, args);
        } catch (Throwable e) {
            Throwable cause = e.getCause();
            throw cause;
        }
    }
}
