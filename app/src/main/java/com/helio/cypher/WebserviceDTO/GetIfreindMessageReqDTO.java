package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetIfreindMessageReqDTO {


     String requestType = "";
     String apikey = "";

    public GetifriendMessageDTO getData() {
        return data;
    }

    GetifriendMessageDTO data ;
    public GetIfreindMessageReqDTO(GetifriendMessageDTO condition)
    {
        super();

        this.requestType = "getIfriendChat";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = condition;

    }
}
