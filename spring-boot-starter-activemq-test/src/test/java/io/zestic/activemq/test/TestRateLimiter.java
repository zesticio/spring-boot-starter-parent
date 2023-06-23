package io.zestic.activemq.test;

import com.google.common.util.concurrent.RateLimiter;
import io.zestic.core.util.DateUtil;

import java.util.concurrent.TimeUnit;

public class TestRateLimiter {

    public static void smoothBursty() {
        RateLimiter r = RateLimiter.create(5);
        Integer count = 0;
        while (count <= 100) {
            System.out.println("get 1 tokens: " + r.acquire() + DateUtil.dateStringFromLocalDate());
        }
    }

    public static void smoothWarmingUp() {
        RateLimiter r = RateLimiter.create(2, 3, TimeUnit.SECONDS);
        Integer count = 0;
        while (count <= 100) {
            System.out.println("get 1 tokens: " + r.acquire(1) + DateUtil.dateStringFromLocalDate());
        }
    }

    public static void main(String[] args) {
        smoothWarmingUp();
    }
}
