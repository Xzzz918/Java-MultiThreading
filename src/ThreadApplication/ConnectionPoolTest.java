package ThreadApplication;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gemini
 * Created in  2021/4/21 16:44
 */
public class ConnectionPoolTest {
    static int initialPoolSize = 10;
    static ConnectionPool pool = new ConnectionPool(initialPoolSize);
    //  保证所有ConnectionRunner能够同时开始
    //  关于CountDownLatch：一种同步帮助，它允许一个或多个线程等待，直到在其他线程中执行的一组操作完成为止。
    static CountDownLatch start = new CountDownLatch(1);
    //  main线程在所有ConnectionRunner结束后才能继续进行
    static CountDownLatch end;

    public static void main(String[] args) throws Exception {
        //线程数量
        int threadCount = 20;
        end = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();
        end.await();
        System.out.println("Connection pool size: " + initialPoolSize);
        System.out.println("thread numbers: " + threadCount);
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection: " + got);
        System.out.println("notGot connection: " + notGot);
        System.out.println("rate of got connection: " + (float) got.get() / (threadCount * count));
    }

    static class ConnectionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (Exception e) {
            }
            while (count > 0) {
                try {
                    //从线程池中获取连接，如果1000ms内未获取到，将会返回null
                    //分别统计连接获取的数量got和未获取的数量notGot
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            try {
                                connection.createStatement();
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }
                            try {
                                connection.commit();
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }

}
