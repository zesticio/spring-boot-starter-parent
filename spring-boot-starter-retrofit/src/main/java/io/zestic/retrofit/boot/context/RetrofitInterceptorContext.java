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

package io.zestic.retrofit.boot.context;

import okhttp3.Interceptor;
import retrofit2.Retrofit;

import java.util.Optional;

/**
 * The k v store for retrofit instance, because the retrofit instance is immutable,
 * and we couldn't get some useful identify from it's public method.
 * <p>
 * In order to support multiply base url endpoint instance, we must create and store them separately.
 */
/**
 * @author deebendukumar
 */
public interface RetrofitInterceptorContext {

    /**
     * Register the given interceptor to specified identity,if the context already hold the given identity,
     * we would return the old interceptor instance
     */
    Interceptor register(String identity, Interceptor interceptor);

    /**
     * remove the given retrofit from context
     *
     * @return true for succeed in remove, false for the given retrofit identity doesn't existed
     */
    boolean unregister(String identity);

    Optional<Interceptor> getRetrofit(String identity);

    boolean hasRetrofit(String identity);

    boolean empty();
}
