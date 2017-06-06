package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetUnreadMessageObjectDTO {


    GetUnreadtChatMessageDTO requestData = null;

    public GetUnreadMessageObjectDTO(GetUnreadtChatMessageDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
