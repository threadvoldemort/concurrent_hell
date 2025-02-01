package oldways;

// this example has problem, it works when there are only 2 threads
// because the if statement can only determine ONCE, it is not thread safe
// if there are many threads running in the same method
// change if statement to while if there are multiple threads running

// formula: THREAD MANIPULATE RESOURCES
// thread cannot communicate with another thread, but sometimes we need such mechanism
// the communication between threads are 3 steps:
// determine -> execute -> notify

// in this example if we use multiple threads, the order will be random
// we cannot let all threads execute one by one in order
// this is also the disadvantage of the traditional consumer producer pattern
// which use wait() and notify(), because this way cannot wake up threads accurately

// btw join is not a good way because it will mess up the order
// and all the treads need to compete for the lock again

public class ProducerConsumerSynchronized {
    public static void main(String[] args) {
        var data = new Data();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increament();
            }
        }, "thread 1").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decreament();
            }
        }, "thread 2").start();
    }
}

class Data {
    private int number = 0;

    public synchronized void increament() {
        if (number != 0) { // determine(use while, don't use if in here)
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        number++; // execute
        System.out.println(Thread.currentThread().getName() + " add 1, number now: " + number);
        notifyAll(); // notify
    }

    public synchronized void decreament() {
        if (number == 0) { // determine(use while, don't use if in here)
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        number--; // execute
        System.out.println(Thread.currentThread().getName() + " deduct 1, number now: " + number);
        notifyAll(); // notify
    }
}
