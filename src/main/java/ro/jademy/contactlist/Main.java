package ro.jademy.contactlist;

import ro.jademy.contactlist.model.Address;
import ro.jademy.contactlist.model.Company;
import ro.jademy.contactlist.model.PhoneNumber;
import ro.jademy.contactlist.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // create a contact list of users
        List<User> userList = new ArrayList<>();
        //user1
        Map<String, PhoneNumber> phoneNumbersUser1 = new HashMap<>();
        phoneNumbersUser1.put("work", new PhoneNumber("+40", "723291325"));
        phoneNumbersUser1.put("mobile", new PhoneNumber("+40", "123332222"));
        phoneNumbersUser1.put("home", new PhoneNumber("+40", "111000111"));
        Address addressUser1 = new Address("Gura Putnei", 28, 1, null,
                "030623", "Bucharest", "Romania");
        Address companyAddressUser1 = new Address("Liviu Rebreanu", 23, 3,
                "4", "034334", "Bucharest", "Romania");
        Company companyUser1 = new Company("Active Consulting", companyAddressUser1);

        userList.add(new User("Bogdan", "Sava", "bogdan.sava@me.com", 44, phoneNumbersUser1,
                addressUser1, "CEO", companyUser1, true));

        userList.forEach(user -> System.out.println(user));

        // list contact list in natural order
        // list contact list by a given criteria
        // display a favorites list
        // search by a given or multiple criteria
        // display some statistics for the contact list
    }
}
