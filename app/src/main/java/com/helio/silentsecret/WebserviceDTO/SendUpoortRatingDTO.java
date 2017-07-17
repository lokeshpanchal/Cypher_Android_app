package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendUpoortRatingDTO {


     String requestType = "";
     String apikey = "";



    SeenSupportRatingDataDTO data ;


    public SendUpoortRatingDTO(SeenSupportRatingDataDTO condition, String requestType)
    {
        super();
        this.requestType = requestType;
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = condition;

    }
}
