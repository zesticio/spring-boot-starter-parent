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

package io.zestic.kafka;

import io.zestic.core.exception.GenericError;
import io.zestic.core.exception.NotImplementedException;
import io.zestic.kafka.config.KafkaProperties;

public class Kafka {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Kafka.class.getSimpleName());

    protected KafkaProperties properties = null;

    public Kafka(KafkaProperties properties) {
        this.properties = properties;
    }

    public void create() throws NotImplementedException {
        throw new NotImplementedException(GenericError.RTE_METHOD_NOT_IMPL);
    }

    public void close() throws NotImplementedException {
        throw new NotImplementedException(GenericError.RTE_METHOD_NOT_IMPL);
    }
}
