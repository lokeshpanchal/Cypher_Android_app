package com.helio.cypher.callbacks;

public interface RiskCheckerCallback {
    void onDone(boolean result, String riskWord, String state);
}
