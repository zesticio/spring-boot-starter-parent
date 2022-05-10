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

import com.zestic.common.utils.Error;

import java.util.HashMap;
import java.util.Map;

public enum Constants {

    RTE_SESSION_NULL(0x100000, "Runtime exception session is null or not running"),
    RTE_PRODUCER_NULL(0x100001, "Runtime exception, producer is null or not connected"),
    RTE_PRODUCER_UNABLE_TO_SUBMIT(0x100002, "Runtime exception, producer is unable to submit message"),
    RTE_UNABLE_ESTABLISH_CONNECTION(0x100003, "Runtime exception, unable to establish connection"),
    RTE_UNABLE_CREATE_PRODUCER(0x100004, "Runtime exception, unable to create producer"),
    RTE_UNABLE_CREATE_CONSUMER(0x100005, "Runtime exception, unable to create producer"),
    RTE_UNABLE_PROCESS_INCOMING_MESSAGE(0x100006, "Runtime exception, unable to process incoming message");

    private static final Map<Integer, Error> LOOKUP = new HashMap<Integer, Error>();

    static {
        for (final Error enumeration : Error.values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final Integer code;

    private final String message;

    private Constants(final Integer code, final String message) {
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
