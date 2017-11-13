package com.helio.silentsecret.models;

/**
 * Created by Maroof Ahmed Siddique on 10/22/2016.
 */
public class QuestionOptionModel
{


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    String label = "";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String value = "";

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    boolean selected = false;
    public QuestionOptionModel(String label, String   value ,boolean selected)
    {
        super();
        this.label = label;
        this.value = value;
        this.selected = selected;
    }

}
