package ro.jademy.contactlist.model;

import java.util.Comparator;

public class CompanyNameComparer implements Comparator<User>
{
    @Override
    public int compare(User user1, User user2)
    {
        return user1.getCompany().getName().compareTo(user2.getCompany().getName());
    }
}
