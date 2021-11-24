public class DataPackage {

    private final int destinationNode;
    private final String data;
    private final long startTime;


    DataPackage(int destinationNode, String data) {
        this.destinationNode = destinationNode;
        this.data = data;
        this.startTime = System.nanoTime();
    }


    public int getDestinationNode() {
        return destinationNode;
    }


    public long getStartTime() {
        return startTime;
    }


    public String getData() {
        return data;
    }

}
