import java.util.concurrent.TimeUnit;

// volatile:
// 1. guarantee visibility
// 2. don't guarantee atomicity
//    (multiple threads adding a number the result will be wrong, use Atomic class)
// 3. prohibit command reorder(singleton, CAS)
//    DCL singleton is the classic implementation of command reorder
//    by inserting the memory barrier(CPU commands)

public class VolatileDemo {

    // without volatile, one thread change it other threads won't see
    // not visible to other threads
    private static int num = 0;
//    private volatile static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (num == 0) {}
        }).start();

        TimeUnit.SECONDS.sleep(2);
        num = 1; // the while loop won't stop after the change
        System.out.println("num: " + num);
    }
}