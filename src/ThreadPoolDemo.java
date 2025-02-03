// BlockingQueue is heavily used in thread pool

// ThreadPoolExecutor is the official way to use thread pool
// DON'T use Executors to create thread pool(will cause OOM)
// because the max pool size parameter is Integer.MAX_VALUE, which might accumulate
// too many requests cause OOM eventually
// just new a ThreadPoolExecutor

import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String[] args) {

        // create threads according to the amount of input
        // ExecutorService service = Executors.newCachedThreadPool(); -> BAD

        // use LinkedBlockingQueue is better because the elements can be fetched
        // from both end of the queue
        // MUST define the capacity parameter(here we give it 3)
        // because by default the capacity of LinkedBlockingQueue is also Integer.MAX_VALUE
        // which may cause OOM
        ExecutorService service = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        try {
            for (int i = 0; i < 50; i++) {
                service.execute(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            service.shutdown();
        }

        // reject strategy
        // 1. AbortPolicy:
        //    this will throw RejectedExecutionException when the input > (max pool size + queue size)
        //    and abort the request
        // 2. DiscardPolicy:
        //    this will abort the request silently without throwing any exception
        // 3. DiscardOldPolicy:
        //    this strategy will try to fetch the element but not guarantee to process it
        // 4. CallerRunPolicy:
        //    let the caller process by itself(for example the main thread)

        // these 4 strategies derived from 4 situations which blocking queue has


        // how to set up maximum thread pool?
        // 1. Runtime.getRuntime().availableProcessors() for CPU intensive
        // 2. If it is IO intensive, set the number to the number of tasks

    }
}




