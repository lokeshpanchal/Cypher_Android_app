package com.helio.silentsecret.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class MeetingDetail implements Serializable {



    public String getLang() {
        return lang;
    }

    public String getLat() {
        return lat;
    }

    public String getClmtngtime01() {
        return clmtngtime01;
    }

    public String getClmtngtitle01() {
        return clmtngtitle01;
    }

    public String getClmediatorun01() {
        return clmediatorun01;
    }

    public String getAddress() {
        return address;
    }

    public String getMeeting_unq_id() {
        return meeting_unq_id;
    }

    public List<String> getClmtngnotemood01() {
        return clmtngnotemood01;
    }

    public List<String> getClmtngnote01() {
        return clmtngnote01;
    }

    public String  lat;
    public String lang;
    public String clmtngtime01 = "";
    public String clmtngtitle01 = "";
    public String clmediatorun01 = "";
    public String address = "";
    public String meeting_unq_id = "";
    public List<String> clmtngnotemood01 = null;
    public List<String> clmtngnote01 = null;


    public MeetingDetail()
    {

    }




    public MeetingDetail(String lat , String lang , String clmtngtime01 , String clmtngtitle01, String clmediatorun01, String address
            , String meeting_unq_id, List<String> clmtngnotemood01, List<String> clmtngnote01
    )
    {
        super();
        this.lat = lat;
        this.lang = lang;
        this.clmtngtime01 = clmtngtime01;
        this.clmtngtitle01 = clmtngtitle01;
        this.clmediatorun01 = clmediatorun01;
        this.address = address;
        this.meeting_unq_id = meeting_unq_id;
        this.clmtngnotemood01 = clmtngnotemood01;
        this.clmtngnote01 = clmtngnote01;

    }
}
