package main.fb.suren.com.flybouncemain_v01.database;

/**
 * Created by suren on 13/8/17.
 */

import com.j256.ormlite.field.DatabaseField;
import javax.persistence.Column;
import javax.persistence.Entity;

public class Groups {
    @DatabaseField(generatedId = true)
    int id;

    @Column
    String group_id;

    @Column
    String group_name;

    @Column
    int no_of_players;
}
