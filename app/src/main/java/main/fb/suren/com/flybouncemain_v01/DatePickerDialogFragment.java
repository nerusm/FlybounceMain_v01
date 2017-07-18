package main.fb.suren.com.flybouncemain_v01;

/**
 * Created by suren on 18/7/17.
 */


        import android.app.DatePickerDialog;
        import android.app.Dialog;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.support.v4.app.DialogFragment;
        import android.widget.DatePicker;

public class DatePickerDialogFragment extends DialogFragment{
    Handler mHandler ;
    int mDay;
    int mMonth;
    int mYear;

    public DatePickerDialogFragment(Handler h){
        /** Getting the reference to the message handler instantiated in MainActivity class */
        mHandler = h;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        /** Creating a bundle object to pass currently set date to the fragment */
        Bundle b = getArguments();

        /** Getting the day of month from bundle */
        mDay = b.getInt("set_day");

        /** Getting the month of year from bundle */
        mMonth = b.getInt("set_month");

        /** Getting the year from bundle */
        mYear = b.getInt("set_year");

        /** DatePickerDialog's "Set" click listener */
        DatePickerDialog.OnDateSetListener listener  = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDay = dayOfMonth;
                mMonth = monthOfYear;
                mYear = year;

                /** Creating a bundle object to pass currently set date to the fragment */
                Bundle b = new Bundle();

                /** Adding currently set day to bundle object */
                b.putInt("set_day", mDay);

                /** Adding currently set month to bundle object */
                b.putInt("set_month", mMonth);

                /** Adding currently set year to bundle object */
                b.putInt("set_year", mYear);

                /** Adding Current date in a string to bundle object */
                b.putString("set_date", "Set Date : " + Integer.toString(mDay) + " / " + Integer.toString(mMonth+1) + " / " + Integer.toString(mYear));

                /** Creating an instance of Message */
                Message m = new Message();

                /** Setting bundle object on the message object m */
                m.setData(b);

                /** Message m is sending using the message handler instantiated in MainActivity class */
                mHandler.sendMessage(m);
            }
        };

        /** Opening the DatePickerDialog window */
        return new DatePickerDialog(getActivity(), listener, mYear, mMonth, mDay);
    }
}