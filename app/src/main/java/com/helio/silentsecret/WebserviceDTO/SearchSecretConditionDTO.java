package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SearchSecretConditionDTO {

    String clstxt01 = "";
    String age = "";
    String _page = "";


    public SearchSecretConditionDTO(String username, String age, String _page)
    {
        super();
        this.clstxt01 = username;
        this.age = age;
        this._page = _page;
    }
}
