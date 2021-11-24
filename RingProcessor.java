import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


/**
 * In the constructor, the ring is initialized, that is, all the nodes and data on the nodes are created.
 * In the {@link RingProcessor # startProcessing ()} method, the ring is started -
 * the data starts processing clockwise. Also, logging to {@linkRingProcessor#logs}.
 * happens. All work must be thread safe and handle all possible exceptions.
 * If necessary, you can create your own exception classes.
 */
public class RingProcessor {

    private final int nodesAmount;
    private final int dataAmount;

    private final FileHandler logs;
    private final Logger logger;

    private List<Node> nodeList;

    /**
     * A record of the transit time of each data package.
     * Used in {@link RingProcessor # averageTime ()} to calculate average time
     * of reaching the coordinator by the data.
     */
    List<Long> timeList;


    RingProcessor(int nodesAmount, int dataAmount, FileHandler logs) {
        this.nodesAmount = nodesAmount;
        this.dataAmount = dataAmount;
        this.logs = logs;

        logger = Logger.getLogger("ringLogger");
        logger.addHandler(logs);

        init();
    }


    // Computation of the average traversing time.
    private long averageTime() {

        return 0;
    }


    //initialize ring
    private void init() {
        nodeList = new ArrayList<Node>();
        int coordinator = (int) (Math.random() * nodesAmount);

        for (int i = 0; i < nodesAmount; i++) {
            Node node = new Node(i, coordinator, nodeList, logger, nodesAmount * dataAmount);
            nodeList.add(node);
        }

        for (int i = 0; i < nodesAmount; i++) {
            for (int j = 0; j < dataAmount; j++) {
                int destinationNode;
                do
                    destinationNode = (int) (Math.random() * nodesAmount);
                while (destinationNode == i);

                String data = "Message from " + i + " to " + destinationNode;
                DataPackage dataPackage = new DataPackage(destinationNode, data);
                nodeList.get(i).setData(dataPackage);
            }
        }
    }


    public void startProcessing() {
        Thread nodeThread[] = new Thread[nodesAmount];

        for (int i = 0; i < nodesAmount; i++) {
            nodeThread[i] = new Thread(nodeList.get(i));
            nodeThread[i].start();
        }

        for (int i = 0; i < nodesAmount; i++) {
            try {
                nodeThread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
