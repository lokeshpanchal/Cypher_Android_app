package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetSecretbyidDTO {

    String requestType = "";
    String apikey = "";
    GetSecretbyIdDataDTO data;


    public GetSecretbyidDTO(GetSecretbyIdDataDTO data)
    {
        super();
        this.requestType = "getSecretById";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;

    }
}
