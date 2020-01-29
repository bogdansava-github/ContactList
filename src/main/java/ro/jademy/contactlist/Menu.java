package ro.jademy.contactlist;

import ro.jademy.contactlist.model.*;
import ro.jademy.contactlist.service.FileUserService;
import ro.jademy.contactlist.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {

    private Scanner scanner=new Scanner(System.in);
    private UserService userService;

    public Menu (UserService userService){
        this.userService=userService;
    }

    public void printUserMap(Map<Character, List<User>>result, int listSize){

        // userService.getContacts()

        for (Map.Entry<Character, List<User>> listEntry : result.entrySet()) {
            System.out.println("         " + listEntry.getKey());

            for (User user : listEntry.getValue()) {
                System.out.println(user.getId()
                        + ". "
                        + user.getLastName()
                        + ", "
                        + user.getFirstName()
                        + ((user.isFavorite()) ? " *" : ""));

            }
            System.out.println("================== " + listEntry.getValue().size() + " contacts");
        }
        System.out.println("Total number of contacts: " + listSize);
        System.out.println();
    }







    public void showMenu(){



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

                    User.printUserMap();
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
                    ContactsFileIO.writeUserListToFile(userList,"contacts.csv");

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

    }
}
