package com.helio.cypher.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;

import com.helio.cypher.application.SilentSecretApplication;

import org.json.JSONArray;

public class Preference {

    private static final String PREF = "silent_secret";
    private static final String USER_LAT = "user_latitude";
    private static final String USER_LON = "user_longitude";
    private static final String DISCLAIMER = "disclaimer";
    private static final String FILTER = "filter";
    private static final String USER_AGE = "user_age";
    private static final String USER_NAME = "user_name";

    private static final String QR_CODE = "qr_code";

    private static final String TUTORIAL_PLUS = "plus_tutorial";
    private static final String TUTORIAL_MENU = "menu_tutorial";
    private static final String TUTORIAL_TRENDING = "trending_tutorial";
    private static final String TUTORIAL_HAPPY = "happy_tutorial";
    private static final String TUTORIAL_FILTER = "filter_tutorial";
    private static final String TUTORIAL_GLIMPSE = "glimpse_tutorial";
    private static final String TUTORIAL_EMOTIONS = "emotions_tutorial";

    private static final String TUTORIAL_FEED_FLAG = "flag_tutorial";
    private static final String TUTORIAL_FEED_SUPPORT = "support_tutorial";

    private static final String TUTORIAL_FEED_HUG = "hug_tutorial";
    private static final String TUTORIAL_FEED_HEART = "heart_tutorial";
    private static final String TUTORIAL_FEED_ME2 = "me2_tutorial";

    private static final String TUTORIAL_FEED_VERIFY = "verify_tutorial";
    private static final String TUTORIAL_PURCHASE = "tutorial_purchase";
    private static final String TUTORIAL_SECRET = "my_secret_tutorial";
    private static final String TUTORIAL_IFRIENDS = "my_iFriends_tutorial";
    private static final String TUTORIAL_APPCOUNSELLING = "my_appcounselling_tutorial";
    private static final String TUTORIAL_INVITE = "my_invite_tutorial";

    private static final String TUTORIAL_PETS = "pets_tutorial";

    private static final String TUTORIAL_CREATE = "create_secret_tutorial";
    private static final String NOTIFICATION_CHECKOUT = "notification";

    private static final String CAUTION = "caution";
    private static final String HAS_DATE_CHANGED = "has_changed_date";
    private static final String IMAGE = "image";
    private static String IMAGE_URI = "image_uri";

    private static final String HELPED = "has_helped";

    private static SharedPreferences sharedPreferences = null;

    public static SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = SilentSecretApplication.instance().getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public synchronized static void saveFilter(int time) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(FILTER, time);
        editor.commit();
    }

    public synchronized static int getFilter() {
        return getSharedPreferences().getInt(FILTER, Constants.STATE_SEE_ALL);
    }

    public synchronized static void setHasDateChanged(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(HAS_DATE_CHANGED, data);
        editor.commit();
    }

    public synchronized static boolean getHasDateChanged() {
        return getSharedPreferences().getBoolean(HAS_DATE_CHANGED, false);
    }


    public synchronized static void saveUserLat(String data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(USER_LAT, data);
        editor.commit();
    }

    public synchronized static String getUserLat() {
        return getSharedPreferences().getString(USER_LAT, "0");
    }

    public synchronized static void saveUserLon(String data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(USER_LON, data);
        editor.commit();
    }

    public synchronized static String getUserLon() {
        return getSharedPreferences().getString(USER_LON, "0");
    }

    public synchronized static void saveUserAge(String data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(USER_AGE, data);
        editor.commit();
    }

    public synchronized static String getUserAge() {
        return getSharedPreferences().getString(USER_AGE, "13");
    }

    public synchronized static void saveUserName(String data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(USER_NAME, data);
        editor.commit();
    }

    public synchronized static String getUserName() {
        return getSharedPreferences().getString(USER_NAME, "");
    }

    public synchronized static void saveUserDisclaimer(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(DISCLAIMER, data);
        editor.commit();
    }

    public synchronized static boolean getCaution() {
        return getSharedPreferences().getBoolean(CAUTION, false);
    }

    public synchronized static void saveCaution(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(CAUTION, data);
        editor.commit();
    }

    public synchronized static boolean getUserDisclaimer() {
        return getSharedPreferences().getBoolean(DISCLAIMER, false);
    }

    public synchronized static void setPlusTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_PLUS, data);
        editor.commit();
    }

    public synchronized static void setEmotionsTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_EMOTIONS, data);
        editor.commit();
    }

    public synchronized static boolean getPlusTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_PLUS, false);
    }

    public synchronized static boolean getEmotionsTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_EMOTIONS, false);
    }

    public synchronized static void setMenuTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_MENU, data);
        editor.commit();
    }

    public synchronized static boolean getMenuTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_MENU, false);
    }

    public synchronized static void setTrendingTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_TRENDING, data);
        editor.commit();
    }

    public synchronized static boolean getTrendingTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_TRENDING, false);
    }

    public synchronized static void setHappyTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_HAPPY, data);
        editor.commit();
    }

    public synchronized static boolean getHappyTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_HAPPY, false);
    }

    public synchronized static void setFilterTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_FILTER, data);
        editor.commit();
    }

    public synchronized static boolean getFilterTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_FILTER, false);
    }

    public synchronized static void setGlempseTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_GLIMPSE, data);
        editor.commit();
    }

    public synchronized static boolean getGlimpseTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_GLIMPSE, false);
    }


    public synchronized static void setFlagTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_FEED_FLAG, data);
        editor.commit();
    }

    public synchronized static boolean getFlagTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_FEED_FLAG, false);
    }

    public synchronized static void setSupportTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_FEED_SUPPORT, data);
        editor.commit();
    }

    public synchronized static boolean getSupportTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_FEED_SUPPORT, false);
    }

    public synchronized static void setHugTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_FEED_HUG, data);
        editor.commit();
    }

    public synchronized static boolean getHugTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_FEED_HUG, false);
    }

    public synchronized static void setHeartTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_FEED_HEART, data);
        editor.commit();
    }

    public synchronized static boolean getHeartTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_FEED_HEART, false);
    }

    public synchronized static void setMe2Tutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_FEED_ME2, data);
        editor.commit();
    }

    public synchronized static boolean getMe2Tutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_FEED_ME2, false);
    }


    public synchronized static void setQrcode(int qrcod) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(QR_CODE, qrcod);
        editor.commit();
    }

    public synchronized static int getQrcode()
    {
        return getSharedPreferences().getInt(QR_CODE, 0);
    }


    public synchronized static void setVerifyTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_FEED_VERIFY, data);
        editor.commit();
    }

    public synchronized static boolean getVerifyTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_FEED_VERIFY, false);
    }

    public synchronized static void setMineTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_SECRET, data);
        editor.commit();
    }
    public synchronized static void setInviteTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_INVITE, data);
        editor.commit();
    }


    public synchronized static boolean getPetsTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_PETS, false);
    }

    public synchronized static void setPetssTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_PETS, data);
        editor.commit();
    }



    public synchronized static void setIFriendsTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_IFRIENDS, data);
        editor.commit();
    }public synchronized static void setAppcounsellingTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_APPCOUNSELLING, data);
        editor.commit();
    }

    public synchronized static boolean getMineTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_SECRET, false);
    }

    public synchronized static boolean getIFriendsTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_IFRIENDS, false);
    }

    public synchronized static boolean getAppcounsellingTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_APPCOUNSELLING, false);
    }

    public synchronized static boolean getInviteTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_INVITE, false);
    }

    public synchronized static void setPurchaseTutorial(boolean data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(TUTORIAL_PURCHASE, data);
        editor.commit();
    }

    public synchronized static boolean getPurchaseTutorial() {
        return getSharedPreferences().getBoolean(TUTORIAL_PURCHASE, false);
    }

    public synchronized static void saveNotificationList(JSONArray data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(NOTIFICATION_CHECKOUT, IOJsonEditor.jsonArrayToString(data));
        editor.commit();
    }

    public synchronized static String getNotificationCheckout() {
        return getSharedPreferences().getString(NOTIFICATION_CHECKOUT, null);
    }

    public synchronized static void saveLastImage(String s) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(IMAGE, s);
        editor.commit();
    }

    public synchronized static String  getLastImage() {
        return getSharedPreferences().getString(IMAGE, "");
    }

    public synchronized static void saveLastImageURI(String s) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(IMAGE_URI, s);
        editor.commit();
    }

    public synchronized static Uri getLastImageURI() {
        return Uri.parse(getSharedPreferences().getString(IMAGE_URI, ""));
    }

    public synchronized static void setHasHelped(boolean data)
    {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(HELPED, data);
        editor.commit();
    }

    public synchronized static boolean hasHelped() {
        return getSharedPreferences().getBoolean(HELPED, false);
    }

    public synchronized static void clearMenuTutorial() {
        getSharedPreferences().edit().remove(TUTORIAL_PLUS).commit();
        getSharedPreferences().edit().remove(TUTORIAL_MENU).commit();
        getSharedPreferences().edit().remove(TUTORIAL_TRENDING).commit();
        getSharedPreferences().edit().remove(TUTORIAL_FILTER).commit();
        getSharedPreferences().edit().remove(TUTORIAL_HAPPY).commit();
        getSharedPreferences().edit().remove(TUTORIAL_GLIMPSE).commit();
        getSharedPreferences().edit().remove(TUTORIAL_FEED_SUPPORT).commit();
        getSharedPreferences().edit().remove(TUTORIAL_FEED_FLAG).commit();
        getSharedPreferences().edit().remove(TUTORIAL_FEED_HEART).commit();
        getSharedPreferences().edit().remove(TUTORIAL_FEED_ME2).commit();
        getSharedPreferences().edit().remove(TUTORIAL_FEED_HUG).commit();
        getSharedPreferences().edit().remove(TUTORIAL_FEED_VERIFY).commit();
        getSharedPreferences().edit().remove(TUTORIAL_SECRET).commit();
        getSharedPreferences().edit().remove(TUTORIAL_PURCHASE).commit();
        getSharedPreferences().edit().remove(TUTORIAL_CREATE).commit();
        getSharedPreferences().edit().remove(TUTORIAL_APPCOUNSELLING).commit();
        getSharedPreferences().edit().remove(TUTORIAL_IFRIENDS).commit();
        getSharedPreferences().edit().remove(TUTORIAL_INVITE).commit();
        getSharedPreferences().edit().remove(TUTORIAL_EMOTIONS).commit();
    }
}
