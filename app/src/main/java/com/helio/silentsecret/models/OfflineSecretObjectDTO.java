package com.helio.silentsecret.models;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class OfflineSecretObjectDTO {




    List<Secret> data ;


    public OfflineSecretObjectDTO(List<Secret> data)
    {
        super();
        this.data = data;
    }
}
