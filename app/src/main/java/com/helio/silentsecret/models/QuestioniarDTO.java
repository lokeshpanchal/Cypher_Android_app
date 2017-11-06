package com.helio.silentsecret.models;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 10/22/2016.
 */
public class QuestioniarDTO
{


   String cltitle01 = "";
    List<QuestioniarModel> clquestioneranswer01 = null;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String value = "";
    String clun01 = "";
    String questioner_unq_id = "";
    String agency_unq_id = "";
    String swuser_unq_id = "";

    public QuestioniarDTO(List<QuestioniarModel> clquestioner01  , String   cltitle01 , String   clun01 , String questioner_unq_id , String agency_unq_id , String swuser_unq_id)
    {
        super();
        this.clquestioneranswer01 = clquestioner01;
        this.cltitle01 = cltitle01;
        this.clun01 = clun01;
        this.questioner_unq_id = questioner_unq_id;
        this.agency_unq_id = agency_unq_id;
        this.swuser_unq_id = swuser_unq_id;

    }



}
