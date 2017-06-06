package com.helio.cypher.callbacks;

import com.helio.cypher.models.SupportOrganisation;

import java.util.List;

public interface SupportCallback {

    void onUpdate(List<SupportOrganisation> data);
}
