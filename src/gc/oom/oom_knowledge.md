# normal
    stackoverflow, outofMemoryError: Java heap space

# special
    - java.lang.OutOfMemoryError: GC overhead limit exceeded
      if GC takes too long time to run or gc keep running it will also cause oom error

    - java.lang.OutOfMemoryError: Direct buffer memory
      if local memory allocated is full this error will be thrown

    - java.lang.OutOfMemoryError: unable to create native Thread
      this error will happen in high concurrency scinerio, and highly related
      to platform
      root cause:
        1. application create too many threads
        2. server stop application from creating too many threads
    
    - java.lang.OutOfMemoryError: Metaspace
      after java 8 use metaspace, it is in local memory, if create too many classes
      this error will be thrown
      if you need to create a lot of classes, enlarge the metaspace
      

