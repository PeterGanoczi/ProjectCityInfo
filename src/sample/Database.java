package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public static final String COUNTRIES="SELECT country.name FROM country ORDER BY name ASC";
    public static final  String CITIES="SELECT city.name, country.Code2, city.CountryCode, JSON_EXTRACT(info, '$.Population') AS Info, country.name " +
            "FROM country JOIN city ON country.code=city.countrycode " +
            "WHERE country.name LIKE ? ORDER BY city.name ASC";


    private final String JDBC="com.mysql.cj.jdbc.Driver";
    private final String URL="jdbc:mysql://itsovy.sk:3306/world_x?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private Connection connection;


    public Connection getConnection() throws Exception {
        Class.forName(JDBC);
        connection= DriverManager.getConnection(URL,"student","kosice2019");
        return connection;
    }

    public List getCountries() throws Exception {

        PreparedStatement stmt=getConnection().prepareStatement(COUNTRIES);
        ResultSet rs=stmt.executeQuery();

        List<String> list=new ArrayList<>();
        while (rs.next()){
            list.add(rs.getString("Name"));
        }

        return list;

    }

    public List getCities(String country) throws Exception{
         PreparedStatement stmt=getConnection().prepareStatement(CITIES);
        stmt.setString(1,country);
        ResultSet rs=stmt.executeQuery();

        List<City> list=new ArrayList<>();
        while (rs.next()){
            String name=rs.getString("city.name");
            String code2=rs.getString("country.Code2");
            String code3=rs.getString("city.CountryCode");
            int population=rs.getInt("Info");
            String countryName=rs.getString("country.name");
            City newCity=new City(name, population,code3,code2,countryName);

            list.add(newCity);
        }
        return list;
    }

    public String getPopulation(String city, String country) throws Exception {
        String cityPop="";
        String population="SELECT json_extract(info, '$.Population') FROM city JOIN country ON country.code=city.countryCode WHERE city.name LIKE ? AND country.name LIKE ? ";
        PreparedStatement stmt=getConnection().prepareStatement(population);
        stmt.setString(1,city);
        stmt.setString(2,country);
        ResultSet rs=stmt.executeQuery();

        if (rs.next()){
            cityPop=rs.getString(1);
        }
        return cityPop;
    }
}
