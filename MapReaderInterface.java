import java.io.FileNotFoundException;
import java.util.List;

public interface MapReaderInterface {
    // public mapReader()
    public List<BuildingInterface> readMap(String filename) throws FileNotFoundException;
}
