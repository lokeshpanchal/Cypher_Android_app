package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetPendingFriendObjectDTO {


    GetPendingFriendDTO requestData = null;

    public GetPendingFriendObjectDTO(GetPendingFriendDTO  requestData)
    {
        super();
        this.requestData = requestData;
    }
}
