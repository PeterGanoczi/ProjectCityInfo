package sample;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public Label lblSunrise;
    public Label lblSunset;
    public CheckBox checkBoxDetail;
    public Label lblLon;
    public Label lblLat;
    List countries;
    List<City> cities=null;
    Database db=new Database();

    public Controller() throws Exception {

        db.getCountries();
    }

    public void initialize(){
        boxCity.setDisable(true);
        btnOk.setDisable(true);
        if (checkBoxDetail.isSelected()==false){
            lblLat.setVisible(false);
            lblLon.setVisible(false);
            lblSunrise.setVisible(false);
            lblSunset.setVisible(false);
        }

    }

    public void chooseCountry(Event event) throws Exception {

        countries=db.getCountries();
        boxCountry.getItems().setAll(countries);
        boxCity.setDisable(false);

    }

    public void chooseCity(Event event) throws Exception {


        cities=db.getCities(boxCountry.getValue());
        boxCity.getItems().clear();
        for (City s: cities){
            boxCity.getItems().add(s.getName());
        }
        btnOk.setDisable(false);

    }

    public void getCityInfo(ActionEvent event) throws Exception {
        City city=null;
        //String pop=db.getPopulation(boxCity.getValue(),boxCountry.getValue());
        String population=boxCity.getValue();
        for (City s:cities){
            if (s.getName().equalsIgnoreCase(population)){
                lblPopulation.setText("Population: " +formatPopString(s.getPopulation()));
                city=s;
            }
        }

        if (city==null){
            return;
        }
        WebWeather weather=new WebWeather();
        System.out.println(weather.getData(city.getName(),city.getCode2()).getTemp());
        lblCity.setText("City: "+weather.getData(city.getName(),city.getCode2()).getName());
        lblCountry.setText("Country: "+weather.getData(city.getName(), city.getCode2()).getCountry());
        lblHumidity.setText("Humidity: "+ weather.getData(city.getName(), city.getCode2()).getHumidity()+"%");
        lblTemperature.setText("Temperature: "+weather.getData(city.getName(), city.getCode2()).getTemp() +"°C");
        lblDistance.setText("Distance: "+weather.getData(city.getName(),city.getCode2()).getDistance()+" m");

        //HIDDEN DETAIL INFO
        lblLon.setText("Lon: "+weather.getData(city.getName(),city.getCode2()).getLon());
        lblLat.setText("Lat: "+weather.getData(city.getName(),city.getCode2()).getLat());
        //lblSunrise.setText("Sunrise: "+weather.getData(city.getName(),city.getCode2()).getSunrise());

        long millisecondsRise=weather.getData(city.getName(),city.getCode2()).getSunrise();
        System.out.println("RISE: "+millisecondsRise);
        long millis=1580388535727L;
        int minutesL = (int) ((millis / (1000)) % 60);
        int hoursL   = (int) ((millis / (1000*60)) % 24);
        System.out.println("CURRENT TIME"+hoursL+":"+minutesL);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisecondsRise);
        long hours = TimeUnit.MILLISECONDS.toHours(millisecondsRise);
        lblSunrise.setText("Sunrise: "+hours+":"+minutes);

        long millisecondsSet=weather.getData(city.getName(),city.getCode2()).getSunset();
        System.out.println("SET: "+millisecondsSet);
       // long minutesSet =  ((millisecondsSet / (1000*60)) % 60);
        //long hoursSet   =  ((millisecondsSet / (1000*60*60)) % 24);
        long minutesSet = TimeUnit.MILLISECONDS.toMinutes(millisecondsSet);
        long hoursSet = TimeUnit.MILLISECONDS.toHours(millisecondsSet);
        lblSunset.setText("Sunset: "+hoursSet+":"+minutesSet);
        //26339

    }

    private String formatPopString(int population) {

        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(population);
    }


    public void showDetailInfo(ActionEvent event) {
        if (checkBoxDetail.isSelected()==true){
            lblLat.setVisible(true);
            lblLon.setVisible(true);
            lblSunrise.setVisible(true);
            lblSunset.setVisible(true);
        } else {
            lblLat.setVisible(false);
            lblLon.setVisible(false);
            lblSunrise.setVisible(false);
            lblSunset.setVisible(false);
        }
        }

}
