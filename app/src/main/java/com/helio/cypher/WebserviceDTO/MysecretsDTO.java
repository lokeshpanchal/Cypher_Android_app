package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class MysecretsDTO {


     String requestType = "";
     String apikey = "";

    String _method = "";

    MysecretConditionDTO condition ;


    public MysecretsDTO( String _method  , MysecretConditionDTO condition)
    {
        super();

        this.requestType = "getSecret";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this._method = _method;
        this.condition = condition;

    }
}
