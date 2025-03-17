# see byte code
    javap -c xxx.class to see the decompiled code
# see GC info
    -XX:+PrintGCDetails
# list all running Java process
    jps -l
# check a Java process has certain param opened
    i.e. check if flag "PrintGCDetails" is added on process which pid is 99887766
    jinfo -flag PrintGCDetails 99887766
    if return "-XX:-PrintGCDetails" means it is off
# check meta space size
    jinfo -flag MetaspaceSize 99887766
# list all default flag
    jinfo -flags 99887766
# print all default flags value
    java -XX:+PrintFlagsInitial
# print all flags value(:= means has modified)
    java -XX:+PrintFlagsFinal
# print all command line related flags
    java -XX:+PrintCommandLineFlags


