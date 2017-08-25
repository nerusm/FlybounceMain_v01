package main.fb.suren.com.flybouncemain_v01.Views;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import main.fb.suren.com.flybouncemain_v01.Utilities.GenerationClass;
import main.fb.suren.com.flybouncemain_v01.MainActivity;
import main.fb.suren.com.flybouncemain_v01.R;
import main.fb.suren.com.flybouncemain_v01.SendNotificationSMS;
import main.fb.suren.com.flybouncemain_v01.Utilities.Utils;
import main.fb.suren.com.flybouncemain_v01.Database.DatabaseHelper;
import main.fb.suren.com.flybouncemain_v01.Models.Member;
import main.fb.suren.com.flybouncemain_v01.Models.Memberships;
import main.fb.suren.com.flybouncemain_v01.Models.Notifications;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by suren on 16/7/17.
 */

public class AddMemberFragment extends Fragment implements MyDialogFragment.UserNameListener{

    private DatabaseHelper databaseHelper = null;
    private Dao<Memberships,Integer> membershipsesDAO;
    private Dao<Notifications,Integer> notificationsDao;
    private Dao<Member, Integer> membersDao;

    EditText editText_Membername;
    EditText editText_MobileNumber;
    EditText editText_StartDate;
    EditText editText_MultiLine;

    Button buttonAddMember;
    Button buttonCheckAvailabilty;
    Button buttonTempDisplay;
    Button buttonTempSetAlarm;
    ImageButton buttonDatePicker;
    Spinner spinner_TimeSelect;
    Spinner spinner_CourtSelect;
    Spinner spinner_PlanSelect;
    RadioGroup radioGroupDuration;
    RadioButton radioButtonDurationSelected;
    private RadioGroup radioGroupMembershipType;
    private RadioButton radioButtonMembershipType;



    Member member;
    Memberships memberships;
    Notifications notifications;
    List<String> listHours = new ArrayList<String>();

    private PendingIntent pendingIntent;



    private int mYear, mMonth, mDay, mHour, mMinute;
    private DatePickerDialog fromDatePickerDialog;
    //private SimpleDateFormat dateFormatter;
    Date inputDate = null;
    Date endDate = null;
    int startTime;
    int courtNo;
    String planName;

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
        buttonTempDisplay = (Button) view.findViewById(R.id.button_tempDisplay);
        buttonTempSetAlarm = (Button) view.findViewById(R.id.button_tempSetAlarm);
        buttonTempSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
            }
        });
        editText_Membername = (EditText) view.findViewById(R.id.edittext_membername);
        editText_MobileNumber = (EditText) view.findViewById(R.id.editext_mobileno);
        editText_StartDate = (EditText) view.findViewById(R.id.editText_startdate);
        editText_MultiLine = (EditText) view.findViewById(R.id.editTextMultiLine);

       // dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        spinner_TimeSelect = (Spinner) view.findViewById(R.id.spinnerTimeSelect);
        spinner_CourtSelect = (Spinner) view.findViewById(R.id.spinnerCourtSelect);
        spinner_PlanSelect = (Spinner) view.findViewById(R.id.spinner_PlanSelect);
        radioGroupDuration = (RadioGroup) view.findViewById(R.id.radioGroupDuration);
        radioGroupMembershipType = (RadioGroup) view.findViewById(R.id.radioGroup_MemberType);

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
        try {
            membershipsesDAO = getHelper().getMembershipDAO();
            notificationsDao = getHelper().getNotificationsDAO();
            membersDao = getHelper().getMembersDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        buttonTempDisplay.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                populateViewNotify(notificationsDao);
            }
        });

        buttonCheckAvailabilty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(MainActivity.LOG_TAG,"Populate");
                populateViewMember(membershipsesDAO);
                FragmentManager manager  = getFragmentManager();
                Fragment fragment = manager.findFragmentByTag("fragment_dialog");
                if(fragment != null){
                    manager.beginTransaction().remove(fragment).commit();
                }

                Bundle args = new Bundle();
                args.putString("resultString", "Court Not available");

                MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.setArguments(args);
                myDialogFragment.show(manager,"fragment_dialog");
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

                radioButtonMembershipType = (RadioButton) getActivity().findViewById(radioGroupMembershipType.getCheckedRadioButtonId());
                String membershipTypeString  = radioButtonMembershipType.getText().toString();


                startTime = Integer.parseInt( spinner_TimeSelect.getSelectedItem().toString());
                courtNo = Integer.parseInt(spinner_CourtSelect.getSelectedItem().toString());
                planName = spinner_PlanSelect.getSelectedItem().toString();
                int durationMonthsInt = 1;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(inputDate);
                if(durationString.equalsIgnoreCase(getString(R.string.tDurationMonthly))){
                    calendar.add(Calendar.MONTH,1);
                    durationMonthsInt = 1;

                } else if(durationString.equalsIgnoreCase(getString(R.string.tDurationQuarterly))){
                    calendar.add(Calendar.MONTH,3);
                    durationMonthsInt = 3;
                } else if(durationString.equalsIgnoreCase(getString(R.string.tDurationYearly))){
                    calendar.add(Calendar.YEAR,1);
                    durationMonthsInt = 12;
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


                String memberID = new GenerationClass().formMemberID(name,mobileNo);
                String membershipID = new GenerationClass().formMembershipID(memberID,planName);
//public Memberships(String membership_ID, String member_ID, int start_time, int court_number, Date start_date, Date end_date, int renewal_count, String plan_name, int duration_months, Notifications notifications) {
                member = new Member(memberID,name,mobileNo);
                notifications = new Notifications(membershipID,myUtils.subtractDate(endDate,getResources().getInteger(R.integer.notification_advance_days)),0,true);
                memberships = new Memberships(membershipID,memberID,startTime,courtNo,inputDate,endDate,0,planName,durationMonthsInt,notifications,durationString, true, membershipTypeString, member);


             /*   memberships = new Memberships(memberID,name,mobileInt,inputDate,endDate, startTime, courtNo,
                        getString(R.string.tDateFormat),notifications,0);*/


                try {
                    membersDao.create(member);
                    notificationsDao.create(notifications);
                    membershipsesDAO.create(memberships);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Log.i(MainActivity.LOG_TAG,"Name: "+name);
            }
        });




        return view;
    }


    private void setAlarm(){
        Intent myIntent = new Intent(getActivity(),SendNotificationSMS.class);
        Bundle bundle = new Bundle();
        bundle.putCharSequence("extraSmsNumber", "0000");
        bundle.putCharSequence("extraSmsText", "hi");
        myIntent.putExtras(bundle);

        pendingIntent = PendingIntent.getService(getActivity(),0,myIntent,0);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(getActivity(),
                "Start Alarm with \n" +
                        "smsNumber = " + "\"0000\"" + "\n" +
                        "smsText = " + "Hi",
                Toast.LENGTH_LONG).show();



    }

    private void populateViewNotify(Dao<Notifications, Integer> notificationsDao){
        try{
            List<Notifications> notificationsList = notificationsDao.queryForAll();
            StringBuffer sb = new StringBuffer("NOTIFY:\n");
            for(int i = notificationsList.size()-1; i >=0; i--){
                sb = sb.append(notificationsList.get(i).toString());
            }
            Log.i(MainActivity.LOG_TAG,sb.toString());
            editText_MultiLine.setText(sb.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void populateViewMember(Dao<Memberships, Integer> memberDAO) {
        try {
            List<Memberships> listOfMembers = memberDAO.queryForAll();
            Log.i(MainActivity.LOG_TAG,"Size: "+listOfMembers.size());
            StringBuffer sb = new StringBuffer("START-\n");
            for (int i = listOfMembers.size()-1;i>=0;i--){
                sb = sb.append(listOfMembers.get(i).toString());
                Notifications notification = listOfMembers.get(i).getNotifications();
                Log.i(MainActivity.LOG_TAG,notification.toString());

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
    /*    if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }*/
    }



    @Override
    public void onStop() {
        super.onStop();
       /* if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }*/
    }

    @Override
    public void onFinishUserDialog(String user) {
        Toast.makeText(getActivity(), "Hello, " + user, Toast.LENGTH_SHORT).show();
    }
}
