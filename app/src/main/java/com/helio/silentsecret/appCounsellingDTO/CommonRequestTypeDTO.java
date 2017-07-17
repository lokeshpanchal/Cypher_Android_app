package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class CommonRequestTypeDTO {


     String requestType = "";
     String apikey = "";

    Object    data ;



    public CommonRequestTypeDTO(Object data,String req_type)
    {
        super();
        this.requestType = req_type;
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this.data = data;
    }
}
