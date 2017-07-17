package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetCommentConditionDTO {



    String secret_id = "";
    boolean flagged = false;


    public GetCommentConditionDTO(String secret_id )
    {
        super();

           this.secret_id = secret_id;
        this.flagged = false;

    }
}
