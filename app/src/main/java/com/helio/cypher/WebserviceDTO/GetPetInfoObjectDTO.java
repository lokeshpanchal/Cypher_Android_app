package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetPetInfoObjectDTO {


    GetPetInfoDTO requestData = null;

    public GetPetInfoObjectDTO(GetPetInfoDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}