package lock.eightlock;

// the execution order will always be "send sms" -> "send email"
// when using synchronized, it is the caller of the method get locked
// so here is phone get locked

import java.util.concurrent.TimeUnit;

public class Lock1 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();

        new Thread(() -> phone.sendSMS(), "thread-A").start();
        TimeUnit.SECONDS.sleep(5);
        new Thread(() -> phone.sendEmail(), "thread-B").start();

    }
}

class Phone {

    public synchronized void sendSMS() {
        System.out.println("send SMS");
    }

    public synchronized void sendEmail() {
        System.out.println("send Email");
    }
}