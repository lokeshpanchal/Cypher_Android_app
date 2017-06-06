package com.helio.cypher.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetCurrentDateObjectDTO {


     GetCurrentDateTimeDTO requestData = null;



    public GetCurrentDateObjectDTO(GetCurrentDateTimeDTO  requestData)
    {
        super();

        this.requestData = requestData;


    }
}
