package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class RedeemCounsellingCodeDTO {


     String requestType = "";
     String apikey = "";

    RedeemCounsellingCodeeDataDTO  data ;



    public RedeemCounsellingCodeDTO(RedeemCounsellingCodeeDataDTO data)
    {
        super();
        this.requestType = "redeemcode";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;
    }
}
