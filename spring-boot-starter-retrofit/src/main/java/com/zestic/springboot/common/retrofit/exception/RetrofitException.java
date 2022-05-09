/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <deebendu.kumar@zestic.in>
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

package com.zestic.springboot.common.retrofit.exception;

import com.zestic.springboot.common.retrofit.utils.RetrofitUtils;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author deebendukumar
 */
public class RetrofitException extends RuntimeException {


    public RetrofitException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrofitException(String message) {
        super(message);
    }

    public static RetrofitException errorStatus(Request request, Response response) {
        String msg = String.format("invalid Response! request=%s, response=%s", request, response);
        try {
            String responseBody = RetrofitUtils.readResponseBody(response);
            if (StringUtils.hasText(responseBody)) {
                msg += ", body=" + responseBody;
            }
        } catch (ReadResponseBodyException e) {
            throw new RetrofitException(String.format("read ResponseBody error! request=%s, response=%s", request, response), e);
        } finally {
            response.close();
        }
        return new RetrofitException(msg);
    }

    public static RetrofitException errorExecuting(Request request, IOException cause) {
        return new RetrofitIOException(cause.getMessage() + ", request=" + request, cause);
    }

    public static RetrofitException errorUnknown(Request request, Exception cause) {
        if (cause instanceof RetrofitException) {
            return (RetrofitException) cause;
        }
        return new RetrofitException(cause.getMessage() + ", request=" + request, cause);
    }
}
