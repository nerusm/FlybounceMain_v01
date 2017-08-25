package main.fb.suren.com.flybouncemain_v01;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import main.fb.suren.com.flybouncemain_v01.Database.DatabaseHelper;
import main.fb.suren.com.flybouncemain_v01.Models.Memberships;
import main.fb.suren.com.flybouncemain_v01.Models.Notifications;


/**
 * Created by suren on 6/8/17.
 */

public class SendNotificationSMS  extends Service{

    String smsNumberToSend, smsTextToSend;

    private DatabaseHelper databaseHelper = null;
    private Dao<Memberships,Integer> membershipDAO;
    private Dao<Notifications,Integer> notificationsDao;

    public void getAllNotitifications(){
        try {
            List<Notifications> notificationsList = notificationsDao.queryForAll();
            for(int i = notificationsList.size()-1; i >=0; i--){
                /*Notifications notification = notificationsList.get(i);
                List<Memberships> membersList = membershipDAO.queryBuilder().where().eq("memberID",notification.getMemberID()).query();
                for (int j = 0; j < membersList.size(); j++){
                    int mobileNo = membersList.get(j).getMobile_number();
                    sendSMS(mobileNo,membersList.get(j).getEndDate().toString());
                }
                    Log.i(MainActivity.LOG_TAG,"Size:"+membersList.size());*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sendSMS(int mobileNo,String renewalDate){
        Log.i(MainActivity.LOG_TAG,"SMS Sent to "+mobileNo);
        Log.i(MainActivity.LOG_TAG,formSMSText(renewalDate));
    }

    public void updateNotificationStatus(String memberID){
        Log.i(MainActivity.LOG_TAG,"Status Updated "+memberID);
    }

    public String formSMSText(String renewalDate){
        String tempResource = getResources().getString(R.string.SMSBody);
        String smsString = tempResource.replaceAll(getResources().getString(R.string.RENEWAL_DATE_REPLACE_STRING), renewalDate);
        smsString = smsString.replaceAll(getResources().getString(R.string.BANK_DETAILS_REPLACE_STRING),getResources().getString(R.string.bankDetails));
        return smsString;
    }
    protected DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub

        Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
        Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Bundle bundle = intent.getExtras();
        smsNumberToSend = (String) bundle.getCharSequence("extraSmsNumber");
        smsTextToSend = (String) bundle.getCharSequence("extraSmsText");


        try {
            notificationsDao = getHelper().getNotificationsDAO();
            membershipDAO = getHelper().getMembershipDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();
        Toast.makeText(this,
                "MyAlarmService.onStart() with \n" +
                        "smsNumberToSend = " + smsNumberToSend + "\n" +
                        "smsTextToSend = " + smsTextToSend,
                Toast.LENGTH_LONG).show();

        getAllNotitifications();

    }
}
