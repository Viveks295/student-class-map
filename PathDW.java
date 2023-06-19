/*
 * A class that represents a path between two buildings.
 */
public class PathDW implements PathInterface {
    private BuildingInterface destinationBuilding;
    private int distance;

    /*
     * Constructs a path between two buildings.
     */
    public PathDW(BuildingInterface destinationBuilding, int distance) {
        this.destinationBuilding = destinationBuilding;
        this.distance = distance;
    }

    /*
     * Returns the building at the end of this path.
     */
    @Override
    public BuildingInterface getDestinationBuilding() {
        return this.destinationBuilding;
    }

    /*
     * Returns the distance between the two buildings.
     */
    @Override
    public int getDistance() {
        return this.distance;
    }
    
}
