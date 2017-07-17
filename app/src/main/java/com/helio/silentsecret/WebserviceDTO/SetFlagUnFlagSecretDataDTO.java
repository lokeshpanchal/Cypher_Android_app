package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetFlagUnFlagSecretDataDTO {



    String clun01 = "";
String secret_id = "";

String is_flagged ;

    public SetFlagUnFlagSecretDataDTO(String clun01  , String flagged, String secret_id)
    {
        super();

        this.clun01 = clun01;
        this.is_flagged = flagged;
        this.secret_id = secret_id;

    }
}
