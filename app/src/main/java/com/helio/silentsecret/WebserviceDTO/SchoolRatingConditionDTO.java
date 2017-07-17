package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SchoolRatingConditionDTO {

    String clun01 = "";
    String clnm01 = "";
    String teaching_ability;
    String teacher_friend_liness = "";
    String atmosphere = "";
    String cleanliness = "";
    String food = "";
    String class_environment = "";
    String extra_curricular = "";
    String it = "";

    String receptionist_friend_liness = "";
    String address = "";
    String age = "";
    String gender = "";
    String range_of_subjects = "";

    public SchoolRatingConditionDTO(String clun01, String clnm01,String address,String age, String gender, String[] ratarra)
    {
        super();

        this.clun01 = clun01;
        this.clnm01 = clnm01;
        this.address =address;
        this.age = age;
        this.gender =gender;
        this.teacher_friend_liness = ratarra[0];
        this.atmosphere = ratarra[1];
        this.cleanliness = ratarra[2];
        this.food = ratarra[3];
        this.class_environment = ratarra[4];
        this.teaching_ability = ratarra[5];
        this.extra_curricular = ratarra[6];
        this.it = ratarra[7];
        this.receptionist_friend_liness = ratarra[8];
        this.range_of_subjects = ratarra[9];

    }



}
