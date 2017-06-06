package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetPetInfoDTO {


     String requestType = "";
     String apikey = "";

    SetPetConditionDTO data ;


    public SetPetInfoDTO( SetPetConditionDTO condition)
    {
        super();

        this.requestType = "setVirtualPet";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = condition;

    }
}
