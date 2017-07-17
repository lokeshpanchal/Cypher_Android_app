package com.helio.silentsecret.WebserviceDTO;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SeenSupportRatingDataDTO {



    String unique_id = "";
//String type = "";
    List<String> users_rated ;
int stars =0;

    public SeenSupportRatingDataDTO(String unique_id , List<String> seen_by , int stars, String type)
    {
        super();

           this.unique_id = unique_id;
        this.users_rated = seen_by;
        this.stars = stars;
      //  this.type = type;

    }
}
