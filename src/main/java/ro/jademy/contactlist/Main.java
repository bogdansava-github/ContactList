package ro.jademy.contactlist;

import ro.jademy.contactlist.model.*;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //create userlist
        ArrayList<User> userList = (ArrayList<User>) UserFactory.createUserListFromStrings(ReadContactsFile.readFile("contacts.csv"));


        //grouping userlist by first lastname character in a map
        Map<Character, List<User>> result = User.makeUserMap(userList);
        //menu
        System.out.println("\nContact List");
        //ReadWriteContactsFile.readFile("contacts.csv");

        scanner.useDelimiter("\\n");
        boolean menuExit = false;

        while (!menuExit) {
            ContactListMenu.printMenu();
            System.out.print("Please choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            System.out.println();
            switch (option) {

                case 1: //List all contacts in natural order

                    User.printUserMap(result, userList.size());
                    break;

                case 2: //List user details

                    ContactListDetails.printContactDetails(userList);
                    break;

                case 3: //List favorites

                    ArrayList<User> favUserList = userList.stream().filter(User::isFavorite).collect(Collectors.toCollection(ArrayList::new));
                    Map<Character, List<User>> result1 = User.makeUserMap(favUserList);
                    User.printUserMap(result1, favUserList.size());
                    break;

                case 4: //search contacts
                    System.out.print("Please input search string: ");
                    String criteria = scanner.next();
                    userList.stream()
                            .filter(user -> user.getLastName().toLowerCase().contains(criteria.toLowerCase())
                                    || user.getFirstName().toLowerCase().contains((criteria.toLowerCase()))
                                    || user.getCompany().getName().toLowerCase().contains((criteria.toLowerCase()))
                                    || user.getJobTitle().toLowerCase().contains((criteria.toLowerCase())))
                            .sorted()
                            .forEach(System.out::println);

                    break;

                case 5: //add new contact
                    IntSummaryStatistics statistics = userList.stream()
                            .mapToInt(User::getId)
                            .summaryStatistics();
                    int id = statistics.getMax() + 1;


                    User newUser = UserFactory.createNewUser(id);
                    userList.add(newUser);
                    result = User.makeUserMap(userList);

                    break;

                case 6: //edit contact

                    break;

                case 7: //remove contact

                    break;

                case 8: //statistics

                    break;

                case 9:
                    System.exit(0);
                    break;

                default:

                    System.out.print("Please choose a valid option (1-9): ");
                    option = scanner.nextInt();
            }


        }


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


