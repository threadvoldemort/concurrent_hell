# jvm optimization
    - mainly adjust heap(99%)
    - method area no need to adjust
    - java stack, native method stack and program counter register won't have garbage

# native(java can't reach, will enter native method stack)
    - java can't create thread, it calls native method
    - unsafe class
    - Robot class(simulate mouse keyboard action)
    - jni

# program counter register
    - every thread has its own program counter register
    - javap -c command can see the counter in decompiled result
      i.e.
        
        Code:
            0: bipush    100
            2: istore_1
            3: sipush    200
            6: istore_2  
            7: sipush    300
            10: istore_3
        
        the number 0, 2, 3, 6, 7, 10 is program counter register

    - program counter register is a very small memory space
      it is the line number of bytecode
    - an tool called OD can do reverse engineering

# method area(is spec not implementation)
    - can shared by threads like heap
    - before jdk 7(method area is in PermGen):
        PermGen is used for saving class information which loaded by jvm
        constant, string, class info, symbolic reference, etc are saved in this area
        PermGen size is limited, when it is full OOM exception will be thrown
        error -> outofMemoryError: PermGen
      after jdk 8(method area is in metaspace):
        PermGen is removed from hotspot jvm, those stuff are allocated in heap or metaspace
        metaspace is also called native heap
        metaspace is the implementation of method area in hotspot jvm
        
      metaspace and PermGen are just the implementation of method area
      the biggest difference between PermGen and metaspace is metaspace is not in jvm
      it is in local memory

      metaspace size is adjustable, i.e. -XX:MetaspaceSize10m
      if metaspace is full -> outofMemoryError: MetaSpace

# stack
    - it is used for managing program
    - save object's reference, local variable in method, primitive types(int, double, short, etc)
    - speed is faster than heap
    - there is no garbage in stack
    - the life cycle is as same as thread, can not shared with other thread
    - stack is composed of stack frame:
        the idea is as same as the frame of video, i.e. 24 frames means 24 snapshots per second

# heap 


