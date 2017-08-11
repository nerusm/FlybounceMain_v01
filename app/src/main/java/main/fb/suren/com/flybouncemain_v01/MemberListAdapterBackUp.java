package main.fb.suren.com.flybouncemain_v01;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;

import java.util.List;

import main.fb.suren.com.flybouncemain_v01.database.DatabaseHelper;
import main.fb.suren.com.flybouncemain_v01.database.Members;
import main.fb.suren.com.flybouncemain_v01.database.Memberships;

/**
 * Created by suren on 9/8/17.
 */

public class MemberListAdapterBackUp extends ArrayAdapter<Members> {

    private DatabaseHelper databaseHelper = null;
    private final Context context;
    private final List<Members> membersList;
    private Dao<Memberships,Integer> membershipsesDAO;

    public MemberListAdapterBackUp(@NonNull Context context, @LayoutRes int resource, @NonNull List<Members> objects) {
        super(context, resource, objects);

        this.context = context;
        this.membersList = objects;

    }

/*
    protected DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }*/

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rownView = inflater.inflate(R.layout.list_member_item,parent,false);

        TextView textViewMemberName = (TextView) rownView.findViewById(R.id.member_name_item);
        TextView textViewMemberPlan = (TextView) rownView.findViewById(R.id.textView_memberPlan);
        TextView textViewMemberDuration = (TextView) rownView.findViewById(R.id.textView_memberDuration);
        Members members = membersList.get(position);

        textViewMemberName.setText(members.getMember_name());
        //textViewMemberPlan.setText(members.get);

        return rownView;

//        return super.getView(position, convertView, parent);
    }
}


