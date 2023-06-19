
import java.util.List;

public interface FrontendInterface{
    public char mainMenuPrompt();
    public void loadDataCommand();
    public List<String> chooseLocationsPrompt();
    public void addLocationCommand(List<String> locations);
    public void removeLocationsCommand(List<String> locations);
    public void searchLocationsCommand(List<String> locations);
    public void displayAllLocationsCommand();
}
