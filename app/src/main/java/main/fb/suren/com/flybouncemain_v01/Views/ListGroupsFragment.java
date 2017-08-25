package main.fb.suren.com.flybouncemain_v01.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import main.fb.suren.com.flybouncemain_v01.R;

/**
 * Created by suren on 15/8/17.
 */

public class ListGroupsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_group_layout,container,false);

        return view;
    }
}
