package com.helio.cypher.appCounsellingDTO;

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

    public ChatCounsellingMessageDTO(String appointmentid, String lastUpdatedDate)
    {
        super();
        this.appointmentid = appointmentid;
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
