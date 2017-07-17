package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendCouponDTO {


     String requestType = "";
     String apikey = "";
    SeenbyCouponCodeDTO data ;



    public SendCouponDTO(SeenbyCouponCodeDTO data)
    {
        super();

        this.requestType = "sendCouponCode";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;


    }
}
