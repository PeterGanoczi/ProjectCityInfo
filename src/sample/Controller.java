package sample;

import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.List;

public class Controller {

    public ComboBox<String> boxCountry;
    public ComboBox boxCity;
    public Label lblPopulation;
    public Label lblTemperature;
    public Label lblHumidity;
    public Label lblDistance;
    List countries;
    Database db=new Database();

    public Controller() throws Exception {

        db.getCountries();
    }

    public void chooseCountry(Event event) throws Exception {
        countries=db.getCountries();
        boxCountry.getItems().setAll(countries);

    }
}
