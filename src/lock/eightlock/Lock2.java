package lock.eightlock;

// the order will always be "send Email" -> "send SMS"

import java.util.concurrent.TimeUnit;

public class Lock2 {
    public static void main(String[] args) throws InterruptedException {
        Phone3 phone = new Phone3();
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "thread-A").start();
        TimeUnit.SECONDS.sleep(2);
        new Thread(() -> phone.sendEmail(), "thread-B").start();
    }
}

class Phone3 {

    public synchronized void sendSMS() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        System.out.println("send SMS");
    }

    public void sendEmail() {
        System.out.println("send Email");
    }
}
