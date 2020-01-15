package ro.jademy.contactlist.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class User implements Comparable<User> {

    private String firstName;
    private String lastName;
    private String email;
    private Integer age;

    private Map<String, PhoneNumber> phoneNumbers;
    private Address address;

    private String jobTitle;
    private Company company;

    private boolean isFavorite;


    public User(String firstName, String lastName, String email, Integer age, Map<String, PhoneNumber> phoneNumbers, Address address, String jobTitle, Company company, boolean isFavorite) {
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
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", phoneNumbers=" + phoneNumbers +
                ", address=" + address +
                ", jobTitle='" + jobTitle + '\'' +
                ", company=" + company +
                ", isFavorite=" + isFavorite +
                '}';
    }
    public void printUserDetails(User user){
        System.out.println("\n"+user.getFirstName() +" "+ user.getLastName());
        System.out.println(user.getJobTitle());
        System.out.println(user.getCompany().getName());
        System.out.println("home phone: " + user.phoneNumbers.get("home"));
        System.out.println("mobile phone: "+user.phoneNumbers.get("mobile"));
        System.out.println("work phone: "+user.phoneNumbers.get("work"));
        System.out.println("email: "+user.email);
        System.out.println("home address: "+user.address);
        System.out.println("work address: "+user.company.getAddress());

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
        if (lastName.compareTo(o.lastName)==0){
            return firstName.compareTo(o.firstName);
        }
        return lastName.compareTo(o.lastName);

        }
}
