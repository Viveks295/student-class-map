import java.util.NoSuchElementException;
 
public interface EdgeInterface<T> {
 
    /**
     * This function returns the first node connected by the edge.
     * @return the first node connected by the edge.
     */
    public NodeInterface<T> getNodeOne();
 
    /**
     * This function returns the second node connected by the edge.
     * @return the second node connected by the edge.
     */
    public NodeInterface<T> getNodeTwo();
 
    /**
     * This function returns the other node connected by the edge.
     * @param node: the node to get the other node from.
     * @return the other node connected by the edge.
     * @throws NoSuchElementException if the node is not found.
     */
    public NodeInterface<T> getOtherNode(NodeInterface<T> node) throws NoSuchElementException;
 
    /**
     * This function returns the weight of the edge.
     * @return the weight of the edge.
     */
    public double getWeight();
 
    /**
     * This function returns a string representation of the edge.
     * @return a string representation of the edge.
     */
    @Override
    public String toString();
}
