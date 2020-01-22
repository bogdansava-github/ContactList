package ro.jademy.contactlist.model;

import java.util.*;

public class UserListFactory {


    public static ArrayList<User> createUserList() {

        List<User> userList = new ArrayList<>();
        //user1
        Map<String, PhoneNumber> phoneNumbersUser1 = new HashMap<>();
        phoneNumbersUser1.put("work", new PhoneNumber("+40", "6556565656"));
        phoneNumbersUser1.put("mobile", new PhoneNumber("+40", "123332222"));
        phoneNumbersUser1.put("home", new PhoneNumber("+40", "111000111"));
        Address addressUser1 = new Address("Gura Putnei", 128, 1, null,
                "030623", "Bucharest", "Romania");
        Address companyAddressUser1 = new Address("Liviu Rebreanu", 23, null,
                null, "034334", "Bucharest", "Romania");
        Company companyUser1 = new Company("Active Consulting", companyAddressUser1);

        //user2

        Map<String, PhoneNumber> phoneNumbersUser2 = new HashMap<>();
        phoneNumbersUser2.put("work", new PhoneNumber("+40", "999666888"));
        phoneNumbersUser2.put("mobile", new PhoneNumber("+40", "321321321"));
        phoneNumbersUser2.put("home", new PhoneNumber("+40", "898998999"));
        Address addressUser2 = new Address("Magheru", 20, 5, "2",
                "045567", "Bucharest", "Romania");
        Address companyAddressUser2 = new Address("Ion Mihalache", 12, 7,
                "1", "077898", "Bucharest", "Romania");
        Company companyUser2 = new Company("Millennium", companyAddressUser2);

        //user3

        Map<String, PhoneNumber> phoneNumbersUser3 = new HashMap<>();
        phoneNumbersUser3.put("work", new PhoneNumber("+40", "444555666"));
        phoneNumbersUser3.put("mobile", new PhoneNumber("+40", "999888777"));
        phoneNumbersUser3.put("home", new PhoneNumber("+40", "123456789"));
        Address addressUser3 = new Address("Iancu de Hunedoara", 8, 10, "4",
                "012325", "Brasov", "Romania");
        Address companyAddressUser3 = new Address("Camil Ressu", 120, 25,
                "10", "011144", "Bucharest", "Romania");
        Company companyUser3 = new Company("Auto Bavaria", companyAddressUser3);

        //user4

        Map<String, PhoneNumber> phoneNumbersUser4 = new HashMap<>();
        phoneNumbersUser4.put("work", new PhoneNumber("+40", "656544556"));
        phoneNumbersUser4.put("mobile", new PhoneNumber("+40", "999888777"));
        phoneNumbersUser4.put("home", new PhoneNumber("+40", "369852147"));
        Address addressUser4 = new Address("Calea Floreasca", 240, 18, "5",
                "033665", "Bucharest", "Romania");
        Address companyAddressUser4 = new Address("Bd. Bratianu", 8, 6,
                "2", "066554", "Bucharest", "Romania");
        Company companyUser4 = new Company("Porsche Romania", companyAddressUser4);

        //user5

        Map<String, PhoneNumber> phoneNumbersUser5 = new HashMap<>();
        phoneNumbersUser5.put("work", new PhoneNumber("+40", "147852369"));
        phoneNumbersUser5.put("mobile", new PhoneNumber("+1", "987456321"));
        phoneNumbersUser5.put("home", new PhoneNumber("+40", "123654789"));
        Address addressUser5 = new Address("Calea Vacaresti", 80, 3, "1",
                "066554", "NY", "US");
        Address companyAddressUser5 = new Address("Theodor Pallady", 112, 45,
                "4", "032323", "NY", "US");
        Company companyUser5 = new Company("Vodafone", companyAddressUser5);


        //userlist
        userList.add(new User(1, "Bogdan", "Sana", "bogdan.sava@yahoo.ro", 44, phoneNumbersUser1,
                addressUser1, "CEO", companyUser1, true));
        userList.add(new User(2, "Gigel", "Popescu", "gigel.popescu@me.com", 30, phoneNumbersUser2,
                addressUser2, "Sales Manager", companyUser2, false));
        userList.add(new User(3, "Gina", "Alexandrescu", "ana.aremere@yahoo.com", 22, phoneNumbersUser3,
                addressUser3, "Assistant Manager", companyUser3, true));
        userList.add(new User(4, "Cecil", "Pasqual", "alina.pasqual@gmail.com", 28, phoneNumbersUser4,
                addressUser4, "Marketing Manager", companyUser4, true));
        userList.add(new User(5, "Brad", "Pitt", "brad.pitt@me.com", 50, phoneNumbersUser5,
                addressUser5, "", companyUser5, false));
        return (ArrayList<User>) userList;
    }

    public static User createNewUser(int id) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input first name: ");
        String fName = scanner.nextLine();

        System.out.print("Input last name: ");
        String lName = scanner.nextLine();

        System.out.print("Input job title: ");
        String jobTitle = scanner.nextLine();
        //scanner.next();

        System.out.print("Input company name: ");
        String companyName = scanner.nextLine();
        //scanner.next();

        System.out.println("Input the work phone number");
        System.out.print("Country Prefix: ");
        String workPhonePrefix = scanner.nextLine();
        System.out.print("Work phone number: ");
        String workPhoneNumber = scanner.nextLine();

        System.out.println("Input the mobile phone number");
        System.out.print("Country Prefix: ");
        String mobilePhonePrefix = scanner.nextLine();
        System.out.print("Mobile phone number: ");
        String mobilePhoneNumber = scanner.nextLine();

        System.out.println("Input the home phone number");
        System.out.print("Country Prefix: ");
        String homePhonePrefix = scanner.nextLine();
        System.out.print("Home phone number: ");
        String homePhoneNumber = scanner.nextLine();

        Map<String, PhoneNumber> phoneNumbersNewUser = new HashMap<>();
        phoneNumbersNewUser.put("work", new PhoneNumber(workPhonePrefix, workPhoneNumber));
        phoneNumbersNewUser.put("mobile", new PhoneNumber(mobilePhonePrefix, mobilePhoneNumber));
        phoneNumbersNewUser.put("home", new PhoneNumber(homePhonePrefix, homePhoneNumber));

        System.out.print("Input the e-mail: ");
        String email = scanner.nextLine();

        System.out.println("Input the home address");
        System.out.print("Street name: ");
        String streetNameHome = scanner.nextLine();

        int x = 1;
        int streetNrHome = 0;
        do {

            try {
                System.out.print("Street number: ");
                streetNrHome = scanner.nextInt();


                x = 2;
            } catch (InputMismatchException ex) {
                System.out.println("Please input a number!!!");
                scanner.nextLine();
            }

        } while (x == 1);
        scanner.nextLine();
        x = 1;
        int aptNrHome = 0;
        do {

            try {
                System.out.print("Apartment number: ");
                aptNrHome = scanner.nextInt();
                scanner.nextLine();

                x = 2;
            } catch (InputMismatchException ex) {
                System.out.println("Please input a number!!!");
                scanner.nextLine();
            }
        } while (x == 1);


        System.out.print("Floor: ");
        String floorHome = scanner.nextLine();
        //  scanner.nextLine();


        System.out.print("ZipCode: ");
        String zipCodeHome = scanner.nextLine();

        System.out.print("City: ");
        String cityHome = scanner.nextLine();


        System.out.print("Country: ");
        String countryHome = scanner.nextLine();


        Address homeAddress = new Address(streetNameHome, streetNrHome, aptNrHome, floorHome, zipCodeHome,
                cityHome, countryHome);

        System.out.println("Input the work address");

        System.out.print("Street name: ");
        String streetNameWork = scanner.nextLine();

        x = 1;
        int streetNrWork = 0;
        do {

            try {
                System.out.print("Street number: ");
                streetNrWork = scanner.nextInt();


                x = 2;
            } catch (InputMismatchException ex) {
                System.out.println("Please input a number!!!");
                scanner.nextLine();
            }

        } while (x == 1);



        x = 1;
        int aptNrWork = 0;
        do {

            try {
                System.out.print("Apartment number: ");
                aptNrWork = scanner.nextInt();
                scanner.nextLine();

                x = 2;
            } catch (InputMismatchException ex) {
                System.out.println("Please input a number!!!");
                scanner.nextLine();
            }

        } while (x == 1);



        System.out.print("Floor: ");
        String floorWork = scanner.nextLine();

        System.out.print("ZipCode: ");
        String zipCodeWork = scanner.nextLine();

        System.out.print("City: ");
        String cityWork = scanner.nextLine();


        System.out.print("Country: ");
        String countryWork = scanner.nextLine();


        Address workAddress = new Address(streetNameWork, streetNrWork, aptNrWork, floorWork, zipCodeWork, cityWork, countryWork);
        Company company = new Company(companyName, workAddress);


        x = 1;
        int age = 0;
        do {

            try {
                System.out.print("Input age: ");
                age = scanner.nextInt();
                scanner.nextLine();

                x = 2;
            } catch (InputMismatchException ex) {
                System.out.println("Please input a number!!!");
                scanner.nextLine();
            }

        } while (x == 1);


        System.out.print("Add to favorite? (y/n): ");
        String answer = scanner.next();
        boolean isFavorite = (answer.equals("y") || answer.equals("Y") ? true : false);
        User user = createContact(id, fName, lName, email, age, phoneNumbersNewUser, homeAddress, jobTitle, company, isFavorite);
        return user;

    }

    public static User createContact(int id, String fName, String lName, String email, int age, Map<String,
            PhoneNumber> phoneNumbers, Address homeAddress, String jobTitle, Company company, boolean isFav) throws InputMismatchException {

        return new User(id, fName, lName, email, age, phoneNumbers, homeAddress, jobTitle, company, isFav);
    }
}
