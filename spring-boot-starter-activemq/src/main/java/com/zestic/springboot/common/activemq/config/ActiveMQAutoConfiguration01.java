/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <deebendu.kumar@zestic.in>
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

package com.zestic.springboot.common.activemq.config;

import com.zestic.springboot.common.activemq.ActiveMQConsumer;
import com.zestic.springboot.common.activemq.ActiveMQProducer;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ActiveMQProperties.class)
public class ActiveMQAutoConfiguration01 implements ApplicationContextAware {

    private final ActiveMQProperties properties;
    private ApplicationContext applicationContext;

    public ActiveMQAutoConfiguration01(ActiveMQProperties properties) {
        this.properties = properties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ActiveMQConsumer activeMQConsumer() {
        return new ActiveMQConsumer(properties);
    }

    @Bean
    public ActiveMQProducer activeMQProducer() {
        return new ActiveMQProducer(properties);
    }
}
