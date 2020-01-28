package sample;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.util.List;

public class Controller {

    public ComboBox<String> boxCountry;
    public ComboBox<String> boxCity;
    public Label lblPopulation;
    public Label lblTemperature;
    public Label lblHumidity;
    public Label lblDistance;
    public Button btnOk;
    public Label lblCountry;
    public Label lblCity;
    List countries;
    List<City> city;
    Database db=new Database();

    public Controller() throws Exception {

        db.getCountries();
    }

    public void initialize(){
        boxCity.setDisable(true);
        btnOk.setDisable(true);

    }

    public void chooseCountry(Event event) throws Exception {

        countries=db.getCountries();
        boxCountry.getItems().setAll(countries);
        boxCity.setDisable(false);

    }

    public void chooseCity(Event event) throws Exception {


        city=db.getCities(boxCountry.getValue());
        boxCity.getItems().clear();
        for (City s: city){
            boxCity.getItems().add(s.getName());
        }
        btnOk.setDisable(false);

    }

    public void getCityInfo(ActionEvent event) throws Exception {

        //String pop=db.getPopulation(boxCity.getValue(),boxCountry.getValue());
        String population=boxCity.getValue();
        for (City s:city){
            if (s.getName().equalsIgnoreCase(population)){
                lblPopulation.setText(formatPopString(s.getPopulation()));
                lblCity.setText("City: "+s.getName());
                lblCountry.setText("Country: "+s.getCountryName());
            }
        }
        if (population==null){
            return;
        }

    }

    private String formatPopString(int population) {

        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(population);



    }
}
