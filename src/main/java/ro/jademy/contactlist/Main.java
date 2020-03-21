package ro.jademy.contactlist;

import ro.jademy.contactlist.service.SimpleFactory;
import ro.jademy.contactlist.service.UserService;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        SimpleFactory simpleFactory = new SimpleFactory();
        UserService preferredType = simpleFactory.createUserService();

        Menu menu = new Menu(preferredType);
        menu.showMenu(new UserForm());
    }

}


