package data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class useData {

    private final HashMap<String, Boolean> uses;
    private final HashMap<String, Integer> useTime;
    private String empID;

    private long timer = 0;

    public useData(){
        uses = new HashMap<>(Map.of("Media", false,
                "Employees", false, "Patrons", false, "Vendors",
                false, "Databases", false));

        useTime = new HashMap<>((Map.of("Media", 0,
                "Employees", 0, "Patrons", 0, "Vendors",
                0, "Databases", 0)));

    }

    public void setEmpID(String empID){
        this.empID = empID;
    }

    public void startTimer(){
        timer = System.currentTimeMillis();
    }


    public void endTimer(String db){
        if(!uses.get(db)) uses.put(db, true);
        long difference = System.currentTimeMillis() - timer;
        try{
            useTime.put(db, useTime.get(db) + ((int) difference / 1000));
        }
        catch(NullPointerException | NumberFormatException e){
            e.printStackTrace();
        }
    }

    public String getEmpID(){
        return empID;
    }

    public boolean accessed(){
        return timer != 0;
    }

    public HashMap<String, Integer> getUseTime(){
        return useTime;
    }

    public HashMap<String, Boolean> getUses(){
        return uses;
    }

}
