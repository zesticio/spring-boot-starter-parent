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

package com.zestic.springboot.common;

import com.zestic.common.entity.Message;
import com.zestic.common.exception.ApplicationRuntimeException;

import java.util.Map;

public interface Producer {

    void submit(Message message) throws ApplicationRuntimeException;
    void submit(Message message, Map<String, Object> optional) throws ApplicationRuntimeException;
    void flush() throws ApplicationRuntimeException;
}
