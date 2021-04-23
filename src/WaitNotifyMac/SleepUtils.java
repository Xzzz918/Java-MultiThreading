import java.util.concurrent.TimeUnit;

/**
 * @author gemini
 * Created in  2021/4/19 9:39
 */
public class SleepUtils {
    public static final void second(long seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        }catch (InterruptedException e){

        }
    }
}
