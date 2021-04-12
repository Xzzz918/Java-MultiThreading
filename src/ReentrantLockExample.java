import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gemini
 * Created in  2021/4/12 19:56
 * Java并发编程的艺术Page 50
 * 锁内存语义的实现
 */
public class ReentrantLockExample {
    int a = 0;
    ReentrantLock lock = new ReentrantLock();

    public void writer(){
        lock.lock();    //获取锁
        try {
            a++;
        }finally {
            lock.unlock();  //释放锁
        }
    }

    public void reader(){
        lock.lock();    //获取锁
        try{
            int i = a;
        }finally {
            lock.unlock();  //释放锁
        }
    }

    public static void main(String[] args) {

    }
}
