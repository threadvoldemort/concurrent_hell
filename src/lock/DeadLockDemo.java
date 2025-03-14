package lock;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lock A";
        String lockB = "lock B";
        new Thread(new BadLock(lockA, lockB)).start();
        new Thread(new BadLock(lockB, lockA)).start();
    }
}

class BadLock implements Runnable {
    private final String lockA;
    private final String lockB;

    public BadLock(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }


    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println("get lock: " + lockA);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("want lock: " + lockB);
            synchronized (lockB) {
                System.out.println("get lock: " + lockB);
            }
        }
    }
}
