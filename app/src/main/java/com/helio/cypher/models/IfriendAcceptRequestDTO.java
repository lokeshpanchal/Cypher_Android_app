package com.helio.cypher.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IfriendAcceptRequestDTO {


     String requestType = "";
     String apikey = "";
    IfriendAcceptRequesttDataDTO data ;




    public IfriendAcceptRequestDTO(IfriendAcceptRequesttDataDTO ifriendAcceptRequesttDataDTO )
    {
        super();

        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.requestType = "acceptIfriendRequest";
        this.data = ifriendAcceptRequesttDataDTO;

    }
}
