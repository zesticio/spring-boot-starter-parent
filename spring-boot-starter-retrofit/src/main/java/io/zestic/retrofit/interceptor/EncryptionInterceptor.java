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

package io.zestic.retrofit.interceptor;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author deebendukumar
 */
@Component
public class EncryptionInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(EncryptionInterceptor.class.getSimpleName());

    public EncryptionInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody raw = request.body();
        String encrypted = "";
        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");

        /**
         * do your encryption implementation here
         * Implement Observer and Observable pattern
         */

        RequestBody body = RequestBody.create(mediaType, encrypted);
        request = request.newBuilder().header("Content-Type", body.contentType().toString())
                .header("Content-Length", String.valueOf(body.contentLength()))
                .method(request.method(), body).build();
        return chain.proceed(request);
    }
}
