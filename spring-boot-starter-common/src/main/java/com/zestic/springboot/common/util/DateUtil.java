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

package com.zestic.springboot.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static Timestamp timestampFromString(String value) {
        Timestamp timestamp = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
            Date parsedDate = dateFormat.parse(value);
            timestamp = new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public static String dateStringFromLocalDate() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        return dt.format(format);
    }

    public static String dateStringFromLocalDate(Date date) {
        String formatted;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN);
        formatted = formatter.format(date);
        return formatted;
    }
}
