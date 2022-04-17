package data;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFile {
    media Media = new media();
    public Employee employee = new Employee();

    //public ReadFile(Employee employee){}

    public void readMediaFile() {
        try {
            File myObj = new File("Media.txt");
            Scanner myReader = new Scanner(myObj);
            String temp_id, mediaType, name, bTime, borrowable, due, vendor;
            while (myReader.hasNextLine()) {
                temp_id = myReader.nextLine();
                mediaType = myReader.nextLine();
                name = myReader.nextLine();
                bTime = myReader.nextLine();
                borrowable = myReader.nextLine();
                due = myReader.nextLine();
                vendor = myReader.nextLine();
                Media.mediaT.put(temp_id, new mediaData(mediaType,name,bTime,borrowable,due,vendor));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void readEmployeeFile() {
        try {
            int linesFound = 0;
            File myObj = new File("employees.txt");
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> temp_val = new ArrayList<>();
            String temp_id, pass_ID;
            String name, department, bossID, project, subject, requestedMaterials, allocatedBudget,amountSpent, permissionLevel, password,employed;
            while (myReader.hasNextLine()) {
                temp_id = myReader.nextLine();
                linesFound++;
                pass_ID = temp_id;
                name = myReader.nextLine();
                linesFound++;
                //System.out.println(data.name);
                department = myReader.nextLine();
                linesFound++;
                bossID = myReader.nextLine();
                linesFound++;
                //System.out.println(linesFound);
                project = myReader.nextLine();
                linesFound++;
                subject = myReader.nextLine();
                linesFound++;
                requestedMaterials = myReader.nextLine();
                linesFound++;
                employed = myReader.nextLine();
                linesFound++;
                allocatedBudget = myReader.nextLine();
                linesFound++;
                amountSpent = myReader.nextLine();
                linesFound++;
                permissionLevel = myReader.nextLine();
                linesFound++;
                password = myReader.nextLine();
                linesFound++;
                employee.employeeT.put(temp_id, new employeeData(name, department, bossID, project, subject, requestedMaterials, allocatedBudget,amountSpent, permissionLevel, password,employed));

            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static ArrayList<ArrayList<String>> readUseData(){

        ArrayList<ArrayList<String>> attributes = new ArrayList<>();
        attributes.add(new ArrayList<>());
        int index = 0;

        try{
            File file = new File("Use.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(!line.isBlank()) {
                    attributes.get(index).add(line);
                }
                else{
                    index += 1;
                    attributes.add(new ArrayList<>());
                }
            }
            attributes.remove(attributes.size() - 1);
            return attributes;
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

}

