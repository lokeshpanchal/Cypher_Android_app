package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetFlagUnFlagCommentDataDTO {



    String clun01 = "";
String comment_id = "";

boolean is_flagged ;

    public SetFlagUnFlagCommentDataDTO(String clun01  , boolean flagged, String comment_id)
    {
        super();

        this.clun01 = clun01;
        this.is_flagged = flagged;
        this.comment_id = comment_id;

    }
}
