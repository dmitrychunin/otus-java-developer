package ru.otus.javadeveloper.hw04.original;


import ru.otus.javadeveloper.hw04.core.Log;

public class MyClassImpl {
    public MyClassImpl() {
        System.out.println("Created object of class MyClassImpl");
    }

    @Log
    public void secureAccess(String param) {
        System.out.println("loggedSecureAccess, param:" + param);
    }

    @Log
    public void someOtherMethod(String param) {
        System.out.println("loggedSomeOtherMethod, param:" + param);
    }

    public void secureAccess2(String param) {
        System.out.println("secureAccess, param:" + param);
    }

    @Log
    public void secureAccess3(int param) {
        System.out.println("secureAccess, param:" + param);
    }

    @Log
    public void secureAccess4(Integer param) {
        System.out.println("secureAccess, param:" + param);
    }

    @Log
    public Integer secureAccess5(Integer integer, boolean flag, String string) {
        System.out.println("secureAccess, param:" + integer + flag + string);
        return integer;
    }

    @Log
    public Integer secureAccess5(Integer integer, String string, boolean flag) {
        System.out.println("secureAccess, param:" + integer + flag + string);
        return integer;
    }

    @Log
    public Integer secureAccess5(Integer integer, boolean flag) {
        System.out.println("secureAccess, param:" + integer + flag);
        return integer;
    }

    @Log
    public Integer secureAccess5(Integer integer, boolean flag, int integer2) {
        System.out.println("secureAccess, param:" + integer + flag + integer2);
        return integer;
    }

    @Override
    public String toString() {
        return "MyClassImpl";
    }
}
