package sample;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import javafx.event.ActionEvent;
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
    List cities;
    Database db=new Database();

    public Controller() throws Exception {

        db.getCountries();
    }

    public void chooseCountry(Event event) throws Exception {
        countries=db.getCountries();
        boxCountry.getItems().setAll(countries);

    }


    public void chooseCity(Event event) throws Exception {
        cities=db.getCities(boxCountry.getValue());
        boxCity.getItems().setAll(cities);

    }

    public void getCityInfo(ActionEvent event) throws Exception {

        String pop=db.getPopulation((String) boxCity.getValue());

        lblPopulation.setText(pop);

    }
}
