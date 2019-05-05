package ru.otus.javadeveloper.hw03.core;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;

import static ru.otus.javadeveloper.hw03.core.ReflectionHelper.*;

@Slf4j
class ReflectionHelperFacade {
    static void execTestAndIgnoreExceptions(Object instanceOfTestClass, Method test) {
        boolean isTestThrowException = execMemberMethod(instanceOfTestClass, test);
        if (!isTestThrowException) {
            log.info("Успешно выполнился тест {}", test.getName());
        }
    }

    static void execAfterEachMethodsAndIgnoreExceptions(Object instanceOfTestClass, List<Method> afterEach) {
        execMemberMethods(instanceOfTestClass, afterEach, false);
    }

    static boolean execBeforeEachMethodsAndDiscardNotYetStartedIfExceptionThrown(Object instanceOfTestClass, List<Method> beforeEach) {
        return execMemberMethods(instanceOfTestClass, beforeEach, true);
    }

    static void execStaticMethodsAndIgnoreExceptions(List<Method> afterAll) {
        execStaticMethods(afterAll, false);
    }

    static boolean execBeforeAllMethodsAndDiscardNotYetStartedIfExceptionThrown(List<Method> beforeAll) {
        return execStaticMethods(beforeAll, true);
    }
}
