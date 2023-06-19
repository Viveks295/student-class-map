import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
public class AlgorithmEngineerTests {

  @Test
  void test1() {
    //This method tests NodeAE, All functions that DO NOT pertain to edges
    //like constructor, getters, setters, to string, and equals
    NodeAE<Integer> node1 = new NodeAE<Integer>(1, 3);
    NodeAE<Integer> node2 = new NodeAE<Integer>(2, 3);
    
    
    //testing getters
    assertTrue(node1.getData() == 3);
    assertTrue(node1.getID() == 1);
    //testing setter
    node1.setData(1);
    assertTrue(node1.getData() == 1);
    
    //testing to string
    //should return testString below
    String testString = "(1)     [has no edges] \n";
    assertTrue(node1.toString().equals(testString));
    
    //testing equals method
    assertTrue(node1.equals(1));
    assertTrue(node1.equals(node1));
    assertFalse(node1.equals(node2));
    
  }
  
  @Test
  void test2() {
    //This method tests NodeAE and edgeAE. Specifically tests the methods 
    //relating to the interactions between the 2 classes
    //tests getAdjNodes, linkNode, unlinkNode, isLinked, getWeight
    NodeAE<Integer> node1 = new NodeAE<Integer>(1, 3);
    NodeAE<Integer> node2 = new NodeAE<Integer>(2, 3);
    
    assertFalse(node1.isLinked(node2));
    //linking the nodes up
    node1.linkNode(node2, 10);
    assertTrue(node1.isLinked(node2));
    //testing if linkNode throws error
    assertThrows(IllegalArgumentException.class, () -> {node1.linkNode(node2, 5);});
    assertTrue(node1.getAdjNodes().get(0).equals(node2));
    assertTrue(node1.getWeight(node2) == 10);
    
    node1.unlinkNode(node2);
    assertFalse(node1.isLinked(node2));
    assertThrows(IllegalArgumentException.class, () -> {node1.unlinkNode(node2);});
    
  }
  
  @Test
  void test3() {
    //test graphAE 
    //methods: constructor, createNode linkNodes unlinkNodes getNode deleteNode toString
    GraphAE<Integer> graph = new GraphAE<Integer>();
    //ID returned should be 0 and 1
    assertTrue(graph.createNode(5) == 0);
    assertTrue(graph.createNode(10) == 1);
    
    graph.linkNodes(0, 1, 36);
    assertTrue(graph.nodes.get(0).isLinked(graph.nodes.get(1)));
    assertTrue(graph.nodes.get(1).isLinked(graph.nodes.get(0)));
    String testString = "(1)  --36.0-->  (0) \n"
                      + "(0)  --36.0-->  (1) \n";
    
    assertTrue(graph.toString().equals(testString));
    
    assertTrue(graph.getNode(0).equals(graph.nodes.get(0)));
    graph.unlinkNodes(0, 1);
    assertFalse(graph.getNode(0).isLinked(graph.getNode(1)));
    //testing deletion
    graph.deleteNode(1);
    assertThrows(NoSuchElementException.class, () -> {graph.getNode(1);});
  }
  
  @Test
  void test4() {
    //test graphAE throws
    //testing throws of linkNodes, unlink nodes, deleteNode, findShortestPath
    GraphAE<Integer> graph = new GraphAE<Integer>();
    graph.createNode(10);
    graph.createNode(20);
    graph.createNode(30);
    graph.linkNodes(0, 1, 100);
    //link nodes throws IllegalArgumentException when nodes are already connected
    //and NoSuchElementException when id's aren't in the graph
    assertThrows(IllegalArgumentException.class, () -> {graph.linkNodes(0, 1, 200);});
    assertThrows(NoSuchElementException.class, () -> {graph.linkNodes(0, 25, 200);});
    //unlinkNodes throws NoSuchElementException when nodeID's arent in the graph
    assertThrows(NoSuchElementException.class, () -> {graph.unlinkNodes(0, 25);});
    //deleteNode throws NoSuchElementException if node is not in the graph
    assertThrows(NoSuchElementException.class, () -> {graph.deleteNode(25);});
    //findShortestPath throws NoSuchElementException when nodes aren't in the graph or there is no path between nodes
    assertThrows(NoSuchElementException.class, () -> {graph.findShortestPath(0,25);});
    assertThrows(NoSuchElementException.class, () -> {graph.findShortestPath(0,2);});
    
  }
  
  @Test
  void test5() {
    //test djiktras algo
    GraphAE<Integer> graph = new GraphAE<Integer>();
    //creating sample graph from https://medium.com/swlh/dijkstras-algorithm-in-an-undirected-graph-c0c086d77145
    graph.createNode(0); 
    graph.createNode(1); 
    graph.createNode(2); 
    graph.createNode(3); 
    graph.createNode(4); 
    //linking nodes
    graph.linkNodes(0, 1, 3.0); 
    graph.linkNodes(0, 3, 8.0); 
    graph.linkNodes(0, 4, 7.0); 
    graph.linkNodes(1, 2, 1.0); 
    graph.linkNodes(1, 3, 4.0); 
    graph.linkNodes(2, 3, 2.0);
    graph.linkNodes(3, 4, 3.0);
    
    
    List<EdgeInterface<Integer>> list = graph.findShortestPath(3, 1); //Shortest Path would be from 3 -(2)-> 2 -(1)-> 1 for a total of 3
    assertTrue(list.size() == 2);
//    System.out.println(list.get(0).toString());
//    System.out.println(list.get(0).getNodeOne().getID());
//    System.out.println(list.get(0).getNodeTwo().getID());
//    System.out.println(list.get(1).toString());
//    System.out.println(list.get(1).getNodeOne().getID());
//    System.out.println(list.get(1).getNodeTwo().getID());
    assertTrue(list.get(0).toString().equals("--2.0-->")); //FIXED
    
    
  }
}
