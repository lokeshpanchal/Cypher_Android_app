package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetSecretbyidObjectDTO {


    GetSecretbyidDTO requestData = null;

    public GetSecretbyidObjectDTO(GetSecretbyidDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
