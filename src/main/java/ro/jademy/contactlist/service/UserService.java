package ro.jademy.contactlist.service;

import ro.jademy.contactlist.model.Address;
import ro.jademy.contactlist.model.Company;
import ro.jademy.contactlist.model.PhoneNumber;
import ro.jademy.contactlist.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    List<User> getContacts();

    List<User> getContactsIfCsvModified();

    Optional<User> getContactById(int userId);

    void addContact(User contact);

    void editContact(int userId, String firstName,
                     String lastName,
                     String email,
                     Integer age, Map<String, PhoneNumber> phoneNumbers,
                     Address address,
                     String jobTitle,
                     Company company,
                     boolean isFavorite);

    void removeContact(int userId);

    List<User> search(String query);

    void createBackup();

    void viewBackupFilesDetails();

    void restoreFromBackup(String fileName);

    void deleteBackupFile(String endFileName);

    Map<Character, List<User>> makeUserMap(ArrayList<User> userList);
}
