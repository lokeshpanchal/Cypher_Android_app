package com.helio.cypher.models;

/*import com.parse.ParseObject;
import com.parse.ParseUser;*/

public class Invite {

/*    private ParseUser user;
    private ParseUser invitee;*/
    private String name;
    private String phoneNo;

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    private String unique_id;
   /* private ParseObject object;

    public ParseUser getUser() {
        return user;
    }

    public void setUser(ParseUser user) {
        this.user = user;
    }

    public ParseUser getInvitee() {
        return invitee;
    }

    public void setInvitee(ParseUser invitee) {
        this.invitee = invitee;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /*public ParseObject getObject() {
        return object;
    }

    public void setObject(ParseObject object) {
        this.object = object;
    }*/
}
