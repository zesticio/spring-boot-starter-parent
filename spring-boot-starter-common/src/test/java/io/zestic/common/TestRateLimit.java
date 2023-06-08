package io.zestic.common;

import io.zestic.ratelimit.RateLimit;

public class TestRateLimit {

    /**
     * this mean it will handle 1 request per second
     * @ApiIgnore HttpServletRequest request
     */
    @RateLimit(value = 1)
    public void testRateLimit() {

    }
}
