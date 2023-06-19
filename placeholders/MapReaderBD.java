import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

/**
 * Placeholder MapReader class for Backend Testing
 */
public class MapReaderBD implements MapReaderInterface {
    
    /**
     * Constructor for MapReader
     */
    public MapReaderBD() {

    }

    /**
     * Reads in a file
     *
     * @param filename name of the file
     * @return list of buildings created from that file
     */
    public List<BuildingInterface> readMap(String filename) throws FileNotFoundException {
        if (filename.equals("wrongfile")) {
            throw new FileNotFoundException();
        }

        List<BuildingInterface> list = new ArrayList<>();
        list.add(new BuildingBD("Dejope"));

        return list;
    }
}

