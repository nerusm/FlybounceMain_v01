package main.fb.suren.com.flybouncemain_v01;

import android.app.Activity;

/**
 * Created by suren on 8/8/17.
 */

public class ADTO extends Activity{
    String[] planNamesArrayResource;

    public ADTO() {
        this.planNamesArrayResource =  getApplication().getResources().getStringArray(R.array.Plan_names);
    }

    public String[] getPlanNamesArrayResource() {
        return planNamesArrayResource;
    }
}
