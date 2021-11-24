import java.io.IOException;
import java.util.logging.FileHandler;


public class Main {

    public static void main(String[] args) {
        String logFile = "tokenRingLog.txt";

        RingProcessor processor = null;
        try {
            processor = new RingProcessor(10, 3, new FileHandler(logFile));
            processor.startProcessing();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
