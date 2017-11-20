package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetMediatorMessageObjectDTO {


    public GetMediatorMessageReqDTO getRequestData() {
        return requestData;
    }

    GetMediatorMessageReqDTO requestData = null;

    public GetMediatorMessageObjectDTO(GetMediatorMessageReqDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
