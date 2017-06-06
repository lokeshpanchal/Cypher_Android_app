package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetSecretUserinfoObjectDTO {


    GetSecretUserInfoDTO requestData = null;

    public GetSecretUserinfoObjectDTO(GetSecretUserInfoDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
