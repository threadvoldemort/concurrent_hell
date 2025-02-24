import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // async no return val
        var f = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("f async no return val.....");
        });
        System.out.println("main thread is running.........");
        f.get();

        // async has return val
        var f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supply async...");
            return 9999;
        });

        f2.whenComplete((t, u) -> {
            System.out.println("t: " + t); // the output if run correctly
            System.out.println("u: " + u); // error if you have one
        }).exceptionally(e -> {
            System.out.println("error: " + e);
            return 500;
        }).get();

    }
}


