package com.helio.cypher.models;

import com.helio.cypher.WebserviceDTO.GetPetConditionDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class AllIfriendDTO {


     String requestType = "";
     String apikey = "";

GetPetConditionDTO data ;


    public AllIfriendDTO(GetPetConditionDTO data)
    {
        super();

        this.requestType = "getAllfriendrequest";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;


    }
}
