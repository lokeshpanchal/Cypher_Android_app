package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetSecretUserInfoDTO {

    String requestType = "";
    String apikey = "";
    FindbyNameDTO data;


    public GetSecretUserInfoDTO(FindbyNameDTO data)
    {
        super();
        this.requestType = "getUserSecretData";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;

    }
}
