
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class StudentMapFrontendFD implements FrontendInterface {

    //initializing the scanner and backend interface
    Scanner scanner;
    StudentMapBackendInterface backend;

    //constructor
    public StudentMapFrontendFD(Scanner scanner, StudentMapBackendInterface backend) {
        this.scanner = scanner;
        this.backend = backend;
    }

    /**
     * receives input from the user
     */
    public void runCommandLoop() {
        while (true) {
            //the user's char input
            char choice = this.mainMenuPrompt();

            //performs different actions based on the user's input(s)
            switch (choice) {
                //loads the data
                case 'L' -> {
                    this.loadDataCommand();
                }
                //allows the user to select locations
                case 'S' -> {
                    List<String> loc = this.chooseLocationsPrompt();
                    searchLocationsCommand(loc);
                }
                //allows the user to add a location
                case 'A' -> {
                    List<String> location = this.chooseLocationsPrompt();
                    addLocationCommand(location);
                }
                //allows the user to remove a location
                case 'R' -> {
                    List<String> location = this.chooseLocationsPrompt();
                    removeLocationsCommand(location);
                }
                //allows the user to add a path
                case 'P' -> {
                    List<String> location = this.choosePathPrompt();
                    addPathCommand(location);
                }
                //allows the user to see the shortest distance and buildings between two locations
                case 'G' -> {
                    List<String> location = this.choosePathPrompt();
                    getShortestPath(location);
                }
                //allows the user to display all locations
                case 'D' -> this.displayAllLocationsCommand();
                //quit
                case 'Q' -> {
                    this.scanner.close();
                    System.out.println("Thank you for using the Student Class Map!");
                    return;
                }
                default -> System.out.println("I don't understand, please try again");
            }
        }
    }

    /**
     * presents a main menu with a list of actions for the users to choose from
     *
     * @return the inputted character and \0 otherwise
     */
    public char mainMenuPrompt() {
        //the list of options for the user
        System.out.println("\nWelcome to the Student Class Map. Here are your options to proceed: ");
        System.out.println("------------------------------");
        System.out.println("[L]oad Locations");
        System.out.println("[S]earch Locations");
        System.out.println("[A]dd Location");
        System.out.println("[R]emove Location");
        System.out.println("Add [P]ath");
        System.out.println("[G]et Shortest Path");
        System.out.println("[D]isplay Locations");
        System.out.println("[Q]uit");
        System.out.println("Your input: ");

        String input = this.scanner.nextLine().toUpperCase();

        //checks if the user inputted more than one character
        if (input.length() != 1) {
            return '\0';
        }

        //checks if the character is a valid option
        char inputChar = input.charAt(0);
        if (inputChar == 'L' || inputChar == 'S' || inputChar == 'A' || inputChar == 'R' || inputChar == 'P' || inputChar == 'G' || inputChar == 'D' || inputChar == 'Q') {
            return inputChar;
        } else {
            return '\0';
        }
    }

    /**
     * loads the data from a given file
     */
    public void loadDataCommand() {
        //receives the desired file from the user
        System.out.println("Please enter the file you would like to use: ");
        String filename = this.scanner.nextLine().trim();

        //checks if the file is a valid option or not
        try {
            this.backend.loadData(filename);
            System.out.println("The file has been successfully loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("The file provided is invalid.");
        }
    }

    /**
     * asks the user to enter the locations they would like to use
     *
     * @return a list of the locations
     */
    public List<String> chooseLocationsPrompt() {
        //asks the user to input their desired location(s)
        System.out.println("Enter which locations you would like to choose: ");
        String input = this.scanner.nextLine().trim();

        //separates the input by "," and stores in a list
        String[] locations = input.split(",");
        List<String> locationList = new ArrayList<>();
        for (String toAdd : locations) {
            locationList.add(toAdd.trim());
        }
        return locationList;
    }

    /**
     * allows the user to add buildings
     *
     * @param locations - the locations the user would like to add
     */
    public void addLocationCommand(List<String> locations) {
        //iterates through the locations the user has inputted and if the building has not been added yet, does so
        for (String buildings : locations) {
            if (backend.addBuilding(buildings)) {
                System.out.println("Locations added successfully.");
            } else {
                System.out.println("The location is already added.");
            }
        }
    }

    /**
     * allows the user to add paths
     * @param paths - the list of paths the user would like to add
     */
    public void addPathCommand(List<String> paths) {
        //setting the user start and end locations
        String start = paths.get(0);
        String end = paths.get(1);
        //asking the user for distance
        System.out.println("Enter the distance between the two locations: ");
        try{
            double input = Double.parseDouble(this.scanner.nextLine());
            //adds the path if it has not been added yet
            if(backend.addPath(start,end,input)){
                System.out.println("Path added successfully.");
            }
            else{
                System.out.println("The path has already been added.");
            }
        } catch(NumberFormatException ignore){
            System.out.println("Please enter a valid number.");
        }
    }

    /**
     * removes buildings
     *
     * @param locations - the locations the user would like to remove
     */
    public void removeLocationsCommand(List<String> locations) {
        //iterates through the locations the user would like to remove and does so unless it has already been removed
        for (String buildings : locations) {
            if (backend.remove(buildings)) {
                System.out.println("Locations removed successfully.");
            } else {
                System.out.println("Location does not exist or has already been removed.");
            }
        }
    }

    /**
     * allows the user to search if specific buildings are contained
     *
     * @param locations - the location(s) the user would like to search for
     */
    public void searchLocationsCommand(List<String> locations) {
        //initializes a string to return all buildings
        String loc1 = backend.getAllBuildingsString();
        //checks if there are buildings or not
        if (!loc1.isEmpty()) {
            //iterates through the user inputs and checks if it is contained
            for (String check : locations) {
                if (loc1.contains(check)) {
                    System.out.println("The location exists.");
                } else {
                    System.out.println("The location does not exist.");
                }
            }
        } else {
            System.out.println("There are no buildings currently.");
        }
    }

    /**
     * returns the shortest path distance between two locations as well as the buildings between
     * @param locations - the locations/buildings the user would like to find the shortest distance and buildings for
     */
    public void getShortestPath(List<String> locations) {

        while(locations.size()<2){
            System.out.println("Please enter two locations: ");
            this.chooseLocationsPrompt();
        }
        //setting the user start and end locations
        String start = locations.get(0);
        String end = locations.get(1);

        try{
            List<Double> distances = backend.findShortestPathDistances(start, end);
            List<BuildingInterface> buildings = backend.findShortestPathBuildings(start, end);
            int distancesIndex = 0;
            double totalDistance = 0;
            boolean isFirst = true;

            //printing buildings along the way
            System.out.println("\nThe buildings along the path: ");
            for (BuildingInterface building : buildings) {
                if (isFirst) {
                    System.out.print(building.getName());
                    isFirst = false;
                } else {
                    System.out.print(" --(" + distances.get(distancesIndex) + ")--> " + building.getName());
                    totalDistance += distances.get(distancesIndex);
                    distancesIndex++;
                }
            }

            //printing total distance
            System.out.println("\nThe total distance: " + totalDistance + "\n");
        } catch (NoSuchElementException e){
            System.out.println("One of the given buildings is not in the map.");
        }
    }

    /**
     * displays all locations
     */
    public void displayAllLocationsCommand() {
        System.out.println(backend.getAllBuildingsString());
    }

    public List<String> choosePathPrompt(){
        while (true) {
            //asks the user to input their desired location(s)
            System.out.println("Please enter a start and end location: ");
            String input = this.scanner.nextLine().trim();

            //separates the input by "," and stores in a list
            String[] locations = input.split(",");
            List<String> locationList = new ArrayList<>();

            for (String toAdd : locations) {
                locationList.add(toAdd.trim());
            }

            if (locationList.size() == 2) {
                return locationList;
            } else {
                System.out.println("Please enter 2 locations.");
            }
        }
    }
}
