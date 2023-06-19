import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Tests for the StudentMapBackendBD class
 */
public class BackendDeveloperTests {

    /**
     * Test loadData methods with valid and invalid files 
     */
    @Test
    public void test1() {
        // initialize the backend with placeholders
        GraphAE<BuildingDW> graph = new GraphAE<>();
        MapReaderBD mapReader = new MapReaderBD();
        StudentMapBackendBD backend = new StudentMapBackendBD(graph, mapReader);

        // check that a FileNotFoundException is thrown when a wrong file is passed in 
        assertThrows(FileNotFoundException.class,
            ()->{
                backend.loadData("wrongfile");
            });
       
        // check that loading a valid file does not throw an exception
        try {
            backend.loadData("map.dot");
        } catch (FileNotFoundException e) {
            Assertions.fail(); 
        }

        // check that the building from the file loaded properly
        Assertions.assertEquals("Buildings: Dejope", backend.getAllBuildingsString());
    }

    /**
     * Test addBuilding method
     */
    @Test
    public void test2() {
        // initialize the backend with placeholders
        GraphAE<BuildingDW> graph = new GraphAE<>();
        MapReaderBD mapReader = new MapReaderBD();
        StudentMapBackendBD backend = new StudentMapBackendBD(graph, mapReader);

        // add the buildings
        backend.addBuilding("Dejope");
        backend.addBuilding("Mosse");
        backend.addBuilding("Bascom");

        // check that the backend contains all the buildings
        String output = backend.getAllBuildingsString();
        Assertions.assertEquals(true, output.contains("Dejope") && output.contains("Mosse") && output.contains("Bascom") ); 
    } 

    /**
     * Test addPath method
     */
    @Test
    public void test3() {
        // initialize the backend with placeholders
        GraphAE<BuildingDW> graph = new GraphAE<>();
        MapReaderBD mapReader = new MapReaderBD();
        StudentMapBackendBD backend = new StudentMapBackendBD(graph, mapReader);

        // check that valid path creations return true
        Assertions.assertEquals(true, backend.addPath("Dejope", "Bascom", 15));
        Assertions.assertEquals(true, backend.addPath("Mosse", "Bascom", 5));

        // check that an invalid path creation returns false
        Assertions.assertEquals(false, backend.addPath("Bascom", "Bascom", 1));
        
        // check that the backend contains all the buildings
        String output = backend.getAllBuildingsString();
        Assertions.assertEquals(true, output.contains("Dejope") && output.contains("Mosse") && output.contains("Bascom") ); 
    } 

    /**
     * Test remove method
     */
    @Test
    public void test4() {
        // initialize the backend with placeholders
        GraphAE<BuildingDW> graph = new GraphAE<>();
        MapReaderBD mapReader = new MapReaderBD();
        StudentMapBackendBD backend = new StudentMapBackendBD(graph, mapReader);

        // check that removing a building not in the backend returns false
        Assertions.assertEquals(false, backend.remove("Dejope"));

        // add the buildings
        backend.addBuilding("Dejope");
        backend.addBuilding("Mosse");
        backend.addBuilding("Bascom");

        // check that the backend contains all the buildings
        String output = backend.getAllBuildingsString();
        Assertions.assertEquals(true, output.contains("Dejope") && output.contains("Mosse") && output.contains("Bascom") ); 

        // check that the backend updates when a building is removed
        Assertions.assertEquals(true, backend.remove("Bascom"));
        output = backend.getAllBuildingsString();
        Assertions.assertEquals(true, output.contains("Dejope") && output.contains("Mosse")); 

        // check that the backend updates when a building is removed
        Assertions.assertEquals(true, backend.remove("Mosse"));
        Assertions.assertEquals("Buildings: Dejope", backend.getAllBuildingsString());

        // check that the backend updates when a building is removed
        Assertions.assertEquals(true, backend.remove("Dejope"));
        Assertions.assertEquals("No Buildings", backend.getAllBuildingsString());
    } 
    
    /**
     * Test findShortestPathDistance and findShortestPathBuildings methods
     */
    @Test
    public void test5() {
        // initialize the backend with placeholders
        GraphAE<BuildingDW> graph = new GraphAE<>();
        MapReaderBD mapReader = new MapReaderBD();
        StudentMapBackendBD backend = new StudentMapBackendBD(graph, mapReader);

        // add the buildings
        backend.addBuilding("Dejope");
        backend.addBuilding("Mosse");
        backend.addBuilding("Bascom");

        backend.addPath("Dejope", "Bascom", 15);
        backend.addPath("Bascom", "Mosse", 8);


        // check that findShortestPathBuildings returns the right values 
        List<BuildingInterface> buildings = backend.findShortestPathBuildings("Dejope", "Mosse");
        Assertions.assertEquals(3, buildings.size());     
        Assertions.assertEquals("Dejope", buildings.get(0).getName());
        Assertions.assertEquals("Bascom", buildings.get(1).getName());
        Assertions.assertEquals("Mosse", buildings.get(2).getName());
        
        // check that findShortestPathDistances returns the right values
        List<Double> distances = backend.findShortestPathDistances("Dejope", "Mosse");
        Assertions.assertEquals(2, distances.size());
        Assertions.assertEquals(15.0, distances.get(0));
        Assertions.assertEquals(8.0, distances.get(1));
    }

    @Test
    public void codeReviewOfFrontendDeveloper1() {

        // simulate user input
        TextUITester tester = new TextUITester("a\nBascom, Dejope\nq");
        GraphAE<BuildingDW> graph = new GraphAE<>();
        MapReaderDW mapReader = new MapReaderDW();
        StudentMapBackendBD backend = new StudentMapBackendBD(graph, mapReader);
        StudentMapFrontendFD frontend = new StudentMapFrontendFD(new Scanner(System.in), backend);
        frontend.runCommandLoop();
        String output = tester.checkOutput();

        // check if the locations were successfully added  
        if (!output.contains("Locations added successfully.")) {
            Assertions.fail("Adding location failed");
        }
    }

    @Test
    public void codeReviewOfFrontendDeveloper2() {

        // simulate user input
        TextUITester tester = new TextUITester("A\n Van Vleck, Union South\nS\nVan Vleck\nQ");
        GraphAE<BuildingDW> graph = new GraphAE<>();
        MapReaderDW mapReader = new MapReaderDW();
        StudentMapBackendBD backend = new StudentMapBackendBD(graph, mapReader);
        StudentMapFrontendFD frontend = new StudentMapFrontendFD(new Scanner(System.in), backend);
        frontend.runCommandLoop();
        String output = tester.checkOutput();

        // check if the location is found 
        if (!output.contains("The location exists.")) {
            Assertions.fail("Searching for location failed");
        }

    }

    @Test
    public void integrationTest1() {
        // simulate user input
        TextUITester tester = new TextUITester("L\nmap.dot\nG\nDejope Residence Hall, Van Hise Hall\nQ");
        GraphAE<BuildingDW> graph = new GraphAE<>();
        MapReaderDW mapReader = new MapReaderDW();
        StudentMapBackendBD backend = new StudentMapBackendBD(graph, mapReader);
        StudentMapFrontendFD frontend = new StudentMapFrontendFD(new Scanner(System.in), backend);
        frontend.runCommandLoop();
        String output = tester.checkOutput();

        // check if file was loaded properly
        if (!output.contains("The file has been successfully loaded.")) {
            Assertions.fail("File not loaded properly");
        }

        // check if the shortest path method works
        if (!output.contains("The buildings along the path:")) {
            Assertions.fail();
        }
        if (!output.contains("Dejope Residence Hall --(8.0)--> Van Vleck Hall --(1.0)--> Van Hise Hall")) {
            Assertions.fail("Path is wrong");
        }
        if (!output.contains("The total distance: 9.0")) {
            Assertions.fail("Distance is wrong");
        }
    }

    @Test
    public void integrationTest2() {
        // simulate user input
        TextUITester tester = new TextUITester("L\nmap.dot\nD\nQ");
        GraphAE<BuildingDW> graph = new GraphAE<>();
        MapReaderDW mapReader = new MapReaderDW();
        StudentMapBackendBD backend = new StudentMapBackendBD(graph, mapReader);
        StudentMapFrontendFD frontend = new StudentMapFrontendFD(new Scanner(System.in), backend);
        frontend.runCommandLoop();
        String output = tester.checkOutput();

        // check if the buildings are added and displayed properly
        if (!output.contains("Buildings: Van Vleck Hall, Van Hise Hall, Dejope Residence Hall, Chamberlain Hall, DeLuca Chemistry Building")) {
            Assertions.fail("Buildings not added properly");
        }
    }
}
