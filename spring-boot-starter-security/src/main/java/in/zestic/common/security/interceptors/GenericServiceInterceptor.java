package in.zestic.common.security.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GenericServiceInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(GenericServiceInterceptor.class.getSimpleName());

    private final PreHandleInterceptor preHandleInterceptor;
    private final PostHandleInterceptor postHandleInterceptor;
    private final AfterCompletionInterceptor afterCompletionInterceptor;

    public GenericServiceInterceptor(PreHandleInterceptor preHandleInterceptor,
                                     PostHandleInterceptor postHandleInterceptor,
                                     AfterCompletionInterceptor afterCompletionInterceptor) {
        this.preHandleInterceptor = preHandleInterceptor;
        this.postHandleInterceptor = postHandleInterceptor;
        this.afterCompletionInterceptor = afterCompletionInterceptor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        preHandleInterceptor.intercept();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        postHandleInterceptor.intercept();
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception exception) throws Exception {
        afterCompletionInterceptor.intercept();
    }
}
