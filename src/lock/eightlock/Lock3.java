package lock.eightlock;

// the order will always be sms -> email
// when having static keyword the lock become the class which is the only one

import java.util.concurrent.TimeUnit;

public class Lock3 {
    public static void main(String[] args) throws InterruptedException {
        Phone4 phone = new Phone4();
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

class Phone4 {
    public static synchronized void sendSMS() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        System.out.println("send SMS");
    }

    public static synchronized void sendEmail() {
        System.out.println("send Email");
    }

}
