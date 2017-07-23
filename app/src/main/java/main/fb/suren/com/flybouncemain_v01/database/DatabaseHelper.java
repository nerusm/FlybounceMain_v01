package main.fb.suren.com.flybouncemain_v01.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import main.fb.suren.com.flybouncemain_v01.MainActivity;

/**
 * Created by suren on 17/7/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "fb_database_beta.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    private static Dao<Member, Integer> memberDAO = null;

    public  Dao<Member, Integer> getMemberDAO() throws SQLException {
        if(memberDAO == null){
            memberDAO = getDao(Member.class);
        }
        return memberDAO;
    }



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource,Member.class);
            Log.i(MainActivity.LOG_TAG,"Table Created");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(MainActivity.LOG_TAG,"In Upgrade");
            TableUtils.createTable(connectionSource,Member.class);
            Log.i(MainActivity.LOG_TAG,"Table Upgraded");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
