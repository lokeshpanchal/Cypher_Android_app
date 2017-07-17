package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetMyrecievedDTO {


     String requestType = "";
     String apikey = "";
    FindbyNameDTO condition ;

    public GetMyrecievedDTO( FindbyNameDTO condition)
    {
        super();
        this.requestType = "getReceivedCount";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.condition = condition;
    }
}
