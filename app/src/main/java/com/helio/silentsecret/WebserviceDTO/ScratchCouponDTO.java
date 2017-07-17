package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class ScratchCouponDTO {


     String requestType = "";
     String apikey = "";
    ScratchCoupondataDTO data ;



    public ScratchCouponDTO(ScratchCoupondataDTO data)
    {
        super();

        this.requestType = "updatePercentagePetAvatar";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;


    }
}
