package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetPetInfoObjectDTO {


    SetPetInfoDTO requestData = null;

    public SetPetInfoObjectDTO(SetPetInfoDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
