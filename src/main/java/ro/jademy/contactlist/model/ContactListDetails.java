package ro.jademy.contactlist.model;
import java.util.*;

public class ContactListDetails {



    public static void printContactDetails(List<User> userList) {


        Scanner scanner = new Scanner(System.in);
        boolean menuExit = false;


        while (!menuExit) {

            ContactListMenu.printContactDetailsMenu();
            System.out.print("Please choose an option (1-2): ");

            int option = scanner.nextInt();

            switch (option) {


                case 1:

                    System.out.print("Input id: ");
                    int id=scanner.nextInt();
                    userList.stream().filter(user -> user.getId() == id).forEach(user -> System.out.println(user));

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
}
