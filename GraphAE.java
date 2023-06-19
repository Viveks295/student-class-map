import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class GraphAE<T> implements GraphInterface<T> {

  //graph object variables
  protected Hashtable<Integer, NodeInterface<T>> nodes = new Hashtable<Integer, NodeInterface<T>>();
  protected int ID = 0;
  //graph constructor
  //tehe no constructor
  
  
  /**
   * This function creates a new node in the graph.
   * You can create node without any data.
   * @param data Data of the node.
   * @return ID of the node created.
   */
  public int createNode(T data) {
    nodes.put(ID, new NodeAE<T>(ID, data));
    //ID++;
    return ID++;
  }

  /**
   * This function links two nodes in the graph, along with the weight of the edge.
   * @param nodeOneID ID of the first node.
   * @param nodeTwoID ID of the second node.
   * @param weight Weight of the edge.
   * @throws NoSuchElementException if the nodes are not found.
   * @throws IllegalArgumentException if the nodes are already linked.
   */
  public void linkNodes(int nodeOneID, int nodeTwoID, double weight)
          throws NoSuchElementException, IllegalArgumentException {
    //checks if the nodes are in the graph
    if(nodes.containsKey(nodeOneID) && nodes.containsKey(nodeTwoID)) {
      nodes.get(nodeOneID).linkNode(nodes.get(nodeTwoID), weight);
      nodes.get(nodeTwoID).linkNode(nodes.get(nodeOneID), weight);
    } else {
      throw new NoSuchElementException("Nodes are not in the graph");
    }
  }

  /**
   * This function unlinks two nodes in the graph.
   * @param nodeOneID ID of the first node.
   * @param nodeTwoID ID of the second node.
   * @throws NoSuchElementException if the nodes are not found.
   */
  public void unlinkNodes(int nodeOneID, int nodeTwoID) throws NoSuchElementException{
    if(nodes.containsKey(nodeOneID) && nodes.containsKey(nodeTwoID)) {
      nodes.get(nodeOneID).unlinkNode(nodes.get(nodeTwoID));
      nodes.get(nodeTwoID).unlinkNode(nodes.get(nodeOneID));
    } else {
      throw new NoSuchElementException("Nodes are not in the graph");
    }
  }

  /**
   * This function list all the id of the nodes in the graph.
   */
  public List<Integer> listNodes(){
    return new ArrayList<Integer>( nodes.keySet());
  }

  /**
   * This function gets the node with the given id.
   * @param id id of the node.
   * @return Node with the given id.
   * @throws NoSuchElementException if the node is not found.
   */
  public NodeInterface<T> getNode(int id) throws NoSuchElementException{
    if(nodes.contains(id)) {
      return nodes.get(id);
    } else {
      throw new NoSuchElementException("node is not in the graph");
    }
  }

  /**
   * This function deletes the node with the given id.
   * It also unlinks the node from all the other nodes.
   * @param id id of the node.
   * @throws NoSuchElementException if the node is not found.
   */
  public void deleteNode(int id) throws NoSuchElementException{
    //first check if node is in the graph throw error if false
    if(nodes.contains(id)) {
      //then unlinks the node from others
      List<NodeInterface<T>> connectedNodes = nodes.get(id).getAdjNodes();
      for(NodeInterface<T> node: connectedNodes) {
        nodes.get(id).unlinkNode(node);
      }
      //then delete the node from the hashtable
      nodes.remove(id);
    } else {
      throw new NoSuchElementException("node is not in the graph");
    }
  }

  /**
   * This function finds the shortest path between two nodes in the graph
   * using Dijkstra's algorithm. You should use the functions in the Edge
   * interface to get the weight and the nodes of the path.
   * @param startID The ID of the starting node.
   * @param endID The ID of the ending node.
   * @return A list of edges representing the shortest path between the two nodes.

   * @throws NoSuchElementException if either the start node or end node is not found.
   */
  public List<EdgeInterface<T>> findShortestPath(int startID, int endID) throws NoSuchElementException{
    
    if(nodes.get(startID) == null || nodes.get(endID) == null) {
      throw new NoSuchElementException("Start or end nodes are not in graph");
    }
    
    List<EdgeInterface<T>> list = new LinkedList<EdgeInterface<T>>();
    SearchNode currNode = computeShortestPath((NodeAE<T>)nodes.get(startID), (NodeAE<T>)nodes.get(endID));
    while(currNode != null) {
      
      for(EdgeInterface<T> edge: currNode.node.edges) {
        
        if(currNode.otherNode == null) {
          if ((edge.getOtherNode(currNode.node)).equals(nodes.get(startID))) {
            list.add(0,edge);
            currNode = currNode.otherNode;
            break;
          }
        } else if((edge.getOtherNode(currNode.node)).equals(currNode.otherNode.node)) {
          // System.out.println(currNode.node);
          // System.out.println(currNode.otherNode.node);
          list.add(0,edge);
          currNode = currNode.otherNode;
          break;
        }
      }
    }
      return list;
  }
  
  /**
   * While searching for the shortest path between two nodes, a SearchNode
   * contains data about one specific path between the start node and another
   * node in the graph.  The final node in this path is stored in it's node
   * field.  The total cost of this path is stored in its cost field.  And the
   * predecessor SearchNode within this path is referened by the predecessor
   * field (this field is null within the SearchNode containing the starting
   * node in it's node field).
   *
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost
   * SearchNode has the highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
      public NodeAE<T> node;
      public double cost;
      public SearchNode otherNode;
      public SearchNode(NodeAE<T> node, double cost, SearchNode otherNode) {
          this.node = node;
          this.cost = cost;
          this.otherNode = otherNode;
      }
      public int compareTo(SearchNode other) {
          if( cost > other.cost ) return +1;
          if( cost < other.cost ) return -1;
          return 0;
      }
  }

  /**
   * This helper method creates a network of SearchNodes while computing the
   * shortest path between the provided start and end locations.  The
   * SearchNode that is returned by this method is represents the end of the
   * shortest path that is found: it's cost is the cost of that shortest path,
   * and the nodes linked together through predecessor references represent
   * all of the nodes along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found
   *         or when either start or end data do not correspond to a graph node
   */
  
  protected SearchNode computeShortestPath(NodeAE<T> start, NodeAE<T> end) {
      //start or end data are not in graph throws error
      if(nodes.get(start.getID()) == null || nodes.get(end.getID()) == null) {
        throw new NoSuchElementException("Start or end nodes are not in graph");
      }
      
      
      //add priority queue and hastable
      PriorityQueue<SearchNode> nodeQ = new PriorityQueue<>();
      Hashtable<Integer, SearchNode> visitedNodes = new Hashtable<Integer, SearchNode>();
      SearchNode currNode;
      
      //setting up the first search node with start data, and filling the queue with all the connected nodes
      SearchNode startNode = new SearchNode(start, 0, null);
      visitedNodes.put(startNode.node.getID(), startNode);
      for(EdgeInterface<T> edge: startNode.node.edges) {
        nodeQ.add(new SearchNode((NodeAE<T>)edge.getOtherNode(start),  edge.getWeight(), null));
      }
      
      while(!nodeQ.isEmpty()) {
        
        currNode = nodeQ.poll();
        
        //System.out.println(currNode.node.toString());
        //adds all the edges from currNode to nodeQ if they havent been visited to
        //then adds currNode to visitedNoded
        if(!visitedNodes.containsKey(currNode.node.getID())) {
            visitedNodes.put(currNode.node.getID(), currNode);
            for(EdgeInterface<T> edge: currNode.node.edges) {
             // if(!visitedNodes.containsKey(edge.successor.data)) {
              
                nodeQ.add(new SearchNode((NodeAE<T>) edge.getOtherNode(currNode.node), edge.getWeight(), currNode));
             // }
            } 
        }
      }
      
      //end of algorithm, checks if the end is in the hashtable and returns that searchNode
      //else throws error.
      if(visitedNodes.containsKey(end.getID())) {
       
        return visitedNodes.get(end.getID());
      } else {
        
        throw new NoSuchElementException("There is no path from start to end");
      }
        
  }

  /**
   * This function returns a string representation of the graph.
   * @return String representation of the graph.
   */
  @Override
  public String toString() {
    String string = "";
    List<NodeInterface<T>> list = new ArrayList<NodeInterface<T>>(nodes.values());
    for(NodeInterface<T> node: list) {
      string += node;
    }
    
    return string;
  }
}
