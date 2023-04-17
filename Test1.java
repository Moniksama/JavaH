import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class Test1 {
    //String logFile1 = "tokenLog.txt";

    DataPackage paquete = new DataPackage(1, "hola");
    BufferStack<DataPackage> buffer1 = new BufferStack<>(3);

    @Test
    public void testSimple() {
        assertEquals(true, buffer1.isEmpty());
    }

    @Test
    public void testSimple2() {
        BufferStack<DataPackage> buffer2 = new BufferStack<>(2);
        buffer2.insertBuffer(paquete);
        assertEquals(false, buffer2.isEmpty());
    }

    @Test
    public void testSimple3() {
        final FileHandler logs;
        final Logger logger;
        logger = Logger.getLogger("nodeLogger");
        List<Node> nodeList;
        nodeList = new ArrayList<Node>();
        int coordinator = (int) (Math.random() * 2);
        Node node = new Node(1, 1, nodeList, logger, 1);
        node.setToAllData(paquete);
        assertEquals(true, node.AllDataReceived());

    }
}
