package main.fb.suren.com.flybouncemain_v01.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.fb.suren.com.flybouncemain_v01.MainActivity;
import main.fb.suren.com.flybouncemain_v01.Adapters.MemberListAdapter;
import main.fb.suren.com.flybouncemain_v01.Adapters.MembersAdapterDTO;
import main.fb.suren.com.flybouncemain_v01.Models.Member;
import main.fb.suren.com.flybouncemain_v01.R;
import main.fb.suren.com.flybouncemain_v01.Database.DatabaseHelper;
import main.fb.suren.com.flybouncemain_v01.Models.Memberships;

/**
 * Created by suren on 9/8/17.
 */

public class ListMemberFragment extends Fragment{

    private DatabaseHelper databaseHelper = null;
    private Dao<Member, Integer> membersDao;
    private Dao<Memberships,Integer> membershipsesDAO;
    private ListView listView;
    private EditText editTextSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_member_fragment,container,false);
        listView = (ListView) view.findViewById(R.id.listView_Members);
        editTextSearch = (EditText) view.findViewById(R.id.editText_SearchMember);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        try {
            membersDao = getHelper().getMembersDAO();
            membershipsesDAO = getHelper().getMembershipDAO();
            List<Member> listOfMembers = membersDao.queryForAll();

            List<MembersAdapterDTO> listOfDTO = new ArrayList<>();
            for(int i = 0; i<listOfMembers.size(); i++){

                MembersAdapterDTO membersAdapterDTO;
                Map fieldValues = new HashMap();
                fieldValues.put("member_ID",listOfMembers.get(i).getMember_id());
                fieldValues.put("membership_status",true);
                List<Memberships> membershipForMember = membershipsesDAO.queryForFieldValues(fieldValues);
                if(membershipForMember.size() > 0) {
                    membersAdapterDTO = new MembersAdapterDTO(listOfMembers.get(i), membershipForMember.get(0), true);
                } else {
                    membersAdapterDTO = new MembersAdapterDTO(listOfMembers.get(i), false);
                }
                listOfDTO.add(membersAdapterDTO);
            }




           /* MemberListAdapter memberListAdapter = new MemberListAdapter(getActivity(),R.layout.list_member_item_odd,listOfMembers);
            listView.setAdapter(memberListAdapter);*/

           final MemberListAdapter memberListAdapter = new MemberListAdapter(getActivity(),R.layout.list_member_item_odd,listOfDTO);
            editTextSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.i(MainActivity.LOG_TAG,"CS1: "+charSequence);
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    memberListAdapter.getFilter().filter(charSequence);
                    Log.i(MainActivity.LOG_TAG,"CS2: "+charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            listView.setAdapter(memberListAdapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return view;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(MainActivity.LOG_TAG,"In OnDestroy");
        if (databaseHelper != null) {
           /* OpenHelperManager.releaseHelper();
            databaseHelper = null;*/
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        Log.i(MainActivity.LOG_TAG,"In OnStop");
        if (databaseHelper != null) {
            /*OpenHelperManager.releaseHelper();
            databaseHelper = null;
        */}
    }



    protected DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
