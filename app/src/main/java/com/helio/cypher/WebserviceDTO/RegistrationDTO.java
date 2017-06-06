package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class RegistrationDTO {


     String requestType = "";
     String apikey = "";
    String _table = "";
    String _method = "";
    RegistrationDataDTO data ;



    public RegistrationDTO(RegistrationDataDTO data, String _table, String _method)
    {
        super();

        this.requestType = "registration";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this._table = _table;
        this._method = _method;
        this.data = data;


    }
}
