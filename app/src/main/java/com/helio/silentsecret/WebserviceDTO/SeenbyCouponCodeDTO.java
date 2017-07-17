package com.helio.silentsecret.WebserviceDTO;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SeenbyCouponCodeDTO {



    String code = "";
    List<String> users ;


    public SeenbyCouponCodeDTO(String code , List<String> users )
    {
        super();

           this.code = code;
        this.users = users;

    }
}
