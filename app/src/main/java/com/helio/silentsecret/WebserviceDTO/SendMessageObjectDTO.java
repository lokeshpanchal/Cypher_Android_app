package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendMessageObjectDTO {


    public SendMessageIfriendDTO getRequestData() {
        return requestData;
    }

    SendMessageIfriendDTO requestData = null;

    public SendMessageObjectDTO(SendMessageIfriendDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
