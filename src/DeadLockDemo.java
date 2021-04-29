import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gemini
 * Created in  2021/4/10 14:28
 * Java并发编程的艺术Page 05
 * 死锁
 * 以下代码会引起死锁，使线程T1和T2互相等待对方释放锁
 * 线程T1未能释放A锁；线程T2未能释放B锁，导致两个线程都无法继续运行下去，造成死锁。
 * 哪个线程能进入临界区，需要看synchronized究竟是对哪个对象加锁，
 * 修饰static方法是对.class对象加锁，
 * 修饰成员方法是对当前类的某个对象加锁，
 * 修饰代码块是对特定对象加锁。
 * 因此测试
 */
public class DeadLockDemo {

    private static String A = "A";
    private static String B = "b";
    public static void main(String[] args) {
        new DeadLockDemo().deadlock();
    }
    private void deadlock(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A){
                    System.out.println("T1 executed.");
                    try {
                        System.out.println("T1 sleep.");
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    synchronized (B){
                        System.out.println("1");
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B){
                    System.out.println("T2 executed.");
                    synchronized (A){
                        System.out.println("2");
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }


}
