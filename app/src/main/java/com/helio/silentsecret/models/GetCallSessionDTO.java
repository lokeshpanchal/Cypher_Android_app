package com.helio.silentsecret.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetCallSessionDTO {


     String requestType = "";
     String apikey = "";
    String appointmentid = "";
    String counsellorid = "";
    String userid = "";


    public GetCallSessionDTO(String requestType, String appointmentid, String sender, String reciever)
    {
        super();
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.requestType = requestType;
        this.appointmentid = appointmentid;
        this.counsellorid = sender;
        this.userid = reciever;

    }
}
