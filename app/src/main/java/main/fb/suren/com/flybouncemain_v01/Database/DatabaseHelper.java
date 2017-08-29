package main.fb.suren.com.flybouncemain_v01.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import main.fb.suren.com.flybouncemain_v01.MainActivity;
import main.fb.suren.com.flybouncemain_v01.Models.Groups;
import main.fb.suren.com.flybouncemain_v01.Models.Member;
import main.fb.suren.com.flybouncemain_v01.Models.Memberships;
import main.fb.suren.com.flybouncemain_v01.Models.Notifications;
import main.fb.suren.com.flybouncemain_v01.Models.Payments;
import main.fb.suren.com.flybouncemain_v01.Models.Plans;
import main.fb.suren.com.flybouncemain_v01.Models.Players;

/**
 * Created by suren on 17/7/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "fb_database_beta.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 8;

    private static Dao<Member,Integer> membersDAO = null;
    private static Dao<Memberships, Integer> membershipDAO = null;
    private static Dao<Notifications, Integer> notificationsDAO = null;
    private static Dao<Plans,Integer> plansDao= null;
    private static Dao<Payments,Integer> paymentsDao = null;
    private static Dao<Players,Integer> playersDao = null;
    private static Dao<Groups,Integer> groupsDao = null;

    public Dao<Groups, Integer> getGroupsDao() throws SQLException{
        if(groupsDao == null){
            groupsDao = getDao(Groups.class);
        }
        return groupsDao;
    }

    public Dao<Payments, Integer> getPaymentsDao() throws  SQLException{
        if(paymentsDao == null){
            paymentsDao = getDao(Payments.class);
        }
        return paymentsDao;
    }

    public Dao<Players,Integer> getPlayersDao() throws SQLException{
        if(playersDao == null){
            playersDao = getDao(Players.class);
        }
        return playersDao;
    }

    public Dao<Plans, Integer> getPlansDao() throws SQLException{
        if(plansDao == null){
            plansDao = getDao(Plans.class);
        }
        return plansDao;
    }
    public Dao<Member, Integer> getMembersDAO() throws SQLException{
        if(membersDAO == null){
            membersDAO = getDao(Member.class);
        }
        return membersDAO;
    }


    public  Dao<Memberships, Integer> getMembershipDAO() throws SQLException {
        if(membershipDAO == null){
            membershipDAO = getDao(Memberships.class);
        }
        return membershipDAO;
    }

    public Dao<Notifications, Integer> getNotificationsDAO() throws SQLException{
        if(notificationsDAO == null){
            notificationsDAO = getDao(Notifications.class);
        }
        return notificationsDAO;
    }



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource,Member.class);
            TableUtils.createTable(connectionSource,Memberships.class);
            TableUtils.createTable(connectionSource,Notifications.class);
            TableUtils.createTable(connectionSource,Plans.class);
            TableUtils.createTable(connectionSource,Payments.class);
            TableUtils.createTable(connectionSource,Players.class);
            TableUtils.createTable(connectionSource,Groups.class);
            populatePlans();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void populatePlans() throws SQLException {
        String[] plansArray = MainActivity.plansArray;
        for (int i = 0; i < plansArray.length; i++) {
            Plans plans = new Plans(plansArray[i],plansArray[i],plansArray[i]);
            getPlansDao().create(plans);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(MainActivity.LOG_TAG,"In Upgrade");
            TableUtils.dropTable(connectionSource,Member.class, true);
            TableUtils.dropTable(connectionSource,Memberships.class,true);
            TableUtils.dropTable(connectionSource, Notifications.class,true);
            TableUtils.dropTable(connectionSource, Plans.class,true);
            TableUtils.dropTable(connectionSource,Payments.class,true);
            TableUtils.dropTable(connectionSource,Players.class,true);
            TableUtils.dropTable(connectionSource,Groups.class,true);

            TableUtils.createTable(connectionSource,Member.class);
            TableUtils.createTable(connectionSource,Memberships.class);
            TableUtils.createTable(connectionSource,Notifications.class);
            TableUtils.createTable(connectionSource,Plans.class);
            TableUtils.createTable(connectionSource,Payments.class);
            TableUtils.createTable(connectionSource,Players.class);
            TableUtils.createTable(connectionSource,Groups.class);
            populatePlans();
            Log.i(MainActivity.LOG_TAG,"Table Upgraded");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
