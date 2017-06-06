package com.helio.cypher.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IfriendAcceptRequesttDataDTO {



    String sender = "";
    String  username = "";
    String sender_lat ="";
    String sender_lng = "";
    String user_lat = "";
    String user_lng = "";
    String user_profilepic = "";


    public IfriendAcceptRequesttDataDTO(String username, String sender, String sender_lat, String  sender_lng,
                                        String user_lat,String user_lng  ,String user_profilepic)
    {
        super();

        this.sender = sender;
        this.username = username;
        this.sender_lat = sender_lat;
        this.sender_lng = sender_lng;
        this.user_lat = user_lat;
        this.user_lng = user_lng;
        this.user_profilepic = user_profilepic;
    }
}
