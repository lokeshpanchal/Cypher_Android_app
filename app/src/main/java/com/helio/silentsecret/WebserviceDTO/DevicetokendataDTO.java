package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class DevicetokendataDTO {



    String clun01 = "";
    String device_type = "";
    String device_token = "";
    String version = "";

    String imei_number ;
    String device_details = "";
    public DevicetokendataDTO(String username , String device_token, String version, String mei_number, String device_details)
    {
        super();
        this.clun01 = username;
        this.device_type = "android";
        this.device_token = device_token;
        this.version = version;
        this.imei_number = mei_number;
        this.device_details = device_details;

    }
}
