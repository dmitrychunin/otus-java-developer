package ru.otus.javadeveloper.hw03;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ru.otus.javadeveloper.hw03.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
                beforeAll.add(method);
            }
            if (method.isAnnotationPresent(BeforeEach.class)) {
                beforeEach.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                tests.add(method);
            }
            if (method.isAnnotationPresent(AfterEach.class)) {
                afterEach.add(method);
            }
            if (method.isAnnotationPresent(AfterAll.class)) {
                afterAll.add(method);
            }
        }
    }

    public static void runTest(@NonNull Class<?> clazz) throws Exception {
        CustomJunit5AnnotationRunner runnerContainer = new CustomJunit5AnnotationRunner(clazz);
        runnerContainer.executeTests();
    }

    void executeTests() throws Exception {
        safeExecStaticMethods(beforeAll.toArray(new Method[0]));
        for (Method test : tests) {
            Object instanceOfTestClass = clazz.getDeclaredConstructor().newInstance();
            safeExecMemberMethods(instanceOfTestClass, beforeEach.toArray(new Method[0]));
            safeExecMemberMethods(instanceOfTestClass, test);
            safeExecMemberMethods(instanceOfTestClass, afterEach.toArray(new Method[0]));
        }
        safeExecStaticMethods(afterAll.toArray(new Method[0]));
    }

    private void safeExecMemberMethods(Object instanceOfTestClass, Method... methods) {
        for (int i = 0; i < methods.length; i++) {
            try {
                methods[i].invoke(instanceOfTestClass);
            } catch (Exception e) {
                log.error("Ошибка исполнения метода {} :", methods[i].getName(), e.getCause());
            }
        }
    }

    private void safeExecStaticMethods(Method... methods) {
        safeExecMemberMethods(null, methods);
    }
}
