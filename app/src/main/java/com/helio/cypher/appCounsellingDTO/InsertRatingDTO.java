package com.helio.cypher.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class InsertRatingDTO {



    String appointmentid = "";
    String counsellorid = "";
    String userid = "";
    String rated_by = "";
boolean overallrating = false;


    public InsertRatingDTO(String appointmentid, String counsellorid, String userid, String rated_by,boolean overall)
    {
        super();

        this.appointmentid = appointmentid;
        this.userid = userid;
        this.counsellorid =counsellorid;
        this.rated_by = rated_by;
        this.overallrating = overall;

    }
}
