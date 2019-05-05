package ru.otus.javadeveloper.hw04.core;

import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;
import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;

class MethodVisitorHelper {
    private final MethodVisitor mv;
    private final String name;
    private final String descriptor;
    private final Type[] args;
    private final Type ret;

    MethodVisitorHelper(MethodVisitor mv, String name, String descriptor) {
        this.mv = mv;
        this.name = name;
        this.descriptor = descriptor;
        args = Type.getArgumentTypes(descriptor);
        ret = Type.getReturnType(descriptor);
    }

    void concatAndPrintAllArgs() {
        Handle handle = new Handle(
                H_INVOKESTATIC,
                Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                "makeConcatWithConstants",
                MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                false);
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        String collectArgsDescriptor = Arrays.stream(args).map(Type::getDescriptor).collect(joining());
        loadAllArgsToStack();
        mv.visitInvokeDynamicInsn("makeConcatWithConstants", "(" + collectArgsDescriptor + ")Ljava/lang/String;", handle, createBootstrapMethodArgumentsString());
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

    void callOriginalMethod(String className, String proxiedMethodSuffix) {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        loadAllArgsToStack();
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className, name + proxiedMethodSuffix, descriptor, false);
    }

    void forwardOriginalResult() {
        mv.visitInsn(ret.getOpcode(Opcodes.IRETURN));
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void loadAllArgsToStack() {
        for (int i = 0; i < args.length; i++) {
            mv.visitVarInsn(args[i].getOpcode(Opcodes.ILOAD), i + 1);
        }
    }

    private String createBootstrapMethodArgumentsString() {
        return "logged param: " + Arrays.stream(args).map(type -> "\u0001").collect(joining(", "));
    }
}
