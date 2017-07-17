package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IfriendSecretpushNotiDataDTO {



    String clun01 = "";
    String sender = "";
    boolean secretpn;


    public IfriendSecretpushNotiDataDTO(String clun01,String sender )
    {
        super();

        this.clun01 = clun01;
        this.sender = sender;
        this.secretpn = true;

    }
}
