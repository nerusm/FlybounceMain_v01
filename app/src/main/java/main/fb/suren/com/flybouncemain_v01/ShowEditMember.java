package main.fb.suren.com.flybouncemain_v01;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by suren on 11/8/17.
 */

public class ShowEditMember extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_member,container,false);
//        return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}
