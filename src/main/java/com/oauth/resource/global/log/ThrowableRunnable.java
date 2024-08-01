package com.oauth.resource.global.log;

@FunctionalInterface
public interface ThrowableRunnable {

    void run() throws Throwable;
}
