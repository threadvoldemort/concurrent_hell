# generation
    - young: often gc(use copy algo)
    - old: rare gc(use tag, sweep and compact algo)
    - perm: no gc

    gc not always scan all 3 areas at a time, most of the time only scan young
    full gc will scan old area

# gc 4 algo
    - reference count(deprecated):
        every object has a reference counter, if the conter is zero will be gc
        issue: 
            1. might have circulated reference
            2. tedious to manage the counter

    - copy algo(used in young area)
        copy from from area to to area, from and to switch, who is empty who is to
        copy one time age + 1, > 15 will enter old area
        advantage:
            no need to tag and delete, and no memory fraction 
        disadvantage:
            to area will always empty, waste half of memory space
        this algo is good for young area because most objects are short-lived

    - tag, sweep and compact algo
        tag the alive object, delete other objects, before deletion need to scan again
        advantage: 
            no need extra memory space
        disadvantage:
            need to scan twice, usually the size of old area is double of young area, 
            so it is more time consuming, and will have memory fraction issue
            so need to compact the memory space(second scan)
        for the area which most objects no need to be gc, like old area, is suitable for this algo

# conclusion
    - memory efficiency(time complexity): copy > tag and sweep > tag, sweep and compact 
    - memory tidy: copy = tag, sweep and compact > tag and sweep
    - memory usage rate: tag, sweep and compact = tag and sweep > copy

    from efficiency perspective, copy is the best but waste of space
    from a practical perspective, tag, sweep and compact is better, although efficiency not ideal
    there is no best way to do gc, different area use suitable algo

# gc root
    reachable algo to determine which object is garbage
    who is gc root:
        1. object refered from vm stack
        2. object refered by static variable
        3. constnat in method area
        4. object refered by native mrthod in local memory stack

