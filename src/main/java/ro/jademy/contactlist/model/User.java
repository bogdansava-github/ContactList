package ro.jademy.contactlist.model;

import java.util.*;
import java.util.stream.Collectors;

public class User implements Comparable<User> {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;

    private Map<String, PhoneNumber> phoneNumbers;
    private Address address;

    private String jobTitle;
    private Company company;

    private boolean isFavorite;


    public User(int id, String firstName, String lastName, String email, Integer age, Map<String, PhoneNumber> phoneNumbers, Address address, String jobTitle, Company company, boolean isFavorite) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.phoneNumbers = phoneNumbers;
        this.address = address;
        this.jobTitle = jobTitle;
        this.company = company;
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Map<String, PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Map<String, PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "\n"+ANSI_RED+firstName + " " + lastName+ANSI_RESET+((isFavorite)?" *":"")
                +"\n"+((jobTitle == null||jobTitle=="") ?"":jobTitle+"\n")
                +(company.getName()==null?"-":company.getName())
                +ANSI_YELLOW+"\n----------------------------------------------------------------------------------------------"+ANSI_RESET
                +"\nhome phone: " + phoneNumbers.get("home")
                +"\nmobile phone: " + phoneNumbers.get("mobile")
                +"\nwork phone: " + phoneNumbers.get("work")
                +"\nemail: " + email
                +"\nhome address: " + address
                +"\nwork address: " + company.getAddress()
                +ANSI_YELLOW+"\n----------------------------------------------------------------------------------------------"+ANSI_RESET;

    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";




    public void printUserDetails() {
        System.out.println(ANSI_RED+"\n" + firstName + " " + lastName+ANSI_RESET);
        System.out.println(jobTitle);
        System.out.println(company.getName());
        System.out.println("\nhome phone: " + phoneNumbers.get("home"));
        System.out.println("mobile phone: " + phoneNumbers.get("mobile"));
        System.out.println("work phone: " + phoneNumbers.get("work"));
        System.out.println("email: " + email);
        System.out.println("home address: " + address);
        System.out.println("work address: " + company.getAddress());

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                age.equals(user.age) &&
                company.equals(user.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, company);
    }

    @Override
    public int compareTo(User o) {
        if (lastName.compareTo(o.lastName) == 0) {
            return firstName.compareTo(o.firstName);
        }
        return lastName.compareTo(o.lastName);
    }
    public static Map<Character, List<User>> makeUserMap(ArrayList<User> userList){
        Map<Character, List<User>> result = userList.stream()
                .sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
                .collect(Collectors.groupingBy(user -> user.getLastName().charAt(0), TreeMap::new, Collectors.toList()));
      return result;
    }

    public static void printUserMap(Map<Character, List<User>>result, int listSize){

        for (Map.Entry<Character, List<User>> listEntry : result.entrySet()) {
            System.out.println("         " + listEntry.getKey());

            for (User user : listEntry.getValue()) {
                System.out.println(user.getId()
                        + ". "
                        + user.getLastName()
                        + ", "
                        + user.getFirstName()
                        + ((user.isFavorite()) ? " *" : ""));

            }
            System.out.println("================== " + listEntry.getValue().size() + " contacts");
        }
        System.out.println("Total number of contacts: " + listSize);
        System.out.println();
    }
}
