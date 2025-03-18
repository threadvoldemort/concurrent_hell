package gc.oom;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

// idea: continuous creating new class
// case: the dynamic proxy in Spring
// -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -XX:+PrintGCDetails
public class MetaSpaceOOMDemo {
    public static void main(String[] args) {
        int i = 0; // mimic counter

        try {
            while (true) {
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(MetaSpaceOOMDemo.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object obj,
                                            Method method,
                                            Object[] args,
                                            MethodProxy proxy) throws Throwable {
                        return method.invoke(obj, args);
                    }
                });
                enhancer.create();
            }
        } catch (Exception e) {
            System.out.println("i => " + i);
            e.printStackTrace();
        }
    }
}
