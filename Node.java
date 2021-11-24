import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class Node implements Runnable {

    private final static int NUM_PROCESSING_DATA = 3;

    private final int nodeId;
    private final int corId;
    private List<Node> nodeList;
    private Logger logger;
    private BufferStack<DataPackage> bufferStack = new BufferStack<DataPackage>(NUM_PROCESSING_DATA);
    private int totalDataPackages;

    private List<DataPackage> allData;


    Node(int nodeId, int corId, List<Node> nodeList, Logger logger, int totalDataPackages) {
        this.nodeId = nodeId;
        this.corId = corId;
        this.nodeList = nodeList;
        this.logger = logger;
        this.totalDataPackages = totalDataPackages;

        if (nodeId == corId)
            allData = new ArrayList<DataPackage>();
    }


    public long getId() {

        return nodeId;
    }


    public void setData(DataPackage dataPackage) {

        bufferStack.insertBuffer(dataPackage);
    }

    public BufferStack<DataPackage> getBuffer() {

        return bufferStack;
    }


    // Thread safe
    public synchronized void setToAllData(DataPackage dataPackage) {
        System.out.println("Storing data: " + dataPackage.getData());
        // logger.info("Storing data: " + dataPackage.getData());
        allData.add(dataPackage);
    }


    // Thread safe
    public synchronized boolean AllDataReceived() {
        return (allData.size() == totalDataPackages);
    }

    /**
     * Start of the node. I.e., a package with data is taken from Node.bufferStack
     * and is sent for processing. After that it is transferred to the next node.
     * Here lies the logic according to which only 3 data packages can be processed at the same time.
     */
    @Override
    public void run() {
        Node sigNode = nodeList.get( (nodeId + 1) % (nodeList.size()) );
        Node coordNode = nodeList.get(corId);

        while (!coordNode.AllDataReceived()) {
            while (!bufferStack.isEmpty()) {
                List<DataPackage> dataPackages = bufferStack.getBuffer();

                for (DataPackage dataPackage : dataPackages) {
                    try {
                        Thread.sleep(1);
                        if (dataPackage.getDestinationNode() == nodeId) {
                            if (nodeId == corId)
                                setToAllData(dataPackage);
                            else
                                coordNode.setToAllData(dataPackage);
                        } else {
                            sigNode.setData(dataPackage);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
