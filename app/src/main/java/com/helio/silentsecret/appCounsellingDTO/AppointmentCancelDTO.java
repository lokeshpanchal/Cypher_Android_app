package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class AppointmentCancelDTO {



     String counsellorid = "";
    String status = "";
    String appointmentid = "";
    String  acceptedBy = "";
    String clun01 = "";

    public AppointmentCancelDTO(String username, String status, String appointmentid, String acceptedBy, String clun01)
    {
        super();
        this.counsellorid = username;
        this.status = status;
        this.appointmentid = appointmentid;
        this.acceptedBy =acceptedBy;
        this.clun01 = clun01;


    }
}
