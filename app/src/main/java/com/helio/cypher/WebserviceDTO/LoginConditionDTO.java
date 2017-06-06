package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class LoginConditionDTO {



    String clun01 = "";
    String pin = "";

    public LoginConditionDTO(String username , String pin)
    {
        super();

           this.clun01 = username;

        this.pin = pin;

    }
}
