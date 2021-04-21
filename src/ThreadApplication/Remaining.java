package ThreadApplication;


/**
 * @author gemini
 * Created in  2021/4/21 16:05
 */
public class Remaining {
    public synchronized Object get(long mills) throws InterruptedException{
        Object result = null;
        long future = System.currentTimeMillis() + mills;
        long remaining = mills;
        //当超时大于0并且result返回值不满足要求时
        while ((result == null) && remaining > 0){
            wait(remaining);
            remaining = future - System.currentTimeMillis();
        }
        return result;
    }
}
