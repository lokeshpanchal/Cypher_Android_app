package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendAttandanceDataDTO {


    String usertype = "";
    String datetime = "";
    String appointmentid = "";



    public SendAttandanceDataDTO(String datetime , String appointmentid)
    {
        super();

        this.appointmentid = appointmentid;
        this.datetime = datetime;
        this.usertype = "User";


    }
}
