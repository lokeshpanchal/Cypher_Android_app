package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetFlagCommentDTO {


     String requestType = "";
     String apikey = "";



    SetFlagUnFlagCommentDataDTO data ;


    public SetFlagCommentDTO(SetFlagUnFlagCommentDataDTO condition)
    {
        super();
        this.requestType = "flagComment";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = condition;

    }
}
