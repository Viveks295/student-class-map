import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class StudentMapBackendFD implements StudentMapBackendInterface{
    public void loadData(String filename) throws FileNotFoundException{
        if (!filename.equals("testing.txt")) {
            throw new FileNotFoundException("Invalid file.");
        }
    }
    public boolean addBuilding(String building){

        return true;
    }
    public boolean addPath(String startBuilding, String endBuilding, double distance){
        return true;
    }
    public boolean remove(String building){
        return true;
    }
    public List<BuildingInterface> findShortestPathBuildings(String startBuilding, String endBuilding){
        List<BuildingInterface> tester = new ArrayList<>();
        return tester;
    }
    public List<Double> findShortestPathDistances(String startBuilding, String endBuilding){
        List<Double> tester = new ArrayList<>();
        return tester;
    }
    public String getAllBuildingsString()
    {
        return "Van Vleck \nChamberlain Hall";
    }
}
