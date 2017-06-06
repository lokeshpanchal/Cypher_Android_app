package com.helio.cypher.callbacks;

import com.helio.cypher.models.Notification;

import java.util.List;

public interface NotificationsCallback {

    void onReceive(List<Notification> data);
}
