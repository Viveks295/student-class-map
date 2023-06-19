import java.util.List;
import java.util.NoSuchElementException;
 
public interface NodeInterface<T> {
 
    /**
     * This function returns the ID of the node.
     * @return ID of the node.
     */
    public int getID();
 
    /**
     * This function returns the data of the node.
     * @return Data of the node.
     */
    public T getData();
 
    /**
     * This function sets the data of the node.
     * @param data: Data of the node.
     */
    public void setData(T data);
 
    /**
     * This function returns a list of adjacent nodes.
     * @return List of adjacent nodes.
     */
    public List<NodeInterface<T>> getAdjNodes();
 
    /**
     * This function links the node to another node.
     * @param node: the node to link to.
     * @param weight: the weight of the edge.
     * @throws IllegalArgumentException if the node is already linked to the other node.
     * @throws NoSuchElementException if the node is not found.
     */
    public void linkNode(NodeInterface<T> node, double weight) throws IllegalArgumentException, NoSuchElementException;
 
    /**
     * This function unlinks the node from another node.
     * @param node: the node to unlink from.
     * @throws NoSuchElementException if the node is not found.
     * @throws IllegalArgumentException if the node is not linked to the other node.
     */
    public void unlinkNode(NodeInterface<T> node) throws IllegalArgumentException;
 
    /**
     * This function checks if the node is linked to another node.
     * @param node: ID of the node to check.  
     * @return True if the node is linked to the other node, false otherwise.
     * @throws NoSuchElementException if the node is not found.
     */
    public boolean isLinked(NodeInterface<T> node) throws NoSuchElementException;
 
    /**
     * This function returns the weight of the edge between the node and another node.
     * @param node: the node to check.
     * @return Weight of the edge between the node and the other node.
     * @throws NoSuchElementException if the node is not found.
     * @throws IllegalArgumentException if the node is not linked to the other node.
     */
    public double getWeight(NodeInterface<T> node) throws NoSuchElementException, IllegalArgumentException;
 
    /**
     * This function returns a string representation of the node.
     * @return String representation of the node.
     */
    @Override
    public String toString();
 
    /**
     *  This function check if the node is equal to another node.
     *  @param obj: the node to compare to.
     *              It can be a Node object or an Integer object.
     *              If it is an Integer object, it will be compared to the ID of the node.
     *              If it is a Node object, it will be compared to the ID of the node.
     * @return True if the node is equal to the other node, false otherwise.
     */
    @Override
    public boolean equals(Object obj);
}
