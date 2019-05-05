package ru.otus.javadeveloper.hw03.core;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

@Slf4j
class ReflectionHelper {
    static void checkMethodIsNotStatic(Method method) {
        if (Modifier.isStatic(method.getModifiers())) {
            CustomJunit5PlatformException customJunit5PlatformException = new CustomJunit5PlatformException();
            log.error("Метод {} помеченный аннотацией: BeforeEach или AfterEach не должен быть static", method.getName(), customJunit5PlatformException);
            throw customJunit5PlatformException;
        }
    }

    static void checkMethodIsStatic(Method method) {
        if (!Modifier.isStatic(method.getModifiers())) {
            CustomJunit5PlatformException customJunit5PlatformException = new CustomJunit5PlatformException();
            log.error("Метод {} помеченный аннотацией: BeforeAll или AfterAll обязан быть static", method.getName(), customJunit5PlatformException);
            throw customJunit5PlatformException;
        }
    }

    static boolean execMemberMethod(Object instanceOfTestClass, Method method) {
        boolean originAccess = method.canAccess(instanceOfTestClass);
        try {
            method.setAccessible(true);
            method.invoke(instanceOfTestClass);
        } catch (Exception e) {
            log.error("Ошибка исполнения метода {} :", method.getName(), e.getCause());
            return true;
        } finally {
            method.setAccessible(originAccess);
        }
        return false;
    }

    static boolean execMemberMethods(Object instanceOfTestClass, List<Method> methods, boolean isExecMustDiscardNotYetStartedIfExceptionThrown) {
        boolean isAtLeastOneExceptionIsThrown = false;
        for (Method method : methods) {
            boolean isMethodThrowException = execMemberMethod(instanceOfTestClass, method);
            if (isMethodThrowException && isExecMustDiscardNotYetStartedIfExceptionThrown) {
                return true;
            }
            isAtLeastOneExceptionIsThrown = isMethodThrowException;
        }
        return isAtLeastOneExceptionIsThrown;
    }

    static boolean execStaticMethods(List<Method> methods, boolean isExecMustDiscardNotYetStartedIfExceptionThrown) {
        return execMemberMethods(null, methods, isExecMustDiscardNotYetStartedIfExceptionThrown);
    }
}
