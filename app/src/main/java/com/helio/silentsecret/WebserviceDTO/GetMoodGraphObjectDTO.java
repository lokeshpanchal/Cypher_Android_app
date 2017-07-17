package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetMoodGraphObjectDTO {


    GetMoodgraphDTO requestData = null;

    public GetMoodGraphObjectDTO(GetMoodgraphDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
