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

package com.zestic.springboot.common.retrofit.boot;

import com.zestic.common.utils.ClassInspectionUtil;
import com.zestic.springboot.common.retrofit.annotation.HttpInterceptor;
import com.zestic.springboot.common.retrofit.interceptor.EncryptionInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Scan for a given annotation using reflection API
 */

/**
 * @author deebendukumar
 */
public class AnnotationScanner {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationScanner.class.getSimpleName());

    static AnnotationScanner getInstance() {
        return new AnnotationScanner();
    }

    private AnnotationScanner() {
    }

    /**
     * using reflection library we can scans the provided class path for all classes at the runtime
     *
     * @param packageName
     * @param annotationClass
     */
    public void scan(String packageName, Class<? extends Annotation> annotationClass) {
        Collection<Class<?>> classes = ClassInspectionUtil.findAnnotatedClasses(HttpInterceptor.class, packageName);
    }
}
