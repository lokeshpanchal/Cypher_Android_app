package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetUnreadMessageDataDTO {



    String receiver = "";
    String sender = "";


    public GetUnreadMessageDataDTO(String secret_id ,String sender)
    {
        super();

           this.receiver = secret_id;
            this.sender = sender;

    }
}
