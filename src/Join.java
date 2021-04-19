import java.util.concurrent.TimeUnit;

/**
 * @author gemini
 * Created in  2021/4/19 20:34
 */
public class Join {
    public static void main(String[] args) throws InterruptedException {
        Thread pre = Thread.currentThread();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Domino(pre), String.valueOf(i));
            thread.start();
            pre = thread;
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName() + " terminate.");
    }

    static class Domino implements Runnable{
        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            }catch (InterruptedException e){

            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }
}
