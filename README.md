# TokenRing

Java homework. Multithreading

What do you need to do:
• Implement functionality based on the requirements below
• Unit tests, and JavaDoc for non-trivial methods and classes. Each unit test shall provide a test scenario for a separate method. For example: “transfer” method processes data from “n” to “n+1” node. In the unit test if before “transfer” invocation the data located on the node “n”, after “transfer” the
data shall be on the node “n+1”.
• Connecting all dependencies and building the code into a JAR file using
Maven.
• Create 2 branches in your github repo: master and dev. Develop and push
the code to the dev branch. Once the code is ready, make a PR to master and ask me for a review

You have to implement the TokenRing protocol: the nodes form a ring topology, data can be transmitted clockwise from one node to the n-th, after which the transmission goes from the n- th to the 1st, if the n-th node is not the destination node.
Each node is an instance of the Node class, the main view of which is shown below. Please, pay attention that each node is a separate thread. The transmitted information is also a separate class DataPackage, which contains the time when the information was created (necessary to take into account statistics of the average delivery time), the destination node and a random string value, which is the "data".
Data is "processed" at the node - that is, between the moments of receiving and further transmitting data, the node holds the data package on itself for some time, the node thread falls asleep for 1 millisecond.
The node initially stores all data in a buffer (BufferStack class), which must be thread safe. That is, when the data arrives at the node, it is written to the BufferStack. Each node can process a limited amount of data (for example, 3) - if more data arrives at the node (for example, 4), one package waits in the buffer until the node is free to receive it.
The number of nodes, the initial amount of data on each node, which will then go in a circle, as well as the file for writing logs are provided as parameters to the RingProcessor. The data does not run in a circle forever (otherwise the meaning of the task is lost). Each data package has a destination node. When the data package reaches the destination, the destination node writes it to the coordinator node, which saves it to its collection. The coordinator node is selected randomly when the ring is initialized. Each node has an id, thus the data package has an id of its destination node, and each node has an id of the coordinator node for the whole ring.
The entire process of the program should be logged. Logs should contain:
• Beginning of the work. How many nodes are based on the topology and the amount
of data on each node. Buffer size. Coordinator number.
• Logging each data package transmission, from which node “n” to which node
“n+1” it was transmitted. That is, every act of transmitting a data package must be recorded.
• At the end of the work: recording the average network latency (how long does it take for a package with data to reach the destination node)
• Fixing the average latency in the buffer (how long, on average, a node located in the buffer)
• Logging can be performed using a standard tool from java.util.logging.Logger.
• The program ends when the last data package has reached its destination (corresponding node) and has been saved on the coordinator. Thus, there should be the check that the initial number of data packages equals the number of data
packages in the destination node.
