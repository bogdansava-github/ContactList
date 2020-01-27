package ro.jademy.contactlist.model;



import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactsFileIO {

    public static List<String> readFile (String fileName){
        List<String> lines=new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))){
            String line = null;
            while ((line=in.readLine())!=null){
                lines.add(line);
            }

        } catch (IOException ex) {
            System.out.println("File not found \n" + ex);;
        }
        return lines;
    }

public static void writeUserListToFile(List<User>userList,String fileName){

        try(BufferedWriter out=new BufferedWriter(new FileWriter(fileName))){
           List<String>lines=new ArrayList<>();

            for (User user : userList) {
                String line=String.valueOf(user.getId())+"|"+user.getFirstName()+"|"+user.getLastName()+"|"+user.getEmail()+"|"+
                        String.valueOf(user.getAge())+"|home_"+user.getPhoneNumbers().get("home").getCountryCode()+
                        "_"+user.getPhoneNumbers().get("home").getNumber()+",mobile_"+user.getPhoneNumbers().get("mobile").getCountryCode()+
                        "_"+user.getPhoneNumbers().get("mobile").getNumber()+",work_"+user.getPhoneNumbers().get("work").getCountryCode()+
                        "_"+user.getPhoneNumbers().get("work").getNumber()+"|"+user.getAddress().getStreetName()+","+
                        String.valueOf(user.getAddress().getStreetNumber())+","+String.valueOf(user.getAddress().getApartmentNumber())+","+
                        user.getAddress().getFloor()+","+user.getAddress().getZipCode()+","+user.getAddress().getCity()+","+user.getAddress().getCountry()+"|"+
                        user.getJobTitle()+"|"+user.getCompany().getName()+"_"+user.getCompany().getAddress().getStreetName()+","+
                        String.valueOf(user.getCompany().getAddress().getStreetNumber())+","+
                        String.valueOf(user.getCompany().getAddress().getApartmentNumber())+","+
                        user.getCompany().getAddress().getFloor()+","+
                        user.getCompany().getAddress().getZipCode()+","+
                        user.getCompany().getAddress().getCity()+","+
                        user.getCompany().getAddress().getCountry()+"|"+
                        String.valueOf(user.isFavorite());


               // user.getPhoneNumbers().get("home").getCountryCode()
                lines.add(line);

            }

            for (String line : lines) {
                out.write(line);
                out.newLine();
            }
            



        }
        catch (FileNotFoundException ex){
            System.out.println("File not found "+fileName+"\n"+ex);
        }
        catch (IOException ex){
            System.out.println("Failed to write content to file "+ fileName+"\n"+ex);
        }

}
}
