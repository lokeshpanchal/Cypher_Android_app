package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetFlagSecretDTO {


     String requestType = "";
     String apikey = "";



    SetFlagUnFlagSecretDataDTO data ;


    public SetFlagSecretDTO(SetFlagUnFlagSecretDataDTO condition)
    {
        super();
        this.requestType = "flagSecret";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = condition;

    }
}
