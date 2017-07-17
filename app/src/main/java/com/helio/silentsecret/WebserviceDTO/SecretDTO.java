package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SecretDTO {


     String requestType = "";
     String apikey = "";
    SecretDataDTO data ;



    public SecretDTO(SecretDataDTO data, String _table, String _method)
    {
        super();

        this.requestType = "addSecret";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;


    }
}
