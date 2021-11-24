import java.util.ArrayList;
import java.util.List;


public class BufferStack<DataPackage> {

    private final ArrayList<DataPackage> bufferStack;
    private final int numProcessingData;


    BufferStack(int numProcessingData) {

        bufferStack = new ArrayList<DataPackage>();
        this.numProcessingData = numProcessingData;
    }


    // Thread safe
    public synchronized void insertBuffer(DataPackage data) {

        bufferStack.add(data);
    }


    // Thread safe
    public synchronized List<DataPackage> getBuffer() {
        List<DataPackage> processingData = new ArrayList<DataPackage>();

        for (int i = 0; i < numProcessingData; i++) {
            if (!bufferStack.isEmpty())
                processingData.add(bufferStack.remove(0));
        }

        return processingData;
    }


    public synchronized boolean isEmpty() {

        return bufferStack.isEmpty();
    }

}
