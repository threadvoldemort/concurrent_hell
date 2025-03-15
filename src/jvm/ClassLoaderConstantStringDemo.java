package jvm;

public class ClassLoaderConstantStringDemo {
    public static void main(String[] args) {
        System.out.println(ParentTwo.str);
    }
}

// variable "str" is constant, it will be put into constant pool of class "ClassLoaderConstantStringDemo"
// during compilation stage, it won't initialize the instance,
// and the variable "str" is nothing to do with the class "ParentTwo"
// after being put into constant pool
class ParentTwo {
    // this constant's value is fixed when compiled, so it will be put into
    // ClassLoaderConstantStringDemo's constant poll
    public static final String str = "I am Parent 2";

    // this will not print
    static {
        System.out.println("parent 2 static block");
    }
}
