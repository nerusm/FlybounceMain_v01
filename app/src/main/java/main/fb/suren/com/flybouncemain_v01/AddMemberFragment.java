package main.fb.suren.com.flybouncemain_v01;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.types.IntegerObjectType;

import java.sql.SQLException;

import main.fb.suren.com.flybouncemain_v01.database.DatabaseHelper;
import main.fb.suren.com.flybouncemain_v01.database.Member;

/**
 * Created by suren on 16/7/17.
 */

public class AddMemberFragment extends Fragment {

    private DatabaseHelper databaseHelper = null;
    private Dao<Member,Integer> memberDAO;

    EditText editTextMembername;
    EditText editTextMobileNumber;
    Button buttonAddMember;
    Member member;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_member_fragment,container,false);
        buttonAddMember = (Button) view.findViewById(R.id.button_addMember);
        editTextMembername = (EditText) view.findViewById(R.id.edittext_membername);
        editTextMobileNumber = (EditText) view.findViewById(R.id.editext_mobileno);



        try {
            memberDAO = getHelper().getMemberDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        buttonAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextMembername.getText().toString();
                String mobileNo = editTextMobileNumber.getText().toString();
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
