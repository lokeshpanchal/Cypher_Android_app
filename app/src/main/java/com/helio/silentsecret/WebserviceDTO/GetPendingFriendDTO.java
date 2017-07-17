package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetPendingFriendDTO {


     String requestType = "";
     String apikey = "";
    String _table = "";
    String _method = "";


    GetPetConditionDTO data ;


    public GetPendingFriendDTO(GetPetConditionDTO condition)
    {
        super();

        this.requestType = "getPendingFriendRequest";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this._table = "IMRReLTuwXMYGJ2pHU2kcw==";
        this._method = "fXdCaUENcBum40zO41PVug==";
        this.data = condition;

    }
}
