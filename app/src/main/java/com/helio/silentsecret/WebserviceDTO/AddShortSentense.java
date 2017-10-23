package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class AddShortSentense {

  String clun01 = "";

    String secret_id = "";
    String short_sentense = "";
    String type = "";

    public AddShortSentense(String clun01, String secret_id , String short_sentense, String type)
    {
        super();
        this.clun01 = clun01;
        this.short_sentense = short_sentense;
        this.secret_id = secret_id;
        this.type = type;


    }
}
