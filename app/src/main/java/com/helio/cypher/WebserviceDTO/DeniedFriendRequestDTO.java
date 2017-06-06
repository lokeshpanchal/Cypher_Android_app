package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class DeniedFriendRequestDTO {



    String sender = "";
    String target = "";


    public DeniedFriendRequestDTO(String sender ,String target)
    {
        super();

           this.sender = sender;
        this.target = target;

    }
}
