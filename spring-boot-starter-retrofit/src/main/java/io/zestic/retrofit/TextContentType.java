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

package io.zestic.retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author deebendukumar
 */
public enum TextContentType {

    TEXT_CSS("text/css", "text/css"),
    TEXT_CSV("text/csv", "text/csv"),
    TEXT_HTML("text/html", "text/html"),
    TEXT_JAVASCRIPT("text/javascript", "text/javascript"),
    TEXT_PLAIN("text/plain", "text/plain"),
    TEXT_XML("text/xml", "text/xml");

    private static final Map<String, TextContentType> LOOKUP = new HashMap<>();

    static {
        for (final TextContentType enumeration : TextContentType.values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final String code;
    private final String message;

    private TextContentType(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
