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
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import main.fb.suren.com.flybouncemain_v01.database.DatabaseHelper;
import main.fb.suren.com.flybouncemain_v01.database.Member;

/**
 * Created by suren on 16/7/17.
 */

public class AddMemberBackUp extends Fragment{

    private DatabaseHelper databaseHelper = null;
    private Dao<Member,Integer> memberDAO;

    EditText editText_Membername;
    EditText editText_MobileNumber;
    EditText editText_StartDate;
    Button buttonAddMember;
    ImageButton buttonDatePicker;
    Spinner spinner_TimeSelect;
    Member member;
    List<String> listHours = new ArrayList<String>();

    private int mYear, mMonth, mDay, mHour, mMinute;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;


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
        editText_Membername = (EditText) view.findViewById(R.id.edittext_membername);
        editText_MobileNumber = (EditText) view.findViewById(R.id.editext_mobileno);
        editText_StartDate = (EditText) view.findViewById(R.id.editText_startdate);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        spinner_TimeSelect = (Spinner) view.findViewById(R.id.spinnerTimeSelect);

        for (int i =5;i<=22;i++){
            listHours.add(i+"");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,listHours);


        try {
            memberDAO = getHelper().getMemberDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }


//        buttonDatePicker.setOnClickListener(this);
        buttonAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText_Membername.getText().toString();
                String mobileNo = editText_MobileNumber.getText().toString();
                int mobileInt = Integer.parseInt(mobileNo);
                member = new Member(name,mobileInt);
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
