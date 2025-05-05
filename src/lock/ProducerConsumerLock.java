package lock;

// Lock and Condition provide a better producer/consumer model
// while using Lock, we don't use wait() and notifyAll() from Object
// we use await() and signal() from Condition instead
// when using synchronized, the monitor is Object, when using Lock, the monitor is Condition
// one Condition can bind to one Lock

// the goal of using Lock and Condition is to wake up the exact thread, so the threads can
// execute the task in the order we want, this cannot be done by using synchronized

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerLock {
    public static void main(String[] args) {
        var data = new Data();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                data.print5Times();
            }
        }, "thread 1").start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                data.print10Times();
            }
        }, "thread 2").start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                data.print15Times();
            }
        }, "thread 3").start();
    }
}

class Data {
    private int number = 1;
    private final Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void print5Times() {
        lock.lock();
        while (number != 1) {
            try {
                condition1.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " print " + i);
            }
            number = 2;
            condition2.signal();
        } finally {
            lock.unlock();
        }
    }

    public void print10Times() {
        lock.lock();
        while (number != 2) {
            try {
                condition2.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " print " + i);
            }
            number = 3;
            condition3.signal();
        } finally {
            lock.unlock();
        }
    }

    public void print15Times() {
        lock.lock();
        while (number != 3) {
            try {
                condition3.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + " print " + i);
            }
            number = 1;
            condition1.signal();
        } finally {
            lock.unlock();
        }
    }
}
