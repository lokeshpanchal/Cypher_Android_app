package com.helio.cypher.pushnotification;

public interface GcmNotificationResponse {
	public void gcmResponse(String msg, String notification_id, String keyword, String key, String game_id);

}
