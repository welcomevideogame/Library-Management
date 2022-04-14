package data;

import java.util.ArrayList;
import java.util.Collections;

public class employeeData {

    String name;
    String department ;
    String bossID;
    String project;
    String subject;
    String requestedMaterials;
    String allocatedBudget;
    String permisisonLevel;
    String password;
    String employed;
    String amountSpent;


    public employeeData(String value,String value1, String value2, String value3,String value4,String value5,String value6,String value7,String value8, String value10,String value11) {
        name = value;
        department = value1;
        bossID = value2;
        project = value3;
        subject = value4;
        requestedMaterials= value5;
        allocatedBudget = value6;
        amountSpent = value11;
        permisisonLevel = value7;
        password = value8;
        employed = value10;

        System.out.println("Name: " + name + "\nDepartment: " + department + "\nBossID: " + bossID
        + "\nProject: " + project + "\nSubject: " + subject + "\nRequestedMaterials: " + requestedMaterials
        + "\nAllocatedBudget: " + allocatedBudget + "\nAmountSpent: " + amountSpent
        + "\nPermissionLevel: " + permisisonLevel + "\nPassword: " + password + "\nEmployed: " + employed);
    }

    public ArrayList<String> getData(){
        ArrayList<String> a = new ArrayList<>();
        Collections.addAll(a, name, department, bossID, project, subject, requestedMaterials, allocatedBudget,amountSpent,
                permisisonLevel, password, employed);
        return a;
    }
}
