package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendCouponCodeObjectDTO {


    SendCouponDTO requestData = null;



    public SendCouponCodeObjectDTO(SendCouponDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
