package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetDateOfBirthDTO {


     String requestType = "";
     String apikey = "";



    SetDatetDataDTO data ;


    public SetDateOfBirthDTO(SetDatetDataDTO condition)
    {
        super();
        this.requestType = "setDOB";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = condition;

    }
}
