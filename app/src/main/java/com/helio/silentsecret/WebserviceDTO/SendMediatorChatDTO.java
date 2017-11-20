package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendMediatorChatDTO {


     String requestType = "";
     String apikey = "";
    String _table = "";
    String _method = "";


    public SendMediatorChatDataDTO getData() {
        return data;
    }

    SendMediatorChatDataDTO data ;


    public SendMediatorChatDTO(SendMediatorChatDataDTO condition)
    {
        super();

        this.requestType = "sendMediatorChat";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = condition;

    }
}
