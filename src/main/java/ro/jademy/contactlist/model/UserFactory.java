package ro.jademy.contactlist.model;

import ro.jademy.contactlist.Main;

import java.util.*;

public class UserFactory {


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
    
    public static List<User> createUserListFromStrings(List<String> lines){
        List<User> userList = new ArrayList<>();
        Map<String, PhoneNumber> phoneNumbersNewUser = new HashMap<>();

        for (String line : lines) {
            String[]userProperties=line.split("\\|");

                int id=Integer.parseInt(userProperties[0]);
                String fName=userProperties[1];
                String lName=userProperties[2];
                String email=userProperties[3];
                int age=Integer.parseInt(userProperties[4]);
                String [] phones=userProperties[5].split("\\,");
                    String [] homePhone = phones[0].split("\\_");
                    String [] mobilePhone = phones[1].split("\\_");
                    String [] workPhone = phones[2].split("\\_");
                    phoneNumbersNewUser.put(homePhone[0],new PhoneNumber(homePhone[1],homePhone[2]));
                    phoneNumbersNewUser.put(mobilePhone[0],new PhoneNumber(mobilePhone[1],mobilePhone[2]));
                    phoneNumbersNewUser.put(workPhone[0],new PhoneNumber(workPhone[1],workPhone[2]));
                String [] homeAdress=userProperties[6].split("\\,");
                Address homeAdressNewUser=new Address(homeAdress[0], Integer.parseInt(homeAdress[1]),Integer.parseInt(homeAdress[2]),
                        homeAdress[3],homeAdress[4],homeAdress[5],homeAdress[6]);
                String title=userProperties[7];
                String[]companyProperties=userProperties[8].split("\\_");
                    String [] companyAddress=companyProperties[1].split("\\,");
                    Address companyAddressNewUser=new Address(companyAddress[0], Integer.parseInt(companyAddress[1]),
                            Integer.parseInt(companyAddress[2]),companyAddress[3],companyAddress[4],companyAddress[5],
                            companyAddress[6]);
                    String companyName=companyProperties[0];
                    Company companyNewUser=new Company(companyName,companyAddressNewUser);
                boolean isFav = Boolean.parseBoolean(userProperties[9]);
            User user = createContact(id, fName, lName, email, age, phoneNumbersNewUser, homeAdressNewUser, title, companyNewUser, isFav);
            userList.add(user);
        }
        
        return userList;
    }

    public static User createNewUser(int id) {

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
        //  scanner.nextLine();


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
        User user = createContact(id, fName, lName, email, age, phoneNumbersNewUser, homeAddress, jobTitle, company, isFavorite);
        return user;

    }

    public static User createContact(int id, String fName, String lName, String email, int age, Map<String,
            PhoneNumber> phoneNumbers, Address homeAddress, String jobTitle, Company company, boolean isFav) throws InputMismatchException {

        return new User(id, fName, lName, email, age, phoneNumbers, homeAddress, jobTitle, company, isFav);
    }
}
