import java.util.concurrent.TimeUnit;

public class VolatileDemo {

    // without volatile, one thread change it other threads won't know
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
