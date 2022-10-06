package com.zestic.springboot.common;

import com.zestic.springboot.common.handlers.TimingDynamicInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class TestTimingDynamicInvocationHandler {

    public static void main(String[] args) {
        Map mapProxyInstance = (Map) Proxy.newProxyInstance(
                TestTimingDynamicInvocationHandler.class.getClassLoader(), new Class[]{Map.class},
                new TimingDynamicInvocationHandler(new HashMap<>()));
        for (int index = 0; index <= 100; index++)
            mapProxyInstance.put("hello", "world");
    }
}
