package ro.jademy.contactlist.service;

import java.util.Scanner;

public class SimpleFactory {

    public UserService createUserService() {
        UserService intendedUserService;
        System.out.println("Enter your choice ( 1 for file, 2 for database )");
        Scanner input = new Scanner(System.in);
        int choice = Integer.parseInt(input.nextLine());
        System.out.println("You have entered: " + choice);

        switch (choice)
        {
            case 1:
                intendedUserService=new FileUserService("contacts.csv");
                break;

            case 2:
                intendedUserService=new DBUserService(JDBConnection.getInstance());
                break;

            default:
                System.out.println("You must enter either 1 or 2");
                throw new IllegalArgumentException("Your choice tried to create an unknown UserService");
        }


        return intendedUserService;
    }
}
