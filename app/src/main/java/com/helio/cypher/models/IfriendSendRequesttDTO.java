package com.helio.cypher.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IfriendSendRequesttDTO {


    public String requestType = "";
    public String apikey = "";
    IfriendSendRequesttDataDTO data;


    public IfriendSendRequesttDTO(IfriendSendRequesttDataDTO data) {
        super();

        this.requestType = "sendFriendRequest";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;
    }
}
