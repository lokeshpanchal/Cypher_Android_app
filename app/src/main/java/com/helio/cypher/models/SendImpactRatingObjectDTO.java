package com.helio.cypher.models;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendImpactRatingObjectDTO {


    List <SendImpactRatingDTO> data = null;

    public String requestType = "";

    public String apikey = "";

    public SendImpactRatingObjectDTO(List<SendImpactRatingDTO>  requestData)
    {
        super();
        this.requestType = "submitImpactQuestions";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = requestData;

    }
}
