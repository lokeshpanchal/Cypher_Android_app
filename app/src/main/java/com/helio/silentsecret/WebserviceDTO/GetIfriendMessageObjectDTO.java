package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetIfriendMessageObjectDTO {


    public GetIfreindMessageReqDTO getRequestData() {
        return requestData;
    }

    GetIfreindMessageReqDTO requestData = null;

    public GetIfriendMessageObjectDTO(GetIfreindMessageReqDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
