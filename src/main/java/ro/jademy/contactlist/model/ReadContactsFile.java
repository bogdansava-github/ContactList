package ro.jademy.contactlist.model;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadContactsFile {

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


}
