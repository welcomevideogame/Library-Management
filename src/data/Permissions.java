package data;

import java.util.ArrayList;
import java.util.Scanner;

public class Permissions {

    public boolean Restricted(String permissionLVL) {
        if (permissionLVL.equals("4")) {
            return true;
        } else {
            return false;

        }
    }
    public boolean Admin(String permissionLVL){
        if(permissionLVL.equals("5")){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Common(int permissionLVL){
        if(permissionLVL >= 1 && permissionLVL <= 3){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Employees(String permissionLVL){
        if(Integer.parseInt(permissionLVL) > 0 && Integer.parseInt(permissionLVL) <= 5) {
            return true;
        }
        else {
            return false;
        }
    }
}
