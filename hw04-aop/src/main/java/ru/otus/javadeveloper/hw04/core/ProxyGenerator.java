package ru.otus.javadeveloper.hw04.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.objectweb.asm.*;

import java.util.List;

class ProxyGenerator {
    static byte[] generateLogProxiedClass(byte[] originalClass, List<String> loggedMethods, String className) {

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM7, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (loggedMethods.contains(name)) {
                    String proxiedMethodSuffix = RandomStringUtils.randomAlphabetic(20);
                    MethodVisitor mv = cw.visitMethod(access, name, descriptor, signature, exceptions);
                    MethodVisitorHelper methodVisitorHelper = new MethodVisitorHelper(mv, name, descriptor);
                    methodVisitorHelper.concatAndPrintAllArgs();
                    methodVisitorHelper.callOriginalMethod(className, proxiedMethodSuffix);
                    methodVisitorHelper.forwardOriginalResult();
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
}
