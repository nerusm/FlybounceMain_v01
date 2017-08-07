package main.fb.suren.com.flybouncemain_v01.database;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import main.fb.suren.com.flybouncemain_v01.MainActivity;
import main.fb.suren.com.flybouncemain_v01.Utils;

/**
 * Created by suren on 5/8/17.
 */

@Entity(name = "notifications")
public class Notifications {

    // id is generated by the database and set on the object automagically
    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    @Column(length = 50, nullable = false)
    String memberID;

    @DatabaseField
    Date notificationDate;


    @DatabaseField
    int noOfNotiticationsSent;


    @DatabaseField
    boolean sendNotification;



    public Notifications(String memberID, Date notificationDate, int noOfNotiticationsSent, boolean sendNotification) {
        this.memberID = memberID;
        this.notificationDate = notificationDate;
        this.noOfNotiticationsSent = noOfNotiticationsSent;
        this.sendNotification = sendNotification;
    }

    @Override
    public String toString() {
        return "Notifications{" +
                "id=" + id +
                ", memberID='" + memberID + '\'' +
                ", notificationDate=" + notificationDate +
                '}';
    }

    public Notifications() {
    }

    public int getId() {
        return id;
    }

    public String getMemberID() {
        return memberID;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public int getNoOfNotiticationsSent() {
        return noOfNotiticationsSent;
    }

    public boolean isSendNotification() {
        return sendNotification;
    }
}
