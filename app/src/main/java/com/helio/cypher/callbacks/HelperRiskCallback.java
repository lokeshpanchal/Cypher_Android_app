package com.helio.cypher.callbacks;

import com.helio.cypher.models.RiskState;

import java.util.List;

public interface HelperRiskCallback {

    void onUpdate(List<RiskState> data);
}
