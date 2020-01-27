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
        String countries="SELECT country.name FROM country limit 1";
        PreparedStatement stmt=getConnection().prepareStatement(countries);
        ResultSet rs=stmt.executeQuery();
        String country;
        List<String> list=new ArrayList<>();
        while (rs.next()){
            country=rs.getString("Name");
            System.out.println(country);
            list.add(rs.getString("Name"));
        }

        return null;

    }
}
