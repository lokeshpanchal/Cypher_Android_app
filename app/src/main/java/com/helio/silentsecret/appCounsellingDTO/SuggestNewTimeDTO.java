package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SuggestNewTimeDTO {




     String counsellorid = "";

    String clun01 = "";
    String appointmentid = "";
    String  suggested_by = "";
    String appointmentdate = "";
    String same_counsellor ;
    String counsellor_firstname = "";
    String appointment_hours = "",appointment_day = "";
    public SuggestNewTimeDTO(String clun01, String username, String appointmentid, String suggested_by, String appointmentdate , String same_counsellor , String counsellor_firstname, String appointment_hours , String appointment_day)
    {
        super();
        this.clun01 = clun01;
        this.counsellorid = username;
        this.appointmentid = appointmentid;
        this.suggested_by =suggested_by;
        this.appointmentdate = appointmentdate;
        this.same_counsellor =same_counsellor;
        this.counsellor_firstname = counsellor_firstname;
        this.appointment_hours = appointment_hours;
        this.appointment_day =appointment_day;

    }
}
