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

package in.zestic.springboot.common.retrofit.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @author deebendukumar
 */
@Data
public class Connection implements Serializable {

    /**
     * # The maximum number of idle connections for each address.
     */
    private Integer maxIdleConnections = 16;

    /**
     * # The time (minutes) to live for each idle connections
     */
    private Integer keepAliveDuration = 2;

    private Integer retryTimes = 0;
    private Long readTimeout = 10000L;
    private Long writeTimeout = 10000L;
    private Long connectTimeout = 10000L;
}
