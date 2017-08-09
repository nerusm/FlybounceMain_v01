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

import java.util.List;

import main.fb.suren.com.flybouncemain_v01.database.Members;

/**
 * Created by suren on 9/8/17.
 */

public class MemberListAdapter extends ArrayAdapter<Members> {

    private final Context context;
    private final List<Members> membersList;

    public MemberListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Members> objects) {
        super(context, resource, objects);

        this.context = context;
        this.membersList = objects;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rownView = inflater.inflate(R.layout.list_member_item,parent,false);

        TextView textViewMemberName = (TextView) rownView.findViewById(R.id.member_name_item);
        Members members = membersList.get(position);
        textViewMemberName.setText(members.getMember_name());

        return rownView;

//        return super.getView(position, convertView, parent);
    }
}


