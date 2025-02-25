import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// heavily used in concurrency
// can be used for flow control
// case: 6 cars fight for 3 parking slot
public class SemaphoreDemo {
    public static void main(String[] args) {
        var s = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    s.acquire();
                    System.out.println(Thread.currentThread().getName() + " get the slot");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    s.release();
                    System.out.println(Thread.currentThread().getName() + " release the slot");
                }
            }).start();
        }
    }
}
