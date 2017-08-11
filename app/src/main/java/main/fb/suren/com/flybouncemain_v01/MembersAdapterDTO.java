package main.fb.suren.com.flybouncemain_v01;

import main.fb.suren.com.flybouncemain_v01.database.Members;
import main.fb.suren.com.flybouncemain_v01.database.Memberships;

/**
 * Created by suren on 10/8/17.
 */

public class MembersAdapterDTO {
    private Memberships memberships;
    private Members members;
    private boolean hasMembership;

    public MembersAdapterDTO(Members members, Memberships memberships, boolean hasMembership){
        this.memberships = memberships;
        this.members = members;
        this.hasMembership = hasMembership;
    }


    public MembersAdapterDTO(Members members, boolean hasMembership){
        this.members = members;
        this.hasMembership = hasMembership;
    }

    public Memberships getMemberships() {
        return memberships;
    }

    public Members getMembers() {
        return members;
    }

    public boolean isHasMembership() {
        return hasMembership;
    }

    @Override
    public String toString() {
        return members.getMember_name().toString();
    }
}
