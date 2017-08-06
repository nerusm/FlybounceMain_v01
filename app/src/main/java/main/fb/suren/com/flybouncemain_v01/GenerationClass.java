package main.fb.suren.com.flybouncemain_v01;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by suren on 5/8/17.
 */

public class GenerationClass {

    Random randomGen;
    Calendar calendar;

    public GenerationClass(){
        randomGen = new Random(12345);
        calendar = Calendar.getInstance();
    }


    public String formMemberID(String name, String planDuration){
        return name+planDuration+String.valueOf(calendar.getTimeInMillis());

    }
}
