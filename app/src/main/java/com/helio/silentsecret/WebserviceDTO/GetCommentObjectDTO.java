package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetCommentObjectDTO {


    GetCommentDTO requestData = null;

    public GetCommentObjectDTO(GetCommentDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
