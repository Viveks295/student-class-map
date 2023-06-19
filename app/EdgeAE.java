import java.util.NoSuchElementException;

public class EdgeAE<T> implements EdgeInterface<T> {
  //object variables
  public double weight; 
  public NodeInterface<T> nodeOne;
  public NodeInterface<T> nodeTwo;
  
  public EdgeAE(double weight, NodeInterface<T> nodeOne, NodeInterface<T> nodeTwo) {
      this.weight = weight;
      this.nodeOne = nodeOne;
      this.nodeTwo = nodeTwo;
  }
  
  /**
   * This function returns the first node connected by the edge.
   * @return the first node connected by the edge.
   */
  public NodeInterface<T> getNodeOne() {
    return this.nodeOne;
  }

  /**
   * This function returns the second node connected by the edge.
   * @return the second node connected by the edge.
   */
  public NodeInterface<T> getNodeTwo() {
    return this.nodeTwo;
  }

  /**
   * This function returns the other node connected by the edge.
   * @param node: the node to get the other node from.
   * @return the other node connected by the edge.
   * @throws NoSuchElementException if the node is not found.
   */
  public NodeInterface<T> getOtherNode(NodeInterface<T> node) throws NoSuchElementException {
    if(node.getID() == nodeOne.getID()) {
      return nodeTwo;
    } else {
      return nodeOne;
    }
  }

  /**
   * This function returns the weight of the edge.
   * @return the weight of the edge.
   */
  public double getWeight() {
    return weight;
  }
  
  //String is built like: --weight-->
  /**
   * This function returns a string representation of the edge.
   * @return a string representation of the edge.
   */
  @Override
  public String toString() {
    
    return "--" + weight + "-->";
  }
  
}
