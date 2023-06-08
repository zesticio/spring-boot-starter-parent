/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <Deebendu Kumar>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.zestic.activemq.config;

import io.zestic.activemq.ActiveMQConsumer;
import io.zestic.activemq.ActiveMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ActiveMQProperties.class)
@ComponentScan(basePackages = {"io.zestic.activemq.*"})
public class ActiveMQConfig implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQConfig.class.getSimpleName());

    private final ActiveMQProperties properties;
    private ApplicationContext applicationContext;

    public ActiveMQConfig(ActiveMQProperties properties) {
        this.properties = properties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

//    @Bean
//    public ActiveMQConsumer activeMQConsumer() {
//        logger.info("Creating activemq consumer bean");
//        return new ActiveMQConsumer();
//    }
//
//    @Bean
//    public ActiveMQProducer activeMQProducer() {
//        logger.info("Creating activemq producer bean");
//        return new ActiveMQProducer();
//    }
}
