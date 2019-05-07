package ru.otus.javadeveloper.hw04.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.objectweb.asm.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

class ProxyGenerator {
    static byte[] setLogProxiedClass(byte[] originalClass, List<Method> loggedMethods, String className) {

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM7, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (isMethodsContainVisited(loggedMethods, name, descriptor)) {
                    String proxiedMethodSuffix = RandomStringUtils.randomAlphabetic(20);
                    MethodVisitor mv = cw.visitMethod(access, name, descriptor, signature, exceptions);
                    MethodVisitorHelper methodVisitorHelper = new MethodVisitorHelper(mv, name, descriptor);
                    methodVisitorHelper.logBeforeOriginalMethod(className, proxiedMethodSuffix);
                    return super.visitMethod(access, name + proxiedMethodSuffix, descriptor, signature, exceptions);
                } else {
                    return super.visitMethod(access, name, descriptor, signature, exceptions);
                }
            }
        };

        ClassReader cr = new ClassReader(originalClass);
        cr.accept(cv, Opcodes.ASM7);

        return cw.toByteArray();
    }

    private static boolean isMethodsContainVisited(List<Method> methods, String name, String descriptor) {
        Predicate<Method> isMethodEqualVisited = method ->
                name.equals(method.getName()) &&
                Arrays.equals(Type.getArgumentTypes(method), Type.getArgumentTypes(descriptor));
        return methods.stream()
                .anyMatch(isMethodEqualVisited);
    }
}
