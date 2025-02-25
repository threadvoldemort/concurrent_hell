import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CycBarrierDemo {
    public static void main(String[] args) {
        var bar = new CyclicBarrier(7, () -> {
            System.out.println("7 dragon ball collected!");
        });

        for (int i = 0; i < 7; i++) {
            int iClone = i;
            new Thread(() -> {
                System.out.println("collect dragon ball number: " + iClone);
                try {
                    bar.await(); // block
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}
