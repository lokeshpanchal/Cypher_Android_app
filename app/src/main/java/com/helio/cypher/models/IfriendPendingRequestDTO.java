package com.helio.cypher.models;

import java.util.Date;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IfriendPendingRequestDTO {


     String sender = "";
     String target = "";
     String status = "";

    public Date getCreatedAt() {
        return CreatedAt;
    }

    Date CreatedAt ;
    String sender_lng = "";
    String sender_lat = "";

    public String getRequestid() {
        return requestid;
    }

    String requestid = "";


    public String getSender_lat() {
        return sender_lat;
    }

    public String getSender() {
        return sender;
    }

    public String getTarget() {
        return target;
    }

    public String getStatus() {
        return status;
    }

    public String getSender_lng() {
        return sender_lng;
    }




    public IfriendPendingRequestDTO(String sender, String target,String status,String sender_lat, String  sender_lng,Date CreatedAt,String requestid)
    {
        super();

        this.sender = sender;
        this.target = target;
        this.status = status;
        this.sender_lng = sender_lng;
        this.sender_lat = sender_lat;
        this.CreatedAt = CreatedAt;
        this.requestid = requestid;


    }
}
