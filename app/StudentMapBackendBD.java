import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.LinkedList;
import java.util.Hashtable;

/**
 * Backend class for the student map
 */
public class StudentMapBackendBD implements StudentMapBackendInterface {

    private GraphAE<BuildingDW> graph;
    private MapReaderInterface mapReader; 
    private Hashtable<String, Integer> buildingIDs; 

    /**
     * Constructor for the backend
     *
     * @param graph graph with Dijkstra's algorithm
     * @param mapReader reader to read in the map file
     */
    public StudentMapBackendBD(GraphAE<BuildingDW> graph, MapReaderInterface mapReader) {
        this.graph = graph;
        this.mapReader = mapReader;
        this.buildingIDs = new Hashtable<>();
    }

    /**
     * Loads the data in to the graph
     *
     * @param filename file containing the graph
     * @throws FileNotFoundException if the provided file is not found
     */
    public void loadData(String filename) throws FileNotFoundException {
        // use mapReader to read in the file
		List<BuildingInterface> buildings = mapReader.readMap(filename);

        // iterate through the buildings
		for (BuildingInterface building : buildings) {
            // add the building to the graph
            this.addBuilding(building.getName());

            // add the paths for each building to the graph
            if (building.getPaths() == null) {
                continue;
            }
            
            for (PathInterface path : building.getPaths()) {
                this.addPath(building.getName(), path.getDestinationBuilding().getName(), path.getDistance());
            }
        }
    }

    /**
     * Adds a building to the graph
     *
     * @param building building to be added
     * @return true if the building was successfully added, false otherwise
     */
    public boolean addBuilding(String building) {
        // if the building is not already in the graph, add it
        if (!this.buildingIDs.containsKey(building.toLowerCase())) {
            BuildingDW newBuilding = new BuildingDW(building);
            int nodeID = graph.createNode(newBuilding);
            this.buildingIDs.put(building.toLowerCase(), nodeID);
            return true;
        }
        return false;
    }

    /**
     * Adds a path between two buildings in the graph
     *
     * @param startBuilding first building in the path
     * @param endBuilding second building in the path
     * @param distance distance between the two buildings
     * @return true if the path wsa successfully added, false otherwise
     */
    public boolean addPath(String startBuilding, String endBuilding, double distance) {
        // if either the start or end building are not in the graph, add it
        if (!this.buildingIDs.containsKey(startBuilding.toLowerCase())) {
            this.addBuilding(startBuilding);
        }
        if (!this.buildingIDs.containsKey(endBuilding.toLowerCase())) {
            this.addBuilding(endBuilding);
        }

        try {
            // link the nodes
            graph.linkNodes(this.buildingIDs.get(startBuilding.toLowerCase()), this.buildingIDs.get(endBuilding.toLowerCase()), distance);
        } catch (IllegalArgumentException e) {
            // the nodes were already linked
            return false;
        }
        return true;
    }

    /**
     * Removes a building from the graph
     *
     * @param building building to be removed
     * @return true if the building was successfully removed, false otherwise 
     */
    public boolean remove(String building) {
        // check if building is in the graph
        if (!this.buildingIDs.containsKey(building.toLowerCase())) {
            return false;
        }

        // remove the building from the graph and the hashtable
        int buildingID = this.buildingIDs.get(building.toLowerCase());
        graph.deleteNode(buildingID);
        this.buildingIDs.remove(building.toLowerCase());

        return true;
    }

    /**
     * Calls Dijkstra's algorithm to find the shortest path between two buildings and returns a linked list with the buildings
     * in the path
     *
     * @param startBuilding building to start the search at
     * @param endBuilding building to end the search at
     * @return a LinkedList containing every building in the path
     * @throws NoSuchElementException if the start or end building is not in the graph
     */
    public List<BuildingInterface> findShortestPathBuildings(String startBuilding, String endBuilding) throws NoSuchElementException {
        // if either building is not in the graph, throw an error
        if (!this.buildingIDs.containsKey(startBuilding.toLowerCase())) {
            throw new NoSuchElementException();
        }
        if (!this.buildingIDs.containsKey(endBuilding.toLowerCase())) {
            throw new NoSuchElementException();
        }

        List<BuildingInterface> shortestPath = new LinkedList<>(); 

        // get the shortest path between the nodes using AE's Dijkstra's algorithm
        List<EdgeInterface<BuildingDW>> output = graph.findShortestPath(this.buildingIDs.get(endBuilding.toLowerCase()), this.buildingIDs.get(startBuilding.toLowerCase()));
       
        if (output.size() == 0) {
            return shortestPath;
        }

        // add the first node to the LinkedList
        shortestPath.add(0, output.get(0).getNodeTwo().getData());

        // add the second node for each edge to the LinkedList
        for (EdgeInterface<BuildingDW> edge : output) {
            shortestPath.add(0, edge.getNodeOne().getData());
        }

        return shortestPath;
    }

    /**
     * Calls Dijkstra's algorithm to find the shortest path between two buildings and returns the distance between each building 
     *
     * @param startBuilding building to start the search at
     * @param endBuilding building to end the search at
     * @return a list of distances between every building in the shortest path 
     * @throws NoSuchElementException if the start or end building is not in the graph
     */
    public List<Double> findShortestPathDistances(String startBuilding, String endBuilding) throws NoSuchElementException {
        // if either building is not in the graph, throw an error
        if (!this.buildingIDs.containsKey(startBuilding.toLowerCase())) {
            throw new NoSuchElementException();
        }
        if (!this.buildingIDs.containsKey(endBuilding.toLowerCase())) {
            throw new NoSuchElementException();
        }

        List<Double> distances = new LinkedList<>(); 

        // get the shortest path between the nodes using AE's Dijkstra's algorithm
        List<EdgeInterface<BuildingDW>> output = graph.findShortestPath(this.buildingIDs.get(endBuilding.toLowerCase()), this.buildingIDs.get(startBuilding.toLowerCase()));
        
        // sum the distance for each edge
        for (EdgeInterface<BuildingDW> edge : output) {
            distances.add(0, edge.getWeight());
        }
        
        return distances;
    }

    /**
     * Returns a string containing every building in the graph
     *
     * @return a string representation of all buildings in the graph
     */
    public String getAllBuildingsString() {
        if (buildingIDs.size() == 0) {
            return "No Buildings";
        }

        String output = "Buildings: ";
        boolean isFirst = true;

        // iterate through the building Hashtable and add its name to the output string
        for (Integer id: buildingIDs.values()) {
            if (isFirst) {
                output += graph.getNode(id).getData().getName();
                isFirst = false;
            } else {
                output += ", " + graph.getNode(id).getData().getName();
            }
        }

        return output;
    }
}
