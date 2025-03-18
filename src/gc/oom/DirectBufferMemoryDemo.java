package gc.oom;

import java.nio.ByteBuffer;

// -XX:MaxDirectMemorySize=5m -XX:+PrintGCDetails
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        // we allocate 5m for local memory but create ByteBuffer with 6m
        ByteBuffer.allocateDirect(6*1024*1024);
    }
}

// object allocated in local memory is not managed by jvm
// so it won't be gc
