package io.zestic.retrofit.http;

import io.zestic.core.entity.Result;
import io.zestic.retrofit.annotation.EnumIntercept;
import io.zestic.retrofit.annotation.Intercept;
import io.zestic.retrofit.annotation.RetrofitClient;
import io.zestic.retrofit.interceptor.EnvEnum;
import io.zestic.retrofit.interceptor.TimeStampInterceptor;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

import java.util.HashMap;
import java.util.Map;

@RetrofitClient(baseUrl = "${test.baseUrl}")
//@Intercept(handler = TimeStampInterceptor.class, include = "/a/b", exclude = "/c/d")
@Intercept(handler = TimeStampInterceptor.class)
@EnumIntercept(envEnum = EnvEnum.test)
public interface TestRetrofitClient {

    @GET("person")
    Result<HashMap<Object, Object>> getPerson(@Query("id") Long id);

    @GET("testMap")
    Map<String, Map<String, String>> testMap();

    @HTTP(method = "get", path = "/getPersonBody", hasBody = true)
    Result<HashMap<Object, Object>> getPersonBody(@Body HashMap<Object, Object> person);
}
