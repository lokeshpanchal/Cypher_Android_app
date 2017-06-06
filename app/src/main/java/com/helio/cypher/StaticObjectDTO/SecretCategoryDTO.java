package com.helio.cypher.StaticObjectDTO;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SecretCategoryDTO {


    public String getCat_name() {
        return cat_name;
    }

    public List<String> getFeel_array() {
        return feel_array;
    }

    String cat_name = "";
    List<String>  feel_array ;





    public SecretCategoryDTO(String state, List<String>  keys)
    {
        super();

        this.cat_name = state;
        this.feel_array = keys;



    }
}
