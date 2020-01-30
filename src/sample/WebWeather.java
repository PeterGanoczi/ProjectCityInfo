package sample;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WebWeather {

    public Weather getData(String city, String code2) {
        Weather weather = null;


        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + code2 + "&units=metric&&APPID=17ff3e124c68ed8eedf43e2e12c683c8");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept","application/json");

            if(conn.getResponseCode() == 200){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output = br.readLine();
                System.out.println(output);
                JSONObject outputObj = new JSONObject(output);
                String cityName = outputObj.getString("name");
                String country = outputObj.getJSONObject("sys").getString("country");
                double temperature = outputObj.getJSONObject("main").getDouble("temp");
                int humidity = outputObj.getJSONObject("main").getInt("humidity");
                double lon = outputObj.getJSONObject("coord").getDouble("lon");
                double lat = outputObj.getJSONObject("coord").getDouble("lat");
                double distance= Double.parseDouble(String.valueOf(outputObj.getDouble("visibility")));
                long sunrise=outputObj.getJSONObject("sys").getLong("sunrise");
                long sunset=outputObj.getJSONObject("sys").getLong("sunset");
                weather = new Weather(cityName, country, temperature, humidity, lon, lat,distance,sunrise,sunset);
                return weather;

            }
            conn.disconnect();

        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
