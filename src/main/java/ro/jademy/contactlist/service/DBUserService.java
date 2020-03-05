package ro.jademy.contactlist.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.Properties;
import java.util.*;
import java.util.stream.Collectors;

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
        String dbName = props.getProperty("db.name");
        String dbUrl = "jdbc:mysql://" + props.getProperty("db.url") + ":" + props.getProperty("db.port") + "/" + dbName + "?useOldAliasMetadataBehavior=true";

        try {
            conn = DriverManager.getConnection(dbUrl, setConnectionProps(props));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void createDataBase(Properties props) throws SQLException {
        Statement stmt = null;
        String dbName = props.getProperty("db.name");
        String dbUrl = "jdbc:mysql://" + props.getProperty("db.url") + ":" + props.getProperty("db.port");

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
    public List<User> getContacts() {
        return null;
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
        String query = "SELECT \n" +
                "users.user_id," +
                "users.first_name," +
                "users.last_name," +
                "users.email," +
                "users.age," +
                "ph1.phone_cat," +
                "ph1.country_code," +
                "ph1.phone_number," +
                "ph2.phone_cat," +
                "ph2.country_code," +
                "ph2.phone_number,\n" +
                "ph3.phone_cat," +
                "ph3.country_code," +
                "ph3.phone_number," +
                "ad_h.street_name," +
                "ad_h.street_no," +
                "ad_h.apt_no," +
                "ad_h.apt_floor," +
                "ad_h.zip_code," +
                "ad_h.city city," +
                "ad_h.country,\n" +
                "companies.job_title," +
                "companies.company_name," +
                "ad_w.street_name," +
                "ad_w.street_no," +
                "ad_w.apt_no," +
                "ad_w.apt_floor," +
                "ad_w.zip_code," +
                "ad_w.city, " +
                "ad_w.country," +
                "users.is_favourite\n" +
                "\n" +
                "FROM users\n" +
                "JOIN phonenumbers AS ph1 ON (users.user_id=ph1.user_id AND ph1.phone_cat='m')\n" +
                "JOIN phonenumbers AS ph2 ON (users.user_id=ph2.user_id AND ph2.phone_cat='h')\n" +
                "JOIN phonenumbers AS ph3 ON (users.user_id=ph3.user_id AND ph3.phone_cat='w')\n" +
                "JOIN companies ON (users.user_id=companies.user_id)\n" +
                "JOIN addresses AS ad_h ON (users.user_id=ad_h.user_id AND ad_h.address_cat='h')\n" +
                "JOIN addresses AS ad_w ON (users.user_id=ad_w.user_id AND ad_w.address_cat='w');";


        try {
            Statement stm = conn.createStatement();
            ResultSet result = stm.executeQuery(query);
            while (result.next()) {
                int id = result.getInt(1);
                String fName = result.getString(2);
                String lName = result.getString(3);
                String email = result.getString(4);
                int age = result.getInt(5);
                phoneNumbersNewUser.put(result.getString(6), new PhoneNumber(result.getString(7), result.getString(8)));
                phoneNumbersNewUser.put(result.getString(9), new PhoneNumber(result.getString(10), result.getString(11)));
                phoneNumbersNewUser.put(result.getString(12), new PhoneNumber(result.getString(13), result.getString(14)));
                Address homeAdressNewUser = new Address(
                        result.getString(15),
                        result.getInt(16),
                        result.getInt(17),
                        result.getString(18),
                        result.getString(19),
                        result.getString(20),
                        result.getString(21));

                String title = result.getString(22);

                Address companyAddressNewUser = new Address(
                        result.getString(24),
                        result.getInt(25),
                        result.getInt(26),
                        result.getString(27),
                        result.getString(28),
                        result.getString(29),
                        result.getString(30));
                String companyName = result.getString(23);
                Company companyNewUser = new Company(companyName, companyAddressNewUser);
                boolean isFav = result.getBoolean(31);

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
        return contacts.stream().filter(u -> u.getUserId() == userId).findFirst();
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
    public List<User> search(String criteria) {

        contacts.stream()
                .filter(user -> user.getLastName().toLowerCase().contains(criteria.toLowerCase())
                        || user.getFirstName().toLowerCase().contains((criteria.toLowerCase()))
                        || user.getCompany().getName().toLowerCase().contains((criteria.toLowerCase()))
                        || user.getJobTitle().toLowerCase().contains((criteria.toLowerCase())))
                .sorted()
                .forEach(System.out::println);


        return new ArrayList<>();

    }

    @Override
    public Map<Character, List<User>> makeUserMap(ArrayList<User> userList) {
        return userList.stream()
                .sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
                .collect(Collectors.groupingBy(user -> user.getLastName().charAt(0), TreeMap::new, Collectors.toList()));

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
