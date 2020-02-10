package ro.jademy.contactlist;

import ro.jademy.contactlist.model.*;
import ro.jademy.contactlist.service.FileUserService;
import ro.jademy.contactlist.service.UserService;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private UserService userService;

    public Menu(UserService userService) {
        this.userService = userService;
    }

    public void printMenu() {
        System.out.println(User.ANSI_YELLOW + "****************************" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 1. List all contacts     " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 2. Details by id         " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 3. List favorites        " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 4. Search contacts       " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 5. Add new contact       " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 6. Edit contact          " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 7. Remove contact        " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 8. Statistics            " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 9. Backups               " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "*" + User.ANSI_RESET + " 10. EXIT                 " + User.ANSI_YELLOW + "*" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "****************************" + User.ANSI_RESET);
    }

    public void printContactDetailsMenu() {

        System.out.println("*******************");
        System.out.println("* 1. Input Id     *");
        System.out.println("* 2. Return       *");
        System.out.println("*******************");

    }

    public void printEditContactMenu() {

        System.out.println("*********Edit*********");
        System.out.println("* 1.  First name     *");
        System.out.println("* 2.  Last name      *");
        System.out.println("* 3.  Job title      *");
        System.out.println("* 4.  Company name   *");
        System.out.println("* 5.  Mobile number  *");
        System.out.println("* 6.  Home number    *");
        System.out.println("* 7.  Work number    *");
        System.out.println("* 8.  Return         *");
        System.out.println("**********************");

    }

    public void printBackupsMenu() {

        System.out.println("***************Backups***************");
        System.out.println("* 1.  Create backup                 *");
        System.out.println("* 2.  View backup file details      *");
        System.out.println("* 3.  Restore from backup           *");
        System.out.println("* 4.  Delete backup                 *");
        System.out.println("* 5.  Return                        *");
        System.out.println("*************************************");

    }

    public void showMenu(UserForm userForm) {

        scanner.useDelimiter("\\n");
        boolean menuExit = false;
        while (!menuExit) {
            printMenu();

            int x = 1;
            int option = 0;
            do {

                try {
                    System.out.print("Please choose an option (1-10): ");
                    option = scanner.nextInt();


                    x = 2;
                } catch (InputMismatchException ex) {
                    System.out.print("Please input a number!!!\n");
                    scanner.nextLine();
                }

            } while (x == 1);


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
                    for (User user : userService.search(criteria)) {
                        System.out.println(user);
                    }
                    break;

                case 5: //add new contact
                    IntSummaryStatistics statistics = userService.getContacts().stream()
                            .mapToInt(User::getUserId)
                            .summaryStatistics();
                    int id = statistics.getMax() + 1;
                    userService.addContact(userForm.createNewUser(id));
                    break;

                case 6: //edit contact


                    System.out.print("Search for a contact: ");
                    criteria = scanner.next();
                    userService.getContacts();
                    for (User user : userService.search(criteria)) {
                        System.out.println(user);
                    }
                    System.out.print("Please input a user id you want to edit: ");
                    int userId = scanner.nextInt();

                    Optional<User> userOpt = userService.getContactById(userId);

                    if (userOpt.isPresent()) {
                        User editedUser = editContactDetails(userOpt.get());

                        userService.editContact(editedUser.getUserId(), editedUser.getFirstName(), editedUser.getLastName(), editedUser.getEmail(),
                                editedUser.getAge(), editedUser.getPhoneNumbers(), editedUser.getAddress(), editedUser.getJobTitle(), editedUser.getCompany(),
                                editedUser.isFavorite());
                    } else {
                        System.out.println("\nThis user id is not in your contact list\n");
                    }
                    break;

                case 7: //remove contact
                    System.out.print("Please input a user id you want to remove: ");
                    userId = scanner.nextInt();
                    userOpt = userService.getContactById(userId);


                    if (userOpt.isPresent()) {
                        userService.getContactById(userId).get();

                        userService.removeContact(userId);
                        System.out.println("Done!!!");

                    } else {
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
                    backupContactFile();
                    break;
                case 10:
                    System.exit(0);
                    break;

                default:

                    System.out.print("Please choose a valid option (1-10)\n\n");
            }


        }

    }

    public void printContactDetails() {


        Scanner scanner = new Scanner(System.in);
        boolean menuExit = false;


        while (!menuExit) {

            printContactDetailsMenu();
            System.out.print("Please choose an option (1-2): ");

            int option = scanner.nextInt();

            switch (option) {


                case 1:

                    System.out.print("Input id: ");
                    int id = scanner.nextInt();

                    try {
                        System.out.println(userService.getContactById(id).get());
                    } catch (NoSuchElementException ex) {
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

    public void backupContactFile() {

        String path = "backup" + File.separator;
        File dir = new File(path);
        dir.mkdir();

        Scanner scanner = new Scanner(System.in);
        boolean menuExit = false;
        while (!menuExit) {
            printBackupsMenu();


            //validate that the input is a number
            int x = 1;
            int option = 0;
            do {

                try {
                    System.out.print("Please choose an option (1-5): ");
                    option = scanner.nextInt();


                    x = 2;
                } catch (InputMismatchException ex) {
                    System.out.println("Please input a number!!!");
                    scanner.nextLine();
                }

            } while (x == 1);


            //option menu
            switch (option) {
                case 1:
                    //create backup
                    userService.createBackup();
                    break;
                case 2:
                    //view backup files details
                    userService.viewBackupFilesDetails();
                    break;

                case 3:
                    //restore from backups
                    System.out.print("input file name you want to restore: ");
                    scanner.nextLine();
                    String fileName = scanner.nextLine();

                    userService.restoreFromBackup(fileName);
                    break;

                case 4:
                    //search for a file
                    //delete that file
                    System.out.print("input end of file number .csv: ");
                    scanner.nextLine();
                    String endFileName = scanner.nextLine();

                    userService.deleteBackupFile(endFileName);

                    break;

                case 5:

                    menuExit = true;

                    break;


                default:
                    System.out.print("\nPlease choose an option (1-5): ");
                    scanner.nextInt();
            }
        }
    }


    public User editContactDetails(User user) {


        User editedUser = new User(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getAge(), user.getPhoneNumbers(), user.getAddress(), user.getJobTitle(), user.getCompany(),
                user.isFavorite());

        Scanner scanner = new Scanner(System.in);
        boolean menuExit = false;


        while (!menuExit) {

            printEditContactMenu();

            //validate that the input is a number
            int x = 1;
            int option = 0;
            do {

                try {
                    System.out.print("Please choose an option (1-8): ");
                    option = scanner.nextInt();


                    x = 2;
                } catch (InputMismatchException ex) {
                    System.out.print("Please input a number!!!\n");
                    scanner.nextLine();
                }

            } while (x == 1);


            switch (option) {


                case 1:

                    System.out.print("Input first name: ");
                    String fName = scanner.next();
                    editedUser.setFirstName(fName);

                    break;

                case 2:
                    System.out.print("Input last name: ");
                    String lName = scanner.next();
                    editedUser.setLastName(lName);

                    break;

                case 3:
                    System.out.print("Input job title: ");
                    String jobTitle = scanner.next();
                    editedUser.setJobTitle(jobTitle);
                    break;

                case 4:
                    System.out.print("Input company name: ");
                    String companyName = scanner.next();
                    editedUser.setCompanyName(companyName);

                    break;

                case 5:

                    System.out.print("Country code: ");
                    String countryCode = scanner.next();
                    System.out.println("Phone number");
                    String mobileNumber = scanner.next();
                    editedUser.setMobilePhoneNumber(countryCode, mobileNumber);

                    break;

                case 6:
                    System.out.print("Country code: ");
                    countryCode = scanner.next();
                    System.out.println("Phone number");
                    mobileNumber = scanner.next();
                    editedUser.setHomePhoneNumber(countryCode, mobileNumber);
                    break;

                case 7:
                    System.out.print("Country code: ");
                    countryCode = scanner.next();
                    System.out.println("Phone number");
                    mobileNumber = scanner.next();
                    editedUser.setWorkPhoneNumber(countryCode, mobileNumber);
                    break;


                case 8:

                    menuExit = true;

                    break;


                default:
                    System.out.println("\nPlease choose an option (1-8): ");
                    option = scanner.nextInt();

            }
        }
        return editedUser;
    }

    public void printUserMap(Map<Character, List<User>> map, int listSize) {

        for (Map.Entry<Character, List<User>> listEntry : map.entrySet()) {
            System.out.println("         " + listEntry.getKey());

            for (User user : listEntry.getValue()) {
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
