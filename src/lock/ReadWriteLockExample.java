package lock;

// read lock is a shared lock, can occupied by many threads at the same time
// write lock can only be occupied by one thread at a time
// read write lock usually used by framework

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    public static void main(String[] args) {
        //MyCache cache = new MyCache();

        MyCacheWithLock cache = new MyCacheWithLock();
        // when no lock is used, the put operation will be interfered by other operation like:
        // writer thread put 0 ok
        // reader thread get: 32
        // writer thread put: 1
        // reader thread get: 33
        // writer thread put 1 ok

        // the put operation should be atomic, means the "put ok" should be right after
        // the corresponding put option
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                cache.put(String.valueOf(i), String.valueOf(i));
            }
        }, "writer thread").start();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                cache.get(String.valueOf(i));
            }
        }, "reader thread").start();
    }
}

class MyCache {
    private volatile Map<String, String> cache = new HashMap<>();

    // should be atomic
    public void put(String key, String value) {
        System.out.println(Thread.currentThread().getName() + " put: " + key);
        cache.put(key, value);
        System.out.println(Thread.currentThread().getName() + " put " + key + " ok" );
    }

    public void get(String key) {
        System.out.println(Thread.currentThread().getName() + " get: " + key);
    }
}

class MyCacheWithLock {
    private volatile Map<String, String> cache = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    // should be atomic
    public void put(String key, String value) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " put: " + key);
            cache.put(key, value);
            System.out.println(Thread.currentThread().getName() + " put " + key + " ok" );
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void get(String key) {
        lock.readLock().lock(); // can be accessed by many threads at a time
        try {
            System.out.println(Thread.currentThread().getName() + " get: " + key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock();
        }
    }
}
