package in.zestic.springboot.common.web.client.config;

import in.zestic.springboot.common.web.client.annotation.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.lang.reflect.*;

public class WebClientFactoryBean<T> implements FactoryBean<T>, EnvironmentAware, ApplicationContextAware {

    private final static Logger logger = LoggerFactory.getLogger(WebClientFactoryBean.class);

    private Class<T> webClientInterface;
    private Environment environment;
    private WebClientProperties properties;
    private WebClientConfigBean webClientConfigBean;
    private ApplicationContext applicationContext;
    private WebClient webClient;

    public WebClientFactoryBean(Class<T> retrofitInterface) {
        this.webClientInterface = retrofitInterface;
        webClient = retrofitInterface.getAnnotation(WebClient.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        T source = null;
        Object fallback = null;
        // proxy
        return (T) Proxy.newProxyInstance(webClientInterface.getClassLoader(),
                new Class<?>[]{webClientInterface},
                new WebClientInvocationHandler(source, fallback, properties)
        );
    }

    @Override
    public Class<T> getObjectType() {
        return this.webClientInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.webClientConfigBean = applicationContext.getBean(WebClientConfigBean.class);
        this.properties = webClientConfigBean.getProperties();
    }
}
