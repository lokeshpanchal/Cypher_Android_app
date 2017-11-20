package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetMediatorMessageReqDTO {


     String requestType = "";
     String apikey = "";

    public GetMediatorMessageDTO getData() {
        return data;
    }

    GetMediatorMessageDTO data ;
    public GetMediatorMessageReqDTO(GetMediatorMessageDTO condition)
    {
        super();

        this.requestType = "getMessageMediator";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = condition;

    }
}
