package com.helio.cypher.appCounsellingDTO;



/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class ChatMessageReqDTO {


     String requestType = "";
     String apikey = "";


    public ChatCounsellingMessageDTO getData() {
        return data;
    }

    ChatCounsellingMessageDTO data;


    public ChatMessageReqDTO(String requestType, ChatCounsellingMessageDTO data)
    {
        super();

        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.requestType = requestType;
        this.data = data;



    }
}
