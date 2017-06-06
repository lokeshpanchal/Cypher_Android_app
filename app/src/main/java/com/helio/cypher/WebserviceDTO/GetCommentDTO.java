package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetCommentDTO {


     String requestType = "";
     String apikey = "";
    String _table = "";


    GetCommentConditionDTO condition ;


    public GetCommentDTO( GetCommentConditionDTO condition)
    {
        super();

        this.requestType = "getConditionalData";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this._table = "aKm0UflPao/2b8+S+zY6Ig==";
        this.condition = condition;

    }
}
