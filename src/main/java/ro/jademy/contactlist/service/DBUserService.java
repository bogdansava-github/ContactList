package ro.jademy.contactlist.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.*;

import org.apache.ibatis.jdbc.ScriptRunner;
import ro.jademy.contactlist.model.Address;
import ro.jademy.contactlist.model.Company;
import ro.jademy.contactlist.model.PhoneNumber;
import ro.jademy.contactlist.model.User;


public class DBUserService implements UserService {

    private List<User> contacts = new ArrayList<>();

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
    public static Connection getConnection(Properties props) {
        Connection conn = null;
        String dbUrl = "jdbc:mysql://" + props.getProperty("db.url") + ":" + props.getProperty("db.port") + "/";
        String dbName = props.getProperty("db.name");
        try {
            conn = DriverManager.getConnection(dbUrl + dbName, setConnectionProps(props));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void createDataBase(Properties props) throws SQLException {
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


    @Override
    public List<User> getContacts(Connection conn) {

        // check if contacts is empty
        if (contacts.isEmpty()) {
            contacts.addAll(readFromDB(conn));
        }

        // else return the current list of contacts
        return contacts;

    }

    private List<User> readFromDB(Connection conn) {
        List<User> contactList = new ArrayList<>();
        Map<String, PhoneNumber> phoneNumbersNewUser = new HashMap<>();

        //TODO: write the query to get User from DB
        String query = "SELECT u.user_id";


        try {
            Statement stm = conn.createStatement();
            ResultSet result = stm.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("user_id");
                String fName = result.getString("first_name");
                String lName = result.getString("last_name");
                String email = result.getString("email");
                int age = result.getInt("age");

                //TODO: phone numbers map


                User user = new User(id, fName, lName, email, age, phoneNumbersNewUser, homeAdressNewUser, title, companyNewUser, isFav);
                contactList.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return contactList;
    }

    @Override
    public List<User> getContactsIfCsvModified() {
        return null;
    }

    @Override
    public Optional<User> getContactById(int userId) {
        return Optional.empty();
    }

    @Override
    public void addContact(User contact) {

    }

    @Override
    public void editContact(int userId, String firstName, String lastName, String email, Integer age, Map<String,
            PhoneNumber> phoneNumbers, Address address, String jobTitle, Company company, boolean isFavorite) {

    }

    @Override
    public void removeContact(int userId) {

    }

    @Override
    public List<User> search(String query) {
        return null;
    }

    @Override
    public Map<Character, List<User>> makeUserMap(ArrayList<User> userList) {
        return null;
    }

    @Override
    public void createBackup() {

    }

    @Override
    public void viewBackupFilesDetails() {

    }

    @Override
    public void restoreFromBackup(String fileName) {

    }

    @Override
    public void deleteBackupFile(String endFileName) {

    }
}
