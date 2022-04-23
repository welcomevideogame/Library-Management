package data;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Object that contains all the data for the object
 */
public class mediaData {
    String mediaType, name, bTime, borrowable, due, vendor;
    boolean vendorH, vendorM;


    public mediaData(String value1, String value2, String value3, String value4, String value5, String value6) {
        mediaType = value1;
        name = value2;
        bTime = value3;
        borrowable = value4;
        due = value5;
        vendor = value6;
    }

    /**
     * @return Returns an ArrayList that contains all the attributes of the data
     */
    public ArrayList<String> getData(){
        ArrayList<String> a = new ArrayList<>();
        Collections.addAll(a, mediaType ,name, bTime, borrowable, due,vendor);
        return a;
    }
}
