package com.helio.silentsecret.models;

/**
 * Created by Maroof Ahmed Siddique on 10/22/2016.
 */
public class DoowappTagDTO
{


    public String getTagname() {
        return tagname;
    }



    String tagname = "";

    public String getTagid() {
        return tagid;
    }



    String tagid = "";

    public DoowappTagDTO(String tagname,String   tagid )
    {
        super();


        this.tagname = tagname;
        this.tagid = tagid;



    }



}
