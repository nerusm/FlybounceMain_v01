package main.fb.suren.com.flybouncemain_v01;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import main.fb.suren.com.flybouncemain_v01.database.DatabaseHelper;
import main.fb.suren.com.flybouncemain_v01.database.Member;

/**
 * Created by suren on 16/7/17.
 */

public class AddMemberFragment extends Fragment{

    private DatabaseHelper databaseHelper = null;
    private Dao<Member,Integer> memberDAO;

    EditText editText_Membername;
    EditText editText_MobileNumber;
    EditText editText_StartDate;
    EditText editText_MultiLine;

    Button buttonAddMember;
    Button buttonCheckAvailabilty;
    ImageButton buttonDatePicker;
    Spinner spinner_TimeSelect;
    Spinner spinner_CourtSelect;
    RadioGroup radioGroupDuration;
    RadioButton radioButtonDurationSelected;

    Member member;
    List<String> listHours = new ArrayList<String>();



    private int mYear, mMonth, mDay, mHour, mMinute;
    private DatePickerDialog fromDatePickerDialog;
    //private SimpleDateFormat dateFormatter;
    Date inputDate = null;
    Date endDate = null;
    int startTime;
    int courtNo;

    Utils myUtils;

    /** This handles the message send from DatePickerDialogFragment on setting date */

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();

            mDay = b.getInt("set_day");
            mMonth = b.getInt("set_month");
            mYear = b.getInt("set_year");

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_member_fragment,container,false);
        buttonAddMember = (Button) view.findViewById(R.id.button_addMember);
        buttonDatePicker = (ImageButton) view.findViewById(R.id.button_DatePicker);
        buttonCheckAvailabilty = (Button) view.findViewById(R.id.button_courtsAvailable);
        editText_Membername = (EditText) view.findViewById(R.id.edittext_membername);
        editText_MobileNumber = (EditText) view.findViewById(R.id.editext_mobileno);
        editText_StartDate = (EditText) view.findViewById(R.id.editText_startdate);
        editText_MultiLine = (EditText) view.findViewById(R.id.editTextMultiLine);

       // dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        spinner_TimeSelect = (Spinner) view.findViewById(R.id.spinnerTimeSelect);
        spinner_CourtSelect = (Spinner) view.findViewById(R.id.spinnerCourtSelect);
        radioGroupDuration = (RadioGroup) view.findViewById(R.id.radioGroupDuration);

        final String inputString = "11-11-2012";
       //final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        myUtils = new Utils();


        try {
           // Log.i(MainActivity.LOG_TAG,dateFormat.format(dateFormat.parse(inputString)));

           //inputDate = dateFormat.parse(inputString);
            inputDate = myUtils.stringToDate(inputString,getString(R.string.tInputDateFormat));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i =5;i<=22;i++){
            listHours.add(i+"");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,listHours);

        spinner_TimeSelect.setAdapter(dataAdapter);

        for(int i = 1; i<=3;i++){

        }
        try {
            memberDAO = getHelper().getMemberDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        buttonCheckAvailabilty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(MainActivity.LOG_TAG,"Populate");
                populateView(memberDAO);
            }
        });
//        buttonDatePicker.setOnClickListener(this);
        buttonAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText_Membername.getText().toString();
                String mobileNo = editText_MobileNumber.getText().toString();
                int sel = radioGroupDuration.getCheckedRadioButtonId();
                radioButtonDurationSelected = (RadioButton) getActivity().findViewById(sel);
                String durationString = radioButtonDurationSelected.getText().toString();
                startTime = Integer.parseInt( spinner_TimeSelect.getSelectedItem().toString());
                courtNo = Integer.parseInt(spinner_CourtSelect.getSelectedItem().toString());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(inputDate);
                if(durationString.equalsIgnoreCase(getString(R.string.tDurationMonthly))){
                    calendar.add(Calendar.MONTH,1);

                } else if(durationString.equalsIgnoreCase(getString(R.string.tDurationQuarterly))){
                    calendar.add(Calendar.MONTH,3);
                } else if(durationString.equalsIgnoreCase(getString(R.string.tDurationYearly))){
                    calendar.add(Calendar.YEAR,1);
                }

                Log.i(MainActivity.LOG_TAG,calendar.toString());
                endDate = calendar.getTime();
                /*try {
                    endDate = dateFormat.parse( dateFormat.format(endDate));
                    Log.i(MainActivity.LOG_TAG,durationString + "- " + dateFormat.parse( dateFormat.format(endDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

                int mobileInt = Integer.parseInt(mobileNo);
                member = new Member(name,mobileInt,inputDate,endDate, startTime, courtNo,
                        getString(R.string.tDateFormat));

                try {
                    memberDAO.create(member);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Log.i(MainActivity.LOG_TAG,"Name: "+name);
            }
        });




        return view;
    }

    private void populateView(Dao<Member, Integer> memberDAO) {
        try {
            List<Member> listOfMembers = memberDAO.queryForAll();
            Log.i(MainActivity.LOG_TAG,"Size: "+listOfMembers.size());
            StringBuffer sb = new StringBuffer("START-\n");
            for (int i = listOfMembers.size()-1;i>=0;i--){
                sb = sb.append(listOfMembers.get(i).toString());

            }
            Log.i(MainActivity.LOG_TAG,sb.toString());
            editText_MultiLine.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }




}
