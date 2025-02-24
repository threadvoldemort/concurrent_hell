package collection;

// under multithreaded env, all collections are not safe(List, Map, etc)

import java.util.ArrayList;
import java.util.List;

public class NotSafeList {
    public static void main(String[] args) {
        List<String> list  = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(Thread.currentThread().getName());
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}

// ConcurrentModificationException
// reason: the "add" method is not locked

// solution 1: use Vector which add method has synchronized keyword, but Vector very slow
// solution 2: use Collections.synchronizedList(new ArrayList())
// solution 3: use CopyOnWriteArrayList
//      thread copy the resources when writing something to it, this is a concept of
//      read/write separation, the "add" method uses ReentrantLock, and use
//      Arrays.copyOf() to copy the original resource, and write new value to the
//      new copy
