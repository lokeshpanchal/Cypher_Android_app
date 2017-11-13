package com.helio.silentsecret.models;

import java.util.Date;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class DailyEmotionList {


    public String getEmoji_name() {
        return emoji_name;
    }

    String emoji_name = "";

    public Date getCreated_at() {
        return created_at;
    }


    Date created_at = null;




    public DailyEmotionList(String emoji_name,Date created_at)
    {
        super();

        this.emoji_name = emoji_name;
        this.created_at = created_at;


    }
}
