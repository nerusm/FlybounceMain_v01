package main.fb.suren.com.flybouncemain_v01;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import main.fb.suren.com.flybouncemain_v01.database.DatabaseHelper;
import main.fb.suren.com.flybouncemain_v01.database.Members;
import main.fb.suren.com.flybouncemain_v01.database.Memberships;

/**
 * Created by suren on 9/8/17.
 */

public class ListMemberFragment extends Fragment{

    private DatabaseHelper databaseHelper = null;
    private Dao<Members, Integer> membersDao;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_member_fragment,container,false);
        listView = (ListView) view.findViewById(R.id.listView_Members);
        try {
            membersDao = getHelper().getMembersDAO();
            List<Members> listOfMembers = membersDao.queryForAll();
            MemberListAdapter memberListAdapter = new MemberListAdapter(getActivity(),R.layout.list_member_item,listOfMembers);
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
