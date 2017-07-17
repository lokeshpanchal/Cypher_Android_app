package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetVerifyFlagConditionDTO {


    String clun01 = "";
    String type = "";
    String value = "";


    public SetVerifyFlagConditionDTO(String username, String type,String value) {
        super();

        this.clun01 = username;
        this.type = type;
        this.value = value;

    }
}
