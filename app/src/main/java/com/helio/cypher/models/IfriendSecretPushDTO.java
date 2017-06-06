package com.helio.cypher.models;

import com.helio.cypher.WebserviceDTO.IfriendSecretpushNotiDataDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IfriendSecretPushDTO {


    public String requestType = "";

    public String apikey = "";

    IfriendSecretpushNotiDataDTO data;



    public IfriendSecretPushDTO(IfriendSecretpushNotiDataDTO data)
    {
        super();

        this.requestType = "IFirendShareSecretNotification";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;

    }
}
