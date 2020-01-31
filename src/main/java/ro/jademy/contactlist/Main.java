package ro.jademy.contactlist;

import ro.jademy.contactlist.service.FileUserService;

import java.util.*;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Menu menu = new Menu(new FileUserService("contacts.csv"));
        menu.showMenu(new UserForm());
    }
}


