package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SearchSecretDTO {

    String requestType = "";
    String apikey = "";
    SearchSecretConditionDTO condition;


    public SearchSecretDTO(SearchSecretConditionDTO data)
    {
        super();
        this.requestType = "searchSecret";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.condition = data;

    }
}
