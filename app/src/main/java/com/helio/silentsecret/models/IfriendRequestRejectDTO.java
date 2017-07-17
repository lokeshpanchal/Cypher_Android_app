package com.helio.silentsecret.models;

import com.helio.silentsecret.WebserviceDTO.DeniedFriendRequestDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IfriendRequestRejectDTO {


    public String requestType = "";

    public String apikey = "";
    String _method = "";
    String  _table = "";

DeniedFriendRequestDTO data ;

    public IfriendRequestRejectDTO(DeniedFriendRequestDTO data, String _table ,String _method, String requestType)
    {
        super();

        this.requestType = requestType;
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this._method = _method;
        this._table = _table;
        this.data = data;
    }
}
