package jvm;

public class ClassLoaderTypeDemo {
    public static void main(String[] args) {
        Object o = new Object();
        Parent p = new Parent();

        // null, doesn't mean null, the reason is this is loaded by bootstrap loader
        // which is written in C++
        System.out.println(o.getClass().getClassLoader());
        System.out.println(p.getClass().getClassLoader()); // app class loader

        // Parent Delegation Mechanism
        System.out.println("---------------------------------------");
        System.out.println(p.getClass().getClassLoader()); // app
        System.out.println(p.getClass().getClassLoader().getParent()); // platform(ext)
        System.out.println(p.getClass().getClassLoader().getParent().getParent()); // null

        // sys(app) -> platform(ext) -> bootstrap(core, C++)

        // if you define a new java.lang.String it won't be loaded
        // because in Parent Delegation Mechanism, java will let the parent
        // loader to load the class, if parent can not load then will delegate
        // to its child loader to load

    }
}


