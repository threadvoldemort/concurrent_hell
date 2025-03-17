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

# method area(is spec not implementation, method area also called "non-heap")
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
    - before jdk7:
        one jvm instance only has one heap, heap size is adjustable
        it saves: 
            a) constant
            b) class info
            c) method
            d) real information of type reference
        heap has 3 parts:
            a) young area(the place a class is born, grow and vanish):
                young area has 3 parts:
                    1. eden: 
                        all instances get newed here, when eden is full a light-weight gc will be triggered
                        those objects which not gargabe collected will be put into survive area
                        99% of objects will be gc in eden
                    2. survive0(from)
                    3. survive1(to)
            b) old area:
                objects not gargabe collected after 15 times(can change) gc will be put into old area
                when old area is full a full gc will be triggered
            c) perm(after jdk8 become metaspace):
                jdk class, interface metadata are stored here, this area won't run gc
                this area could have outofMemoryError: PermGen
                if the jdk loads too many third party package this might happen
                the size of perm is adjustable(no more permgen after jdk8)

        GC mainly act in young and old area, GC has 2 types, normal GC and full GC
        if heap is full OOM will be thrown

    - optimization: see HeapDemo.java
    - OOM: outofMemoryError: Java heap space

    - dump:
        jdk/bin/jconsole.exe
        Eclipse MAT plugin
        Idea Jprofiler
        -XX:+HeapDumpOnOutofMemory -> will create a .hprof file can open via jprofiler

# jvm param type(3 types)
    - standard param: 
        i.e. -version, -help, etc
    - -X param(very rare use):
        i.e. -Xint, -Xcomp, -Xmixed, etc
    - -XX param:
        "+" means on, "-" means off
    - alias:
        -Xms initial heap size, equals to -XX:InitialHeapSize
        -Xmx maximum heap size, equals to -XX:MaxHeapSize

# most frequent used param
    -Xms
    -Xmx
    -Xss thread stack size default 512k~1024k
    -Xmn young area size rare adjust
    -XX:MetaspaceSize rare adjust
    -XX:+PrintGCDetails
    -XX:SurvivorRatio default is 8, this is the ratio of s0 and s1 size in young area
        default 8 means eden:s0:s1 is 8:1:1 rare adjust
    -XX:NewRatio the ratio of young area to old area, defaul is 2
        means young area is 1 and old area is 2, means young area size is 1/3
        of the whole heap size
    -XX:MaxTenuringThreashold the threashold to enter old area default is 15
    







