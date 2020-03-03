package ro.jademy.contactlist.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUserService {


    public static Connection getConnection() throws SQLException {
        Properties connectionProps=new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password","@@vipermatrix");

        Connection conn = DriverManager.getConnection("jdbc:"+"mysql"+"://"+
                "localhost"+":"+"3306"+"/employees",connectionProps);

        return conn;
    }
}
