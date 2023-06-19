import java.util.List;

public interface BuildingInterface {
    // public Building()
    public String getName();
    public List<PathInterface> getPaths();
    public void addPath(PathInterface path);
}
