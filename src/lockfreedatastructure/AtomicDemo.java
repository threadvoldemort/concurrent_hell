package lockfreedatastructure;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// ACID(not dividable, all success or all fail)
// ++, new, etc. are not atomic operation
// AtomicInteger uses unsafe to achieve atomic
public class AtomicDemo {

    private static final AtomicInteger num = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    num.getAndIncrement();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(20);
        System.out.println("num: " + num);
    }
}
