package collection;

// HashSet under the hood is a HashMap, add method is put

import java.util.HashSet;
import java.util.Set;

public class NotSafeSet {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(Thread.currentThread().getName());
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
// ConcurrentModificationException
// solution 1: use Collections.synchronizedSet(new HashSet())
// solution 2: use CopyOnWriteArraySet