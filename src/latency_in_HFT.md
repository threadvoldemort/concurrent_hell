# how to solve latency issue when building a HFT system using Java

Building low-latency HFT systems in Java requires a paradigm shift from typical Java development, focusing on JVM internals, memory management, and hardware optimization. Here's a structured approach:

### Core Principles for Low-Latency Java

**1. Eliminate Garbage Collection Jitter**

- **Avoid object allocation** in hot paths using object pooling (e.g., `ThreadLocal` caches, reusable buffers)[^1_1][^1_4].
- **Off-heap memory**: Store data directly in native memory via libraries like Chronicle Map or Aeron’s Simple Binary Encoding (SBE)[^1_1][^1_5][^1_6].
- **Tuning GC**: Use low-pause collectors like ZGC or Shenandoah with flags like `-XX:+UseNUMA` for NUMA-aware memory allocation[^1_1][^1_4].

**2. Thread and CPU Optimization**

- **Single-threaded hot paths**: Dedicate CPU cores to critical threads (via `taskset` or `isolcpus`), avoiding thread synchronization[^1_1][^1_3].
- **Spin waits**: Use busy-wait loops (e.g., `while (!condition) {}`) instead of `LockSupport.parkNanos()` to prevent context switches[^1_1].
- **CPU affinity and cache locality**: Align data structures to cache lines (64/128 bytes) and use `@sun.misc.Contended` to avoid false sharing[^1_3][^1_4].


### Key Libraries and Frameworks

| Tool | Purpose | Latency Impact |
| :-- | :-- | :-- |
| **Aeron** | UDP/TCP messaging with zero-copy | Sub-microsecond IPC[^1_1][^1_6] |
| **Disruptor** | Lock-free ring buffer for event processing | 10x faster than `ArrayBlockingQueue`[^1_5] |
| **Chronicle Queue** | Persistent, off-heap data storage | Nanosecond-level reads[^1_1] |
| **OpenHFT** | Low-level memory utilities (e.g., `Bytes`) | Direct memory access[^1_3] |

**Example Networking Setup with Aeron**

```java
// Aeron configuration for low-latency UDP
final MediaDriver.Context ctx = new MediaDriver.Context()
    .dirDeleteOnStart(true)
    .threadingMode(ThreadingMode.DEDICATED)
    .conductorIdleStrategy(new BusySpinIdleStrategy())
    .receiverIdleStrategy(new NoOpIdleStrategy());
```


### JVM and Runtime Tactics

- **Warm-up phase**: Preload historical data to force JIT optimization of critical code paths[^1_1][^1_4].
- **Tiered compilation disabled**: Use `-XX:-TieredCompilation` to skip lower optimization tiers[^1_4].
- **Profile-guided optimization**: Collect JIT logs with `-XX:+PrintCompilation` and `-XX:+LogCompilation`[^1_3].


### Hardware and OS Adjustments

- **Kernel-bypass networking**: Use SolarFlare’s OpenOnload with `onload` preload library for userspace TCP stack[^1_1][^1_6].
- **Huge pages**: Enable via `-XX:+UseLargePages` to reduce TLB misses[^1_3].
- **IRQ balancing**: Isolate network interrupts to non-critical CPU cores[^1_1].


### Anti-Patterns to Avoid

- **Mutable vs. Immutable**: Prefer mutable objects (reused buffers) over immutable to reduce allocation[^1_1][^1_5].
- **Boxed primitives**: Replace `Integer`/`Double` with `int`/`double` to avoid heap overhead[^1_3][^1_4].
- **System calls**: Replace logging with `ChronicleLogger` (asynchronous, off-heap) instead of `log4j`[^1_1].

**Latency Comparison**
Typical optimizations yield:

- **Unoptimized Java**: 100–500µs per trade
- **Tuned Java**: 25–50µs (comparable to C++ in some cases)[^1_1][^1_7]

For ultra-low latency (sub-10µs), consider hybrid architectures: critical path logic in C++/Rust (via JNI or IPC) with Java handling higher-level strategy[^1_1][^1_7]. Profile continuously with tools like **JMH** and **PerfSee** to detect regressions from GC, thread contention, or cache misses.

[^1_1]: https://www.reddit.com/r/java/comments/1ctpebe/low_latency/

[^1_2]: https://stackoverflow.com/questions/9951501/high-frequency-trading-in-the-jvm-with-scala-akka

[^1_3]: https://www.youtube.com/watch?v=QfvtxTtD2oQ

[^1_4]: https://www.youtube.com/watch?v=FpDLN_GE77U

[^1_5]: https://masteranyfield.com/2023/07/04/unveiling-the-ultimate-secret-of-high-concurrency-how-java-thrives-in-high-frequency-trading/

[^1_6]: https://github.com/aeron-io/simple-binary-encoding/wiki/Why-Low-Latency

[^1_7]: https://www.efinancialcareers.com/news/2020/11/low-latency-java-trading-systems

