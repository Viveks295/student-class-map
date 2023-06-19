import java.util.ArrayList;
import java.util.List;

/*
 * A class that represents a building on campus.
 */
public class BuildingDW implements BuildingInterface {
    private String name;
    private List<PathInterface> outgoingPaths; // Only store outgoing paths, this
    // is enough to construct a graph
    
    public BuildingDW(String name, List<PathInterface> paths) {
        this.name = name;
        this.outgoingPaths = paths;
    }

    /*
     * A constructor for storing a tail building.
     */
    public BuildingDW(String name) {
        this.name = name;
        this.outgoingPaths = null;
    }

    /*
     * Returns the name of the building.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /*
     * Returns the paths that start from this building.
     */
    @Override
    public List<PathInterface> getPaths() {
        return this.outgoingPaths;
    }

    /*
     * Adds a path to the list of paths.
     */
    @Override
    public void addPath(PathInterface path) {
        if (this.outgoingPaths == null)
            this.outgoingPaths = new ArrayList<PathInterface>();
        
        this.outgoingPaths.add(path);
    }
    
}
