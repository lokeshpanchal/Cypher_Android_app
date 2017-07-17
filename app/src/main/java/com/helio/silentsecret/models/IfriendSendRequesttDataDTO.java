package com.helio.silentsecret.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IfriendSendRequesttDataDTO {



    String sender = "";
    String  target = "";
    String sender_lat ="";
    String sender_long = "";
    String status =  "";


    public IfriendSendRequesttDataDTO( String sender, String target, String sender_lat, String sender_lng )
    {
        super();

              this.sender = sender;
        this.target = target;
        this.sender_lat = sender_lat;
        this.sender_long = sender_lng;
        this.status = "waiting";
    }
}
