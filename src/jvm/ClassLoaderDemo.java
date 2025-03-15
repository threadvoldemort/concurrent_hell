package jvm;

public class ClassLoaderDemo {
    public static void main(String[] args) {
        ClassLoaderDemo instance = new ClassLoaderDemo();
        Class<? extends ClassLoaderDemo> clazz = instance.getClass();
        System.out.println(clazz.getClassLoader()); // jdk.internal.loader.ClassLoaders$AppClassLoader@76ed5528

        System.out.println(Kid.str2);
    }
}

class Parent {
    public static String str = "I am parent";
    static {
        System.out.println("parent static block");
    }
}

class Kid extends Parent {
    public static String str2 = "I am kid";
    static {
        System.out.println("kid static block");
    }
}