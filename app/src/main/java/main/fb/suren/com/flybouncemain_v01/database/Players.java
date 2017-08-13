package main.fb.suren.com.flybouncemain_v01.database;

/**
 * Created by suren on 13/8/17.
 */

import java.util.Date;
import com.j256.ormlite.field.DatabaseField;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "players")
public class Players {

    @DatabaseField(generatedId = true)
    int id;

    @Column(length = 50, nullable = false)
    String membership_ID;

    @Column
    String player_id;

    @Column
    String player_name;

    @Column
    String group_id;
}
