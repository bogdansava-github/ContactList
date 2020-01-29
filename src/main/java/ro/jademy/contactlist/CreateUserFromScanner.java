package ro.jademy.contactlist;


import ro.jademy.contactlist.model.Address;
import ro.jademy.contactlist.model.Company;
import ro.jademy.contactlist.model.PhoneNumber;
import ro.jademy.contactlist.model.User;

import ro.jademy.contactlist.service.FileUserService;

import java.util.*;

public class CreateUserFromScanner {



    
    //public static User createNewUser(int id) {
    public static ArrayList<Object> createNewUser(int id) {

        System.out.print("Input first name: ");
        String fName = Main.scanner.nextLine();

        System.out.print("Input last name: ");
        String lName = Main.scanner.nextLine();

        System.out.print("Input job title: ");
        String jobTitle = Main.scanner.nextLine();
        //scanner.next();

        System.out.print("Input company name: ");
        String companyName = Main.scanner.nextLine();
        //scanner.next();

        System.out.println("Input the work phone number");
        System.out.print("Country Prefix: ");
        String workPhonePrefix = Main.scanner.nextLine();
        System.out.print("Work phone number: ");
        String workPhoneNumber = Main.scanner.nextLine();

        System.out.println("Input the mobile phone number");
        System.out.print("Country Prefix: ");
        String mobilePhonePrefix = Main.scanner.nextLine();
        System.out.print("Mobile phone number: ");
        String mobilePhoneNumber = Main.scanner.nextLine();

        System.out.println("Input the home phone number");
        System.out.print("Country Prefix: ");
        String homePhonePrefix = Main.scanner.nextLine();
        System.out.print("Home phone number: ");
        String homePhoneNumber = Main.scanner.nextLine();

        Map<String, PhoneNumber> phoneNumbersNewUser = new HashMap<>();
        phoneNumbersNewUser.put("work", new PhoneNumber(workPhonePrefix, workPhoneNumber));
        phoneNumbersNewUser.put("mobile", new PhoneNumber(mobilePhonePrefix, mobilePhoneNumber));
        phoneNumbersNewUser.put("home", new PhoneNumber(homePhonePrefix, homePhoneNumber));

        System.out.print("Input the e-mail: ");
        String email = Main.scanner.nextLine();

        System.out.println("Input the home address");
        System.out.print("Street name: ");
        String streetNameHome = Main.scanner.nextLine();

        int x = 1;
        int streetNrHome=0;
        do {

            try {
                System.out.print("Street number: ");
                streetNrHome = Main.scanner.nextInt();


                x = 2;

            } catch (InputMismatchException ex) {
                System.out.println("Please input a number!!!");
                Main.scanner.nextLine();
            }

        } while (x == 1);
        Main.scanner.nextLine();
        x = 1;
        int aptNrHome = 0;
        do {

            try {
                System.out.print("Apartment number: ");
                aptNrHome = Main.scanner.nextInt();
                Main.scanner.nextLine();

                x = 2;
            } catch (InputMismatchException ex) {
                System.out.println("Please input a number!!!");
                Main.scanner.nextLine();
            }
        } while (x == 1);


        System.out.print("Floor: ");
        String floorHome = Main.scanner.nextLine();


        System.out.print("ZipCode: ");
        String zipCodeHome = Main.scanner.nextLine();

        System.out.print("City: ");
        String cityHome = Main.scanner.nextLine();


        System.out.print("Country: ");
        String countryHome = Main.scanner.nextLine();


        Address homeAddress = new Address(streetNameHome, streetNrHome, aptNrHome, floorHome, zipCodeHome,
                cityHome, countryHome);

        System.out.println("Input the work address");

        System.out.print("Street name: ");
        String streetNameWork = Main.scanner.nextLine();

        x = 1;
        int streetNrWork = 0;
        do {

            try {
                System.out.print("Street number: ");
                streetNrWork = Main.scanner.nextInt();


                x = 2;
            } catch (InputMismatchException ex) {
                System.out.println("Please input a number!!!");
                Main.scanner.nextLine();
            }

        } while (x == 1);



        x = 1;
        int aptNrWork = 0;
        do {

            try {
                System.out.print("Apartment number: ");
                aptNrWork = Main.scanner.nextInt();
                Main.scanner.nextLine();

                x = 2;
            } catch (InputMismatchException ex) {
                System.out.println("Please input a number!!!");
                Main.scanner.nextLine();
            }

        } while (x == 1);



        System.out.print("Floor: ");
        String floorWork = Main.scanner.nextLine();

        System.out.print("ZipCode: ");
        String zipCodeWork = Main.scanner.nextLine();

        System.out.print("City: ");
        String cityWork = Main.scanner.nextLine();


        System.out.print("Country: ");
        String countryWork = Main.scanner.nextLine();


        Address workAddress = new Address(streetNameWork, streetNrWork, aptNrWork, floorWork, zipCodeWork, cityWork, countryWork);
        Company company = new Company(companyName, workAddress);


        x = 1;
        int age = 0;
        do {

            try {
                System.out.print("Input age: ");
                age = Main.scanner.nextInt();
                Main.scanner.nextLine();

                x = 2;
            } catch (InputMismatchException ex) {
                System.out.println("Please input a number!!!");
                Main.scanner.nextLine();
            }

        } while (x == 1);


        System.out.print("Add to favorite? (y/n): ");
        String answer = Main.scanner.next();
        boolean isFavorite = (answer.equals("y") || answer.equals("Y") ? true : false);

        ArrayList<Object>userData=new ArrayList<>();

        userData.add(id);
        userData.add(fName);
        userData.add(lName);
        userData.add(email);
        userData.add(age);
        userData.add(phoneNumbersNewUser);
        userData.add(homeAddress);
        userData.add(jobTitle);
        userData.add(company);
        userData.add(isFavorite);
        //ArrayList<Object>userData=Arrays.asList(id, fName,lName, email,age, phoneNumbersNewUser, homeAddress, jobTitle, company, isFavorite);


        //User user = createContact(id, fName, lName, email, age, phoneNumbersNewUser, homeAddress, jobTitle, company, isFavorite);
        //return user;
        return userData;

    }








    //public static User createContact(int id, String fName, String lName, String email, int age, Map<String,
     //       PhoneNumber> phoneNumbers, Address homeAddress, String jobTitle, Company company, boolean isFav) throws InputMismatchException {
    public static User createContact(ArrayList<Object>userData){

        //return new User(id, fName, lName, email, age, phoneNumbers, homeAddress, jobTitle, company, isFav);
        return new User((int)userData.get(0), (String) userData.get(1), (String) userData.get(2), (String) userData.get(3), (int)userData.get(4),
                (Map<String, PhoneNumber>) userData.get(5), (Address) userData.get(6), (String) userData.get(7), (Company) userData.get(8), (boolean)userData.get(9));
    }
}
