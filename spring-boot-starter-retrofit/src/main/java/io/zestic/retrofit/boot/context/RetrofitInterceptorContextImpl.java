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

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author deebendukumar
 */
public class RetrofitInterceptorContextImpl extends ConcurrentHashMap<String, Interceptor> implements RetrofitInterceptorContext {
    private static final long serialVersionUID = -5865286831705661141L;

    private static RetrofitInterceptorContextImpl _instance;

    private RetrofitInterceptorContextImpl() {
    }

    public static RetrofitInterceptorContextImpl getInstance() {
        if (_instance == null) {
            _instance = new RetrofitInterceptorContextImpl();
        }
        return _instance;
    }

    @Override
    public Interceptor register(String identity, Interceptor interceptor) {
        return put(identity, interceptor);
    }

    @Override
    public boolean unregister(String identity) {
        return remove(identity) != null;
    }

    @Override
    public Optional<Interceptor> getRetrofit(String identity) {
        return Optional.ofNullable(get(identity));
    }

    @Override
    public boolean hasRetrofit(String identity) {
        return containsKey(identity);
    }

    @Override
    public boolean empty() {
        clear();
        return true;
    }
}
