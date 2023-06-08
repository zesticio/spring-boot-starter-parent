package io.zestic.ratelimit;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Aspect
@Component
public class RateLimitAspect {

    private static final String RATE_LIMIT_PRECONDITION_FAIL = "If you want to use the @RateLimit support you have to configure as last parameter of your method, the current HttpServletRequest parameter";

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RateLimitAspect.class);
    private final ConcurrentHashMap<String, RateLimiter> map = new ConcurrentHashMap<>();

    @Before("@annotation(rateLimit)")
    public void rateLimit(JoinPoint jp, RateLimit rateLimit) throws RateLimitException {
        RateLimiter limiter = map.computeIfAbsent(createKey(jp), createLimiter(rateLimit));
        Boolean acquired = limiter.tryAcquire(0, TimeUnit.SECONDS);
        if (acquired) {
            logger.info("Acquired rate limit permission");
        } else {
            logger.error("Unable to acquire rate limit permission");
            throw new RateLimitException(RateLimitError.RATE_LIMIT_EXCEEDED.getCode(), RateLimitError.RATE_LIMIT_EXCEEDED.getMessage());
        }
    }

    private Function<String, RateLimiter> createLimiter(RateLimit limit) {
        return name -> RateLimiter.create(limit.value());
    }

    private String createKey(JoinPoint jp) {
        Object[] args = jp.getArgs();
        if (args.length <= 0) {
            throw new IllegalArgumentException(RATE_LIMIT_PRECONDITION_FAIL);
        }

        Object param = args[args.length - 1];

        if (param instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) param;

            String ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
            return ipAddress;
        } else {
            throw new IllegalArgumentException(RATE_LIMIT_PRECONDITION_FAIL);
        }
    }
}
