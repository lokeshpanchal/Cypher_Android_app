package com.helio.cypher.creator;

import android.graphics.Bitmap;

import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.models.LikeHugNotificationDTO;
import com.helio.cypher.models.LikeHugNotificationObjectDTO;

public class ObjectMaker {

    public static final String EMPTY = "";
    public static final String DEFAULT_AGE = "13";
    public static final String DEFAULT_HOME = "0";
    public static final String DEFAULT_SPLIT = ", ";

    /*public static Secret form(ParseObject data) {
        Secret item = new Secret();
        try {


            item.setAddress(data.getString(Constants.SECRET_ADDRESS) != null ? data.getString(Constants.SECRET_ADDRESS) : EMPTY);
            item.setAge(data.getString(Constants.SECRET_AGE) != null ? data.getString(Constants.SECRET_AGE) : DEFAULT_AGE);
            item.setBgImageName(data.getString(Constants.SECRET_BG_IMAGE_NAME) != null ? data.getString(Constants.SECRET_BG_IMAGE_NAME) : "bg0.jpg");
            item.setCategory(data.getString(Constants.SECRET_CATEGORY) != null ? data.getString(Constants.SECRET_CATEGORY) : EMPTY);
            item.setCreatedByUser(data.getString(Constants.SECRET_CREATED_BY_USER) != null ? data.getString(Constants.SECRET_CREATED_BY_USER) : "");
            item.setDate(data.getDate(Constants.SECRET_DATE) != null ? data.getDate(Constants.SECRET_DATE) : new Date());
            item.setFeel(data.getString(Constants.SECRET_FEEL) != null ? data.getString(Constants.SECRET_FEEL) : EMPTY);
            item.setFlagUsers(data.get(Constants.SECRET_FLAG_USERS) != null ? (ArrayList<String>) data.get(Constants.SECRET_FLAG_USERS) : new ArrayList<String>());
            item.setFlags(data.getInt(Constants.SECRET_FLAGS));
            item.setGender(data.getString(Constants.SECRET_GENDER) != null ? data.getString(Constants.SECRET_GENDER) : EMPTY);
            item.setHasNotification(data.getBoolean(Constants.SECRET_HAS_NOTIFICATION));
            item.setHashtags(data.getString(Constants.SECRET_HASH_TAGS) != null ? data.getString(Constants.SECRET_HASH_TAGS) : EMPTY);
            item.setHeartUsers(data.get(Constants.SECRET_HEART_USERS) != null ? (ArrayList<String>) data.get(Constants.SECRET_HEART_USERS) : new ArrayList<String>());
            item.setHearts(data.getInt(Constants.SECRET_HEARTS));
            item.setHome(data.getString(Constants.SECRET_HOME) != null ? data.getString(Constants.SECRET_HOME) : DEFAULT_HOME);
            item.setHugUsers(data.get(Constants.SECRET_HUG_USERS) != null ? (ArrayList<String>) data.get(Constants.SECRET_HUG_USERS) : new ArrayList<String>());
            item.setHugs(data.getInt(Constants.SECRET_HUGS));
            item.setImage(data.getParseFile(Constants.SECRET_IMAGE) != null ? data.getParseFile(Constants.SECRET_IMAGE) : null);
            item.setLatLon(data.getString(Constants.SECRET_LAT_LONG) != null ? data.getString(Constants.SECRET_LAT_LONG) : EMPTY);
            item.setLikes(data.getInt(Constants.SECRET_LIKES));
            item.setMe2Users(data.get(Constants.SECRET_ME2USERS) != null ? (ArrayList<String>) data.get(Constants.SECRET_ME2USERS) : new ArrayList<String>());
            item.setMe2s(data.getInt(Constants.SECRET_ME2S));
            item.setNotificationSent(data.getInt(Constants.SECRET_NOTIFICATION_SENT));
            item.setReadNotification(data.getBoolean(Constants.SECRET_READ_NOTIFICATION));
            item.setRiskState(data.getString(Constants.SECRET_RISK_STATE) != null ? data.getString(Constants.SECRET_RISK_STATE) : EMPTY);
            item.setStressLevel(data.getString(Constants.SECRET_STRESS_LEVEL) != null ? data.getString(Constants.SECRET_STRESS_LEVEL) : EMPTY);
            item.setSupportOrganisations(data.get(Constants.SECRET_SUPPORT_ORGANISATIONS) != null ? (ArrayList<String>) data.get(Constants.SECRET_SUPPORT_ORGANISATIONS) : new ArrayList<String>());
            item.setText(data.getString(Constants.SECRET_TEXT) != null ? data.getString(Constants.SECRET_TEXT) : EMPTY);
            item.setUser(data.getParseUser(Constants.SECRET_USER) != null ? data.getParseUser(Constants.SECRET_USER) : null);
            item.setWms(data.getInt(Constants.SECRET_WMS));
            item.setObjectId(data.getObjectId() != null ? data.getObjectId() : EMPTY);
            item.setObject(data);
            item.setCreatedDate(data.getCreatedAt());
            item.setUserFilled(data.getParseObject(Constants.INCLUDE_USER_POINTER) != null ? data.getParseObject(Constants.INCLUDE_USER_POINTER) : null);
            item.setFont(data.get(Constants.SECRET_FONT) != null ? data.getInt(Constants.SECRET_FONT) : 1);
        } catch (Exception e) {

        }
        return item;
    }*/

    /*public static RiskState formRisk(ParseObject data) {
        RiskState item = new RiskState();
        try {
            item.setRiskWords(data.get(Constants.RISK_KEYS) != null ? (ArrayList<String>) data.get(Constants.RISK_KEYS) : new ArrayList<String>());
            item.setState(data.getString(Constants.RISK_STATE) != null ? data.getString(Constants.RISK_STATE) : EMPTY);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }

    public static Category formCategory(ParseObject data) {
        Category item = new Category();
        try {
            item.setList(data.get(Constants.CATEGORY_KEYS) != null ? (ArrayList<String>) data.get(Constants.CATEGORY_KEYS) : new ArrayList<String>());
            item.setName(data.getString(Constants.CATEGORY_NAME) != null ? data.getString(Constants.CATEGORY_NAME) : EMPTY);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public static SupportOrganisation formSupport(ParseObject data) {

        SupportOrganisation item = new SupportOrganisation();
        try {
            item.setLocation(data.get(Constants.SUPPORT_ORGANISATIONS_LOCATIONS) != null ? (List<String>) data.get(Constants.SUPPORT_ORGANISATIONS_LOCATIONS) : new ArrayList<String>());
            item.setName(data.getString(Constants.SUPPORT_ORGANISATIONS_NAME) != null ? data.getString(Constants.SUPPORT_ORGANISATIONS_NAME) : EMPTY);
            item.setThumb(data.getString(Constants.SUPPORT_ORGANISATIONS_THUMB) != null ? data.getString(Constants.SUPPORT_ORGANISATIONS_THUMB) : EMPTY);
            item.setTriggers(data.get(Constants.SUPPORT_ORGANISATIONS_TRIGGERS) != null ? (List<String>) data.get(Constants.SUPPORT_ORGANISATIONS_TRIGGERS) : null);
            item.setUrl(data.getString(Constants.SUPPORT_ORGANISATIONS_URL) != null ? data.getString(Constants.SUPPORT_ORGANISATIONS_URL) : EMPTY);
            item.setTriggerWords(data.get(Constants.SUPPORT_ORGANISATIONS_TRIGGER_WORDS) != null ? Arrays.asList(data.getString(Constants.SUPPORT_ORGANISATIONS_TRIGGER_WORDS).split(DEFAULT_SPLIT)) : null);
            item.setUserRated(data.get(Constants.SUPPORT_ORGANISATIONS_USERS_RATED) != null ? (List<String>) data.get(Constants.SUPPORT_ORGANISATIONS_USERS_RATED) : new ArrayList<String>());
            item.setPhoneNo(data.getString(Constants.SUPPORT_ORGANISATIONS_PHONE_NO) != null ? data.getString(Constants.SUPPORT_ORGANISATIONS_PHONE_NO) : EMPTY);
            item.setClicks(data.getInt(Constants.SUPPORT_ORGANISATIONS_CLICKS));
            item.setObjectId(data.getObjectId() != null ? data.getObjectId() : EMPTY);
            item.setUsersClicked(data.get(Constants.SUPPORT_ORGANISATIONS_USERS_CLICKED) != null ? (List<String>) data.get(Constants.SUPPORT_ORGANISATIONS_USERS_CLICKED) : new ArrayList<String>());
            //item.setOrg(data);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return item;
    }*/


 /*   public static Comment formComment(ParseObject data) {
        Comment item = new Comment();
        try {
            item.setText(data.getString(Constants.COMMENTS_TEXT) != null ? data.getString(Constants.COMMENTS_TEXT) : EMPTY);
            item.setSeenBy(data.get(Constants.COMMENTS_SEEN_BY) != null ? (List<String>) data.get(Constants.COMMENTS_SEEN_BY) : new ArrayList<String>());
            item.setFlagUser(data.getParseUser(Constants.COMMENTS_FAG_USER) != null ? data.getParseUser(Constants.COMMENTS_FAG_USER) : null);
            item.setObjectId(data.getObjectId());
            item.setSecret(data.getParseObject(Constants.COMMENTS_SECRET) != null ? data.getParseObject(Constants.COMMENTS_SECRET) : null);
            item.setText(data.getString(Constants.COMMENTS_TEXT) != null ? data.getString(Constants.COMMENTS_TEXT) : EMPTY);
            item.setFlagged(data.getBoolean(Constants.COMMENTS_FLAGGED));
            item.setCreatedAt(data.getCreatedAt());
            item.setUser(data.getParseUser(Constants.COMMENTS_USER) != null ? data.getParseUser(Constants.COMMENTS_USER) : null);
            item.setReply(data.get(Constants.COMMENTS_REPLY) != null ? data.getBoolean(Constants.COMMENTS_REPLY) : false);
            item.setReplyTo(data.get(Constants.COMMENTS_REPLY_TO) != null ? data.getParseObject(Constants.COMMENTS_REPLY_TO) : null);



            item.setObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }
*/



/*

   static private class IFirendShareSecretNotification extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(MainActivity.ct);

                LikeHugNotificationObjectDTO likeHugNotificationObjectDTO = new LikeHugNotificationObjectDTO(likeHugNotificationDTO);


                response = http.sendmehuglike(likeHugNotificationObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {


        }
    }
*/




    static LikeHugNotificationDTO likeHugNotificationDTO = null;



}
