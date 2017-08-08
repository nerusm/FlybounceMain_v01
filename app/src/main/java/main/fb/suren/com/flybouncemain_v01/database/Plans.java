package main.fb.suren.com.flybouncemain_v01.database;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by suren on 8/8/17.
 */

@Entity(name = "plans")
public class Plans {

    @Column
    String plan_name;

    @Column
    String plan_id;

    @Column
    String plan_desc;

    public Plans(String plan_name, String plan_id, String plan_desc) {
        this.plan_name = plan_name;
        this.plan_id = plan_id;
        this.plan_desc = plan_desc;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public String getPlan_desc() {
        return plan_desc;
    }

    public Plans() {
    }
}
