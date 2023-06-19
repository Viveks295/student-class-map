import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * Tests for the DataWrangler class.
 */
public class DataWranglerTests {
    /*
     * Test if a simple map (two buildings, one path) can be read.
     */
    @Test
    public void testSimpleMap() {
        // Create simple_map.dot
        File file = new File("simple_map.dot");

        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println("Building 1 -> Building 2 [label=10]");
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Could not create simple_map.dot");
        }

        MapReaderDW reader = new MapReaderDW();
        List<BuildingInterface> buildings;
        
        // Create the map
        try {
            buildings = reader.readMap("simple_map.dot");
        } catch (FileNotFoundException e) {
            fail("Could not read simple_map.dot");
            return;
        }

        assertEquals(2, buildings.size());
        
        // The building list has no enforced order, so iterate through it and test
        for (BuildingInterface building : buildings) {
            if (building.getName().equals("Building 1")) {
                assertEquals(1, building.getPaths().size());
                assertEquals("Building 2", building.getPaths().get(0).getDestinationBuilding().getName());
                assertEquals(10, building.getPaths().get(0).getDistance());
            } else if (building.getName().equals("Building 2")) {
                assertEquals(null, building.getPaths());
            } else {
                fail("Unexpected building name: " + building.getName());
            }
        }
    }

    /*
     * Tests for duplicates in the building list.
     */
    @Test
    public void testDuplicateBuildings() {
        // Create simple_map.dot
        File file = new File("complex_duplicate_map.dot");
        
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println("Building 1 -> Building 2 [label=10]");
            writer.println("Building 2 -> Building 3 [label=10]");
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Could not create simple_map.dot");
        }

        MapReaderDW reader = new MapReaderDW();
        List<BuildingInterface> buildings;

        // Create the map
        try {
            buildings = reader.readMap("complex_duplicate_map.dot");
        } catch (FileNotFoundException e) {
            fail("Could not read simple_map.dot");
            return;
        }

        assertEquals(3, buildings.size());

        // The building list has no enforced order, so iterate through it and test
        for (BuildingInterface building : buildings) {
            if (building.getName().equals("Building 1")) {
                assertEquals(1, building.getPaths().size());
                assertEquals("Building 2", building.getPaths().get(0).getDestinationBuilding().getName());
                assertEquals(10, building.getPaths().get(0).getDistance());
            } else if (building.getName().equals("Building 2")) {
                assertEquals(1, building.getPaths().size());
                assertEquals("Building 3", building.getPaths().get(0).getDestinationBuilding().getName());
                assertEquals(10, building.getPaths().get(0).getDistance());
            } else if (building.getName().equals("Building 3")) {
                assertEquals(null, building.getPaths());
            } else {
                fail("Unexpected building name: " + building.getName());
            }
        }
    }

    /*
     * Tests if an entire map can be found and iterated through.
     */
    @Test
    public void testComplexMap() {
        // Create simple_map.dot
        File file = new File("complex_map.dot");
        
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println("Building 1 -> Building 2 [label=10]");
            writer.println("Building 2 -> Building 5 [label=10]");
            writer.println("Building 3 -> Building 2 [label=10]");
            writer.println("Building 4 -> Building 5 [label=10]");
            writer.println("Building 5 -> Building 2 [label=10]");
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Could not create simple_map.dot");
        }
        
        MapReaderDW reader = new MapReaderDW();
        List<BuildingInterface> buildings;

        // Create the map
        try {
            buildings = reader.readMap("complex_map.dot");
        } catch (FileNotFoundException e) {
            fail("Could not read simple_map.dot");
            return;
        }

        assertEquals(5, buildings.size());

        // The building list has no enforced order, so iterate through it and test
        for (BuildingInterface building : buildings) {
            if (building.getName().equals("Building 1")) {
                assertEquals(1, building.getPaths().size());
                assertEquals("Building 2", building.getPaths().get(0).getDestinationBuilding().getName());
                assertEquals(10, building.getPaths().get(0).getDistance());
            } else if (building.getName().equals("Building 2")) {
                assertEquals(1, building.getPaths().size());
                assertEquals("Building 5", building.getPaths().get(0).getDestinationBuilding().getName());
                assertEquals(10, building.getPaths().get(0).getDistance());
            } else if (building.getName().equals("Building 3")) {
                assertEquals(1, building.getPaths().size());
                assertEquals("Building 2", building.getPaths().get(0).getDestinationBuilding().getName());
                assertEquals(10, building.getPaths().get(0).getDistance());
            } else if (building.getName().equals("Building 4")) {
                assertEquals(1, building.getPaths().size());
                assertEquals("Building 5", building.getPaths().get(0).getDestinationBuilding().getName());
                assertEquals(10, building.getPaths().get(0).getDistance());
            } else if (building.getName().equals("Building 5")) {
                assertEquals(1, building.getPaths().size());
                assertEquals("Building 2", building.getPaths().get(0).getDestinationBuilding().getName());
                assertEquals(10, building.getPaths().get(0).getDistance());
            } else {
                fail("Unexpected building name: " + building.getName());
            }
        }
    }

     /*
      * Tests if outgoing paths do not exist for the tail building.
      */
    @Test
    public void testNoOutgoingPaths() {
        // Create simple_map.dot
        File file = new File("no_outgoing_paths.dot");
        
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println("Building 1 -> Building 2 [label=10]");
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Could not create simple_map.dot");
        }
        
        MapReaderDW reader = new MapReaderDW();
        List<BuildingInterface> buildings;

        // Create the map
        try {
            buildings = reader.readMap("no_outgoing_paths.dot");
        } catch (FileNotFoundException e) {
            fail("Could not read simple_map.dot");
            return;
        }

        assertEquals(2, buildings.size());

        // The building list has no enforced order, so iterate through it and test
        for (BuildingInterface building : buildings) {
            if (building.getName().equals("Building 2")) {
                assertEquals(null, building.getPaths());
            }
        }
    }

    /*
     * Tests if appropriate exceptions are thrown when a poorly formatted .dot file is provided.
     */
    @Test
    public void testBadFormat() {
        // Create simple_map.dot
        File file = new File("bad_format.dot");
        File file2 = new File("bad_format_2.dot");
        
        // Create two bad format files
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println("Building 1 -> Building 2 [label=10");
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Could not create bad_format.dot");
        }

        try {
            PrintWriter writer = new PrintWriter(file2);
            writer.println("Building 1  Building 2 [label=10]");
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Could not create bad_format_2.dot");
        }

        MapReaderDW reader = new MapReaderDW();
        List<BuildingInterface> buildings;

        // Create the map and test for an exception
        try {
            buildings = reader.readMap("bad_format.dot");
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }

        // Create the map and test for an exception
        try {
            buildings = reader.readMap("bad_format_2.dot");
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }
    }

     public static void main(String[] args) {}
}
