package data;

import java.time.LocalTime;
import java.util.ArrayList;

public class Databases {
    ArrayList<ArrayList> containerD = new ArrayList<ArrayList>();
    ArrayList<Integer> databaseID = new ArrayList<Integer>();
    ArrayList<Double> price = new ArrayList<Double>();
    ArrayList<LocalTime> dueDate = new ArrayList<LocalTime>();
    ArrayList<Integer> patronAccess = new ArrayList<Integer>();

    public void Databases() {
        containerD.add(databaseID);
        containerD.add(price);
        containerD.add(dueDate);
        containerD.add(patronAccess);
    }
}