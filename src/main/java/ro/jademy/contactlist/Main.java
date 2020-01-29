package ro.jademy.contactlist;

import ro.jademy.contactlist.model.*;
import ro.jademy.contactlist.service.FileUserService;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Menu menu=new Menu(new FileUserService("contacts.csv"));
        menu.showMenu();




        //create userlist
        ArrayList<User> userList = (ArrayList<User>) UserFactory.createUserListFromStrings(ContactsFileIO.readFile("contacts.csv"));


        //grouping userlist by first lastname character in a map
        Map<Character, List<User>> result = User.makeUserMap(userList);
        //menu
        System.out.println("\nContact List");
        //ReadWriteContactsFile.readFile("contacts.csv");





        // display some statistics for the contact list

        /*System.out.println("\n********************************** Statistics ********************************************");
        System.out.println("You have " + userList.size() + " contacts\n");

        int localContactsCount;
        localContactsCount = (int) userList.stream()
                .filter(user -> user.getAddress().getCity().equals("Bucharest"))
                .count();
        System.out.println(localContactsCount + " of your contacts are from Bucharest");

        int abroadContactsCount;
        abroadContactsCount = (int) userList.stream()
                .filter(user -> !(user.getPhoneNumbers().get("mobile").getCountryCode().equals("+40")))
                .count();
        System.out.println(abroadContactsCount + " has mobile phone registered abroad");

        int ageCount;
        ageCount = (int) userList.stream()
                .filter(user -> (user.getAge() >= 20 && user.getAge() <= 30))
                .count();
        System.out.println(ageCount + " of your contacts have ages between 20 and 30");

        IntSummaryStatistics statistics = userList.stream()
                .mapToInt(User::getAge)
                .summaryStatistics();

        int minAge = statistics.getMin();
        int maxAge = statistics.getMax();
        System.out.println("Youngest contact is " + minAge + " years old, the eldest one is " + maxAge);

        double averageAge = statistics.getAverage();
        System.out.println("The average age of your contact list is: " + averageAge + " years");*/
    }
}


