package com.helio.cypher.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SuggestNewTimeDTO {




     String counsellorid = "";

    String clun01 = "";
    String appointmentid = "";
    String  suggested_by = "";
    String appointmentdate = "";

    public SuggestNewTimeDTO(String clun01, String username, String appointmentid, String suggested_by, String appointmentdate)
    {
        super();
        this.clun01 = clun01;
        this.counsellorid = username;
        this.appointmentid = appointmentid;
        this.suggested_by =suggested_by;
        this.appointmentdate = appointmentdate;


    }
}
