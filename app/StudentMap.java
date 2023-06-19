import java.util.Scanner;

public class StudentMap {
    public static void main(String[] args) {
        MapReaderDW reader = new MapReaderDW();

        GraphAE<BuildingDW> graph = new GraphAE<>();

        StudentMapBackendBD backend = new StudentMapBackendBD(graph, reader);

        Scanner scanner = new Scanner(System.in);

        StudentMapFrontendFD frontend = new StudentMapFrontendFD(scanner, backend);

        frontend.runCommandLoop();
    }
}
