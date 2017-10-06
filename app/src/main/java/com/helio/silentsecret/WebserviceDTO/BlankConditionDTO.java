package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class BlankConditionDTO {

String age = "";

    String _page = "";

    public void setClun01(String clun01) {
        this.clun01 = clun01;
    }

    String clun01 = "";
    public BlankConditionDTO(String age,String _page)
    {
        super();
        this.age = age;
        this._page = _page;


    }
}
