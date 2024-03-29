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
public enum JSONContentType {

    APPLICATION_JSON("application/json", "Application JSON"),
    APPLICATION_EDI_X12("application/EDI-X12", "Application EDI-X12"),
    APPLICATION_EDIFACT("application/EDIFACT", "Application EDIFACT"),
    APPLICATION_JAVA_SCRIPT("application/javascript", "Application JavaScript"),
    APPLICATION_OCTET_STREAM("application/octet-stream", "Application Octet Stream"),
    APPLICATION_OGG("application/ogg", "Application Ogg"),
    APPLICATION_PDF("application/pdf", "Application Pdf"),
    APPLICATION_XHTML_XML("application/xhtml+xml", "Application XHTML+XML"),
    APPLICATION_X_SHOCKWAVE_FLASH("application/x-shockwave-flash", "Application x-shockwave-flash"),
    APPLICATION_LD_JSON("application/ld+json", "Application ld+json"),
    APPLICATION_XML("application/xml", "Application xml"),
    APPLICATION_ZIP("application/zip", "Application Zip"),
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded", "Application x-www-form-urlencoded");

    private static final Map<String, JSONContentType> LOOKUP = new HashMap<>();

    static {
        for (final JSONContentType enumeration : JSONContentType.values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final String code;
    private final String message;

    private JSONContentType(final String code, final String message) {
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
