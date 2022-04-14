package data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class WriteData {

    public static void writeEmp(Hashtable<String, employeeData> e){
        try{
            FileWriter writer = new FileWriter("employees.txt");
            for (Map.Entry<String, employeeData> set: e.entrySet()){
                writer.write(set.getKey()+ "\n");
                ArrayList<String> temp_val = set.getValue().getData();
                for(int i = 0; i != temp_val.size(); i++){
                    writer.write(temp_val.get(i) + "\n");
                }
            }
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    public static void writeMed(Hashtable<String, mediaData> e){
        try{
            FileWriter writer = new FileWriter("Media.txt");
            for (Map.Entry<String, mediaData> set: e.entrySet()){
                writer.write(set.getKey()+ "\n");
                ArrayList<String> temp_val = set.getValue().getData();
                for(int i = 0; i != temp_val.size(); i++){
                    writer.write(temp_val.get(i) + "\n");
                }
            }
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
