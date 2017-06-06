package com.helio.cypher.StaticObjectDTO;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class RiskWordDTO {


     String state = "";

    public List<String> getKeys() {
        return keys;
    }

    public String getState() {
        return state;
    }

    List<String>  keys ;





    public RiskWordDTO(String state,List<String>  keys)
    {
        super();

        this.state = state;
        this.keys = keys;



    }
}
