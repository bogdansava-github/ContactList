package ro.jademy.contactlist.model;

import java.util.Comparator;

public class CompanyNameComparer implements Comparator<User>
{
    @Override
    public int compare(User user1, User user2) {

        try {
            return user1.getCompany().getName().compareTo(user2.getCompany().getName());
        } catch (Exception ex){
            System.out.println("something is wrong!!!");
        }

        if (user1==null||user1.getCompany()==null||user1.getCompany().getName()==null){
            return -1;
        } else return 1;
    }

    public static void main(String[] args) {
        User user1=new User(1, "Ion", "Popescu", "0723291325");
        User user2=new User(2, "Ana", "Georgescu", "0723291325");


        //user1.setCompanyName("Endava");
        Company company1=new Company("Endava");
        Company company2=new Company("Apple");
        user1.setCompany(null);
        user2.setCompany(company2);

        //user2.setCompanyName("Apple");
        System.out.println(new CompanyNameComparer().compare(user1, user2));

    }
}
