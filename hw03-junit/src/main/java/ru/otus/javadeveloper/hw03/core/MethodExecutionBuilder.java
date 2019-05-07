package ru.otus.javadeveloper.hw03.core;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

@Slf4j
public class MethodExecutionBuilder {
    private final List<Method> methods;
    private Object instance;
    private ExceptionHandlingMode exceptionHandlingMode = ExceptionHandlingMode.IGNORE;

    private MethodExecutionBuilder(List<Method> methods) {
        this.methods = methods;
    }

    public static MethodExecutionBuilder execute(List<Method> methods) {
        return new MethodExecutionBuilder(methods);
    }

    public static MethodExecutionBuilder execute(Method method) {
        return new MethodExecutionBuilder(Collections.singletonList(method));
    }

    public MethodExecutionBuilder onInstance(Object instance) {
        this.instance = instance;
        return this;
    }

    public MethodExecutionBuilder withExceptionHandlingMode(ExceptionHandlingMode exceptionHandlingMode) {
        this.exceptionHandlingMode = exceptionHandlingMode;
        return this;
    }

    public boolean go() {
        return execMemberMethods();
    }

    private boolean execMemberMethod(Method method) {
        boolean originAccess = method.canAccess(instance);
        try {
            method.setAccessible(true);
            method.invoke(instance);
        } catch (Exception e) {
            log.error("Ошибка исполнения метода {} :", method.getName(), e.getCause());
            return true;
        } finally {
            method.setAccessible(originAccess);
        }
        return false;
    }

    private boolean execMemberMethods() {
        boolean isAtLeastOneExceptionIsThrown = false;
        for (Method method : methods) {
            boolean isMethodThrowException = execMemberMethod(method);
            if (isMethodThrowException && ExceptionHandlingMode.INTERRUPT == exceptionHandlingMode) {
                return true;
            }
            isAtLeastOneExceptionIsThrown = isMethodThrowException;
        }
        return isAtLeastOneExceptionIsThrown;
    }
}