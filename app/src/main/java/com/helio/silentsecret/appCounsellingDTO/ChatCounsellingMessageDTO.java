package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class ChatCounsellingMessageDTO
{
    String appointmentid = "";

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    String lastUpdatedDate = "";
    String reciever = "";


    public ChatCounsellingMessageDTO(String appointmentid, String lastUpdatedDate , String reciever)
    {
        super();
        this.appointmentid = appointmentid;
        this.lastUpdatedDate = lastUpdatedDate;
        this.reciever = reciever;

    }
}
