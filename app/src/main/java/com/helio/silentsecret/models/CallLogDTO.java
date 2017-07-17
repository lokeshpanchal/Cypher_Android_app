package com.helio.silentsecret.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class CallLogDTO {


    String appointmentid = "";
    String counsellorid = "";
    String userid = "";
    String status = "";
    String callby = "";

    public String getDuration() {
        return duration;
    }

    public String getAppointmentid() {
        return appointmentid;
    }

    public String getCounsellorid() {
        return counsellorid;
    }

    public String getUserid() {
        return userid;
    }

    public String getStatus() {
        return status;
    }

    public String getCallby() {
        return callby;
    }

    String duration = "";

    public CallLogDTO( String appointmentid, String sender, String reciever, String status,
                      String callby, String duration)
    {
        super();

        this.appointmentid = appointmentid;
        this.counsellorid = sender;
        this.userid = reciever;
        this.status =status;
        this.callby = callby;
        this.duration = duration;
    }
}
