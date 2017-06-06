package com.helio.cypher.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetChatObjectDTO {


    public ChatMessageReqDTO getRequestData() {
        return requestData;
    }

    ChatMessageReqDTO requestData = null;



    public GetChatObjectDTO(ChatMessageReqDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
