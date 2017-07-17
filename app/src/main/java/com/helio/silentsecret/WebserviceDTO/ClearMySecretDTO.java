package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class ClearMySecretDTO {

    String requestType = "";
    String apikey = "";
    ClearMySecretDataDTO condition;


    public ClearMySecretDTO(ClearMySecretDataDTO data)
    {
        super();
        this.requestType = "clearSecret";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.condition = data;

    }
}
