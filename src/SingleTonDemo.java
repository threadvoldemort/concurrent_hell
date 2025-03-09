import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

// key: private constructor
public class SingleTonDemo {
    private SingleTonDemo(){
        System.out.println("new an instance");
    }

    private static SingleTonDemo demo; // wrong, should add volatile

    // DCL double lock way
    public static SingleTonDemo getInstance() {
        if (demo == null) {
            synchronized (SingleTonDemo.class) {
                if (demo == null) {
                    demo = new SingleTonDemo(); // NOT SAFE, NOT ATOMIC
                    // how Java create object:
                    // 1. allocate mem space
                    // 2. call constructor, create new object
                    // 3. point to the object

                    // in memory, a thread can run step 3 first, then run step 1 and 2
                    // if there are 2 threads running at the same time, thread A execute
                    // step 3 first then step 1, at the same time another threads B come in,
                    // it found that the instance is not null, so thread B return the instance
                    // right away, but thread A has never executed step 2, the instance
                    // is not initiated fully, the instance thread B returned is problematic

                    // this DCL Singleton issue is the classic example of command reorder
                    // after adding volatile to variable "demo" the problem is solved

                    // the reason why singleton is safe because its constructor is private
                    // but is it really safe?
                    // under reflection, it is also not safe

                }
            }
        }
        return demo;
    }

    public static void main(String[] args) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        for (int i = 0; i < 100; i++) {
            new Thread(SingleTonDemo::getInstance).start();
        }

        // using reflection:
        // we can see the hashcode is different, it is not safe
        SingleTonDemo demoNormal = SingleTonDemo.getInstance();
        Constructor<SingleTonDemo> cons = SingleTonDemo.class.getDeclaredConstructor(null);
        SingleTonDemo demoReflection = cons.newInstance();
        System.out.println("hashcode of demoNormal: " + demoNormal.hashCode());
        System.out.println("hashcode of demoReflection: " + demoReflection.hashCode());

        // when using reflection, the constructor has never been called
        // so the singleton mechanism is failed
        // use Enum instead(Enum is Singleton safe IF the jdk is not modified)
        // jad.exe can decompile .class doc into a .java file, you can add malicious code
        // into it and compile to .class file again, replace the original .class file with this
        // one then the jdk is hacked
    }
}
