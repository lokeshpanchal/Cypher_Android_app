package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class AppointmentCompleteDTO {

    String status = "";
    String appointmentid = "";

    String clun01 = "";
    String completed_by  = "";
    String completedByUser = "";

    public AppointmentCompleteDTO(String appointmentid, String clun01)
    {
        super();
        this.status = "Completed";
        this.appointmentid = appointmentid;
        this.clun01 = clun01;
        this.completed_by = "android";
        this.completedByUser = "User";

    }
}
