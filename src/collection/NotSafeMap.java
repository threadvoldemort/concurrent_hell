package collection;

// Hash maps are built on top of an underlying array data structure using an indexing system
// Each index in the array can store one key-value pair

import java.util.HashMap;
import java.util.Map;

public class NotSafeMap {
    public static void main(String[] args) {
        // DON'T DO THIS
        Map<String, String> map = new HashMap<>();
        // HashMap has 2 factors:
        // 1. DEFAULT_LOAD_FACTOR = 0.75
        // 2. int initialCapacity default is 16
        // the default capacity is too small, if you have at least 100 data
        // you should use new HashMap<>(100, 0.75f)
        for (int i = 0; i < 60; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), Thread.currentThread().getName());
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
// ConcurrentModificationException
// use ConcurrentHashMap