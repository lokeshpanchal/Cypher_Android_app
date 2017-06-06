package com.helio.cypher.callbacks;

import com.helio.cypher.models.Invite;

import java.util.List;

public interface InviteCallback {

    void onUpdate(List<Invite> data);
}
