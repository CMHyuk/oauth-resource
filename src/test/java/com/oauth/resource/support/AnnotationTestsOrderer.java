package com.oauth.resource.support;

import org.junit.jupiter.api.ClassDescriptor;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;

import java.util.Comparator;

public class AnnotationTestsOrderer implements ClassOrderer {
    @Override
    public void orderClasses(ClassOrdererContext classOrdererContext) {
        classOrdererContext.getClassDescriptors().sort((Comparator<ClassDescriptor>) (o1, o2) -> {
            TestClassesOrder a1 = o1.getTestClass().getDeclaredAnnotation(TestClassesOrder.class);
            TestClassesOrder a2 = o2.getTestClass().getDeclaredAnnotation(TestClassesOrder.class);

            if (a1 == null) {
                return 1;
            }

            if (a2 == null) {
                return -1;
            }

            return Integer.compare(a1.value(), a2.value());
        });
    }
}
