package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/sas";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection cnx=null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cnx= DriverManager.getConnection(URL,USERNAME, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConn(){
        return cnx;
    }




}
