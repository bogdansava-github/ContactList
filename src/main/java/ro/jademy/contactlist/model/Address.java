package ro.jademy.contactlist.model;

public class Address {

    private String streetName;
    private Integer streetNumber;
    private Integer apartmentNumber;
    private String floor;
    private String zipCode;
    private String city;
    private String country;

    public Address(String streetName, Integer streetNumber, Integer apartmentNumber, String floor, String zipCode, String city, String country) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.apartmentNumber = apartmentNumber;
        this.floor = floor;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public Address(String streetName, Integer streetNumber, String city, String country) {
        this(streetName, streetNumber, null, null, null, city, country);
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "St. " + streetName
                + ", No. " + streetNumber
                + ", Apt. " + (apartmentNumber == null ? "-" : apartmentNumber)
                + ", floor " + (floor == null ? "-" : floor)
                + ", zipcode " + zipCode + ", "
                + city + ", "
                + country;
    }
}
