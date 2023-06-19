import java.io.FileNotFoundException;
import java.util.List;
 
public interface StudentMapBackendInterface {
    // public StudentMapBackendInterface(DjikstraGraph<Node> graph, FileReaderInterface fileReader);
    public void loadData(String filename) throws FileNotFoundException;
    public boolean addBuilding(String building);
    public boolean addPath(String startBuilding, String endBuilding, double distance);
    public boolean remove(String building);
    public List<BuildingInterface> findShortestPathBuildings(String startBuilding, String endBuilding);
    public List<Double> findShortestPathDistances(String startBuilding, String endBuilding);
    public String getAllBuildingsString();
}
