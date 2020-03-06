package ro.jademy.contactlist.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public void createDataBase(Properties props) throws SQLException {
        Statement stmt = null;
        String dbName = props.getProperty("db.name");
        try {
            //Create Database if not exists
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

        // check if contacts is empty
        if (contacts.isEmpty()) {
            contacts.addAll(readFromDB());
        }

        // else return the current list of contacts
        return contacts;

    }

    private List<User> readFromDB() {
        List<User> contactList = new ArrayList<>();

        //TODO: write the query to get User from DB


        try {
            String query = Files.lines(Paths.get("src/main/resources/db/readContacts.sql")).collect(Collectors.joining(" "));


            Statement stm = conn.createStatement();
            ResultSet result = stm.executeQuery(query);
            while (result.next()) {
                int id = result.getInt(1);
                String fName = result.getString(2);
                String lName = result.getString(3);
                String email = result.getString(4);
                int age = result.getInt(5);

                Map<String, PhoneNumber> phoneNumbersNewUser = new HashMap<>();
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
                //phoneNumbersNewUser.clear();
            }
        } catch (SQLException | IOException ex) {
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
        Optional<User> userOpt = getContactById(userId);

        // edit the contact only if the user was found
        if (userOpt.isPresent()) {

            User user = userOpt.get();

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setAge(age);
            user.setPhoneNumbers(phoneNumbers);
            user.setAddress(address);
            user.setJobTitle(jobTitle);
            user.setCompany(company);
            user.setFavorite(isFavorite);


            String queryUpdate = "UPDATE users " +
                    "INNER JOIN companies ON users.user_id = companies.user_id " +
                    "SET users.first_name = ?, users.last_name = ?, companies.job_title = ?, companies.company_name = ? " +
                    "WHERE users.user_id = ? ";
            try {
                conn.setAutoCommit(false);

                PreparedStatement preparedStmt = conn.prepareStatement(queryUpdate);
                preparedStmt.setString(1, user.getFirstName());
                preparedStmt.setString(2, user.getLastName());
                preparedStmt.setString(3, user.getJobTitle());
                preparedStmt.setString(4, user.getCompany().getName());
                preparedStmt.setInt(5, user.getUserId());
                preparedStmt.executeUpdate();
                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            String queryUpdateMobilePhones = "UPDATE phonenumbers SET country_code = ?, phone_number = ? WHERE user_id = ? AND phone_cat = 'm'";
            String queryUpdateHomePhones = "UPDATE phonenumbers SET country_code = ?, phone_number = ? WHERE user_id = ? AND phone_cat = 'h'";
            String queryUpdateWorkPhones = "UPDATE phonenumbers SET country_code = ?, phone_number = ? WHERE user_id = ? AND phone_cat = 'w'";

            try {
                conn.setAutoCommit(false);
                PreparedStatement preparedStmt = conn.prepareStatement(queryUpdateMobilePhones);
                preparedStmt.setString(1, user.getPhoneNumbers().get("m").getCountryCode());
                preparedStmt.setString(2, user.getPhoneNumbers().get("m").getNumber());
                preparedStmt.setInt(3, user.getUserId());
                preparedStmt.executeUpdate();
                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.setAutoCommit(false);
                PreparedStatement preparedStmt = conn.prepareStatement(queryUpdateHomePhones);
                preparedStmt.setString(1, user.getPhoneNumbers().get("h").getCountryCode());
                preparedStmt.setString(2, user.getPhoneNumbers().get("h").getNumber());
                preparedStmt.setInt(3, user.getUserId());
                preparedStmt.executeUpdate();
                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.setAutoCommit(false);
                PreparedStatement preparedStmt = conn.prepareStatement(queryUpdateWorkPhones);
                preparedStmt.setString(1, user.getPhoneNumbers().get("w").getCountryCode());
                preparedStmt.setString(2, user.getPhoneNumbers().get("h").getNumber());
                preparedStmt.setInt(3, user.getUserId());
                preparedStmt.executeUpdate();
                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }



        }
    }

    @Override
    public void removeContact(int userId) {
        Optional<User> userOpt = getContactById(userId);

        // remove the contact only if found
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            contacts.remove(user);

        }
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
