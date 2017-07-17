package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class LoginDTO {


     String requestType = "";
     String apikey = "";
    String _table = "";
    String _method = "";

    LoginConditionDTO  condition ;


    public LoginDTO( LoginConditionDTO condition,String _table,String _method)
    {
        super();

        this.requestType = "getConditionalData";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";

        this._table = _table;
        this._method = _method;
        this.condition = condition;


    }
}
