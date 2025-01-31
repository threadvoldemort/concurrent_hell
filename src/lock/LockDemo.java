package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import static java.lang.System.out;
import static java.util.stream.IntStream.range;

// Lock is an object
// it can try to get the lock
// manually release lock, deadlock will happen if never release lock
// when using lock, if thread A get the lock, thread B can try to get the lock,
// it doesn't have to be waited, if it cannot get the lock then it just give up
// when the code base is huge use Lock
public class LockDemo {
    public static void main(String[] args) {
        var ticket = new TicketTwo();

        var t1 = new Thread(() -> range(0, 40).forEach(i -> {
            try {
                ticket.sellTicket();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }), "Thread-1");
        var t2 = new Thread(() -> range(0, 40).forEach(i -> {
            try {
                ticket.sellTicket();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }), "Thread-2");
        var t3 = new Thread(() -> range(0, 40).forEach(i -> {
            try {
                ticket.sellTicket();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }), "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class TicketTwo {
    // by default is unfair lock, thread can cut queue
    // but it can be either fair or unfair
    private final ReentrantLock lock = new ReentrantLock();
    private int number = 30;

    // must unlock in finally block
    public void sellTicket() throws InterruptedException {
        // lock.lock();
        boolean b = lock.tryLock(1, TimeUnit.SECONDS);
        if (!b) {
            throw new InterruptedException();
        }
        try {
            if (number > 0) {
                out.println(Thread.currentThread().getName() + " Ticket sold. Remaining: " + --number);
            } else {
                out.println(Thread.currentThread().getName() + " No tickets left to sell.");
            }
        } finally {
            lock.unlock();
        }
    }
}
