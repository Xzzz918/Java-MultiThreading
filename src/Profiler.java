import java.util.concurrent.TimeUnit;

/**
 * @author gemini
 * Created in  2021/4/19 20:46
 */
public class Profiler {
    //第一次get方法调用时会发生初始化（如果set方法没有调用），每个线程会调用一次
    private static final ThreadLocal<Long> TIME_THREADLOCAL = ThreadLocal.withInitial(System::currentTimeMillis);

    public static void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static long end(){
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + Profiler.end() + " mills.");
    }
}
