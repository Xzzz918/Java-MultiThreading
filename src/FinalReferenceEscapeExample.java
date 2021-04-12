import java.time.chrono.ThaiBuddhistEra;

/**
 * @author gemini
 * Created in  2021/4/12 21:03
 * Java并发编程的艺术Page 60
 * final域 对象引用不能在构造函数中“逸出”
 */
public class FinalReferenceEscapeExample {
    final int i;
    static FinalReferenceEscapeExample obj;

    public FinalReferenceEscapeExample() {
        //以下语句1和语句2可能会发生重排序，从而在final域还没初始化之前就为线程B可见。
        i = 1;  //1
        obj = this; //2
    }

    public static void writer(){
        new FinalReferenceEscapeExample();
    }

    public static void reader(){
        if(obj != null){
            int temp = obj.i;
            System.out.println(temp);
        }
    }

    public static void main(String[] args) {
        new Thread(FinalReferenceEscapeExample::writer).start();
        new Thread(FinalReferenceEscapeExample::reader).start();
    }
}
