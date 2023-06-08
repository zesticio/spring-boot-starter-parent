package io.zestic.retrofit.annotation;

import io.zestic.retrofit.interceptor.EnvEnum;
import io.zestic.retrofit.interceptor.BasePathMatchInterceptor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EnumInterceptor extends BasePathMatchInterceptor {

    private EnvEnum envEnum;

    public void setEnvEnum(EnvEnum envEnum) {
        this.envEnum = envEnum;
    }

    /**
     * do intercept
     *
     * @param chain interceptor chain
     * @return http Response
     * @throws IOException IOException
     */
    @Override
    protected Response doIntercept(Interceptor.Chain chain) throws IOException {

        Request request = chain.request();

        System.out.println(envEnum);

        return chain.proceed(request);
    }
}
