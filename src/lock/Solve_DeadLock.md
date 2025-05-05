# step 1: get process id
   1. run the deadlock code
   2. go to jdk -> bin -> jps.exe, or directly type "jps -l" in terminal
      to check process id

# step 2: check stack information
   1. run "jstack {process id}" in terminal
   2. from the output, search "BLOCKED"
   3. you will log like:
      - waiting to lock <0x0000000445538bd8> (a java.lang.String)
      - locked <0x0000000445538ba8> (a java.lang.String)
      it tells you what is locked and what it is waiting for
      the error stack also contains thread name
   4. you can also search "Found one Java-level deadlock"

# step 3: you can also run "jconsole" in the terminal
# step 4: can use jProfiler in intellij