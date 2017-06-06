package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SeenbyDTO {


     String requestType = "";
     String apikey = "";
    SeenbyConditionDTO condition ;



    public SeenbyDTO(SeenbyConditionDTO data)
    {
        super();

        this.requestType = "seenByComment";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.condition = data;


    }
}
