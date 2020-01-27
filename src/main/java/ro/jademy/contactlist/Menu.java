package ro.jademy.contactlist;

import ro.jademy.contactlist.service.UserService;

import java.util.Scanner;

public class Menu {

    private Scanner scanner=new Scanner(System.in);
    private UserService userService;

    public Menu (UserService userService){
        this.userService=userService;
    }

    public void showMenu(){

    }
}
