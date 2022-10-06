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

package in.zestic.springboot.common.ratelimit;

import in.zestic.springboot.common.util.HTTPErrorCodes;

import java.util.HashMap;
import java.util.Map;

/**
 * runtime error code starting from 0x800000
 */
public enum RateLimitError {

    ROK(0x00000000, "Success"),
    RATE_LIMIT_EXCEEDED(0x00000058, "Limit Exceeded, Too many request");

    private static final Map<Integer, HTTPErrorCodes> LOOKUP = new HashMap<>();

    static {
        for (final HTTPErrorCodes enumeration : HTTPErrorCodes.values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final Integer code;

    private final String message;

    private RateLimitError(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
