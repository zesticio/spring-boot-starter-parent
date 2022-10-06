package in.zestic.springboot.common.retrofit.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.IOException;

public abstract class BasePathMatchInterceptor implements PrototypeInterceptor {

    private String[] include;

    private String[] exclude;

    private PathMatcher pathMatcher = new AntPathMatcher();


    public void setInclude(String[] include) {
        this.include = include;
    }

    public void setExclude(String[] exclude) {
        this.exclude = exclude;
    }

    @Override
    public final Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        String path = request.url().encodedPath();

        if (isMatch(exclude, path)) {
            return chain.proceed(request);
        }

        if (!isMatch(include, path)) {
            return chain.proceed(request);
        }
        return doIntercept(chain);
    }

    protected abstract Response doIntercept(Interceptor.Chain chain) throws IOException;

    private boolean isMatch(String[] patterns, String path) {
        if (patterns == null || patterns.length == 0) {
            return false;
        }
        for (String pattern : patterns) {
            boolean match = pathMatcher.match(pattern, path);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
