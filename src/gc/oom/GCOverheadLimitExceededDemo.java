package gc.oom;

import java.util.ArrayList;

// -Xms10m -Xmx10m -XX:MaxDirectMemorySize=5m(local memory size)
// -XX:+PrintGCDetails
public class GCOverheadLimitExceededDemo {
    public static void main(String[] args) throws Throwable {
        int i = 0;
        var list = new ArrayList<String>();
        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Error e) {
            System.out.println("i => " + i);
            e.printStackTrace();
            throw e;
        }

    }
}
