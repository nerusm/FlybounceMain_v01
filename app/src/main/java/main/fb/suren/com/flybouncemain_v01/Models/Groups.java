package main.fb.suren.com.flybouncemain_v01.Models;

/**
 * Created by suren on 13/8/17.
 */

import com.j256.ormlite.field.DatabaseField;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "groups")
public class Groups {
    @DatabaseField(generatedId = true)
    int id;

    @Column
    String group_id;

    @Column
    String group_name;

    @Column
    int no_of_players;

    @DatabaseField(foreign = true)
    Players players;

    public Groups(String group_id, String group_name, int no_of_players, Players players) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.no_of_players = no_of_players;
        this.players = players;
    }

    public Groups() {
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getNo_of_players() {
        return no_of_players;
    }

    public void setNo_of_players(int no_of_players) {
        this.no_of_players = no_of_players;
    }

    public Players getPlayers() {
        return players;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }
}
