package ro.jademy.contactlist;

import ro.jademy.contactlist.model.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        // create a contact list of users
        List<User> userList = new ArrayList<>();
        //user1
        Map<String, PhoneNumber> phoneNumbersUser1 = new HashMap<>();
        phoneNumbersUser1.put("work", new PhoneNumber("+40", "723291325"));
        phoneNumbersUser1.put("mobile", new PhoneNumber("+40", "123332222"));
        phoneNumbersUser1.put("home", new PhoneNumber("+40", "111000111"));
        Address addressUser1 = new Address("Gura Putnei", 28, 1, "P",
                "030623", "Bucharest", "Romania");
        Address companyAddressUser1 = new Address("Liviu Rebreanu", 23, 3,
                "4", "034334", "Bucharest", "Romania");
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
        userList.add(new User("Bogdan", "Sava", "bogdan.sava@me.com", 44, phoneNumbersUser1,
                addressUser1, "CEO", companyUser1, true));
        userList.add(new User("Alina", "Sava", "gigel.popescu@me.com", 30, phoneNumbersUser2,
                addressUser2, "Sales Manager", companyUser2, false));
        userList.add(new User("Ana", "Aremere", "ana.aremere@yahoo.com", 22, phoneNumbersUser3,
                addressUser3, "Assistant Manager", companyUser3, true));
        userList.add(new User("Alina", "Pasqual", "alina.pasqual@gmail.com", 28, phoneNumbersUser4,
                addressUser4, "Marketing Manager", companyUser4, true));
        userList.add(new User("Brad", "Pitt", "brad.pitt@me.com", 50, phoneNumbersUser5,
                addressUser5, "General Manager", companyUser5, false));

        //userList.forEach(user -> user.printUserDetails(user));

        // list contact list in natural order
        System.out.println("\n********************* Contact list in natural last name order ****************************");

        userList.stream()
                .sorted()
                .forEach(user -> user.printUserDetails(user));

        // list contact list by a given criteria

        System.out.println("\n************************ List contacts by company name ***********************************");
        userList.stream()
                .sorted(Comparator.comparing(user -> user.getCompany().getName()))
                .forEach(user -> user.printUserDetails(user));

        // display a favorites list

        System.out.println("\n*************************** Display favorites list ***************************************");
        userList.stream()
                .filter(User::isFavorite)
                .sorted()
                .forEach(user -> user.printUserDetails(user));

        // search by a given or multiple criteria
        System.out.println("\n************************* Search by a given criteria *************************************");
        String criteria = "sav";
        System.out.println("The search string is: " + criteria);
        userList.stream()
                .filter(user -> user.getLastName().toLowerCase().contains(criteria.toLowerCase()))
                .sorted()
                .forEach(user -> user.printUserDetails(user));


        // display some statistics for the contact list

        System.out.println("\n********************************** Statistics ********************************************");
        System.out.println("You have " + userList.size() + " contacts\n");

        int localContactsCount;
        localContactsCount = (int) userList.stream()
                .filter(user -> user.getAddress().getCity().equals("Bucharest"))
                .count();
        System.out.println(localContactsCount + " of your contacts are from Bucharest");

        int abroadContactsCount;
        abroadContactsCount = (int) userList.stream()
                .filter(user -> !(user.getPhoneNumbers().get("mobile").getCountryCode().equals("+40")))
                .count();
        System.out.println(abroadContactsCount + " has mobile phone registered abroad");

        int ageCount;
        ageCount = (int) userList.stream()
                .filter(user -> (user.getAge() >= 20 && user.getAge() <= 30))
                .count();
        System.out.println(ageCount + " of your contacts have ages between 20 and 30");

        IntSummaryStatistics statistics = userList.stream()
                .mapToInt(User::getAge)
                .summaryStatistics();

        int minAge = statistics.getMin();
        int maxAge = statistics.getMax();
        System.out.println("Youngest contact is " + minAge + " years old, the eldest one is "+maxAge);

        double averageAge = statistics.getAverage();
        System.out.println("The average age of your contact list is: " + averageAge + " years");
    }
}

