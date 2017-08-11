package main.fb.suren.com.flybouncemain_v01.database;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import main.fb.suren.com.flybouncemain_v01.MainActivity;
import main.fb.suren.com.flybouncemain_v01.Utils;


/**
 * Created by suren on 17/7/17.
 */

@Entity(name = "memberships")
public class Memberships {

    //Utils myUtils = new Utils();
    String target_date_pattern;

    // id is generated by the database and set on the object automagically
    @DatabaseField(generatedId = true)
    int id;

    @Column(length = 50, nullable = false)
    String membership_ID;

    @Column(length = 50,nullable = false)
    String member_ID;

        @Column(length = 2,nullable = false)
    int start_time;

    @Column(length = 2, nullable = false)
    int court_number;

    @DatabaseField
    Date start_date;

    @DatabaseField
    Date end_date;

    @DatabaseField
    int renewal_count;

    @Column()
    String plan_name;

    @Column
    int duration_months;

    @Column
    String duration_string;

    @Column
    boolean membership_status;


    @DatabaseField(canBeNull = false, foreign = true)
    Notifications notifications;


/*    public Memberships(String memberID, String member_name, int mobile_number, Date startDate, Date endDate, int startTime, int courtNo, String targetDatePattern, Notifications notifications, int renewalCount) {

        this.targetDatePattern = targetDatePattern;
        this.member_name = member_name;
        this.mobile_number = mobile_number;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.courtNo = courtNo;
        this.memberID = memberID;
        this.notifications = notifications;
        this.renewalCount = renewalCount;

    }*/

    public Memberships(String membership_ID, String member_ID, int start_time, int court_number, Date start_date, Date end_date, int renewal_count, String plan_name, int duration_months, Notifications notifications, String duration_string, boolean membership_status) {
        this.membership_ID = membership_ID;
        this.member_ID = member_ID;
        this.start_time = start_time;
        this.court_number = court_number;
        this.start_date = start_date;
        this.end_date = end_date;
        this.renewal_count = renewal_count;
        this.plan_name = plan_name;
        this.duration_months = duration_months;
        this.notifications = notifications;
        this.duration_string = duration_string;
        this.membership_status = membership_status;
    }

    public Memberships() {

    }

    @Override
    public String toString() {
        return "Memberships{" +
                "id=" + id +
                ", membership_ID='" + membership_ID + '\'' +
                ", member_ID='" + member_ID + '\'' +
                ", start_time=" + start_time +
                ", court_number=" + court_number +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", renewal_count=" + renewal_count +
                ", plan_name='" + plan_name + '\'' +
                ", duration_months=" + duration_months +
                ", notifications=" + notifications +
                '}';
    }

    public Notifications getNotifications() {
        return notifications;
    }

    public String getMembership_ID() {
        return membership_ID;
    }

    public String getMember_ID() {
        return member_ID;
    }

    public int getStart_time() {
        return start_time;
    }

    public int getCourt_number() {
        return court_number;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public int getRenewal_count() {
        return renewal_count;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public int getDuration_months() {
        return duration_months;
    }

    public String getDuration_string() {
        return duration_string;
    }

    public boolean isMembership_status() {
        return membership_status;
    }
}
