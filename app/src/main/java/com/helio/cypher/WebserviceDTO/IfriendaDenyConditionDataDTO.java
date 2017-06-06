package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IfriendaDenyConditionDataDTO {



    String clun01 = "";
    boolean is_deleted;


    public IfriendaDenyConditionDataDTO(String username )
    {
        super();

           this.clun01 = username;
        this.is_deleted = false;

    }
}
