package com.helio.silentsecret.callbacks;

import com.helio.silentsecret.models.SupportOrganisation;

import java.util.List;

public interface SupportCallback {

    void onUpdate(List<SupportOrganisation> data);
}
