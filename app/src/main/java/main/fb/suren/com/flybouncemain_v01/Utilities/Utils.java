package main.fb.suren.com.flybouncemain_v01.Utilities;

import android.app.Activity;
import android.app.PendingIntent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by suren on 23/7/17.
 */

public class Utils {

    public static String formatDate(Date inDate, String targetDateFormat){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(targetDateFormat);
        return simpleDateFormat.format(inDate);
    }

    public static Date stringToDate(String inString, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(inString);
    }

    public static Date subtractDate(Date inDate, int noOfDays){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);

        calendar.add(Calendar.DATE, noOfDays);

        return calendar.getTime();
    }


}
