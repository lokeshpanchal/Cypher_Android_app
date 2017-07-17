package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetVerifyFlagDTO {


     String requestType = "";
     String apikey = "";

    SetVerifyFlagConditionDTO data ;


    public SetVerifyFlagDTO(SetVerifyFlagConditionDTO condition)
    {
        super();

        this.requestType = "verifyUser";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = condition;

    }
}
