/**
 * @author gemini
 * Created in  2021/4/16 20:19
 */
public class Daemon {
    static class DaemonRunner implements Runnable{
        @Override
        public void run() {
            try {
                SleepUtils.second(10);
            }finally {
                System.out.println("DaeminThread finally run.");
            }
        }
    }
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(),"DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }
}
