package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class MysecretConditionDTO {

  String createdByUser = "";
  boolean isRemoved ;
    String _page = "";

    public MysecretConditionDTO(String createdByUser, String _page)
    {
        super();
        this.createdByUser = createdByUser;
        this.isRemoved = false;
        this._page = _page;


    }
}
