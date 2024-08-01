package com.oauth.resource.global.log;

@FunctionalInterface
public interface ThrowableCallable<V> {

    V call() throws Throwable;
}
