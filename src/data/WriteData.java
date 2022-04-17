package data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    public static void writeUse(useData useData){
        try{
            FileWriter writer = new FileWriter("Use.txt", true);
            writer.write(useData.getEmpID() + "\n");


            String[] categories = {"Media", "Employees", "Patrons", "Vendors", "Databases"};
            for(int i = 0; i != categories.length; i++){
                writer.write(useData.getUses().get(categories[i]) + "\n");
            }

            int tempValue;
            HashMap<String, Integer> a = useData.getUseTime();
            for(int i = 0; i != categories.length; i++){
                tempValue = 0;
                Integer b = a.get(categories[i]);
                if(b < 60 && b != 0){
                    tempValue = 1;
                }
                else if(b != 0){
                    tempValue = b / 60;
                }
                writer.write(tempValue + "min\n");
            }

            writer.write("\n");

            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
