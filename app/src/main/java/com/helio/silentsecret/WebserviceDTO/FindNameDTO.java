package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class FindNameDTO {


     String requestType = "";
     String apikey = "";

    FindbyNameDTO data ;


    public FindNameDTO(String _table, String _method , FindbyNameDTO condition)
    {
        super();

        this.requestType = "checkUserVerify";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";

        this.data = condition;



    }
}
