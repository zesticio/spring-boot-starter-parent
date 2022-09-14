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

package com.zestic.springboot.common.jwt;

import lombok.Getter;

import java.util.Optional;

@Getter
public class JwtDecodeResult {

    private final Optional<String> payload;
    private final Optional<JwtHeader> header;
    private final Optional<JwtException> exception;

    JwtDecodeResult(String payload, JwtHeader header) {
        this.payload = Optional.of(payload);
        this.header = Optional.of(header);
        this.exception = Optional.empty();
    }

    JwtDecodeResult(JwtException exception) {
        this.payload = Optional.empty();
        this.header = Optional.empty();
        this.exception = Optional.of(exception);
    }
}
