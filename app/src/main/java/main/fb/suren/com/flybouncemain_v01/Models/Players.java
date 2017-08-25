package main.fb.suren.com.flybouncemain_v01.Models;

/**
 * Created by suren on 13/8/17.
 */

import java.util.Date;
import com.j256.ormlite.field.DatabaseField;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "players")
public class Players {

    @DatabaseField(generatedId = true)
    int id;

    @Column(length = 50, nullable = true)
    String membership_ID;

    @Column
    String player_id;

    @Column
    String player_name;

    @DatabaseField
    String group_id;

    public Players(String membership_ID, String player_id, String player_name, String group_id) {
        this.membership_ID = membership_ID;
        this.player_id = player_id;
        this.player_name = player_name;
        this.group_id = group_id;
    }

    public Players() {
    }

    public String getMembership_ID() {
        return membership_ID;
    }

    public void setMembership_ID(String membership_ID) {
        this.membership_ID = membership_ID;
    }

    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
