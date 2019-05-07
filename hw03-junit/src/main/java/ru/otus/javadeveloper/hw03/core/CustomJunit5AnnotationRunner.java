package ru.otus.javadeveloper.hw03.core;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ru.otus.javadeveloper.hw03.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.javadeveloper.hw03.core.ExceptionHandlingMode.IGNORE;
import static ru.otus.javadeveloper.hw03.core.ExceptionHandlingMode.INTERRUPT;
import static ru.otus.javadeveloper.hw03.core.MethodExecutionBuilder.execute;

@Slf4j
public class CustomJunit5AnnotationRunner {

    private final Class<?> clazz;
    private List<Method> beforeAll = new ArrayList<>();
    private List<Method> beforeEach = new ArrayList<>();
    private List<Method> tests = new ArrayList<>();
    private List<Method> afterEach = new ArrayList<>();
    private List<Method> afterAll = new ArrayList<>();

    CustomJunit5AnnotationRunner(@NonNull Class<?> testClass) {
        clazz = testClass;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeAll.class)) {
                checkIsStatic(method);
                beforeAll.add(method);
            }
            if (method.isAnnotationPresent(BeforeEach.class)) {
                checkIsNotStatic(method);
                beforeEach.add(method);
            }
            if (method.isAnnotationPresent(Test.class) && !Modifier.isPrivate(method.getModifiers())) {
                tests.add(method);
            }
            if (method.isAnnotationPresent(AfterEach.class)) {
                checkIsNotStatic(method);
                afterEach.add(method);
            }
            if (method.isAnnotationPresent(AfterAll.class)) {
                checkIsStatic(method);
                afterAll.add(method);
            }
        }
    }

    public static void runTest(@NonNull Class<?> clazz) throws Exception {
        log.info("Запускаются тесты класса {}", clazz.getName());
        try {
            CustomJunit5AnnotationRunner runnerContainer = new CustomJunit5AnnotationRunner(clazz);
            runnerContainer.executeTests();
            log.info("Прогнаны тесты класса {}", clazz.getName());
        } catch (CustomJunit5PlatformException e) {
            log.error("Тесты класса {} не могут быть запущены", clazz.getName());
        }
    }

    void executeTests() throws Exception {
        boolean isAtLeastOneBeforeAllThrowException = execute(beforeAll)
                .withExceptionHandlingMode(INTERRUPT)
                .go();
        if (!isAtLeastOneBeforeAllThrowException) {
            for (Method test : tests) {
                executeEachAndTest(test);
            }
        }
        execute(afterAll)
                .withExceptionHandlingMode(IGNORE)
                .go();
    }

    private void executeEachAndTest(Method test) throws Exception {
        Object instanceOfTestClass = clazz.getDeclaredConstructor().newInstance();
        boolean isAtLeastOneBeforeEachThrowException = execute(beforeEach)
                .onInstance(instanceOfTestClass)
                .withExceptionHandlingMode(INTERRUPT)
                .go();
        if (!isAtLeastOneBeforeEachThrowException) {
            boolean isTestThrowException = execute(test).onInstance(instanceOfTestClass).go();
            if (!isTestThrowException) {
                log.info("Успешно выполнился тест {}", test.getName());
            }
        }
        execute(afterEach)
                .onInstance(instanceOfTestClass)
                .withExceptionHandlingMode(IGNORE)
                .go();
    }

    private void checkIsNotStatic(Method method) {
        if (Modifier.isStatic(method.getModifiers())) {
            CustomJunit5PlatformException customJunit5PlatformException = new CustomJunit5PlatformException();
            log.error("Метод {} не должен быть static", method.getName(), customJunit5PlatformException);
            throw customJunit5PlatformException;
        }
    }

    private void checkIsStatic(Method method) {
        if (!Modifier.isStatic(method.getModifiers())) {
            CustomJunit5PlatformException customJunit5PlatformException = new CustomJunit5PlatformException();
            log.error("Метод {} обязан быть static", method.getName(), customJunit5PlatformException);
            throw customJunit5PlatformException;
        }
    }
}
