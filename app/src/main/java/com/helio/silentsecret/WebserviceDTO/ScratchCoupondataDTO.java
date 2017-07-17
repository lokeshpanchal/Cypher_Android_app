package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class ScratchCoupondataDTO {


    String clun01 = "";
     String type = "";
    String value = "";
    String scratch_count = "";
    String total_scratch_count = "";


    public ScratchCoupondataDTO(String username , String type, String value, String total_scratch_count, String scratch_count )
    {
        super();

        this.clun01 = username;
        this.type = type;
        this.value = value ;
        this.scratch_count = scratch_count;
        this.total_scratch_count = total_scratch_count;

    }
}
