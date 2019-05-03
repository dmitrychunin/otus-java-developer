package ru.otus.javadeveloper.hw03;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

@Slf4j
public class ReflectionHelper {
    public static void checkMethodIsNotStatic(Method method) {
        if (Modifier.isStatic(method.getModifiers())) {
            CustomJunit5PlatformException customJunit5PlatformException = new CustomJunit5PlatformException();
            log.error("Метод {} помеченный аннотацией: BeforeEach или AfterEach не должен быть static", method.getName(), customJunit5PlatformException);
            throw customJunit5PlatformException;
        }
    }

    public static void checkMethodIsStatic(Method method) {
        if (!Modifier.isStatic(method.getModifiers())) {
            CustomJunit5PlatformException customJunit5PlatformException = new CustomJunit5PlatformException();
            log.error("Метод {} помеченный аннотацией: BeforeAll или AfterAll обязан быть static", method.getName(), customJunit5PlatformException);
            throw customJunit5PlatformException;
        }
    }

    public static void execStaticMethodsAndIgnoreExceptions(List<Method> methods) {
        execAfterEachMethodsAndIgnoreExceptions(null, methods);
    }

    public static boolean execBeforeAllMethodsAndDiscardNotYetStartedIfExceptionThrown(List<Method> methods) {
        return execBeforeEachMethodsAndDiscardNotYetStartedIfExceptionThrown(null, methods);
    }

    public static void execAfterEachMethodsAndIgnoreExceptions(Object instanceOfTestClass, List<Method> methods) {
        for (Method method : methods) {
            boolean originAccess = method.canAccess(instanceOfTestClass);
            try {
                method.setAccessible(true);
                method.invoke(instanceOfTestClass);
            } catch (Exception e) {
                log.error("Ошибка исполнения метода {} :", method.getName(), e.getCause());
            } finally {
                method.setAccessible(originAccess);
            }
        }
    }

    public static void execTestAndIgnoreExceptions(Object instanceOfTestClass, Method test) {
        boolean originAccess = test.canAccess(instanceOfTestClass);
        try {
            test.setAccessible(true);
            test.invoke(instanceOfTestClass);
            log.info("Успешно выполнился тест {}", test.getName());
        } catch (Exception e) {
            log.error("Ошибка исполнения теста {} :", test.getName(), e.getCause());
        } finally {
            test.setAccessible(originAccess);
        }
    }

    public static boolean execBeforeEachMethodsAndDiscardNotYetStartedIfExceptionThrown(Object instanceOfTestClass, List<Method> methods) {
        for (Method method : methods) {
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
        }
        return false;
    }
}
