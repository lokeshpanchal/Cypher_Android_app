package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetLastPostdateDTO {

    String requestType = "";
    String apikey = "";
    FindbyCreateSecUserDTO data;


    public GetLastPostdateDTO(FindbyCreateSecUserDTO data)
    {
        super();
        this.requestType = "getLastPostDate";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;

    }
}
