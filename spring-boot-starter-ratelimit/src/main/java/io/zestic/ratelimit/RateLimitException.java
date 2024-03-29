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

package io.zestic.ratelimit;

public class RateLimitException extends Exception {

    private static final long serialVersionUID = -1771088537167544111L;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RateLimitException.class);

    private Integer code;

    public RateLimitException(Integer code, String message) {
        super(message);
        this.code = code;
        logger.error(message);
    }
}
