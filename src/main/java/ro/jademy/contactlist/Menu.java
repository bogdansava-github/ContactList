package ro.jademy.contactlist;

import ro.jademy.contactlist.model.*;
import ro.jademy.contactlist.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class Menu
{

    private Scanner scanner = new Scanner(System.in);
    private UserService userService;

    public Menu(UserService userService)
    {
        this.userService = userService;
    }

    public static void printMenu()
    {
        System.out.println(User.ANSI_YELLOW + "****************************" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 1. List all contacts     " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 2. Details by id         " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 3. List favorites        " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 4. Search contacts       " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 5. Add new contact       " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 6. Edit contact          " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 7. Remove contact        " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 8. Statistics            " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 9. EXIT                  " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "****************************" + User.ANSI_RESET);
    }

    public static void printContactDetailsMenu()
    {

            System.out.println("*******************");
            System.out.println("* 1. Input Id     *");
            System.out.println("* 2. Return       *");
            System.out.println("*******************");

    }

    public void showMenu()
    {


        scanner.useDelimiter("\\n");
        boolean menuExit = false;
        while (!menuExit) {
            printMenu();
            System.out.print("Please choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            System.out.println();
            switch (option) {

                case 1: //List all contacts in natural order

                    printUserMap(userService.makeUserMap((ArrayList<User>) userService.getContacts()), userService.getContacts().size());
                    break;

                case 2: //List user details

                    printContactDetails();
                    break;

                case 3: //List favorites

                    ArrayList<User> favUserList = userService
                            .getContacts()
                            .stream()
                            .filter(User::isFavorite)
                            .collect(Collectors.toCollection(ArrayList::new));
                    Map<Character, List<User>> result1 = userService.makeUserMap(favUserList);
                    printUserMap(result1, favUserList.size());
                    break;

                case 4: //search contacts
                    System.out.print("Please input search string: ");
                    String criteria = scanner.next();
                    userService.getContacts();
                    for (User user : userService.search(criteria))
                    {
                        System.out.println(user);
                    }
                    break;

                case 5: //add new contact
                    IntSummaryStatistics statistics = userService.getContacts().stream()
                            .mapToInt(User::getUserId)
                            .summaryStatistics();
                    int id = statistics.getMax() + 1;
                    userService.addContact(CreateUserFromScanner.createContact(CreateUserFromScanner.createNewUser(id)));
                    break;

                case 6: //edit contact
                    System.out.print("Please input a user id you want to edit: ");
                    int userId = scanner.nextInt();
                    try
                    {
                        userService.getContactById(userId).get();

                        ArrayList<Object> userData = CreateUserFromScanner.createNewUser(userId);

                        userService.editContact((int) userData.get(0), (String) userData.get(1), (String) userData.get(2),
                                (String) userData.get(3), (int) userData.get(4), (Map<String, PhoneNumber>) userData.get(5),
                                (Address) userData.get(6), (String) userData.get(7), (Company) userData.get(8),
                                (boolean) userData.get(9));

                    }
                    catch (NoSuchElementException ex)
                    {

                        System.out.println("\nThis user id is not in your contact list\n");
                    }
                    break;

                case 7: //remove contact
                    System.out.print("Please input a user id you want to remove: ");
                    userId = scanner.nextInt();
                    try
                    {
                        userService.getContactById(userId).get();

                        userService.removeContact(userId);
                        System.out.println("Done!!!");

                    }
                    catch (NoSuchElementException ex)
                    {
                        System.out.println("\nThis user id is not in your contact list\n");
                    }

                    break;

                case 8: //statistics


                    System.out.println("\n****************** Statistics **********************");
                    System.out.println("You have " + userService.getContacts().size() + " contacts\n");

                    int localContactsCount;
                    localContactsCount = (int) userService.getContacts().stream()
                            .filter(user -> user.getAddress().getCity().equals("Bucharest"))
                            .count();
                    System.out.println(localContactsCount + " of your contacts are from Bucharest");

                    int abroadContactsCount;
                    abroadContactsCount = (int) userService.getContacts().stream()
                            .filter(user -> !(user.getPhoneNumbers().get("mobile").getCountryCode().equals("+40")))
                            .count();
                    System.out.println(abroadContactsCount + " has mobile phone registered abroad");

                    int ageCount;
                    ageCount = (int) userService.getContacts().stream()
                            .filter(user -> (user.getAge() >= 20 && user.getAge() <= 30))
                            .count();
                    System.out.println(ageCount + " of your contacts have ages between 20 and 30");

                    statistics = userService.getContacts().stream()
                            .mapToInt(User::getAge)
                            .summaryStatistics();

                    int minAge = statistics.getMin();
                    int maxAge = statistics.getMax();
                    System.out.println("Youngest contact is " + minAge + " years old, the eldest one is " + maxAge);

                    double averageAge = statistics.getAverage();
                    System.out.println("The average age of your contact list is: " + averageAge + " years");
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

    public void printContactDetails()
    {


        Scanner scanner = new Scanner(System.in);
        boolean menuExit = false;


        while (!menuExit)
        {

            printContactDetailsMenu();
            System.out.print("Please choose an option (1-2): ");

            int option = scanner.nextInt();

            switch (option)
            {


                case 1:

                    System.out.print("Input id: ");
                    int id = scanner.nextInt();

                    try
                    {
                        System.out.println(userService.getContactById(id).get());
                    }
                    catch (NoSuchElementException ex)
                    {
                        System.out.println("\nThis user id is not in your contact list\n");
                    }


                    break;

                case 2:

                    menuExit = true;

                    break;

                default:
                    System.out.println("\nPlease choose an option (1-2): ");
                    option = scanner.nextInt();

            }
        }

    }

    public void printUserMap(Map<Character, List<User>> map, int listSize)
    {

        for (Map.Entry<Character, List<User>> listEntry : map.entrySet())
        {
            System.out.println("         " + listEntry.getKey());

            for (User user : listEntry.getValue())
            {
                System.out.println(user.getUserId()
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
}
