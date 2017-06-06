package com.helio.cypher.StaticObjectDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IFriendSettingDTO {


     String requestduration = "";

    String maxfriend = "";

    public String getSafeguard_update_date() {
        return safeguard_update_date;
    }

    String safeguard_update_date = "";

    public String getSession_duration() {
        return session_duration;
    }

    public String getRequestduration() {
        return requestduration;
    }

    public String getMaxfriend() {
        return maxfriend;
    }

    String session_duration = "";




    public IFriendSettingDTO(String requestduration, String  maxfriend, String session_duration, String safeguard_update_date)
    {
        super();

        this.requestduration = requestduration;
        this.maxfriend = maxfriend;
        this.session_duration = session_duration;
        this.safeguard_update_date= safeguard_update_date;



    }
}
