package main.fb.suren.com.flybouncemain_v01.Adapters;

import main.fb.suren.com.flybouncemain_v01.Models.Member;
import main.fb.suren.com.flybouncemain_v01.Models.Memberships;

/**
 * Created by suren on 10/8/17.
 */

public class MembersAdapterDTO {
    private Memberships memberships;
    private Member members;
    private boolean hasMembership;

    public MembersAdapterDTO(Member members, Memberships memberships, boolean hasMembership){
        this.memberships = memberships;
        this.members = members;
        this.hasMembership = hasMembership;
    }


    public MembersAdapterDTO(Member members, boolean hasMembership){
        this.members = members;
        this.hasMembership = hasMembership;
    }

    public Memberships getMemberships() {
        return memberships;
    }

    public Member getMembers() {
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
