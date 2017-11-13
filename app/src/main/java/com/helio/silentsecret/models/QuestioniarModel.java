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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String value = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public List<QuestionOptionModel> getValues() {
        return values;
    }

    public void setValues(List<QuestionOptionModel> values) {
        this.values = values;
    }

    String subtype = "";
     List<QuestionOptionModel> values = null;

    public QuestioniarModel(String question_type, String name, String question_lable,String className,String subtype, List<QuestionOptionModel> values, String value)
    {
        super();

         this.type = question_type;
         this.name = name;
         this.label = question_lable;
         this.className = className;
         this.subtype = subtype;
         this.values = values;
         this.value = value;

    }
}
