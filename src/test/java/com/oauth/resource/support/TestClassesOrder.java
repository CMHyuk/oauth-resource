package com.oauth.resource.support;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TestClassesOrder {
    int value() default Integer.MAX_VALUE;
}
