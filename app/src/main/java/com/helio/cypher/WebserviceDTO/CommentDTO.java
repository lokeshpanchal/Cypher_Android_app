package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class CommentDTO {


     String requestType = "";
     String apikey = "";

    String _method = "";
    CommentConditionDTO  condition ;



    public CommentDTO(CommentConditionDTO data, String _method)
    {
        super();

        this.requestType = "secretAction";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this._method = _method;
        this.condition = data;


    }
}
