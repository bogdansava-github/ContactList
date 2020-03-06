package ro.jademy.contactlist;

import ro.jademy.contactlist.service.DBUserService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        Connection conn = getConnection(getProperties());
        Menu menu = new Menu(new DBUserService(conn));
        menu.showMenu(new UserForm(), conn);
    }

    public static Properties getProperties() {
        Properties prop = new Properties();
        try (InputStream input = DBUserService.class.getResourceAsStream("/application.properties")){
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    //Open a connection
    private static Connection getConnection(Properties props) {
        Connection conn = null;
        String dbName = props.getProperty("db.name");
        String dbUrl = "jdbc:mysql://" + props.getProperty("db.url") + ":" + props.getProperty("db.port") + "/" + dbName + "?useOldAliasMetadataBehavior=true";

        try {
            conn = DriverManager.getConnection(dbUrl, setConnectionProps(props));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static Properties setConnectionProps(Properties props) {

        Properties connectionProps = new Properties();
        connectionProps.put("user", props.getProperty("db.user"));
        connectionProps.put("password", props.getProperty("db.password"));
        return connectionProps;
    }
}


