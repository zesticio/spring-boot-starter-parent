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

package in.zestic.springboot.common.activemq;

import in.zestic.springboot.common.exception.ApplicationError;
import lombok.Getter;

/**
 * runtime error code starting from 0x100000
 */
@Getter
public enum ActiveMQError implements ApplicationError {

    RTE_SESSION_NULL(0x100000, "Runtime exception session is null or not running"),
    RTE_PRODUCER_NULL(0x100001, "Runtime exception, producer is null or not connected"),
    RTE_PRODUCER_UNABLE_TO_SUBMIT(0x100002, "Runtime exception, producer is unable to submit message"),
    RTE_UNABLE_ESTABLISH_CONNECTION(0x100003, "Runtime exception, unable to establish connection"),
    RTE_UNABLE_CREATE_PRODUCER(0x100004, "Runtime exception, unable to create producer"),
    RTE_UNABLE_CREATE_CONSUMER(0x100005, "Runtime exception, unable to create producer"),
    RTE_UNABLE_PROCESS_INCOMING_MESSAGE(0x100006, "Runtime exception, unable to process incoming message"),
    RTE_JMS_EXCEPTION(0x100007, "Runtime exception, Java messaging service exception"),
    RTE_UNABLE_CREATE_OBJECT_MESSAGE(0x100008, "Runtime exception, Unable to create object message"),
    RTE_JMS_EXPIRATION(0x100009, "Runtime exception, Unable to set JMS expiration value"),
    RTE_UNABLE_SET_MESSAGE_ID(0x100010, "Runtime exception, Unable to set JMS message id"),
    RTE_UNABLE_ENABLE_MESSAGE_TIMESTAMP(0x100011, "Runtime exception, Unable to enable message timestamp");

    private final Integer code;

    private final String message;

    private ActiveMQError(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }
}
