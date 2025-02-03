// nowadays not been used very often
// this technique is used on the computer which has a slower CPU
// or single core CPU
// same idea as map reduce
// fork join only be used when input is very big
// core logic: work stealing, elements in the queue it uses can be fetched from
// both side
// parallel stream is better

import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo extends RecursiveTask {
    public static void main(String[] args) {

    }

    @Override
    protected Object compute() {
        return null;
    }
}
