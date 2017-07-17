package com.helio.silentsecret.WebserviceDTO;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class ClearMySecretDataDTO {


    String clun01 = "";
    List<String> secretids_array = null;

    public ClearMySecretDataDTO(String clun01, List<String> secretids_array) {
        super();

        this.clun01 = clun01;
        this.secretids_array = secretids_array;
    }
}
