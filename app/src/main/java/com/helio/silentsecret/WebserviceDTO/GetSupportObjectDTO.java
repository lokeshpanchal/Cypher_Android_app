package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetSupportObjectDTO {


    GetSupportListDTO requestData = null;

    public GetSupportObjectDTO(GetSupportListDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
