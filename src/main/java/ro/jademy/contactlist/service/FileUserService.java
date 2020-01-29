package ro.jademy.contactlist.service;

import ro.jademy.contactlist.model.Address;
import ro.jademy.contactlist.model.Company;
import ro.jademy.contactlist.model.PhoneNumber;
import ro.jademy.contactlist.model.User;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FileUserService implements UserService{

    private File contactsFile;
    private List<User> contacts = new ArrayList<>();

    public FileUserService(File contactsFile) {
        this.contactsFile = contactsFile;
    }

    public FileUserService(String contactsFileName) {
        this(new File(contactsFileName));
    }

    @Override
    public List<User> getContacts() {

        // check if contacts is empty
        if (contacts.isEmpty()) {
            contacts.addAll(readFromFile());
        }

        // else return the current list of contacts
        return contacts;
    }

    @Override
    public Optional<User> getContactById(int userId) {
        return contacts.stream().filter(u -> u.getId() == userId).findFirst();
    }

    @Override
    public void addContact(User contact) {
        // add user to contact list
        contacts.add(contact);

        // overwrite the whole list of contacts in the file
        writeToFile();
    }

    @Override
    public void editContact(int userId, String firstName, String lastName, String email, Integer age, Map<String, PhoneNumber> phoneNumbers, Address address, String jobTitle, Company company, boolean isFavorite) {
        Optional<User> userOpt = getContactById(userId);

        // edit the contact only if the user was found
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // TODO: use setters and update the user

            // overwrite the whole list of contacts in the file
            writeToFile();
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

        // TODO: write changes to file
    }

    @Override
    public List<User> search(String query) {

        // TODO: implement method

        return new ArrayList<>();
    }

    private List<User> readFromFile() {
        // TODO: read user properties from file and create the user list
        // TODO: remember to check if the file exists first (create it if it does not)

        return new ArrayList<>();
    }

    private void writeToFile() {
        // TODO: implement method using the contacts and file properties
    }

    public  Map<Character, List<User>> makeUserMap(){
        return contacts.stream()
                .sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
                .collect(Collectors.groupingBy(user -> user.getLastName().charAt(0), TreeMap::new, Collectors.toList()));
    }

}
