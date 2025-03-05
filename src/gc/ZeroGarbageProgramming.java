package gc;

/*
Why Avoid Garbage Collection (GC) in Low-Latency Systems?
The JVM Garbage Collector (GC) periodically runs to clean up unused objects. However:
GC Pauses Can Be Unpredictable – Even with modern GCs like ZGC or Shenandoah,
the pause times can be non-deterministic.
Stop-the-World (STW) Events – Certain GC cycles trigger STW pauses,
freezing the application momentarily.
Latency Spikes – Even minor GC overhead (e.g., in G1GC) can introduce jitter in real-time trading.
Frequent Object Creation = More GC Work – Java’s automatic memory management
encourages object allocation, but in ultra-low-latency environments, this becomes a bottleneck.

Solution: Avoid GC by Writing Zero-Garbage Code
Instead of relying on Java’s automatic memory management, zero-garbage programming focuses on:
- Reducing object allocations
- Reusing objects efficiently
- Using off-heap memory when needed
*/

public class ZeroGarbageProgramming {

    // 1. Object Pooling Instead of Frequent Allocations
    // Instead of creating new objects frequently, reuse pre-allocated objects from a pool.
    // Reduces heap allocations and minimizes GC pressure.
    // Example: Use Disruptor's RingBuffer instead of Java’s ArrayBlockingQueue.


    // 2. Use ThreadLocal to Reuse Objects per Thread
    // ThreadLocal ensures each thread reuses its own object instead of creating new ones.
    // Useful for pre-allocated buffers and date formatters.

    // 3. Use Pre-Allocated Arrays Instead of ArrayList
    // ArrayList dynamically resizes and reallocates memory, causing GC overhead.
    // Use fixed-size arrays where possible.

    // 4. Use ByteBuffer for Off-Heap Memory
    // The JVM heap is managed by GC, but off-heap memory isn’t.
    // ByteBuffer.allocateDirect() avoids GC overhead by using direct memory allocation.

    // 5. Avoid Autoboxing & Use Primitive Types Efficiently
    // Java autoboxes primitives (e.g., int → Integer), leading to unnecessary object creation.
    // Avoid using boxed types (Integer, Double) in hot paths.

    // 6. Use High-Performance Libraries for Zero-GC
    // Instead of Java’s built-in collections, use:
    // Disruptor (by LMAX) – Lock-free ring buffer for event processing
    // Agrona – High-performance data structures
    // Chronicle Queue/Map – Off-heap message storage

    /*
      Zero-Garbage Programming is a must-have for low-latency, high-frequency trading systems.
      By reducing object allocations and minimizing GC interruptions, you ensure consistent execution
      times with minimal jitter.
     */
}
