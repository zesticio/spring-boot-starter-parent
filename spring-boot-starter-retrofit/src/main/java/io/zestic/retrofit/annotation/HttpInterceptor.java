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

package io.zestic.retrofit.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author deebendukumar
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//@Repeatable(value = HttpInterceptors.class)
public @interface HttpInterceptor {

    /**
     * Defines the name of the service bean when registered to the underlying context. If left unspecified
     * the name of the service bean is generated using {@link org.springframework.beans.factory.annotation.Qualifier},
     * If no Qualifier annotation, we would use full class name instead.
     *
     * @return the name of the bean.
     */
    String name() default "";

    /**
     * Alias for the {@link #value()} attribute. Allows for more concise annotation
     * declarations e.g.: {@code @RetrofitService("ai")} instead of
     * {@code @RetrofitService(retrofit="ai")}.
     *
     * @return the specified retrofit instance to build endpoint
     */
    String value() default "";

}
