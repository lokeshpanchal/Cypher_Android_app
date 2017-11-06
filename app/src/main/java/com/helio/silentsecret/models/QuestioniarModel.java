package com.helio.silentsecret.models;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class QuestioniarModel {




     String type = "";
     String label = "";
     String name = "";
     String className = "";
     String subtype = "";
     List<QuestionOptionModel> values = null;

    public QuestioniarModel(String question_type, String name, String question_lable,String className,String subtype, List<QuestionOptionModel> values)
    {
        super();

         this.type = question_type;
         this.name = name;
         this.label = question_lable;
         this.className = className;
         this.subtype = subtype;
         this.values = values;

    }
}
