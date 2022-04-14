package data;

import java.util.Hashtable;
import java.util.Scanner;
import java.util.ArrayList;

public class  Employee {

    // key = empID
    // 0   = name
    // 1   = department
    // 2   = boss
    // 3   = project
    // 4   = subject
    // 5   = requestedMaterials
    // 6   = allocatedBudget
    // 7   = permissionLevel
    // 8   = password

    public Hashtable<String, employeeData> employeeT = new Hashtable<String, employeeData>();
    Permissions accessCheck = new Permissions();
    Boolean Permission;
    String loginResult;
    public  Employee(){}

    public String getName(String empID) {

        return employeeT.get(empID).name;
    }

    public String getDepartment() {
        String empID;
        Scanner dept = new Scanner(System.in);
        empID = dept.nextLine();
        return employeeT.get(empID).department;
    }

    public String getBoss(String empID) {
        return employeeT.get(empID).bossID;
    }

    public String getProject(String empID) {
        return employeeT.get(empID).project;
    }

    public String getSubject(String empID) {
        return employeeT.get(empID).subject;
    }

    public String getRequestedMaterials() {
        Scanner turn = new Scanner(System.in);
        String empID;
        empID = turn.nextLine();
        return employeeT.get(empID).requestedMaterials;
    }

    public String getAllRequestedMaterials(String empID) {
        return employeeT.get(empID).requestedMaterials;
    }

    public String login() {
        String checkID;
        String checkPassword;
        Scanner idInput = new Scanner(System.in);
        Scanner pwInput = new Scanner(System.in);
        System.out.println("Please enter your employee ID followed by your password:" + "\n");
        System.out.println("Username: ");
        checkID = idInput.nextLine();
        System.out.println("Password: ");
        checkPassword = pwInput.nextLine();
        if (checkPassword.equals(employeeT.get(checkID).password)) {
            System.out.println("Access Granted! Welcome: " + employeeT.get(checkID).name);
            loginResult = employeeT.get(checkID).permisisonLevel;
            return loginResult;
        }
        else {
            return "Access Denied";
        }
    }

    public boolean login(String user, String password) {
        String checkID,checkPassword, controlT;
        int control = 1;
        checkPassword = password;
        while(control <= employeeT.size()) {
            checkID = employeeT.get(control).name;
            if (checkID.equals(user)) {
                break;
            }
            else {
                control++;
            }
        }
        controlT = String.valueOf(control);
        if (checkPassword.equals(employeeT.get(controlT).password)) {
            loginResult = employeeT.get(controlT).permisisonLevel;
            return true;
        }
        else {
            return false;
        }
    }

    public String getPassword() {
        String empID;
        if (accessCheck.Admin(loginResult)) {
            Scanner idInput = new Scanner(System.in);
            System.out.println("Enter the ID of the desired Employee: ");
            empID = idInput.nextLine();

            return employeeT.get(empID).password;
        }
        else{
            return "Permission Denied";
        }
    }


    public boolean setPassword() {
        String empID;
        if (accessCheck.Admin(loginResult)) {
            Scanner idInput = new Scanner(System.in);
            System.out.println("Enter the ID of the desired Employee: ");
            empID = idInput.nextLine();
            employeeData data = employeeT.get(empID);
            Scanner pasInput = new Scanner(System.in);
            System.out.println("Enter your new Password: ");
            String newPas = pasInput.nextLine();
            data.password = newPas;
            return true;
        }
        else{
            return false;
        }
    }


    public String getPermissionLevel() {
        String empID;
        if (accessCheck.Admin(loginResult)) {
            Scanner idInput = new Scanner(System.in);
            System.out.println("Enter the ID of the desired Employee: ");
            empID = idInput.nextLine();

            return employeeT.get(empID).permisisonLevel;
        } else {
            return "Permission Denied";
        }
    }
    public String requestEmpID(){
        String empID;
        System.out.println("Please enter the corresponding employee ID #: ");
        Scanner idInput = new Scanner(System.in);
        empID = idInput.nextLine();
        return empID;

    }
    public String setEmpID() {
        String empID;
        System.out.println("Enter employee ID #: ");
        Scanner idInput = new Scanner(System.in);
        empID = idInput.nextLine();
        return empID;
    }

        //}
        public void setPermissionLevel(){
        String newPermLVL;
            employeeData data = employeeT.get(setEmpID());
            Scanner permInput = new Scanner(System.in);
            System.out.println("Enter new permission level: ");
            newPermLVL = permInput.nextLine();
            data.permisisonLevel = newPermLVL;
            System.out.println(data.permisisonLevel);

        }

        public String getEmployed() {
        String ID;
        Scanner status = new Scanner(System.in);
        System.out.println("Enter the ID of the Employee you'd like to check");
        ID = status.nextLine();
        return employeeT.get(ID).employed;
        }
        public void addUser(){
            int IDList = 1;
            String Id, name, department, bossID, project, subject, requestedMaterials, allocatedBudget, amountSpent, permissionLevel, password, employed;
            if (accessCheck.Admin(loginResult)) {
                Scanner attInput = new Scanner(System.in);
                System.out.println("Enter attributes of the new Employee: ");
                System.out.println("ID: ");
                Id = attInput.nextLine();
                System.out.println("Name: ");
                name = attInput.nextLine();
                System.out.println("Department: ");
                department = attInput.nextLine();
                System.out.println("bossID: ");
                bossID = attInput.nextLine();
                System.out.println("project: ");
                project = attInput.nextLine();
                System.out.println("subject: ");
                subject = attInput.nextLine();
                System.out.println("requestedMaterials: ");
                requestedMaterials = attInput.nextLine();
                System.out.println("Allocated Budget: ");
                allocatedBudget = attInput.nextLine();
                amountSpent = attInput.nextLine();
                System.out.println("Permission Level: ");
                permissionLevel = attInput.nextLine();
                System.out.println("Password: ");
                password = attInput.nextLine();
                System.out.println("Employed Yes/No: ");
                employed = attInput.nextLine();
                employeeT.put(Id, new employeeData(name, department, bossID, project, subject, requestedMaterials, allocatedBudget, amountSpent,permissionLevel,password,employed));

            } else {
                System.out.println("Access Denied");
            }
        }

        public void terminate() {
            String fireID;
            if (accessCheck.Admin(loginResult)) {
                Scanner fireInput = new Scanner(System.in);
                System.out.println("Enter the ID of the employee you'd like to fire: ");
                fireID = fireInput.nextLine();
                employeeT.remove(fireID);
            } else {
                System.out.println("Access Denied");
            }
        }
            public String getAmountSpent(){
        System.out.println(employeeT.get("4").amountSpent);
                return employeeT.get("4").amountSpent;
            }
        }

