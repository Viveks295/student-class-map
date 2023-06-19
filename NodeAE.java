import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class NodeAE<T> implements NodeInterface<T>{
  //object variables
  public int ID;
  public T data;
  public List<EdgeInterface<T>> edges;
  
  //node Constructor; ID for the building (used for storing nodes in a hash table) and the DATA to store inside the node. 
  //USE THE GRAPH TO CREATE NODES
  public NodeAE(int ID, T data){
    this.ID = ID;
    this.data = data;
    this.edges = new LinkedList<EdgeInterface<T>>();
  }
  
  
  /**
   * This function returns the ID of the node.
   * @return ID of the node.
   */
  public int getID() {
    return this.ID;
    
  }
  
  /**
   * This function returns the data of the node.
   * @return Data of the node.
   */
  public T getData() {
    return this.data;
  }
  
  /**
   * This function sets the data of the node.
   * @param data: Data of the node.
   */
  public void setData(T data) {
    this.data = data;
    
  }

  /**
   * This function returns a list of adjacent nodes.
   * @return List of adjacent nodes.
   */
  //adj nodes is the list of connected nodes
  public List<NodeInterface<T>> getAdjNodes() {
    
    List<NodeInterface<T>> connectedNodes = new LinkedList<NodeInterface<T>>();
    //take the list of edges, find the other nodes and add that to the list
    for(EdgeInterface<T> edge: edges) {
      connectedNodes.add(edge.getOtherNode(this));
    }
    
    return connectedNodes;
  }

  
  /**
   * This function links the node to another node.
   * @param node: the node to link to.
   * @param weight: the weight of the edge.
   * @throws IllegalArgumentException if the node is already linked to the other node.
   */
  public void linkNode(NodeInterface<T> node, double weight)
      throws IllegalArgumentException, NoSuchElementException {
    
    //throw error when nodes are already linked to eachother??? why
    if(this.isLinked(node)) {
      throw new IllegalArgumentException("the nodes are already linked");
    }
    
    //create a new edge, and then adds this to the edges list for both;
    EdgeInterface<T> newEdge = new EdgeAE<T>(weight, this, node);
    this.edges.add(newEdge);
    
    
  }
  
  
  
  /**
   * This function unlinks the node from another node.
   * @param node: the node to unlink from.
   * @throws NoSuchElementException if the node is not found.
   * @throws IllegalArgumentException if the node is not linked to the other node.
   */
  public void unlinkNode(NodeInterface<T> node)
      throws IllegalArgumentException{
    
    //throw error if the nodes are not connected
    //BD should handle the errors thrown
    if(!this.isLinked(node)) {
      throw new IllegalArgumentException("the nodes are not linked");
    }
    
    //search through the list of edges for the other node and edge
    //removes edge from edges if its there. 
    for(EdgeInterface<T> edge: edges) {
      if(edge.getOtherNode(this).equals(node)) {
        edges.remove(edge);
      }
    }
    
  }

  
  /**
   * This function checks if the node is linked to another node.
   * @param node: ID of the node to check.  
   * @return True if the node is linked to the other node, false otherwise.
   * @throws NoSuchElementException if the node is not found.
   */
  public boolean isLinked(NodeInterface<T> node) throws NoSuchElementException {
    //searches through the list of edges. Checks if otherNode function matches node
    for(EdgeInterface<T> edge: edges) {
      if(edge.getOtherNode(this).equals(node)) {
        return true;
      }
    }
    return false;
  }

  
  /**
   * This function returns the weight of the edge between the node and another node.
   * @param node: the node to check.
   * @return Weight of the edge between the node and the other node.
   * @throws NoSuchElementException if the node is not found.
   * @throws IllegalArgumentException if the node is not linked to the other node.
   */
  public double getWeight(NodeInterface<T> node) throws NoSuchElementException{
    // throws error if the nodes are not connected
    if(!this.isLinked(node)) {
      throw new IllegalArgumentException("the nodes are not linked");
    }
    //checks for an edge between the nodes by searching through edges. if found return weight
    for(EdgeInterface<T> edge: edges) {
      if(edge.getOtherNode(this).equals(node)) {
        return edge.getWeight();
      }
    }
    return -100000000; //will never reach here you will tell this is wrong if it does appear lol
  }
  
  /**
   * This function returns a string representation of the node.
   * @return String representation of the node.
   */
  @Override
  public String toString() {
    //going to list all nodes and their edges like this
    // (nodeA) --weight--> (nodeB) \n
    // (nodeA) --weight--> (nodeC) \n
    //if there are no edges just going to print
    // (nodeA) [no edges] \n
    String string = "";
    if(this.edges.isEmpty()) {
      //node has no edges
      string = "(" + this.ID + ")     [has no edges] \n";
    } else {
      for(EdgeInterface<T> edge: edges) {
        string += "(" + this.ID + ")  "
            + edge.toString()
            + "  (" + edge.getOtherNode(this).getID() + ") \n";
      }
    }
    return string;
  }

  /**
   *  This function check if the node is equal to another node.
   *  @param obj: the node to compare to.
   *              It can be a Node object or an Integer object.
   *              If it is an Integer object, it will be compared to the ID of the node.
   *              If it is a Node object, it will be compared to the ID of the node.
   * @return True if the node is equal to the other node, false otherwise.
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object obj) {
    //obj CAN BE EITHER an Integer or NODE
    if(obj instanceof Integer) {
      return ((Integer)this.getID()).equals(obj);
    } else if (obj instanceof NodeInterface) {
      return this.getID() == ((NodeInterface<T>)obj).getID();
    } else {
      return false;
    }
  }

}
