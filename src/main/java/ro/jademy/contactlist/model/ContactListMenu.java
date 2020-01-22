package ro.jademy.contactlist.model;

public class ContactListMenu {

    public static void printMenu()
    {
        System.out.println(User.ANSI_YELLOW+"****************************"+User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW+"*"+User.ANSI_RESET+" 1. List all contacts     "+User.ANSI_YELLOW+"*"+User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW+"*"+User.ANSI_RESET+" 2. Details by id         "+User.ANSI_YELLOW+"*"+User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW+"*"+User.ANSI_RESET+" 3. List favorites        "+User.ANSI_YELLOW+"*"+User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW+"*"+User.ANSI_RESET+" 4. Search contacts       "+User.ANSI_YELLOW+"*"+User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW+"*"+User.ANSI_RESET+" 5. Add new contact       "+User.ANSI_YELLOW+"*"+User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW+"*"+User.ANSI_RESET+" 6. Edit contact          "+User.ANSI_YELLOW+"*"+User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW+"*"+User.ANSI_RESET+" 7. Remove contact        "+User.ANSI_YELLOW+"*"+User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW+"*"+User.ANSI_RESET+" 8. Statistics            "+User.ANSI_YELLOW+"*"+User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW+"*"+User.ANSI_RESET+" 9. EXIT                  "+User.ANSI_YELLOW+"*"+User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW+"****************************"+User.ANSI_RESET);
    }



    public static void printContactDetailsMenu(){
        {
            System.out.println("*******************");
            System.out.println("* 1. Input Id     *");
            System.out.println("* 2. Return       *");
            System.out.println("*******************");
        }
    }
}
