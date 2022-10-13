package in.zestic.common;

import in.zestic.common.ratelimit.RateLimit;

public class TestRateLimit {

    /**
     * this mean it will handle 1 request per second
     * @ApiIgnore HttpServletRequest request
     */
    @RateLimit(value = 1)
    public void testRateLimit() {

    }
}
