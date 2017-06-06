package com.helio.cypher.models;

import com.helio.cypher.WebserviceDTO.IfriendaDenyConditionDataDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IfriendRequestDTO {


    public String requestType = "";
    IfriendaDenyConditionDataDTO data;
    public String apikey = "";
    String _table = "";
    String _method = "";


    public IfriendRequestDTO(IfriendaDenyConditionDataDTO data , String table, String _method)
    {
        super();

        this.requestType = "searchFriendList";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;
        this._table = table;
        this._method = _method;

    }
}
