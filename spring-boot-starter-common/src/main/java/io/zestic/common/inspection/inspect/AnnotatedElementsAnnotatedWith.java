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

package io.zestic.common.inspection.inspect;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.zestic.common.inspection.ClassAnnotationMetadata;
import io.zestic.common.inspection.ClassInspector;
import io.zestic.common.inspection.InspectionHelper;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import org.slf4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static com.google.common.collect.Collections2.transform;

public class AnnotatedElementsAnnotatedWith implements ClassInspector<ClassAnnotationMetadata> {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AnnotatedElementsAnnotatedWith.class);

    private Class<? extends Annotation> _annotation;

    private Map<String, InternalClassMetadata> _internalMatches = Maps.newHashMap();

    private Collection<ClassAnnotationMetadata> _matches;

    public AnnotatedElementsAnnotatedWith(Class<? extends Annotation> annotation) {
        _annotation = annotation;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void inspect(ClassFile type, InspectionHelper helper) {
        logger.trace("Checking to see if class " + type.getName() + " matches criteria [" + toString() + "]");
        {
            InternalClassMetadata classMetadata = _internalMatches.get(type.getName());
            if (classMetadata != null) {
                logger.warn("Class " + type.getName() + " was already preocessed!");
                return;
            }
        }
        try {
            if (isAllowedOn(_annotation, ElementType.TYPE)) {
                if (isAnnotationPresent(type, _annotation)) {
                    storeAndGetClassMetadata(helper, type, true);
                }
            }
            if (isAllowedOn(_annotation, ElementType.FIELD)) {
                for (FieldInfo field : (List<FieldInfo>) type.getFields()) {
                    if (isAnnotationPresent(field, _annotation)) {
                        InternalClassMetadata classMetadata = storeAndGetClassMetadata(helper, type, false);
                        classMetadata.annotatedFields.add(classMetadata.clazz.getField(field.getName()));
                    }
                }
            }
            if (isAllowedOn(_annotation, ElementType.METHOD)) {
                for (MethodInfo method : (List<MethodInfo>) type.getMethods()) {
                    if (isAnnotationPresent(method, _annotation)) {
                        InternalClassMetadata classMetadata = storeAndGetClassMetadata(helper, type, false);
                        for (Method m : classMetadata.clazz.getMethods()) {
                            if (m.getName().equals(method.getName()) && m.isAnnotationPresent(_annotation)) {
                                classMetadata.annotatedMethods.add(m);
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException t) {
            logger.warn("Could not load class '" + type.getName() + "'.", t);

        } catch (NoSuchFieldException t) {
            logger.warn("Could not load field of class '" + type.getName() + "'.", t);
        }
    }

    private boolean isAllowedOn(Class<? extends Annotation> a, ElementType type) {
        Target target = a.getAnnotation(Target.class);
        if (target == null || Arrays.binarySearch(target.value(), type) >= 0) {
            return true;
        }
        return false;
    }

    private InternalClassMetadata storeAndGetClassMetadata(InspectionHelper helper, ClassFile type, boolean isClassAnnotated) throws ClassNotFoundException {
        InternalClassMetadata classMetadata = _internalMatches.get(type.getName());
        if (classMetadata == null) {
            Class<?> clazz = helper.loadClass(type);
            classMetadata = new InternalClassMetadata(clazz, isClassAnnotated);
            _internalMatches.put(type.getName(), classMetadata);
        }
        return classMetadata;
    }

    private static boolean isAnnotationPresent(ClassFile type, Class<? extends Annotation> annotation) {
        AnnotationsAttribute visible = (AnnotationsAttribute) type.getAttribute(AnnotationsAttribute.visibleTag);
        return isAnnotationPresent(annotation, visible);
    }

    private static boolean isAnnotationPresent(FieldInfo type, Class<? extends Annotation> annotation) {
        AnnotationsAttribute visible = (AnnotationsAttribute) type.getAttribute(AnnotationsAttribute.visibleTag);
        return isAnnotationPresent(annotation, visible);
    }

    private static boolean isAnnotationPresent(MethodInfo type, Class<? extends Annotation> annotation) {
        AnnotationsAttribute visible = (AnnotationsAttribute) type.getAttribute(AnnotationsAttribute.visibleTag);
        return isAnnotationPresent(annotation, visible);
    }

    private static boolean isAnnotationPresent(Class<? extends Annotation> annotation, AnnotationsAttribute visible) {
        if (visible == null) {
            return false;
        }
        for (javassist.bytecode.annotation.Annotation ann : visible.getAnnotations()) {
            // System.out.println("@" + ann.getTypeName());
            if (ann.getTypeName().equals(annotation.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<ClassAnnotationMetadata> getElements() {
        if (_matches == null) {
            _matches = Lists.newArrayList(transform(_internalMatches.values(), InternalClassMetadata.ToClassAnnotationMetadata.INSTANCE));
        }
        return _matches;
    }

    @Override
    public String toString() {
        return "elements (classes, fields and methods annotated with @" + _annotation.getSimpleName();
    }

    private static class InternalClassMetadata {
        final Class<?> clazz;

        final boolean isClassAnnotated;

        final Set<Field> annotatedFields = Sets.newHashSet();

        final Set<Method> annotatedMethods = Sets.newHashSet();

        InternalClassMetadata(Class<?> clazz, boolean isClassAnnotated) {
            this.clazz = clazz;
            this.isClassAnnotated = isClassAnnotated;
        }

        @Override
        public String toString() {
            return "ClassMetadata [clazz=" + clazz + ", isClassAnnotated=" + isClassAnnotated + ", annotatedFields=" + annotatedFields + ", annotatedMethods=" + annotatedMethods + "]";
        }

        enum ToClassAnnotationMetadata implements Function<InternalClassMetadata, ClassAnnotationMetadata> {
            INSTANCE;

            @Override
            public ClassAnnotationMetadata apply(InternalClassMetadata input) {
                return new ClassAnnotationMetadataImpl(input.clazz, input.isClassAnnotated, input.annotatedFields, input.annotatedMethods);
            }

        }
    }

    private static class ClassAnnotationMetadataImpl implements ClassAnnotationMetadata {
        private final Class<?> _clazz;

        private final boolean _isClassAnnotated;

        private final Collection<Field> _annotatedFields;

        private final Collection<Method> _annotatedMethods;

        ClassAnnotationMetadataImpl(Class<?> clazz, boolean isClassAnnotated, Collection<Field> annotatedFields, Collection<Method> annotatedMethods) {
            _clazz = clazz;
            _isClassAnnotated = isClassAnnotated;
            _annotatedFields = Collections.unmodifiableCollection(annotatedFields);
            _annotatedMethods = Collections.unmodifiableCollection(annotatedMethods);
        }

        @Override
        public Class<?> getRelatedClass() {
            return _clazz;
        }

        @Override
        public boolean isRelatedClassAnnotated() {
            return _isClassAnnotated;
        }

        @Override
        public Collection<Field> getAnnotatedFields() {
            return _annotatedFields;
        }

        @Override
        public Collection<Method> getAnnotatedMethods() {
            return _annotatedMethods;
        }
    }

}
