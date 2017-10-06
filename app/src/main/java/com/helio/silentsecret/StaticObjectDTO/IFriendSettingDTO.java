package com.helio.silentsecret.StaticObjectDTO;

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

    public String getQuotes() {
        return quotes;
    }

    public String getAuthor_name() {
        return author_name;
    }

    String quotes = "", author_name = "";

    public String getBanned_word() {
        return banned_word;
    }

    public String getDefault_word() {
        return default_word;
    }

    String banned_word = "", default_word = "";

    public IFriendSettingDTO(String requestduration, String  maxfriend, String session_duration, String safeguard_update_date , String quotes , String author_name , String banned_word , String default_word)
    {
        super();

        this.requestduration = requestduration;
        this.maxfriend = maxfriend;
        this.session_duration = session_duration;
        this.safeguard_update_date= safeguard_update_date;
        this.quotes = quotes;
        this.author_name = author_name;
        this.banned_word = banned_word;
        this.default_word = default_word;


    }
}
