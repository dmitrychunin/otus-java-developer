package ru.otus.javadeveloper.hw04.core;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.javadeveloper.hw04.Utils.createResultByteCodeFile;
import static ru.otus.javadeveloper.hw04.core.ProxyGenerator.generateLogProxiedClass;

public class LogAnnotationAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classFileBuffer) {

                if (isClassLoadedByBootstrapClassLoader(loader)) {
                    return classFileBuffer;
                }

                Class<?> aClass = new ByteArrayToClassConverter().convert(className, classFileBuffer, protectionDomain);
                List<String> loggedMethods = findClassLoggedMethodNames(aClass);
                if (!loggedMethods.isEmpty()) {
                    byte[] proxiedClass = generateLogProxiedClass(classFileBuffer, loggedMethods, className);
                    createResultByteCodeFile(className, proxiedClass);
                    return proxiedClass;
                }
                return classFileBuffer;
            }
        });
    }

    private static boolean isClassLoadedByBootstrapClassLoader(ClassLoader loader) {
        return loader == null;
    }

    private static List<String> findClassLoggedMethodNames(Class<?> clazz) {
        List<String> loggedMethods = new ArrayList<>();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(Log.class)) {
                loggedMethods.add(method.getName());
            }
        }
        return loggedMethods;
    }

    private static class ByteArrayToClassConverter extends ClassLoader {
        private Class convert(String className, byte[] b, ProtectionDomain protectionDomain) {
            String dottedClassName = className.replace('/', '.');
            ByteBuffer byteBuffer = ByteBuffer.wrap(b);
            return super.defineClass(dottedClassName, byteBuffer, protectionDomain);
        }
    }
}
