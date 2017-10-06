package com.helio.silentsecret.Event;


import com.helio.silentsecret.models.PhotoModel;

import java.util.List;

/**
 * Created by gturedi on 7.02.2017.
 */
public class SearchEvent
        extends BaseServiceEvent<List<PhotoModel>> {

    public SearchEvent(List<PhotoModel> item, Throwable exception) {
        super(item, exception);
    }

}