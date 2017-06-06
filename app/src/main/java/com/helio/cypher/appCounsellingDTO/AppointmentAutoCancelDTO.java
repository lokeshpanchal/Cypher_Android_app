package com.helio.cypher.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class AppointmentAutoCancelDTO {

    String status = "";
    String appointmentid = "";

    String clun01 = "";
    String cancelled_by  = "";
    String cancelledByUser = "";

    public AppointmentAutoCancelDTO(  String appointmentid,  String clun01)
    {
        super();
        this.status = "Cancelled";
        this.appointmentid = appointmentid;
        this.clun01 = clun01;
        this.cancelled_by = "android";
        this.cancelledByUser = "User";

    }
}
