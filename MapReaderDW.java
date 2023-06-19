import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

/*
 * A class that provides a method to read a map from a file.
 */
public class MapReaderDW implements MapReaderInterface {
    
    /*
     * Parse a .dot file and return a list of buildings.
     */
    public List<BuildingInterface> readMap(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        List<BuildingInterface> buildings = new ArrayList<BuildingInterface>();

        // Read the file line by line and parse it
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("->") && line.contains("label=") && line.contains("[") && line.contains("]")) {
                String[] parts = line.split("->");
                String startBuildingName = parts[0].trim();
                String destinationBuildingName = parts[1].split("\\[")[0].trim();
                int distance = 0;
                
                // Check if distance is a valid integer
                try {
                    distance = Integer.parseInt(parts[1].split("label=")[1].split("\\]")[0].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid distance");
                    continue;
                }
                
                // Flags to check whether or not to construct new buildings
                BuildingInterface startBuilding = null;
                BuildingInterface destinationBuilding = null;

                // Check if building already exists
                for (BuildingInterface building : buildings) {
                    if (building.getName().equals(startBuildingName)) {
                        startBuilding = building;
                    }

                    if (building.getName().equals(destinationBuildingName)) {
                        destinationBuilding = building;
                    }
                }

                // Create the destination building first so that we can use it for the start building
                if (destinationBuilding == null) {
                    destinationBuilding = new BuildingDW(destinationBuildingName);
                    buildings.add(destinationBuilding);
                }

                // Create the start building if it doesn't already exist
                if (startBuilding == null) {
                    List<PathInterface> pathList = new ArrayList<PathInterface>();
                    pathList.add(new PathDW(destinationBuilding, distance));
                    startBuilding = new BuildingDW(startBuildingName, pathList);
                    buildings.add(startBuilding);
                } else {
                    // Check if path doesn't already exist
                    if (startBuilding.getPaths() == null || !startBuilding.getPaths().contains(new PathDW(destinationBuilding, distance)))
                    startBuilding.addPath(new PathDW(destinationBuilding, distance));
                }
            } else {
                throw new IllegalArgumentException("Invalid file format.");
            }
        }

        // Close the scanner to prevent resource leaks
        scanner.close();

        // Return the list of buildings
        return buildings;
    }
}
