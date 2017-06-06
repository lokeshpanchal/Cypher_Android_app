package com.helio.cypher.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendCounsellorRatingDTO {



     String counsellorid = "";
    String appointmentid = "";
    String  questionid = "";

    String rated_by = "";

    String status = "";

    int ratting = 0;
    String comment = "";

    String type = "";

    public SendCounsellorRatingDTO(String status, String counsellorid, String appointmentid, String questionid, String rated_by
    , int rattingcount, String comment)
    {
        super();

        this.status = status;
        this.counsellorid = counsellorid;
        this.appointmentid = appointmentid;
        this.questionid =questionid;
        this.rated_by = rated_by;
        this.ratting = rattingcount;
        this.comment = comment;
        this.type= "User";


    }
}
