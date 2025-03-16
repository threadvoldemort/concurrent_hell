package jvm;

public class HeapDemo {
    public static void main(String[] args) {
        // default heap size
        // the max memory jvm try to use(usually 0.25 of physical memory)
        double maxMem = (double) Runtime.getRuntime().maxMemory()/1024/1024;
        // jvm default init memory size(usually 0.015 of physical memory)
        double totalMem = (double) Runtime.getRuntime().totalMemory()/1024/1024;
        System.out.println("max memory: " + maxMem + " MB");
        System.out.println("total memory: " + totalMem + " MB");

        // customize heap size
        // biggest allocated memory -> -Xmx:1024m
        // init allocated memory    -> -Xms:1024m
        // in Java 21 is -Xmx1024m -Xms1024m
    }
}
