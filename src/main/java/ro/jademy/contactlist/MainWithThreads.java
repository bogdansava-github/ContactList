package ro.jademy.contactlist;

import ro.jademy.contactlist.service.FileUserService;
import ro.jademy.contactlist.service.UserService;

import java.io.File;
import java.util.concurrent.*;

public class MainWithThreads {

    static long date1;

    public static void main(String[] args) {

        UserService userService = new FileUserService("contacts.csv");
        File file = new File("contacts.csv");

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        System.out.println("\nUnmodified list");
        userService.getContacts().stream().forEach(System.out::println);

        date1 = file.lastModified();

        executorService.scheduleAtFixedRate(() -> {
            long date2 = file.lastModified();
            if (date2 != date1) {

                System.out.println("\nUpdated");
                userService.getContactsIfCsvModified().stream().forEach(System.out::println);

                date1 = date2;
            }
        }, 0, 5, TimeUnit.SECONDS);

    }

}






