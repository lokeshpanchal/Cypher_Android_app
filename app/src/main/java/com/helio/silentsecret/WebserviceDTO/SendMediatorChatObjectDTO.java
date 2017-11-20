package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendMediatorChatObjectDTO {


    public SendMediatorChatDTO getRequestData() {
        return requestData;
    }

    SendMediatorChatDTO requestData = null;

    public SendMediatorChatObjectDTO(SendMediatorChatDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
