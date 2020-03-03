package ro.jademy.contactlist;

import ro.jademy.contactlist.service.DBUserService;
import ro.jademy.contactlist.service.FileUserService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //DBUserService db=new DBUserService(DBUserService.getConnection(getProperties()));

        //Menu menu = new Menu(new FileUserService("contacts.csv"));
        Menu menu = new Menu(new DBUserService(DBUserService.getConnection(getProperties())));
        menu.showMenu(new UserForm());
    }

    public static Properties getProperties() {
        Properties prop = new Properties();
        try (InputStream input = DBUserService.class.getResourceAsStream("/application.properties")){
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}


