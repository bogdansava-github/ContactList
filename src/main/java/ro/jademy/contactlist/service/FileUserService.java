package ro.jademy.contactlist.service;

import ro.jademy.contactlist.model.Address;
import ro.jademy.contactlist.model.Company;
import ro.jademy.contactlist.model.PhoneNumber;
import ro.jademy.contactlist.model.User;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

public class FileUserService implements UserService
{

    private File contactsFile;
    private List<User> contacts = new ArrayList<>();

    public FileUserService(File contactsFile)
    {
        this.contactsFile = contactsFile;
    }

    public FileUserService(String contactsFileName)
    {
        this(new File(contactsFileName));
    }

    @Override
    public List<User> getContacts()
    {

        // check if contacts is empty
        if (contacts.isEmpty())
        {
            contacts.addAll(readFromFile());
        }

        // else return the current list of contacts
        return contacts;
    }

    @Override
    public Optional<User> getContactById(int userId)
    {
        return contacts.stream().filter(u -> u.getUserId() == userId).findFirst();
    }

    @Override
    public void addContact(User contact)
    {
        // add user to contact list
        contacts.add(contact);

        // overwrite the whole list of contacts in the file
        writeToFile();
    }

    @Override
    public void editContact(int userId, String firstName, String lastName, String email, Integer age, Map<String,
            PhoneNumber> phoneNumbers, Address address, String jobTitle, Company company, boolean isFavorite)
    {

        Optional<User> userOpt = getContactById(userId);

        // edit the contact only if the user was found
        if (userOpt.isPresent())
        {

            // TODO: use setters and update the user
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


            // overwrite the whole list of contacts in the file
            writeToFile();
        }
    }

    @Override
    public void removeContact(int userId)
    {
        Optional<User> userOpt = getContactById(userId);

        // remove the contact only if found
        if (userOpt.isPresent())
        {
            User user = userOpt.get();
            contacts.remove(user);
        }

        // TODO: write changes to file
        writeToFile();
    }

    @Override
    public List<User> search(String criteria)
    {

        contacts.stream()
                .filter(user -> user.getLastName().toLowerCase().contains(criteria.toLowerCase())
                        || user.getFirstName().toLowerCase().contains((criteria.toLowerCase()))
                        || user.getCompany().getName().toLowerCase().contains((criteria.toLowerCase()))
                        || user.getJobTitle().toLowerCase().contains((criteria.toLowerCase())))
                .sorted()
                .forEach(System.out::println);


        return new ArrayList<>();
    }

    private List<User> readFromFile()
    {
        // TODO: read user properties from file and create the user list
        // TODO: remember to check if the file exists first (create it if it does not)

        List<String> lines = new ArrayList<>();
        List<User> contactList = new ArrayList<>();
        Map<String, PhoneNumber> phoneNumbersNewUser = new HashMap<>();

        try (BufferedReader in = new BufferedReader(new FileReader(contactsFile)))
        {
            String line = null;
            while ((line = in.readLine()) != null)
            {
                lines.add(line);
            }

        }
        catch (IOException ex)
        {
            System.out.println("File not found \n" + ex);
            ;
        }

        for (String line : lines)
        {
            String[] userProperties = line.split("\\|");

            int id = Integer.parseInt(userProperties[0]);
            String fName = userProperties[1];
            String lName = userProperties[2];
            String email = userProperties[3];
            int age = Integer.parseInt(userProperties[4]);
            String[] phones = userProperties[5].split("\\,");
            String[] homePhone = phones[0].split("\\_");
            String[] mobilePhone = phones[1].split("\\_");
            String[] workPhone = phones[2].split("\\_");
            phoneNumbersNewUser.put(homePhone[0], new PhoneNumber(homePhone[1], homePhone[2]));
            phoneNumbersNewUser.put(mobilePhone[0], new PhoneNumber(mobilePhone[1], mobilePhone[2]));
            phoneNumbersNewUser.put(workPhone[0], new PhoneNumber(workPhone[1], workPhone[2]));
            String[] homeAdress = userProperties[6].split("\\,");
            Address homeAdressNewUser = new Address(homeAdress[0], Integer.parseInt(homeAdress[1]), Integer.parseInt(homeAdress[2]),
                    homeAdress[3], homeAdress[4], homeAdress[5], homeAdress[6]);
            String title = userProperties[7];
            String[] companyProperties = userProperties[8].split("\\_");
            String[] companyAddress = companyProperties[1].split("\\,");
            Address companyAddressNewUser = new Address(companyAddress[0], Integer.parseInt(companyAddress[1]),
                    Integer.parseInt(companyAddress[2]), companyAddress[3], companyAddress[4], companyAddress[5],
                    companyAddress[6]);
            String companyName = companyProperties[0];
            Company companyNewUser = new Company(companyName, companyAddressNewUser);
            boolean isFav = Boolean.parseBoolean(userProperties[9]);
            User user = new User(id, fName, lName, email, age, phoneNumbersNewUser, homeAdressNewUser, title, companyNewUser, isFav);
            contactList.add(user);
        }


        return contactList;
    }

    private void writeToFile() {
        writeToFile(contactsFile);
    }

    private void writeToFile(File contactsFile)
    {
        // TODO: implement method using the contacts and file properties

        try (BufferedWriter out = new BufferedWriter(new FileWriter(contactsFile)))
        {
            List<String> lines = new ArrayList<>();

            for (User user : contacts)
            {
                String line = user.getUserId() + "|" + user.getFirstName() + "|" + user.getLastName() + "|" + user.getEmail() + "|" +
                        user.getAge() + "|home_" + user.getPhoneNumbers().get("home").getCountryCode() + "_" +
                        user.getPhoneNumbers().get("home").getNumber() + ",mobile_" +
                        user.getPhoneNumbers().get("mobile").getCountryCode() + "_" +
                        user.getPhoneNumbers().get("mobile").getNumber() + ",work_" +
                        user.getPhoneNumbers().get("work").getCountryCode() + "_" +
                        user.getPhoneNumbers().get("work").getNumber() + "|" + user.getAddress().getStreetName() + "," +
                        user.getAddress().getStreetNumber() + "," + user.getAddress().getApartmentNumber() + "," +
                        user.getAddress().getFloor() + "," + user.getAddress().getZipCode() + "," +
                        user.getAddress().getCity() + "," + user.getAddress().getCountry() + "|" +
                        user.getJobTitle() + "|" + user.getCompany().getName() + "_" +
                        user.getCompany().getAddress().getStreetName() + "," +
                        user.getCompany().getAddress().getStreetNumber() + "," +
                        user.getCompany().getAddress().getApartmentNumber() + "," +
                        user.getCompany().getAddress().getFloor() + "," +
                        user.getCompany().getAddress().getZipCode() + "," +
                        user.getCompany().getAddress().getCity() + "," +
                        user.getCompany().getAddress().getCountry() + "|" +
                        user.isFavorite();

                lines.add(line);

            }

            for (String line : lines)
            {
                out.write(line);
                out.newLine();
            }

        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File not found " + contactsFile + "\n" + ex);
        }
        catch (IOException ex)
        {
            System.out.println("Failed to write content to file " + contactsFile + "\n" + ex);
        }


    }


    public void createBackup(){
        String path = "backup" + File.separator;
        try {


            String fileName = "contacts_bkp_" + System.currentTimeMillis() + ".csv";

            File source = new File("contacts.csv");
            File destination = new File(path + fileName);
            Files.copy(source.toPath(), destination.toPath());


            System.out.println("Backup created with the name: " + fileName + "\n");

        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public void viewBackupFilesDetails(){
        String path = "backup" + File.separator;
        File dir = new File(path);
        try {

            for (String d : dir.list()
            ) {
                Path path1 = FileSystems.getDefault().getPath("backup" + File.separator, d);
                BasicFileAttributes attr = Files.readAttributes(path1, BasicFileAttributes.class);
                System.out.println(d + " ");
                System.out.println("created on " + attr.creationTime());
                System.out.println(attr.size() + " bytes");
                System.out.println();

            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public  void restoreFromBackup(String fileName){
        String path = "backup" + File.separator;
        try {


            //String fileName = "contacts_bkp_" + System.currentTimeMillis() + ".csv";

            File destination = new File("contacts.csv");
            File source = new File(path + fileName);
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);


            System.out.println("Backup restored!!!");

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void deleteBackupFile(String endFileName){
        String path = "backup" + File.separator;
        File dir = new File(path);
        FilenameFilter filter = (dir1, s) -> s.endsWith(endFileName);
        String[] results = dir.list(filter);

        if (results == null) {
            System.out.println("file not found");
        } else
            for (int i = 0; i < results.length; i++) {
                String filename = results[i];
                File f = new File(path + filename);
                System.out.println(filename + " deleted");
                f.delete();
            }

    }

    public Map<Character, List<User>> makeUserMap(ArrayList<User> userList)
    {
        return userList.stream()
                .sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
                .collect(Collectors.groupingBy(user -> user.getLastName().charAt(0), TreeMap::new, Collectors.toList()));
    }



}
