package com.helio.cypher.WebserviceDTO;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class UpdateSafeguardDataDTO {


    String clun01 = "";
   String safeguarding = null;

    public UpdateSafeguardDataDTO(String clun01, String safeguarding) {
        super();

        this.clun01 = clun01;
        this.safeguarding = safeguarding;
    }
}
