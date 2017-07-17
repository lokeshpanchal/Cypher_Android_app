package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class ScoolratingDTO {


     String requestType = "";
     String apikey = "";
    String _table = "";
    SchoolRatingConditionDTO  condition ;



    public ScoolratingDTO(SchoolRatingConditionDTO data)
    {
        super();

        this.requestType = "add";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this._table = "E9Z1frpSinxc/PMTIG98Aw==";
        this.condition = data;


    }
}
