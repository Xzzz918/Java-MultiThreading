import java.util.concurrent.TimeUnit;

/**
 * @author gemini
 * Created in  2021/4/19 9:55
 */
public class ShutDown {
    public static class Runner implements Runnable{
        private long i;
        private volatile boolean on = true;
        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println("Count i = " + i);
        }

        public void cancel(){
            on = false;
        }
    }

    public static void main(String[] args) throws Exception{
        Runner first = new Runner();
        Thread counterThread1 = new Thread(first, "CountThread1");
        counterThread1.start();
        //睡眠一秒，main线程对CountThread1线程进行中断，使CountThread1线程能够感知中断而结束。
        TimeUnit.SECONDS.sleep(1);
        counterThread1.interrupt();
        Runner second = new Runner();
        Thread counterThread2 = new Thread(second, "CountThread2");
        counterThread2.start();
        //睡眠一秒，main线程对Runner second进行取消，使CountThread2线程能够感知on为false而结束。
        TimeUnit.SECONDS.sleep(1);
        second.cancel();

    }
}
