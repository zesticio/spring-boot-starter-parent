package com.zestic.springboot.common.retrofit.annotation;

import com.zestic.springboot.common.retrofit.core.DefaultErrorDecoder;
import com.zestic.springboot.common.retrofit.core.ErrorDecoder;
import com.zestic.springboot.common.retrofit.interceptor.LogLevel;
import com.zestic.springboot.common.retrofit.interceptor.LogStrategy;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RetrofitClient {

    /**
     * An absolute URL (the protocol is necessary).
     * Can be specified as property key, eg: ${propertyKey}.
     * If baseUrl is not configured, you must configure serviceId and path optional configuration.
     *
     * @return baseUrl
     */
    String baseUrl() default "";

    /**
     * The name of the service.
     * Can be specified as property key, eg: ${propertyKey}.
     */
    String serviceId() default "";

    /**
     * Path prefix to be used by all method-level mappings.
     */
    String path() default "";

    /**
     * Converter factory for the current interface, higher priority than global converter factory.
     * The converter instance is first obtained from the Spring container. If it is not obtained, it is created by reflection.
     */
    Class<? extends Converter.Factory>[] converterFactories() default {};

    /**
     * callAdapter factory for the current interface, higher priority than global callAdapter factory.
     * The converter instance is first obtained from the Spring container. If it is not obtained, it is created by reflection.
     */
    Class<? extends CallAdapter.Factory>[] callAdapterFactories() default {};

    /**
     * Fallback class for the specified retrofit client interface. The fallback class must
     * implement the interface annotated by this annotation and be a valid spring bean.
     */
    Class<?> fallback() default void.class;


    /**
     * Define a fallback factory for the specified Feign client interface. The fallback
     * factory must produce instances of fallback classes that implement the interface
     * annotated by {@link RetrofitClient}.The fallback factory must be a valid spring bean.
     * bean.
     */
    Class<?> fallbackFactory() default void.class;


    /**
     * 针对当前接口是否启用日志打印
     * Whether to enable log printing for the current interface
     *
     * @return enableLog
     */
    boolean enableLog() default true;

    LogLevel logLevel() default LogLevel.NULL;

    LogStrategy logStrategy() default LogStrategy.NULL;

    /**
     * When calling {@link Retrofit#create(Class)} on the resulting {@link Retrofit} instance, eagerly validate the
     * configuration of all methods in the supplied interface.
     *
     * @return validateEagerly
     */
    boolean validateEagerly() default false;

    Class<? extends ErrorDecoder> errorDecoder() default DefaultErrorDecoder.class;

    /**
     * connection pool name
     *
     * @return connection pool name
     */
    String poolName() default "default";

    /**
     * Sets the default connect timeout for new connections. A value of 0 means no timeout,
     * otherwise values must be between 1 and Integer.MAX_VALUE when converted to milliseconds.
     * If it is configured as -1, the global default configuration is used.
     *
     * @return connectTimeoutMs
     */
    int connectTimeoutMs() default -1;

    /**
     * Sets the default read timeout for new connections. A value of 0 means no timeout,
     * otherwise values must be between 1 and Integer.MAX_VALUE when converted to milliseconds.
     * If it is configured as -1, the global default configuration is used.
     *
     * @return readTimeoutMs
     */
    int readTimeoutMs() default -1;

    /**
     * Sets the default write timeout for new connections. A value of 0 means no timeout,
     * otherwise values must be between 1 and Integer.MAX_VALUE when converted to milliseconds.
     * If it is configured as -1, the global default configuration is used.
     *
     * @return writeTimeoutMs
     */
    int writeTimeoutMs() default -1;


    /**
     * Sets the default timeout for complete calls. A value of 0 means no timeout,
     * otherwise values must be between 1 and Integer.MAX_VALUE when converted to milliseconds.
     *
     * @return callTimeout
     */
    int callTimeoutMs() default -1;

    /**
     * Sets the interval between HTTP/2 and web socket pings initiated by this client.
     * Use this to automatically send ping frames until either the connection fails or it is closed.
     * This keeps the connection alive and may detect connectivity failures.
     *
     * @return pingInterval
     */
    int pingIntervalMs() default 0;


    /**
     * Configure this client to allow protocol redirects from HTTPS to HTTP and from HTTP to HTTPS.
     * Redirects are still first restricted by followRedirects. Defaults to true.
     *
     * @return followSslRedirects
     */
    boolean followSslRedirects() default true;

    /**
     * Configure this client to follow redirects. If unset, redirects will be followed.
     *
     * @return followRedirects
     */
    boolean followRedirects() default true;

    /**
     * Configure this client to retry or not when a connectivity problem is encountered.
     * By default, this client silently recovers from the following problems:
     *
     * @return retryOnConnectionFailure
     */
    boolean retryOnConnectionFailure() default true;

}
