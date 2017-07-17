package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetAvailabilityDataDTO {



    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    String from = "";

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    String clcnslrun01 = "";
    String to ="";
    String day = "";
    String counc_unq_id = "";
    String agency_unq_id = "";
    String counce_firstname = "";

    public String getAvailable_type() {
        return available_type;
    }

    public void setAvailable_type(String available_type) {
        this.available_type = available_type;
    }

    public String getClcnslrun01() {
        return clcnslrun01;
    }

    public void setClcnslrun01(String clcnslrun01) {
        this.clcnslrun01 = clcnslrun01;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCounc_unq_id() {
        return counc_unq_id;
    }

    public void setCounc_unq_id(String counc_unq_id) {
        this.counc_unq_id = counc_unq_id;
    }

    public String getAgency_unq_id() {
        return agency_unq_id;
    }

    public void setAgency_unq_id(String agency_unq_id) {
        this.agency_unq_id = agency_unq_id;
    }

    public String getCounce_firstname() {
        return counce_firstname;
    }

    public void setCounce_firstname(String counce_firstname) {
        this.counce_firstname = counce_firstname;
    }

    String available_type = "";

    public SetAvailabilityDataDTO(String clcnslrun01 , String from ,String to,String day,String counc_unq_id,
                                  String agency_unq_id, String counce_firstname, String available_typeString)
    {
        super();
        this.clcnslrun01 = clcnslrun01;
        this.from = from;
        this.to = to;
        this.day = day;
        this.counc_unq_id = counc_unq_id;
        this.agency_unq_id =agency_unq_id;
        this.counce_firstname = counce_firstname;
        this.available_type = available_typeString;
    }
}
