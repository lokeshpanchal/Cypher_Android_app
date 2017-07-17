package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class DeviceTokenDTO {


     String requestType = "";
     String apikey = "";
    String _table = "";
    String _method = "";

    DevicetokendataDTO data ;


    public DeviceTokenDTO(String _table, String _method, DevicetokendataDTO condition)
    {
        super();
        this.requestType = "updateDeviceToken";
        this.apikey = "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R";
        this._table = _table;
        this._method = _method;
        this.data = condition;
    }
}
