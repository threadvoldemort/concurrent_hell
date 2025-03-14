package oldways;

import static java.lang.System.*;
import static java.util.stream.IntStream.*;

// synchronized can apply to method or code block
// old fashion, use Lock in JUC
// synchronized is a keyword not an object
// when using it, if thread A get the lock, thread B can only be wait there
// when code is small can use synchronized
public class TraditionalSync {
    public static void main(String[] args) {
        var ticket = new Ticket();

        var t1 = new Thread(() -> range(0, 400).forEach(i -> ticket.sellTicket()), "Thread-1");
        var t2 = new Thread(() -> range(0, 400).forEach(i -> ticket.sellTicket()), "Thread-2");
        var t3 = new Thread(() -> range(0, 400).forEach(i -> ticket.sellTicket()), "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class Ticket {
    private int number = 300;

    // the result will be wrong without synchronized
    // it by default is unfair, it can only be unfair
    // it cannot try to get the lock
    // it auto release lock
    public synchronized void sellTicket() {
        if (number > 0) {
            out.println(Thread.currentThread().getName() + " Ticket sold. Remaining: " + --number);
        } else {
            out.println(Thread.currentThread().getName() + " No tickets left to sell.");
        }
    }
}