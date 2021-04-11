/**
 * @author gemini
 * Created in  2021/4/11 23:01
 * Java并发编程的艺术Page 05
 * 重排序对多线程的影响
 */
public class ReorderExample {
    int a = 0;
    boolean flag = false;

    public void writer(){
        //下两行语句之间无数据依赖关系，可以对其进行重排序
        a = 2;
        flag = true;
        System.out.println("a = " + a);
    }

    public void reader(){
        //下两行语句之间存在控制依赖关系
        if (flag){
            int i = a * a;
            System.out.println( "i = " + i);
        }
    }

    public static void main(String[] args) {
        ReorderExample r = new ReorderExample();
        Thread t1 = new Thread(r::writer);
        Thread t2 = new Thread(r::reader);
        t2.start();
        t1.start();
    }
}
