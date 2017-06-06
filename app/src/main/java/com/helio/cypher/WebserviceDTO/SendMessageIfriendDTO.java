package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendMessageIfriendDTO {


     String requestType = "";
     String apikey = "";
    String _table = "";
    String _method = "";


    public SendMessagetofriedDataDTO getData() {
        return data;
    }

    SendMessagetofriedDataDTO data ;


    public SendMessageIfriendDTO(SendMessagetofriedDataDTO condition)
    {
        super();

        this.requestType = "sendIfriendChat";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this._table = "IMRReLTuwXMYGJ2pHU2kcw==";
        this._method = "fXdCaUENcBum40zO41PVug==";
        this.data = condition;

    }
}
