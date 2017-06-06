package com.helio.cypher.callbacks;

import com.helio.cypher.models.Secret;

import java.util.List;

public interface SearchUpdateCallback {

    void onUpdate(List<Secret> list, int skip);
}
