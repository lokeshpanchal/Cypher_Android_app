package com.helio.cypher.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SecretPushNotificayionDTO {


    public String requestType = "";
    public String username = "";
    public String apikey = "";
    String secretid = "";


    public SecretPushNotificayionDTO(String requestType, String username, String secretid
            ) {
        super();

        this.requestType = requestType;
        this.username = username;
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.secretid = secretid;
    }
}
