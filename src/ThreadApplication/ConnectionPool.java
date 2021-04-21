package ThreadApplication;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author gemini
 * Created in  2021/4/21 16:14
 */
public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0){
            for (int i = 0; i < initialSize; i++) {
                //在链表后端插入元素
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    //将连接放回线程池
    public void releaseConnection(Connection connection){
        if (connection != null){
            synchronized (pool){
                //连接释放后需要通知其他消费者连接池已经归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    //在mills里无法获取到连接，将会返回null
    public Connection fetchConnection(long mills) throws InterruptedException{
        synchronized (pool){
            //完全超时
            if (mills <= 0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }
            else {
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0){
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
