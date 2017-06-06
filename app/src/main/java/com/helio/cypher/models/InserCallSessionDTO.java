package com.helio.cypher.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class InserCallSessionDTO {


     String requestType = "";
     String apikey = "";
    String appointmentid = "";
    String counsellorid = "";
    String userid = "";
    String status = "";
    String callby = "";
    String duration = "";

    public InserCallSessionDTO( String appointmentid, String sender, String reciever, String status,
                               String callby,String duration)
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
