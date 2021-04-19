import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author gemini
 * Created in  2021/4/19 20:16
 * 代码清单4-12
 */
public class Piped {
    static class Print implements Runnable{
        private PipedReader in;
        public Print(PipedReader in){
            this.in = in;
        }
        @Override
        public void run() {
            int receive;
            try {
                while ((receive = in.read()) != -1){
                    System.out.print((char) receive);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        PipedReader in = new PipedReader();
        PipedWriter out = new PipedWriter();
        //将输入流和输出流进行连接，否则在使用时会抛出IOException
        out.connect(in);
        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        int receive;
        try {
            while ((receive = System.in.read()) != -1) {
                out.write(receive);
            }
        }finally {
            out.close();
        }

    }
}
