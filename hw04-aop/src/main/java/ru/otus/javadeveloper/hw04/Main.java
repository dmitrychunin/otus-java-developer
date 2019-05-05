package ru.otus.javadeveloper.hw04;

import ru.otus.javadeveloper.hw04.original.MyClassImpl;
import ru.otus.javadeveloper.hw04.original.MyClassImpl2;

public class Main {
    public static void main(String[] args) {
        MyClassImpl myClass = new MyClassImpl();
        myClass.secureAccess("Security Param");
        myClass.secureAccess2("Security Param2");
        myClass.secureAccess3(123);
        myClass.secureAccess4(345);
        myClass.secureAccess5(123, true, "string param");
        myClass.someOtherMethod("not some Security");
        myClass.someOtherMethod("not some Security");
        myClass.secureAccess5(123, true, "string param");
        myClass.secureAccess5(123, "string", true);
        myClass.secureAccess5(123, false);
        myClass.secureAccess5(123, false, 345);

        MyClassImpl2 myClass2 = new MyClassImpl2();
        myClass2.secureAccess("Security Param");
        myClass2.secureAccess2("Security Param2");
        System.out.println(myClass2.someOtherMethod("not some Security"));
        System.out.println(myClass2.someOtherMethod2("not some Security"));
        System.out.println(myClass2.someOtherMethod3("not some Security"));
    }
}
