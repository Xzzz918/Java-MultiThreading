import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

/**
 * @author gemini
 * Created in  2021/4/19 9:38
 */
public class Interrupted {
    static class SleepRunner implements Runnable{
        @Override
        public void run() {
            while (true){
                SleepUtils.second(10);
            }
        }
    }

    static class BusyRunner implements Runnable{
        @Override
        public void run() {
            while (true){

            }
        }
    }

    public static void main(String[] args) throws Exception{
        //sleepThread try to sleep all time
        Thread sleepThread = new Thread(new SleepRunner(),"SleepThread");
        sleepThread.setDaemon(true);
        //busyThread running all time
        Thread busyThread = new Thread(new BusyRunner(),"BusyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();
        //sleep 5 seconds to make sleepThread and busyThread running sufficiently
        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        //prevent sleepThread and busyThread exit at a moment
        SleepUtils.second(2);
    }
}
