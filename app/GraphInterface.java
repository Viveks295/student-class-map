import java.util.List;
import java.util.NoSuchElementException;
 
public interface GraphInterface<T> {
 
    /**
     * This function creates a new node in the graph.
     * You can create node without any data.
     * @param data Data of the node.
     * @return ID of the node created.
     */
    public int createNode(T data);
 
    /**
     * This function links two nodes in the graph, along with the weight of the edge.
     * @param nodeOneID ID of the first node.
     * @param nodeTwoID ID of the second node.
     * @param weight Weight of the edge.
     * @throws NoSuchElementException if the nodes are not found.
     * @throws IllegalArgumentException if the nodes are already linked.
     */
    public void linkNodes(int nodeOneID, int nodeTwoID, double weight)
            throws NoSuchElementException, IllegalArgumentException;
 
    /**
     * This function unlinks two nodes in the graph.
     * @param nodeOneID ID of the first node.
     * @param nodeTwoID ID of the second node.
     * @throws NoSuchElementException if the nodes are not found.
     */
    public void unlinkNodes(int nodeOneID, int nodeTwoID) throws NoSuchElementException;
 
    /**
     * This function list all the id of the nodes in the graph.
     */
    public List<Integer> listNodes();
 
    /**
     * This function gets the node with the given id.
     * @param id id of the node.
     * @return Node with the given id.
     * @throws NoSuchElementException if the node is not found.
     */
    public NodeInterface<T> getNode(int id) throws NoSuchElementException;
 
    /**
     * This function deletes the node with the given id.
     * It also unlinks the node from all the other nodes.
     * @param id id of the node.
     * @throws NoSuchElementException if the node is not found.
     */
    public void deleteNode(int id) throws NoSuchElementException;
 
    /**
     * This function finds the shortest path between two nodes in the graph
     * using Dijkstra's algorithm. You should use the functions in the Edge
     * interface to get the weight and the nodes of the path.
     * @param startID The ID of the starting node.
     * @param endID The ID of the ending node.
     * @return A list of edges representing the shortest path between the two nodes.
 
     * @throws NoSuchElementException if either the start node or end node is not found.
     */
    public List<EdgeInterface<T>> findShortestPath(int startID, int endID) throws NoSuchElementException;
 
    /**
     * This function returns a string representation of the graph.
     * @return String representation of the graph.
     */
    public String toString();
}
