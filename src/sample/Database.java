package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final String JDBC="com.mysql.cj.jdbc.Driver";
    private final String URL="jdbc:mysql://itsovy.sk:3306/world_x?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private Connection connection;


    public Connection getConnection() throws Exception {
        Class.forName(JDBC);
        connection= DriverManager.getConnection(URL,"student","kosice2019");
        return connection;
    }

    public List getCountries() throws Exception {
        String countries="SELECT country.name FROM country";
        PreparedStatement stmt=getConnection().prepareStatement(countries);
        ResultSet rs=stmt.executeQuery();

        List<String> list=new ArrayList<>();
        while (rs.next()){
            list.add(rs.getString("Name"));
        }

        return list;

    }

    public List getCities(String country) throws Exception{
        String cities="SELECT city.name FROM country JOIN city ON country.code=city.countrycode WHERE country.name LIKE ?";
        PreparedStatement stmt=getConnection().prepareStatement(cities);
        stmt.setString(1,country);
        ResultSet rs=stmt.executeQuery();

        List<String> list=new ArrayList<>();
        while (rs.next()){
            list.add(rs.getString("Name"));
        }
        return list;
    }

    public String getPopulation(String city) throws Exception {
        String cityPop="";
        String population="SELECT json_extract(info, '$.Population') FROM city WHERE name LIKE ?";
        PreparedStatement stmt=getConnection().prepareStatement(population);
        stmt.setString(1,city);
        ResultSet rs=stmt.executeQuery();

        if (rs.next()){
            cityPop=rs.getString(1);
        }
        return cityPop;
    }
}
