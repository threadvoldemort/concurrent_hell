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

      










