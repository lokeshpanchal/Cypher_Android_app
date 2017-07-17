package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class CheckUserSessionDTO {


     String requestType = "";
     String apikey = "";

    CheckUserSessionDataDTO  data ;



    public CheckUserSessionDTO(CheckUserSessionDataDTO data)
    {
        super();
        this.requestType = "checkUserSession";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;
    }
}
