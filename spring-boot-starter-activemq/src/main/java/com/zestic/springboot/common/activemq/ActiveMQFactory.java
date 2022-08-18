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

package com.zestic.springboot.common.activemq;

import com.zestic.springboot.common.mq.Broker;
import com.zestic.springboot.common.activemq.config.ActiveMQProperties;
import com.zestic.springboot.common.mq.Consumer;
import com.zestic.springboot.common.mq.Producer;

public class ActiveMQFactory implements Broker {

    private ActiveMQProperties properties;

    public ActiveMQFactory(ActiveMQProperties properties) {
        this.properties = properties;
    }

    @Override
    public Consumer createConsumer() {
        return new ActiveMQConsumer(this.properties);
    }

    @Override
    public Producer createProducer() {
        return new ActiveMQProducer(this.properties);
    }
}