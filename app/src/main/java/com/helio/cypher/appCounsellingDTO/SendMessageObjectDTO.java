package com.helio.cypher.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendMessageObjectDTO {


    public SendMessageDTO getRequestData() {
        return requestData;
    }

    SendMessageDTO requestData = null;

    public SendMessageObjectDTO( SendMessageDTO requestData)
    {
        super();
        this.requestData = requestData;
    }
}
