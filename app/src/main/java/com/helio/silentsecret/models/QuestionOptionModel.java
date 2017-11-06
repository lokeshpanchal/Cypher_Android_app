package com.helio.silentsecret.models;

/**
 * Created by Maroof Ahmed Siddique on 10/22/2016.
 */
public class QuestionOptionModel
{


   String label = "";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String value = "";
    public QuestionOptionModel(String label, String   value )
    {
        super();
        this.label = label;
        this.value = value;
    }

}
