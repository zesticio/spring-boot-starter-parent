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

package com.zestic.springboot.common.retrofit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author deebendukumar
 */
@Data
@ConfigurationProperties(prefix = "retrofit")
public class RetrofitProperties implements Serializable {

    private Integer timeout = 5000;
    @NestedConfigurationProperty
    private Connection connection;
    @NestedConfigurationProperty
    private Log logging;
    @NestedConfigurationProperty
    private List<Endpoint> endpoints = new ArrayList<>();
    @NestedConfigurationProperty
    private Endpoint endpoint;
}
