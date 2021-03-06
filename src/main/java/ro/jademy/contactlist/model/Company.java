package ro.jademy.contactlist.model;

public class Company
{

    private String name;
    private Address address;

    public Company(String name)
    {
        this.name = name;
    }

    public Company(String name, Address address)
    {
        this.name = name;
        this.address = address;
    }
    public Company(){

    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return "Company{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }

 /*   @Override
    public int compareTo(Company company) {
        if (name.compareTo(company.getName())==0){
            return
        }

        name.compareTo(company.getName());
    }*/
}
