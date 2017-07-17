package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetAllNotificationjListDTO {

    String requestType = "";
    String apikey = "";
    FindbyNameDTO data;


    public GetAllNotificationjListDTO( FindbyNameDTO data)
    {
        super();
        this.requestType = "getAllNotifications";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;

    }
}
