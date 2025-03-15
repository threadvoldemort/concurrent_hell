package jvm;

import java.util.UUID;

public class ClassLoaderConstantObjectDemo {
    public static void main(String[] args) {
        System.out.println(ParentThree.str);
    }
}

// in this case UUID is a class, in order to call the method of this class
// the class must be initialized, so the static block of ParentThree
// will be run
class ParentThree {
    // this constant's value is not sure during compilation, so
    // it will not be put into ClassLoaderConstantObjectDemo's constant pool
    public static final String str = UUID.randomUUID().toString();

    // this will print
    static {
        System.out.println("parent 3 static block");
    }
}
