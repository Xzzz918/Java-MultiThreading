import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author gemini
 * Created in  2021/4/19 19:43
 * 代码清单4-11
 */
public class WaitNotify {
    static boolean flag = true;
    static Object lock = new Object();

    static class Wait implements Runnable {
        @Override
        public void run() {
            //Add Lock. it has lock's Monitor.
            synchronized (lock) {
                //when the condition is not satisfied,keep waiting and free lock's Lock;
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + " flag is true.wait @ " +
                                new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //while satisfying the condition, complete next work.
                System.out.println(Thread.currentThread() + " flag is false.running @ " +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable{
        @Override
        public void run() {
            //Add Lock. it has lock's Monitor.
            synchronized (lock){
                //获取lock的锁，然后进行通知，通知时不会释放lock的锁，
                //直到当前线程释放了lock后，WaitThread才能从wait方法中返回
                System.out.println(Thread.currentThread() + " hold back. notify @ " +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }
            //Add Lock again
            synchronized (lock){
                System.out.println(Thread.currentThread() + " hold back again. sleep @ " +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    public static void main(String[] args) throws Exception{
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }
}
/*Thread[WaitThread,5,main] flag is true.wait @ 20:09:27
Thread[NotifyThread,5,main] hold back. notify @ 20:09:28
Thread[NotifyThread,5,main] hold back again. sleep @ 20:09:33
Thread[WaitThread,5,main] flag is false.running @ 20:09:33*/