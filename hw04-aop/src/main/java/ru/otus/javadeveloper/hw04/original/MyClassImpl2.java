package ru.otus.javadeveloper.hw04.original;


import ru.otus.javadeveloper.hw04.core.Log;

public class MyClassImpl2 {
    public MyClassImpl2() {
        System.out.println("Created object of class MyClassImpl2");
    }
    @Log
    public void secureAccess(String param) {
        System.out.println("loggedSecureAccess, param:" + param);
    }

    @Log
    public int someOtherMethod(String param) {
        System.out.println("int loggedSomeOtherMethod, param:" + param);
        return 456;
    }

    @Log
    public String someOtherMethod2(String param) {
        System.out.println("str loggedSomeOtherMethod, param:" + param);
        return param;
    }

    @Log
    public boolean someOtherMethod3(String param) {
        System.out.println("bool loggedSomeOtherMethod, param:" + param);
        return true;
    }

    public void secureAccess2(String param) {
        System.out.println("secureAccess, param:" + param);
    }

    @Override
    public String toString() {
        return "MyClassImpl{}";
    }
}
