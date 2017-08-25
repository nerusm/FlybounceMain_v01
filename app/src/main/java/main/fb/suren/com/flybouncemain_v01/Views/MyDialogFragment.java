package main.fb.suren.com.flybouncemain_v01.Views;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import main.fb.suren.com.flybouncemain_v01.MainActivity;
import main.fb.suren.com.flybouncemain_v01.R;

/**
 * Created by suren on 25/7/17.
 */

public class MyDialogFragment extends DialogFragment implements Button.OnClickListener{
    public MyDialogFragment() {
    }

    private TextView textView_SearchResultString;
    String resultString;
   // private Button button_CloseDialog;

    @Override
    public void onClick(View view) {
        // Return input text to activity
        UserNameListener activity = (UserNameListener) getFragmentManager();
        Log.i(MainActivity.LOG_TAG,"Button Clicked 1");
/*        switch (view.getId()){
            case R.id.buttonCloseDialog:
                Log.i(MainActivity.LOG_TAG,"Button Clicked 2");
                activity.onFinishUserDialog(button_CloseDialog.getText().toString());
                this.dismiss();
                break;
        }*/

    }

    public interface UserNameListener {
        void onFinishUserDialog(String user);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.showresult_dialogue_fragment,container);
        textView_SearchResultString = (TextView) view.findViewById(R.id.textView_SearchResultString);
        resultString = getArguments().getString("resultString");
        textView_SearchResultString.setText(resultString);
/*        button_CloseDialog = (Button) view.findViewById(R.id.buttonCloseDialog);
        button_CloseDialog.setOnClickListener(this);*/
        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("Enter Username");
        getDialog().getWindow().setLayout(500,600);
        return view;
    }


}
