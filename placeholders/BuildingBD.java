import java.util.List;
import java.util.ArrayList;

/**
 * Placeholder building class for Backend Testing
 */
public class BuildingBD implements BuildingInterface {

    private String name;
    private List<PathInterface> list;
   
    /**
     * Constructor for building
     *
     * @param name name of building
     */
    public BuildingBD(String name) {
        this.name = name;
        this.list = new ArrayList<>();
    }

    /**
     * Getter for name of building
     *
     * @return name of building
     */
    public String getName() {
        return this.name;
    }

    /**
     * List of Paths connected to the building
     *
     * @return list of connected paths
     */
    public List<PathInterface> getPaths() { 
        return this.list;
    }

    /**
     * Add a path to the building
     *
     * @param path path to be added
     */
    public void addPath(PathInterface path) {

    }
}

