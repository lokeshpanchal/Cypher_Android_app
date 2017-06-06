package com.helio.cypher.callbacks;

import com.helio.cypher.models.Secret;

public interface AccessSecretCallback {

    void onReceive(Secret item);
}
