
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class FrontendDeveloperTests {

    /**
     * testing loadDataCommand() to ensure files are loaded correctly
     */
    @Test
    public void testOne(){
        //initializing the input of the user
        TextUITester uiTester = new TextUITester("L\ntesting.txt\nQ");
        StudentMapBackendFD backend = new StudentMapBackendFD();
        StudentMapFrontendFD feTester = new StudentMapFrontendFD(new Scanner(System.in), backend);
        feTester.runCommandLoop();
        String output = uiTester.checkOutput();

        //checking if the file is found or not
        if(!output.contains("The file has been successfully loaded.")){
            Assertions.fail("The loadDataCommand() method failed when loading a valid file.");
        }

        //initializing the input of the user with a wrong file
        TextUITester fakeTester = new TextUITester("L\nfakeInput.txt\nQ");
        StudentMapBackendFD backend2 = new StudentMapBackendFD();
        StudentMapFrontendFD feTester2 = new StudentMapFrontendFD(new Scanner(System.in), backend2);
        feTester2.runCommandLoop();
        String fakeOutput = fakeTester.checkOutput();

        //checking if an invalid file is found or not
        if(!fakeOutput.contains("The file provided is invalid.")){
            Assertions.fail("The loadDataCommand() method failed when loading an invalid file.");
        }
    }

    /**
     * testing chooseLocationsPrompt() which naturally occurs when inputting "A" and addLocationCommand()
     */
    @Test
    public void testTwo(){

        //initializing the input of the user
        TextUITester uiTester = new TextUITester("A\nVan Vleck,Chamberlain Hall\nQ");
        StudentMapBackendFD backend = new StudentMapBackendFD();
        StudentMapFrontendFD feTester = new StudentMapFrontendFD(new Scanner(System.in), backend);
        feTester.runCommandLoop();
        String output = uiTester.checkOutput();

        //checking if the locations were added successfully or not
        if(!output.contains("Locations added successfully.")){
            Assertions.fail("The addLocationCommand() method failed.");
        }
    }

    /**
     * testing removeLocationsCommand()
     */
    @Test
    public void testThree(){

        //initializing the input of the user
        TextUITester uiTester = new TextUITester("R\nVan Vleck,Chamberlain Hall\nQ");
        StudentMapBackendFD backend = new StudentMapBackendFD();
        StudentMapFrontendFD feTester = new StudentMapFrontendFD(new Scanner(System.in), backend);
        feTester.runCommandLoop();
        String output = uiTester.checkOutput();

        //checking if the locations were removed successfully or not
        if(!output.contains("Locations removed successfully.")){
            Assertions.fail("The removeLocationsCommand() method failed.");
        }
    }

    /**
     * testing searchLocationsCommand()
     */
    @Test
    public void testFour(){

        //initializing the input of the user
        TextUITester uiTester = new TextUITester("S\nVan Vleck\nQ");
        StudentMapBackendFD backend = new StudentMapBackendFD();
        StudentMapFrontendFD feTester = new StudentMapFrontendFD(new Scanner(System.in), backend);
        feTester.runCommandLoop();
        String output = uiTester.checkOutput();

        //checking if the location is found or not
        if(!output.contains("The location exists.")){
            Assertions.fail("The searchLocationsCommand() failed when searching for a valid location.");
        }

        //initializing input of a user which should fail
        TextUITester uiTester2 = new TextUITester("S\nDejope\nQ");
        StudentMapBackendFD backend2 = new StudentMapBackendFD();
        StudentMapFrontendFD feTester2 = new StudentMapFrontendFD(new Scanner(System.in), backend2);
        feTester2.runCommandLoop();
        String output2 = uiTester2.checkOutput();

        //checking if the fake location is found or not
        if(!output2.contains("The location does not exist.")){
            Assertions.fail("The searchLocationsCommand() failed when searching for an invalid location.");
        }
    }

    /**
     * testing displayAllLocationsCommand()
     */
    @Test
    public void testFive(){

        //initializing the input of the user
        TextUITester uiTester = new TextUITester("D\nQ");
        StudentMapBackendFD backend = new StudentMapBackendFD();
        StudentMapFrontendFD feTester = new StudentMapFrontendFD(new Scanner(System.in), backend);
        feTester.runCommandLoop();
        String output = uiTester.checkOutput();

        if(!output.contains("Van Vleck \nChamberlain Hall")){
            Assertions.fail("The displayAllLocationsCommand() method does not function properly.");
        }
    }
}
