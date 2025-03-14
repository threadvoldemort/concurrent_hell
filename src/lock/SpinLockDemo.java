package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {
    public static void main(String[] args) {
        var selfLock = new SelfDefinedLock();
        new Thread(() -> {
            selfLock.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            selfLock.unlock();
        }, "T1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            selfLock.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            selfLock.unlock();
        }, "T2").start();

        // in this case T1 get the lock first, 1 second later T2
        // get the same lock, but T2 cannot release the lock
        // until T1 release the lock
    }
}

class SelfDefinedLock {

    // this self defined lock locks thread
    // AtomicInteger default is 0
    // AtomicReference default is null
    // so by default the var "lock" is null
    // if "lock" is null, we use compareAndSet to set the thread
    // to atomicReference
    AtomicReference<Thread> atomLock = new AtomicReference<>();

    public void lock() {
        var thread = Thread.currentThread();
        System.out.println(thread.getName() + " is locked");
        // here we lock(keep spinning)
        while(!atomLock.compareAndSet(null, thread)) {

        }
    }

    public void unlock() {
        var thread = Thread.currentThread();
        atomLock.compareAndSet(thread, null);
        System.out.println(thread.getName() + " is unlocked");
    }
}
