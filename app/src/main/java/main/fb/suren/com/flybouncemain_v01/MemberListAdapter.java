package main.fb.suren.com.flybouncemain_v01;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.fb.suren.com.flybouncemain_v01.database.DatabaseHelper;
import main.fb.suren.com.flybouncemain_v01.database.Members;
import main.fb.suren.com.flybouncemain_v01.database.Memberships;

/**
 * Created by suren on 9/8/17.
 */

public class MemberListAdapter extends ArrayAdapter<MembersAdapterDTO> implements Filterable{

    private DatabaseHelper databaseHelper = null;
    private final Context context;
    private List<MembersAdapterDTO> membersAdapterDTOsList;
    private List<MembersAdapterDTO> membersAdapterDTOsListCOPY;
    private Dao<Memberships,Integer> membershipsesDAO;
    MembersFilter mMembersFilter;

    public MemberListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<MembersAdapterDTO> objects) {
        super(context, resource, objects);

        this.context = context;
        this.membersAdapterDTOsList = objects;
        this.membersAdapterDTOsListCOPY = objects;

    }

    @NonNull
    @Override
    public Filter getFilter() {
        if(mMembersFilter == null){
            mMembersFilter = new MembersFilter();
        }
        return mMembersFilter;
    }

    @Override
    public int getCount() {
        return membersAdapterDTOsList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rownView = inflater.inflate(R.layout.list_member_item,parent,false);

        TextView textViewMemberName = (TextView) rownView.findViewById(R.id.member_name_item);
        TextView textViewMemberPlan = (TextView) rownView.findViewById(R.id.textView_memberPlan);
        TextView textViewMemberDuration = (TextView) rownView.findViewById(R.id.textView_memberDuration);
        TextView textViewMemberRenewalDate = (TextView) rownView.findViewById(R.id.textView_RenewalDate);
        Log.i(MainActivity.LOG_TAG,"getView position: "+position);
        MembersAdapterDTO membersAdapterDTO = membersAdapterDTOsList.get(position);

        Members members = membersAdapterDTO.getMembers();
        Memberships memberships = membersAdapterDTO.getMemberships();

        textViewMemberName.setText(members.getMember_name());
        textViewMemberDuration.setText(memberships.getDuration_string());
        textViewMemberPlan.setText(memberships.getPlan_name());
        textViewMemberRenewalDate.setText( textViewMemberRenewalDate.getText() + " "+ Utils.formatDate(memberships.getEnd_date(),"dd-MMM-yyyy"));

        return rownView;


    }

    /*
    protected DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }*/

   /* @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rownView = inflater.inflate(R.layout.list_member_item,parent,false);

        TextView textViewMemberName = (TextView) rownView.findViewById(R.id.member_name_item);
        TextView textViewMemberPlan = (TextView) rownView.findViewById(R.id.textView_memberPlan);
        TextView textViewMemberDuration = (TextView) rownViewfindViewById(R.id.textView_memberDuration);
        Members members = membersList.get(position);

        textViewMemberName.setText(members.getMember_name());
        //textViewMemberPlan.setText(members.get);

        return rownView;

//        return super.getView(position, convertView, parent);
    }*/

   private class MembersFilter extends Filter {

       @Override
       protected FilterResults performFiltering(CharSequence constraint) {
           FilterResults filterResults = new FilterResults();

           Log.i(MainActivity.LOG_TAG,"Costrain: "+constraint);
           if(constraint == null || constraint.length() == 0){
               filterResults.values = membersAdapterDTOsListCOPY;
               filterResults.count = membersAdapterDTOsListCOPY.size();
           } else {
               ArrayList<MembersAdapterDTO> filteredMembersDTO = new ArrayList<MembersAdapterDTO>();

               for (MembersAdapterDTO memberDTO : membersAdapterDTOsListCOPY) {
                    if( (memberDTO.getMembers().getMember_name().toLowerCase().contains(constraint.toString().toLowerCase())) || memberDTO.getMembers().getMobile_number().contains(constraint)  ){
                        filteredMembersDTO.add(memberDTO);
                    }
               }

               filterResults.values = filteredMembersDTO;
               filterResults.count = filteredMembersDTO.size();

           }
           return filterResults;
       }

       @Override
       protected void publishResults(CharSequence constraint, FilterResults filterResults) {
           Log.i(MainActivity.LOG_TAG,"From Publish :"+filterResults.count);
           membersAdapterDTOsList = (List<MembersAdapterDTO>) filterResults.values;
            notifyDataSetChanged();
       }
   }
}


