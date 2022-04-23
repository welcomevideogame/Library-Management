package data;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Date;

/**
 * Object that contains a Hashtable for the mediaData object
 */
public class media {
    public Hashtable<String, mediaData> mediaT = new Hashtable();
    String mediaID;

    public media() {
    }

    public String getType() {
        String ID;
        Scanner type = new Scanner(System.in);
        System.out.println("Enter the Media ID: ");
        ID = type.nextLine();
        return mediaT.get(ID).mediaType;
    }

    public String getName() {
        String ID;
        Scanner type = new Scanner(System.in);
        System.out.println("Enter the Media ID: ");
        ID = type.nextLine();
        return mediaT.get(ID).name;
    }

    public String getBTime() {
        String ID;
        Scanner type = new Scanner(System.in);
        System.out.println("Enter the Media ID: ");
        ID = type.nextLine();
        return mediaT.get(ID).bTime;
    }

    public String getBorrowable() {
        String ID;
        Scanner type = new Scanner(System.in);
        System.out.println("Enter the Media ID: ");
        ID = type.nextLine();
        return mediaT.get(ID).borrowable;
    }
    public float borrowTime(String mediaID){
        String requestedTime;
        Scanner chach = new Scanner(System.in);
        requestedTime = chach.nextLine();
        float borrowSeconds = 0, borrowMinutes = 0, borrowHours = 0, borrowDays = 0;
        if(getBorrowable().equals("Yes")){
            borrowSeconds++;
        }
        borrowMinutes = borrowSeconds / 60;
        borrowHours = borrowMinutes / 60;
        borrowDays = borrowHours / 12;
        if(requestedTime.equals("Seconds")){
            return borrowSeconds;
        }
        if (requestedTime.equals("Minutes")){
            return borrowMinutes;
        }
        if(requestedTime.equals("Hours")){
            return borrowHours;
        }
        if(requestedTime.equals("Days")){
            return borrowDays;
        }
        if(requestedTime.equals("Total")){
            System.out.println(borrowDays + " Days" + borrowHours + " Hours" + borrowMinutes + " Minutes" + borrowSeconds + " Seconds");
        }
        return 0;
    }

    public String isPastDue() {
        Date due = new Date(mediaT.get(mediaID).due);
        Date current = new Date();
        if (due.before(current)) {
            return "Past Due";
        }
        return "Due " + due;
    }

    public String getDue(int id) {
        String ID;
        Scanner type = new Scanner(System.in);
        System.out.println("Enter the Media ID: ");
        ID = type.nextLine();
        return mediaT.get(ID).due;
    }

    public String getVendor() {
        String ID;
        Scanner type = new Scanner(System.in);
        System.out.println("Enter the Media ID: ");
        ID = type.nextLine();
        return mediaT.get(ID).vendor;
    }
    public Boolean vendorMood(){
        String vendorN,vendorL, vendorB;
        int vendorO = 0, vendorT = 0;
        float vendorP = 0;
        int control = 1;
        Scanner vendor = new Scanner(System.in);
        vendorN = vendor.nextLine();
        while(control >= mediaT.size()){
          vendorL = mediaT.get(String.valueOf(control)).vendor;
          vendorB = mediaT.get(String.valueOf(control)).borrowable;
          if(vendorN.equals(vendorL)){
              vendorT++;
          }
          if(vendorB.equals("No")){
              vendorO++;
          }
          control++;
        }
        vendorP = vendorO / vendorT;
        if(vendorP >= .6){
            return true;
        }
        else {
            return false;
        }

    }
}
