package ru.otus.javadeveloper.hw03;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ru.otus.javadeveloper.hw03.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.javadeveloper.hw03.ReflectionHelper.*;

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
                checkMethodIsStatic(method);
                beforeAll.add(method);
            }
            if (method.isAnnotationPresent(BeforeEach.class)) {
                checkMethodIsNotStatic(method);
                beforeEach.add(method);
            }
            if (method.isAnnotationPresent(Test.class) && !Modifier.isPrivate(method.getModifiers())) {
                tests.add(method);
            }
            if (method.isAnnotationPresent(AfterEach.class)) {
                checkMethodIsNotStatic(method);
                afterEach.add(method);
            }
            if (method.isAnnotationPresent(AfterAll.class)) {
                checkMethodIsStatic(method);
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
        boolean isAtLeastOneBeforeAllThrowException = execBeforeAllMethodsAndDiscardNotYetStartedIfExceptionThrown(beforeAll);
        if (!isAtLeastOneBeforeAllThrowException) {
            for (Method test : tests) {
                executeEachAndTest(test);
            }
        }
        execStaticMethodsAndIgnoreExceptions(afterAll);
    }

    private void executeEachAndTest(Method test) throws Exception {
        Object instanceOfTestClass = clazz.getDeclaredConstructor().newInstance();
        boolean isAtLeastOneBeforeEachThrowException = execBeforeEachMethodsAndDiscardNotYetStartedIfExceptionThrown(instanceOfTestClass, beforeEach);
        if (!isAtLeastOneBeforeEachThrowException) {
            execTestAndIgnoreExceptions(instanceOfTestClass, test);
        }
        execAfterEachMethodsAndIgnoreExceptions(instanceOfTestClass, afterEach);
    }
}
