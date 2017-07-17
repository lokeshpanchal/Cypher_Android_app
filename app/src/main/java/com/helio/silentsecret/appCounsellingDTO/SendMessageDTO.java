package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendMessageDTO {


     String requestType = "";
     String apikey = "";



    public SendMessageDataDTO getData() {
        return data;
    }

    SendMessageDataDTO data ;


    public SendMessageDTO(SendMessageDataDTO condition)
    {
        super();

        this.requestType = "sendMessage";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = condition;

    }
}
