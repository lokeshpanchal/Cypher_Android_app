package com.helio.silentsecret.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IfriendRemovehDTO {


     String requestType = "";
     String apikey = "";
     String friend = "";
      String username = "";



    public IfriendRemovehDTO(String requestType, String username , String friend )
    {
        super();

        this.requestType = requestType;
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.username = username;
        this.friend = friend;
    }
}
