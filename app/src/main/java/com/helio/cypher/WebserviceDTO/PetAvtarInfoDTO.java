package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class PetAvtarInfoDTO {



    String  m2_water = "";
    String  hug_oxygen = "";
    String  heart_food = "";

    public void setTotal_scratch_count(String total_scratch_count) {
        this.total_scratch_count = total_scratch_count;
    }

    public void setM2_water(String m2_water) {
        this.m2_water = m2_water;
    }

    public void setHug_oxygen(String hug_oxygen) {
        this.hug_oxygen = hug_oxygen;
    }

    public void setHeart_food(String heart_food) {
        this.heart_food = heart_food;
    }

    public void setScratch_count(String scratch_count) {
        this.scratch_count = scratch_count;
    }

    String scratch_count = "";

    public String getTotal_scratch_count() {
        return total_scratch_count;
    }

    public String getM2_water() {
        return m2_water;
    }

    public String getHug_oxygen() {
        return hug_oxygen;
    }

    public String getHeart_food() {
        return heart_food;
    }

    public String getScratch_count() {
        return scratch_count;
    }

    String total_scratch_count = "";

    public String getComments_count() {
        return comments_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    String comments_count = "";

    public PetAvtarInfoDTO(String m2_water, String ug_oxygen, String heart_food, String scratch_count, String total_scratch_count, String comments_count)
    {
        super();



        this.m2_water = m2_water;
        this.hug_oxygen = ug_oxygen;
        this.heart_food = heart_food;
        this.scratch_count = scratch_count;
        this.total_scratch_count = total_scratch_count;
        this.comments_count = comments_count;

    }
}
