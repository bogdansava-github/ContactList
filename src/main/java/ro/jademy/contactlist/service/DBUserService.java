package ro.jademy.contactlist.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;


public class DBUserService implements UserService {

    private Connection conn;

    public DBUserService(Connection conn) {
        this.conn = conn;
    }

    public static Properties setConnectionProps(Properties props) {

        Properties connectionProps = new Properties();
        connectionProps.put("user", props.getProperty("db.user"));
        connectionProps.put("password", props.getProperty("db.password"));
        return connectionProps;
    }

    //Open a connection
    public static Connection getConnection(Properties props) throws SQLException {

        String dbUrl = "jdbc:mysql://" + props.getProperty("db.url") + ":" + props.getProperty("db.port") + "/";
        String dbName = props.getProperty("db.name");
        try {
            Connection conn = DriverManager.getConnection(dbUrl + dbName, setConnectionProps(props));
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        public void createDataBase (Properties props){
            Statement stmt = null;
            String dbUrl = "jdbc:mysql://" + props.getProperty("db.url") + ":" + props.getProperty("db.port") + "/";
            String dbName = props.getProperty("db.name");
            try {
                //Create Database if not exists
                System.out.println("Connecting ...");
                Connection conn = DriverManager.getConnection(dbUrl, setConnectionProps(props));
                System.out.println("Connection established!");

                System.out.println("Creating database...");
                stmt = conn.createStatement();
                stmt.execute("CREATE DATABASE " + dbName);
                System.out.println("Database created!");
                System.out.println("Creating tables......");


                //Create tables from a SQL Script

                //Initialize the script runner
                ScriptRunner sr = new ScriptRunner(conn);
                //Creating a reader object
                Reader reader = new BufferedReader(new FileReader("contactList.sql"));
                //Running the script
                sr.runScript(reader);
                System.out.println("Tables created!");

                //insert contacts in dbase
                reader = new BufferedReader(new FileReader("contactsInsert.sql"));
                sr.runScript(reader);

            } catch (SQLException e) {
                System.out.println("Database exist!");
                System.out.println("Accessing " + dbName + " database ...\n");
                stmt.execute("USE " + dbName);
                System.exit(0);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
