package com.helio.silentsecret.connection;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.StaticObjectDTO.IFriendSettingDTO;
import com.helio.silentsecret.StaticObjectDTO.RiskWordDTO;
import com.helio.silentsecret.StaticObjectDTO.SchoolDTO;
import com.helio.silentsecret.StaticObjectDTO.SecretCategoryDTO;
import com.helio.silentsecret.StaticObjectDTO.StataicObjectDTO;
import com.helio.silentsecret.WebserviceDTO.ChatMessageDTO;
import com.helio.silentsecret.WebserviceDTO.ClearMysecretObjectDTO;
import com.helio.silentsecret.WebserviceDTO.CommentObjectDTO;
import com.helio.silentsecret.WebserviceDTO.DeviceTokenObjectDTO;
import com.helio.silentsecret.WebserviceDTO.FindObjectDTO;
import com.helio.silentsecret.WebserviceDTO.FindbynameObjectDTO;
import com.helio.silentsecret.WebserviceDTO.ForgetObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetAllNotificationObjecttDTO;
import com.helio.silentsecret.WebserviceDTO.GetCommentObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetIfriendMessageObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetLastPostdateObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetMoodGraphObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetPendingFriendObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetPetInfoObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetSecretUserinfoObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyidObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetSupportObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetUnreadMessageObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetmyRecievedObjectDTO;
import com.helio.silentsecret.WebserviceDTO.LoginbjectDTO;
import com.helio.silentsecret.WebserviceDTO.Me2HugLikeObjectDTO;
import com.helio.silentsecret.WebserviceDTO.MysecretObjectDTO;
import com.helio.silentsecret.WebserviceDTO.PetAvtarInfoDTO;
import com.helio.silentsecret.WebserviceDTO.RegistrationObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SchoolRatingObjectDTO;
import com.helio.silentsecret.WebserviceDTO.ScratchCouponObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SearchSecretObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SecretObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SeenbyObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SendCouponCodeObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SendMessageObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SendSupportRatingObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SetDateOfBitrhObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SetVerifyFlagObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SetflagUnflagCommentObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SetflagUnflagObjectDTO;
import com.helio.silentsecret.WebserviceDTO.StaticObjectDTO;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.appCounsellingDTO.CheckUserSessionObjectDTO;
import com.helio.silentsecret.appCounsellingDTO.FinalObjectDTO;
import com.helio.silentsecret.appCounsellingDTO.RattingQuestionDTO;
import com.helio.silentsecret.models.AllIfriendobjectDTO;
import com.helio.silentsecret.models.CallLogDTO;
import com.helio.silentsecret.models.Comment;
import com.helio.silentsecret.models.GetCurrentDateObjectDTO;
import com.helio.silentsecret.models.GetImpactRatingObjectDTO;
import com.helio.silentsecret.models.IfriendAcceptObjectDTO;
import com.helio.silentsecret.models.IfriendListDTO;
import com.helio.silentsecret.models.IfriendPendingRequestDTO;
import com.helio.silentsecret.models.IfriendRequestObjectDTO;
import com.helio.silentsecret.models.IfriendRequestRejectObjectDTO;
import com.helio.silentsecret.models.IfriendSendRequestObjectDTO;
import com.helio.silentsecret.models.IfriendsecrtpushobjectDTO;
import com.helio.silentsecret.models.Notification;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.models.SendImpactRatingDTO;
import com.helio.silentsecret.models.SentImpactratinganswerObjectDTO;
import com.helio.silentsecret.models.SupportOrganisation;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.Preference;
import com.helio.silentsecret.utils.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * Created by Maroof Ahmed Siddique on 10/4/2016.
 */
public class IfriendRequest {
    String serviceUrl = "";


   // String Baseurl = "http://192.169.142.91/~silentsecret/pushnotification/api.php";


    String MainServer = "https://dev1.eu-gb.mybluemix.net/api/service";
    String RecoveryServer = "https://dev1.eu-gb.mybluemix.net/api/service";
 //    String RecoveryServer = "https://dev2.eu-gb.mybluemix.net/api/service";



   // String MainServer = "http://71254479.ngrok.io/api/service";     // local
   // String RecoveryServer = "http://71254479.ngrok.io/api/service"; // local
   // String RecoveryServer = "https://dev1.eu-gb.mybluemix.net/api/service";

    //String MainServer = "https://cypherproduction.eu-gb.mybluemix.net/api/service";
    //String RecoveryServer = "https://cypherproduction.eu-gb.mybluemix.net/api/service";
   // String RecoveryServer = "https://cypherrecovery.eu-gb.mybluemix.net/api/service";

    // http://api.doowapp.me/1.0/doowapp?tagId=1&fullTrackInfo=true&apikey=573f314f4f979

    String APIkey = "573f314f4f979";
    String TAG = getClass().getSimpleName();
    private String json = null;
    Context mContext;
    String failer_response = "404 Not Found";

    public IfriendRequest(Context context) {
        super();
        mContext = context;
    }



    public String makeConnection(String serviceUrl, String entity) {
        String response = "", urlStr = "";
        String completeURL = "";
        try {
            urlStr = serviceUrl;

            HttpPost httpPost = new HttpPost(urlStr);


            StringEntity se = new StringEntity(entity, "UTF-8");

            httpPost.setEntity(se);

            httpPost.setHeader(HTTP.CONTENT_TYPE,
                    "application/json");

            HttpParams httpParameters = new BasicHttpParams();

            int timeoutConnection = 5000;

            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

            Log.i(TAG, "makeConnection completeURL: " + serviceUrl + "&" + completeURL);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();


            response = EntityUtils.toString(httpEntity, HTTP.UTF_8);
            entity = null;
        } catch (UnsupportedEncodingException e) {
            response = "Can't connect to server.";
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            response = "Can't connect to server.";
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            response = "Can't connect to server.";
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e) {
            response = "Can't connect to server.";
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            response = "Can't connect to server.";
            e.printStackTrace();
            return null;
        }
        Log.i(TAG, "makeConnection response :: " + response);
        return response;
    }


    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public List<RattingQuestionDTO> InsertRatingQuestion(Object contact)
    {

        try {
            String jsonData = new Gson().toJson(contact);
            if (Constants.primary_server) {
                json = makeConnection(MainServer, jsonData);

                if (json == null || json.equalsIgnoreCase("") || json.contains(failer_response)) {
                    Constants.primary_server = false;
                    json = makeConnection(RecoveryServer, jsonData);
                }
            } else {
                json = makeConnection(RecoveryServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = true;
                    json = makeConnection(MainServer, jsonData);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ParseQuestion(json);
    }


    private List<RattingQuestionDTO> ParseQuestion(String jsonResponse)
    {

        String response = "";

        String status = "";
        String currentdatetime ="";
        String suggested_by = "";
        String sameCounsellor = "",sessionleft = "",session_duration = "",user_id = "";

        List<RattingQuestionDTO> acceptedAppointmentDTOs = new ArrayList<>();

        JSONArray json_array = null;
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                String counsellorid =""; String appointmentid =""; String questionid =""; String userid ="";String rated_by
                        ="";String questiontext ="";
                int rattingcount =0;String comment ="";


                if (jsonObject.has("status"))
                    status  = jsonObject.getString("status");

               // if(status!= null && status.equalsIgnoreCase("true"))
                //{
                   /* if(jsonObject.has("datetime"))
                        currentdatetime =  jsonObject.getString("datetime");*/

                    response = "success";
                    json_array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json_array.length(); i++)
                    {
                        JSONObject jsonObject1 = new JSONObject(json_array.getString(i));


                        counsellorid = jsonObject1.getString("clcnslrun01");

                        appointmentid = jsonObject1.getString("apntmnt_id");

                        if(jsonObject1.has("questionid"))
                            questionid = jsonObject1.getString("questionid");

                        if(jsonObject1.has("clun01"))
                            userid = jsonObject1.getString("clun01");

                        if(jsonObject1.has("questiontext"))
                            questiontext = jsonObject1.getString("questiontext");

                        if(jsonObject1.has("rattingcount")) {
                            String   rattingcount1 = jsonObject1.getString("rattingcount");
                            rattingcount = Integer.parseInt(rattingcount1);
                        }


                        acceptedAppointmentDTOs.add(new RattingQuestionDTO( counsellorid,  appointmentid,  questionid,  userid, ""
                                , questiontext, rattingcount, ""));

                    }
                //}



            } else
            {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acceptedAppointmentDTOs;
    }

    public String ForgetUser(ForgetObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseForget(json);
    }


    private String ParseForget(String jsonResponse) {
        String username = "";
        String age = "", gender = "", pin = "", security_answer = "", security_question = "";


        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    String userinfo = "";

                    if (jsonObject.has("data"))
                        userinfo = jsonObject.getString("data");

                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json_array.length(); i++) {

                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));


                        if (UserInfoobj.has("clun01"))
                            username = UserInfoobj.getString("clun01");

                        if (username != null && !username.equalsIgnoreCase(""))
                            status = "success";
                        else
                            return "";


                        if (UserInfoobj.has("age"))
                            age = UserInfoobj.getString("age");


                        if (UserInfoobj.has("gender"))
                            gender = UserInfoobj.getString("gender");

                        if (UserInfoobj.has("pin"))
                            pin = UserInfoobj.getString("pin");

                        if (UserInfoobj.has("security_answer"))
                            security_answer = UserInfoobj.getString("security_answer");

                        if (UserInfoobj.has("security_question"))
                            security_question = UserInfoobj.getString("security_question");

                        Preference.saveUserAge(age);

                        Preference.saveUserName(CryptLib.decrypt(username));


                        AppSession.save(mContext, Constants.USER_PIN, CryptLib.decrypt(pin));
                        AppSession.save(mContext, Constants.USER_SECURITY_QUESTION, CryptLib.decrypt(security_question));
                        AppSession.save(mContext, Constants.USER_SECURITY_ANSWER, CryptLib.decrypt(security_answer));
                        AppSession.save(mContext, Constants.USER_GENDER, gender);
                        AppSession.save(mContext, Constants.USER_AGE, age);
                    }
                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    public List<Secret> FindMySecret(MysecretObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseHappy(json);
    }


    public List<Secret> Me2HuglikeAction(Me2HugLikeObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseHappy(json);
    }


    public List<Comment> GetComment(GetCommentObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseComment(json);
    }


    private List<Comment> ParseComment(String jsonResponse) {

        String cltxt01 = "";
        String clun01 = "";
        String comment_id = "";
        String flagged = "";
        String is_reply = "";
        String secret_id = "";
        String seen_by = "";
        String pet_name = "";
        String reply_to = "";
        String created_at = "";
        PetAvtarInfoDTO petAvtarInfoDTO = null;
        List<Comment> comments = new ArrayList<>();
        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    String userinfo = "";

                    if (jsonObject.has("data"))
                        userinfo = jsonObject.getString("data");

                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json_array.length(); i++) {

                        Comment comment = new Comment();
                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));


                        if (UserInfoobj.has("cltxt01"))
                            cltxt01 = UserInfoobj.getString("cltxt01");

                        comment.setText(CryptLib.decrypt(cltxt01));

                        if (UserInfoobj.has("clun01"))
                            clun01 = UserInfoobj.getString("clun01");
                        comment.setUser(clun01);

                        if (UserInfoobj.has("comment_id"))
                            comment_id = UserInfoobj.getString("comment_id");

                        comment.setComment_id(comment_id);

                        if (UserInfoobj.has("flagged"))
                            flagged = UserInfoobj.getString("flagged");

                        if (flagged != null && flagged.equalsIgnoreCase("true"))
                            comment.setFlagged(true);
                        else
                            comment.setFlagged(false);


                        if (UserInfoobj.has("is_reply"))
                            is_reply = UserInfoobj.getString("is_reply");

                        if (is_reply != null && is_reply.equalsIgnoreCase("true"))
                            comment.setReply(true);
                        else
                            comment.setReply(false);


                        if (UserInfoobj.has("reply_to"))
                            reply_to = UserInfoobj.getString("reply_to");


                        comment.setReplyTo(reply_to);

                        if (UserInfoobj.has("pet_name"))
                            pet_name = UserInfoobj.getString("pet_name");


                        comment.setPet_name(pet_name);


                        if (UserInfoobj.has("seen_by"))
                            seen_by = UserInfoobj.getString("seen_by");


                        if (UserInfoobj.has(Constants.COMMENTS_SEEN_BY)) {


                            JSONArray flausers = UserInfoobj.getJSONArray(Constants.COMMENTS_SEEN_BY);

                            String flausers1[] = toStringArray(flausers);

                            ArrayList<String> flagusers = new ArrayList<String>();
                            for (int k = 0; k < flausers1.length; k++) {
                                flagusers.add(flausers1[k]);
                            }
                            comment.setSeenBy(flagusers);

                        } else {
                            comment.setSeenBy(new ArrayList<String>());

                        }


                        if (UserInfoobj.has("secret_id"))
                            secret_id = UserInfoobj.getString("secret_id");

                        comment.setSecretid(secret_id);

                        if (UserInfoobj.has("created_at"))
                            created_at = UserInfoobj.getString("created_at");


                        try {
                            Date Created_at = Utils.StringTodate(created_at);
                            Created_at  = getLocalTime(Created_at);
                            comment.setCreatedAt(Created_at);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        comments.add(comment);
                    }
                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }

    public PetAvtarInfoDTO GetPetInfo(GetPetInfoObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseAvtarInfo(json);
    }


    public String UpdateSeenby(SeenbyObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response))
            {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response))
            {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }


    public List<ChatMessageDTO> GetChat(GetIfriendMessageObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseChat(json);
    }

    private List<ChatMessageDTO> ParseChat(String jsonResponse) {

        String  sender = "", receiver = "", risk_word = "", message = "", flags = "", flag_risk = "", read = "";
        Date created_at = null, curretime = null;

        List<ChatMessageDTO> chatMessageDTO = new ArrayList<>();
        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true"))
                {
                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    if (jsonObject.has("currenttime"))
                        curretime = Utils.StringTodate(jsonObject.getString("currenttime"));


                    for (int i = 0; i < json_array.length(); i++) {

                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                        if (UserInfoobj.has("sender"))
                            sender = UserInfoobj.getString("sender");

                        if (UserInfoobj.has("receiver"))
                            receiver = UserInfoobj.getString("receiver");

                        if (UserInfoobj.has("risk_word"))
                            risk_word = UserInfoobj.getString("risk_word");

                        if (UserInfoobj.has("message"))
                            message = UserInfoobj.getString("message");

                        if (UserInfoobj.has("flags"))
                            flags = UserInfoobj.getString("flags");

                        if (UserInfoobj.has("flag_risk"))
                            flag_risk = UserInfoobj.getString("flag_risk");

                        if (UserInfoobj.has("read"))
                            read = UserInfoobj.getString("read");

                        if (UserInfoobj.has("created_at"))
                            created_at = Utils.StringTodate(UserInfoobj.getString("created_at"));




                        created_at =  getLocalTime(created_at);
                        chatMessageDTO.add(new ChatMessageDTO(sender, receiver, risk_word, CryptLib.decrypt(message), flags, flag_risk, read, created_at,curretime));
                    }
                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatMessageDTO;
    }


    public String SendChat(SendMessageObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }

    public String Getsecretflagverified(FindbynameObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseSecretverifiedFlagged(json);
    }

    private String ParseSecretverifiedFlagged(String jsonResponse) {

        String isverified = "", isflagged = "";

        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true"))
                {
                    String userinfo = "";

                    if (jsonObject.has("data"))
                        userinfo = jsonObject.getString("data");

                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json_array.length(); i++) {

                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                        if (UserInfoobj.has("is_verified"))
                            isverified = UserInfoobj.getString("is_verified");


                    }
                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isverified;
    }


    public String SetNotificationRead(FinalObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }


    public JSONObject GetSecretUserInfo(GetSecretUserinfoObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseSecretUserInfo(json);
    }

    public String SetFlagUnflag(SetflagUnflagCommentObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }

    public String SetFlagUnflag(SetflagUnflagObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }



    public String ClearmySecret(ClearMysecretObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }


    private JSONObject ParseSecretUserInfo(String jsonResponse) {

        String isverified = "", isflagged = "";
        JSONObject userjsonObject = null;
        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    String userinfo = "";

                    if (jsonObject.has("data"))
                        userinfo = jsonObject.getString("data");

                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json_array.length(); i++) {
                        userjsonObject = new JSONObject(json_array.getString(i));
                    }
                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userjsonObject;
    }

    public JSONObject GetMyRecieved(GetmyRecievedObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseMyRecievd(json);
    }

    private JSONObject ParseMyRecievd(String jsonResponse) {

        String isverified = "", isflagged = "";

        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    String userinfo = "";

                    if (jsonObject.has("data"))
                        userinfo = jsonObject.getString("data");

                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json_array.length(); i++) {

                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));
                        return UserInfoobj;
                    }
                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<SupportOrganisation> GetSupportlist(GetSupportObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseGetsupport(json);
    }


    public List<Secret> GetSecretbyID(GetSecretbyidObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseHappy(json);
    }



    public List<Secret> GetSearchSecret(SearchSecretObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseHappy(json);
    }


    public String SendCoupon(SendCouponCodeObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }


    public List<Notification> GetAllNotificationList(GetAllNotificationObjecttDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseNotification(json);
    }


    private List<Notification> ParseNotification(String jsonResponse) {
        String isverified = "", isflagged = "";
        final String EMPTY = "";
        final String DEFAULT_SPLIT = ", ";
        List<Notification> notifications = new ArrayList<>();
        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    String userinfo = "";

                    if (jsonObject.has("data"))
                        userinfo = jsonObject.getString("data");

                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");
                    try {
                        for (int i = 0; i < json_array.length(); i++) {

                            JSONObject UserInfoobj = new JSONObject(json_array.getString(i));
                            Notification item = new Notification();

                            if (UserInfoobj.has(Constants.NOTIFICATION_TEXT))
                                item.setText(UserInfoobj.getString(Constants.NOTIFICATION_TEXT) != null ? UserInfoobj.getString(Constants.NOTIFICATION_TEXT) : EMPTY);


                            if (UserInfoobj.has(Constants.SECRET_ID))
                                item.setSecret_id(UserInfoobj.getString(Constants.SECRET_ID) != null ? UserInfoobj.getString(Constants.SECRET_ID) : EMPTY);


                            if (UserInfoobj.has(Constants.NOTIFICATION_TYPE))
                                item.setType(UserInfoobj.getString(Constants.NOTIFICATION_TYPE) != null ? UserInfoobj.getString(Constants.NOTIFICATION_TYPE) : EMPTY);


                            if (UserInfoobj.has(Constants.NOTIFICATION_USER))
                                item.setUser(UserInfoobj.getString(Constants.NOTIFICATION_USER) != null ? UserInfoobj.getString(Constants.NOTIFICATION_USER) : EMPTY);

                            if (UserInfoobj.has(Constants.NOTIFICATION_READ_UNREAD))
                                item.setRead_status(UserInfoobj.getString(Constants.NOTIFICATION_READ_UNREAD) != null ? UserInfoobj.getString(Constants.NOTIFICATION_READ_UNREAD) : EMPTY);

                            if (UserInfoobj.has(Constants.NOTIFICATION_ID))
                                item.setObjectId(UserInfoobj.getString(Constants.NOTIFICATION_ID) != null ? UserInfoobj.getString(Constants.NOTIFICATION_ID) : EMPTY);



;
                            notifications.add(item);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notifications;
    }


    public String SendSupportRating(SendSupportRatingObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }

    private List<SupportOrganisation> ParseGetsupport(String jsonResponse) {

        String isverified = "", isflagged = "";
        final String EMPTY = "";
        final String DEFAULT_SPLIT = ", ";
        List<SupportOrganisation> supportOrganisations = new ArrayList<>();
        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    String userinfo = "";

                    if (jsonObject.has("data"))
                        userinfo = jsonObject.getString("data");

                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");
                    try {
                        for (int i = 0; i < json_array.length(); i++) {

                            JSONObject UserInfoobj = new JSONObject(json_array.getString(i));
                            SupportOrganisation item = new SupportOrganisation();
                            if (UserInfoobj.has(Constants.SUPPORT_ORGANISATIONS_LOCATIONS)) {
                                List<String> location_arr = new ArrayList<>();
                                location_arr.add(UserInfoobj.getString(Constants.SUPPORT_ORGANISATIONS_LOCATIONS));
                                item.setLocation(location_arr);
                            }
                            if (UserInfoobj.has(Constants.SUPPORT_ORGANISATIONS_NAME))
                                item.setName(UserInfoobj.getString(Constants.SUPPORT_ORGANISATIONS_NAME) != null ? UserInfoobj.getString(Constants.SUPPORT_ORGANISATIONS_NAME) : EMPTY);

                            if (UserInfoobj.has(Constants.SUPPORT_ORGANISATIONS_THUMB))
                                item.setThumb(UserInfoobj.getString(Constants.SUPPORT_ORGANISATIONS_THUMB) != null ? UserInfoobj.getString(Constants.SUPPORT_ORGANISATIONS_THUMB) : EMPTY);

                            if (UserInfoobj.has(Constants.SUPPORT_ORGANISATIONS_TRIGGERS)) {
                                JSONArray flausers = UserInfoobj.getJSONArray(Constants.SUPPORT_ORGANISATIONS_TRIGGERS);

                                ArrayList<String> flagusers = new ArrayList<String>();
                                for (int k = 0; k < flausers.length(); k++) {
                                    String kk = flausers.getString(k);
                                    flagusers.add(kk);
                                }
                                item.setTriggers(flagusers);

                            }
                            if (UserInfoobj.has(Constants.SUPPORT_ORGANISATIONS_URL))
                                item.setUrl(UserInfoobj.getString(Constants.SUPPORT_ORGANISATIONS_URL) != null ? UserInfoobj.getString(Constants.SUPPORT_ORGANISATIONS_URL) : EMPTY);

                            if (UserInfoobj.has(Constants.SUPPORT_ORGANISATIONS_TRIGGER_WORDS))
                                item.setTriggerWords(UserInfoobj.get(Constants.SUPPORT_ORGANISATIONS_TRIGGER_WORDS) != null ? Arrays.asList(UserInfoobj.getString(Constants.SUPPORT_ORGANISATIONS_TRIGGER_WORDS).split(DEFAULT_SPLIT)) : null);

                            if (UserInfoobj.has(Constants.SUPPORT_ORGANISATIONS_USERS_RATED)) {
                                JSONArray flausers = UserInfoobj.getJSONArray(Constants.SUPPORT_ORGANISATIONS_USERS_RATED);

                                ArrayList<String> flagusers = new ArrayList<String>();
                                for (int k = 0; k < flausers.length(); k++) {
                                    String kk = flausers.getString(k);
                                    flagusers.add(kk);
                                }
                                item.setUserRated(flagusers);

                                //item.setUserRated(UserInfoobj.get(Constants.SUPPORT_ORGANISATIONS_USERS_RATED) != null ? (List<String>) UserInfoobj.get(Constants.SUPPORT_ORGANISATIONS_USERS_RATED) : new ArrayList<String>());
                            }
                            if (UserInfoobj.has(Constants.SUPPORT_ORGANISATIONS_PHONE_NO))
                                item.setPhoneNo(UserInfoobj.getString(Constants.SUPPORT_ORGANISATIONS_PHONE_NO) != null ? UserInfoobj.getString(Constants.SUPPORT_ORGANISATIONS_PHONE_NO) : EMPTY);

                            if (UserInfoobj.has(Constants.SUPPORT_ORGANISATIONS_CLICKS))
                                item.setClicks(UserInfoobj.getInt(Constants.SUPPORT_ORGANISATIONS_CLICKS));

                            // if (UserInfoobj.has(Constants.SUPPORT_ORGANISATIONS_CLICKS))
                            // item.setObjectId(UserInfoobj.getObjectId() != null ? data.getObjectId() : EMPTY);

                            if (UserInfoobj.has(Constants.SUPPORT_ORGANISATIONS_USERS_CLICKED)) {
                                JSONArray flausers = UserInfoobj.getJSONArray(Constants.SUPPORT_ORGANISATIONS_USERS_CLICKED);

                                ArrayList<String> flagusers = new ArrayList<String>();
                                for (int k = 0; k < flausers.length(); k++) {
                                    String kk = flausers.getString(k);
                                    flagusers.add(kk);
                                }
                                item.setUsersClicked(flagusers);
                                // item.setUsersClicked(UserInfoobj.get(Constants.SUPPORT_ORGANISATIONS_USERS_CLICKED) != null ? (List<String>) UserInfoobj.get(Constants.SUPPORT_ORGANISATIONS_USERS_CLICKED) : new ArrayList<String>());
                            }
                            if (UserInfoobj.has(Constants.SUPPORT_ORGANISATIONS_UNIQUE_ID)) {
                                String id = UserInfoobj.getString(Constants.SUPPORT_ORGANISATIONS_UNIQUE_ID);

                                if (id != null && !id.equalsIgnoreCase(""))
                                    item.setOrgId(UserInfoobj.getString(Constants.SUPPORT_ORGANISATIONS_UNIQUE_ID));
                                else
                                    item.setOrgId("1111");
                            } else
                                item.setOrgId("1111");

                            supportOrganisations.add(item);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supportOrganisations;
    }



    public String Schoolrating(SchoolRatingObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseverifiedFlagged(json);
    }



    public String RedeemCounsellingCode(FinalObjectDTO contact)
    {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response))
            {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return json;
    }


    public String AcceptAppointment(Object contact)
    {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return json;
    }


    public String SuggestTime(Object contact)
    {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return json;
    }


    public List<RattingQuestionDTO> GetRatingQuestion(Object contact) {

        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }

        return ParseQuestion(json);
    }

    public String Getappointment(Object contact)
    {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return json;
    }


    public String SendPetname(String contact)
    {
      //  String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, contact);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, contact);
            }
        } else {
            json = makeConnection(RecoveryServer, contact);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, contact);
            }
        }
        return json;
    }


    public String CheckUserSession(CheckUserSessionObjectDTO contact)
    {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return json;
    }



    public String Getflagverified(FindbynameObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseverifiedFlagged(json);
    }


    public String SetDAte(SetDateOfBitrhObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }


    private String ParseverifiedFlagged(String jsonResponse) {

        String isverified = "", isflagged = "";

        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    String userinfo = "";

                    if (jsonObject.has("data"))
                        userinfo = jsonObject.getString("data");

                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json_array.length(); i++) {

                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                        if (UserInfoobj.has("is_verified"))
                            isverified = UserInfoobj.getString("is_verified");

                        if (UserInfoobj.has("is_flaged"))
                            isflagged = UserInfoobj.getString("is_flaged");


                        AppSession.save(mContext, Constants.USER_FLAG, isflagged);
                        AppSession.save(mContext, Constants.USER_VERIFIED, isverified);

                    }
                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isverified;
    }


    public String GetLastPostDate(GetLastPostdateObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return Parseday(json);
    }


    private String Parseday(String jsonResponse) {

        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (jsonObject.has("data"))
                    status = jsonObject.getString("data");


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    public String UpdateFlagVeryfi(SetVerifyFlagObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }


    public StataicObjectDTO GetstaticData(StaticObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseStaticdata(json);
    }


    private StataicObjectDTO ParseStaticdata(String jsonResponse) {


        StataicObjectDTO staticObjectDTO = null;
        IFriendSettingDTO iFriendSettingDTO = null;
        List<RiskWordDTO> riskWordDTOs = new ArrayList<>();
        List<SchoolDTO> schoolDTOs = new ArrayList<>();

        List<SecretCategoryDTO> secretCategoryDTOs = new ArrayList<>();

        String status = "", state = "", keywords = "", requestduration = "", maxfriend = "", quotes = "", creator ="", safeguard_update_date = "",session_duration = "", school_name = "", school_code = "", school_id = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {


                    JSONObject json_obj = null;
                    if (jsonObject.has("data"))
                        json_obj = jsonObject.getJSONObject("data");

                    JSONArray json_array = null;
                    if (json_obj.has("risk_word"))
                        json_array = json_obj.getJSONArray("risk_word");


                    for (int i = 0; i < json_array.length(); i++) {

                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));


                        if (UserInfoobj.has("state"))
                            state = UserInfoobj.getString("state");

                        JSONArray keys_array = null;
                        if (UserInfoobj.has("keys"))
                            keys_array = UserInfoobj.getJSONArray("keys");

                        String flausers1[] = toStringArray(keys_array);

                        ArrayList<String> flagusers = new ArrayList<String>();
                        for (int k = 0; k < flausers1.length; k++) {
                            flagusers.add(flausers1[k]);
                        }
                        riskWordDTOs.add(new RiskWordDTO(state, flagusers));

                    }

                    JSONArray keywords_array = null;
                    if (json_obj.has("keywords"))
                        keywords_array = json_obj.getJSONArray("keywords");


                    for (int i = 0; i < keywords_array.length(); i++) {

                        JSONObject UserInfoobj = new JSONObject(keywords_array.getString(i));


                        JSONArray keys_array = null;
                        if (UserInfoobj.has("keys"))
                            keys_array = UserInfoobj.getJSONArray("keys");

                        String flausers1[] = toStringArray(keys_array);
                        keywords = flausers1[0];


                    }


                    JSONArray categ_array = null;
                    if (json_obj.has("category"))
                        categ_array = json_obj.getJSONArray("category");


                    for (int i = 0; i < categ_array.length(); i++) {

                        JSONObject UserInfoobj = new JSONObject(categ_array.getString(i));


                        if (UserInfoobj.has("clnm01"))
                            state = UserInfoobj.getString("clnm01");

                        JSONArray keys_array = null;
                        if (UserInfoobj.has("keys"))
                            keys_array = UserInfoobj.getJSONArray("keys");

                        String flausers1[] = toStringArray(keys_array);

                        ArrayList<String> flagusers = new ArrayList<String>();
                        for (int k = 0; k < flausers1.length; k++) {
                            flagusers.add(flausers1[k]);
                        }
                        secretCategoryDTOs.add(new SecretCategoryDTO(state, flagusers));

                    }


                    JSONArray setting_array = null;
                    if (json_obj.has("ifriend_settings"))
                        setting_array = json_obj.getJSONArray("ifriend_settings");


                    for (int i = 0; i < setting_array.length(); i++) {

                        JSONObject UserInfoobj = new JSONObject(setting_array.getString(i));


                        if (UserInfoobj.has("requestduration"))
                            requestduration = UserInfoobj.getString("requestduration");

                        if (UserInfoobj.has("maxfriend"))
                            maxfriend = UserInfoobj.getString("maxfriend");

                        if (UserInfoobj.has("session_duration"))
                            session_duration = UserInfoobj.getString("session_duration");

                        if (UserInfoobj.has("safeguard_update_date"))
                            safeguard_update_date = UserInfoobj.getString("safeguard_update_date");

                        if (UserInfoobj.has("safeguard_update_date"))
                            safeguard_update_date = UserInfoobj.getString("safeguard_update_date");

                        if (UserInfoobj.has("quotes"))
                            quotes = UserInfoobj.getString("quotes");

                        if (UserInfoobj.has("creator"))
                            creator = UserInfoobj.getString("creator");

                        iFriendSettingDTO = new IFriendSettingDTO(requestduration, maxfriend, session_duration , safeguard_update_date ,quotes , creator);

                    }


                    JSONArray school_array = null;
                    if (json_obj.has("school"))
                        setting_array = json_obj.getJSONArray("school");


                    for (int i = 0; i < setting_array.length(); i++) {

                        JSONObject UserInfoobj = new JSONObject(setting_array.getString(i));


                        if (UserInfoobj.has("clnm01"))
                            school_name = UserInfoobj.getString("clnm01");

                        if (UserInfoobj.has("code"))
                            school_code = UserInfoobj.getString("code");

                        if (UserInfoobj.has("school_id"))
                            school_id = UserInfoobj.getString("school_id");


                        JSONArray keys_array = null;
                        if (UserInfoobj.has("users"))
                            keys_array = UserInfoobj.getJSONArray("users");

                        String flausers1[] = toStringArray(keys_array);

                        ArrayList<String> flagusers = new ArrayList<String>();
                        for (int k = 0; k < flausers1.length; k++) {
                            flagusers.add(flausers1[k]);
                        }

                        SchoolDTO schoolDTO = new SchoolDTO();

                        schoolDTO.setCode(school_code);
                        schoolDTO.setName(school_name);
                        schoolDTO.setUsers(flagusers);
                        schoolDTO.setSchool_id(school_id);
                        schoolDTOs.add(schoolDTO);
                    }


                    staticObjectDTO = new StataicObjectDTO(riskWordDTOs, keywords, secretCategoryDTOs, iFriendSettingDTO, schoolDTOs);

                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staticObjectDTO;
    }

    public String UpdateScratch(ScratchCouponObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }

    public PetAvtarInfoDTO SetPetInfo(Object contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseAvtarInfo(json);
    }


    private PetAvtarInfoDTO ParseAvtarInfo(String jsonResponse) {

        String m2_water = "";
        String ug_oxygen = "";
        String heart_food = "";
        String scratch_count = "";
        String total_scratch_count = "";
        String comments_count = "";
        PetAvtarInfoDTO petAvtarInfoDTO = null;
        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    String userinfo = "";

                    if (jsonObject.has("data"))
                        userinfo = jsonObject.getString("data");

                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json_array.length(); i++) {

                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));


                        if (UserInfoobj.has("m2_water"))
                            m2_water = UserInfoobj.getString("m2_water");

                        if (UserInfoobj.has("hug_oxygen"))
                            ug_oxygen = UserInfoobj.getString("hug_oxygen");

                        if (UserInfoobj.has("heart_food"))
                            heart_food = UserInfoobj.getString("heart_food");

                        if (UserInfoobj.has("scratch_count"))
                            scratch_count = UserInfoobj.getString("scratch_count");

                        if (UserInfoobj.has("total_scratch_count"))
                            total_scratch_count = UserInfoobj.getString("total_scratch_count");

                        if (UserInfoobj.has("comments_count"))
                            comments_count = UserInfoobj.getString("comments_count");


                        petAvtarInfoDTO = new PetAvtarInfoDTO(m2_water, ug_oxygen, heart_food, scratch_count, total_scratch_count, comments_count);
                    }
                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return petAvtarInfoDTO;
    }


    public List<Secret> FindHappySecret(FindObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseHappy(json);
    }

    public List<Secret> ParseOfflinesecret(String jsonResponse)
    {
        String username = "";
        String age = "", gender = "", pin = "", security_answer = "", security_question = "";
        List<Secret> list = new ArrayList<>();

        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);







                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json_array.length(); i++) {
                        Secret secret = new Secret();
                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                        if (UserInfoobj.has("address"))
                            secret.setAddress(UserInfoobj.getString("address"));
                        if (UserInfoobj.has("age"))
                            secret.setAge(UserInfoobj.getString("age"));
                        if (UserInfoobj.has("bgImageName"))
                            secret.setBgImageName(UserInfoobj.getString("bgImageName"));

                        if (UserInfoobj.has("category"))
                            secret.setCategory(UserInfoobj.getString("category"));

                        try {
                            if (UserInfoobj.has("createdByUser"))
                                secret.setCreatedByUser(UserInfoobj.getString("createdByUser"));
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                        try {
                            if (UserInfoobj.has("date"))
                                secret.setDate(Utils.StringTodate(UserInfoobj.getString("date")));
                        } catch (Exception e) {

                        }
                        if (UserInfoobj.has("feel"))
                            secret.setFeel(UserInfoobj.getString("feel"));


                        if (UserInfoobj.has("flagUsers")) {


                            JSONArray flausers = UserInfoobj.getJSONArray("flagUsers");

                            String flausers1[] = toStringArray(flausers);

                            ArrayList<String> flagusers = new ArrayList<String>();
                            for (int k = 0; k < flausers1.length; k++) {
                                flagusers.add(flausers1[k]);
                            }
                            secret.setFlagUsers(flagusers);


                        } else {
                            secret.setFlagUsers(new ArrayList<String>());
                        }
                        if (UserInfoobj.has("flags")) {
                            String flags = UserInfoobj.getString("flags");
                            secret.setFlags(Integer.parseInt(flags));
                        } else {
                            secret.setFlags(0);
                        }
                        if (UserInfoobj.has("gender")) {
                            gender = UserInfoobj.getString("gender");
                            secret.setGender(gender);

                        } else {
                            secret.setGender("");
                        }

                        if (UserInfoobj.has("hasNotification")) {
                            String hasNotification = UserInfoobj.getString("hasNotification");

                            if (hasNotification != null && hasNotification.equalsIgnoreCase("true"))
                                secret.setHasNotification(true);
                            else
                                secret.setHasNotification(false);
                        } else
                            secret.setHasNotification(false);


                        if (UserInfoobj.has(Constants.SECRET_HASH_TAGS)) {
                            String temp = UserInfoobj.getString(Constants.SECRET_HASH_TAGS);
                            secret.setHashtags(temp);

                        } else {
                            secret.setHashtags("");
                        }


                        if (UserInfoobj.has("heartUsers")) {

                            JSONArray flausers = UserInfoobj.getJSONArray("heartUsers");

                            String flausers1[] = toStringArray(flausers);

                            ArrayList<String> flagusers = new ArrayList<String>();
                            for (int k = 0; k < flausers1.length; k++) {
                                flagusers.add(flausers1[k]);
                            }
                            secret.setHeartUsers(flagusers);
                        } else {
                            secret.setHeartUsers(new ArrayList<String>());
                        }


                        if (UserInfoobj.has(Constants.SECRET_HEARTS)) {
                            String flags = UserInfoobj.getString(Constants.SECRET_HEARTS);
                            secret.setHearts(Integer.parseInt(flags));
                        } else {
                            secret.setHearts(0);
                        }

                        if (UserInfoobj.has(Constants.SECRET_HOME)) {
                            String temp = UserInfoobj.getString(Constants.SECRET_HOME);
                            secret.setHome(temp);

                        } else {
                            secret.setHome("");
                        }

                        if (UserInfoobj.has("hugUsers")) {
                            JSONArray flausers = UserInfoobj.getJSONArray("hugUsers");

                            String flausers1[] = toStringArray(flausers);

                            ArrayList<String> flagusers = new ArrayList<String>();
                            for (int k = 0; k < flausers1.length; k++) {
                                flagusers.add(flausers1[k]);
                            }
                            secret.setHugUsers(flagusers);


                        } else {
                            secret.setHugUsers(new ArrayList<String>());
                        }

                        try {
                            if (UserInfoobj.has(Constants.SECRET_HUGS)) {
                                String flags = UserInfoobj.getString(Constants.SECRET_HUGS);
                                secret.setHugs(Integer.parseInt(flags));
                            } else {
                                secret.setHugs(0);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            secret.setHugs(0);
                        }


                        //   secret.setImage(data.getParseFile(Constants.SECRET_IMAGE) != null ? data.getParseFile(Constants.SECRET_IMAGE) : null);

                        if (UserInfoobj.has("latLon")) {
                            String temp = UserInfoobj.getString("latLon");
                            secret.setLatLon(temp);

                        } else {
                            secret.setLatLon("");
                        }


                        try {
                            if (UserInfoobj.has(Constants.SECRET_LIKES)) {
                                String flags = UserInfoobj.getString(Constants.SECRET_LIKES);
                                secret.setLikes(Integer.parseInt(flags));
                            } else {
                                secret.setLikes(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            secret.setLikes(0);
                        }
                        if (UserInfoobj.has("me2Users")) {
                            try {
                                JSONArray flausers = UserInfoobj.getJSONArray("me2Users");

                                String flausers1[] = toStringArray(flausers);

                                ArrayList<String> flagusers = new ArrayList<String>();
                                for (int k = 0; k < flausers1.length; k++) {
                                    flagusers.add(flausers1[k]);
                                }
                                secret.setMe2Users(flagusers);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {
                            secret.setMe2Users(new ArrayList<String>());
                        }

                        try {
                            if (UserInfoobj.has(Constants.SECRET_ME2S)) {
                                String flags = UserInfoobj.getString(Constants.SECRET_ME2S);
                                secret.setMe2s(Integer.parseInt(flags));
                            } else {
                                secret.setMe2s(0);
                            }
                        } catch (Exception e) {
                            secret.setMe2s(0);
                            e.printStackTrace();
                        }


                        if (UserInfoobj.has("notificationSent")) {
                            String flags = UserInfoobj.getString("notificationSent");
                            secret.setNotificationSent(Integer.parseInt(flags));
                        } else {
                            secret.setNotificationSent(0);
                        }


                        if (UserInfoobj.has("readNotification"))
                        {
                            String hasNotification = UserInfoobj.getString("readNotification");
                            if (hasNotification != null && hasNotification.equalsIgnoreCase("true"))
                                secret.setReadNotification(true);
                            else
                                secret.setReadNotification(false);
                        } else
                            secret.setReadNotification(false);


                        if (UserInfoobj.has("riskState")) {
                            String temp = UserInfoobj.getString("riskState");
                            secret.setRiskState(temp);

                        } else {
                            secret.setRiskState("");
                        }

                        if (UserInfoobj.has(Constants.SECRET_STRESS_LEVEL)) {
                            String temp = UserInfoobj.getString(Constants.SECRET_STRESS_LEVEL);
                            secret.setStressLevel(temp);

                        } else {
                            secret.setStressLevel("");
                        }

                        if (UserInfoobj.has("supportOrganisations")) {
                            String flausers = UserInfoobj.getString("supportOrganisations");
                            ArrayList<String> flagusers = new ArrayList<String>();
                            flagusers.add(flausers);
                            secret.setSupportOrganisations(flagusers);

                        } else {
                            secret.setSupportOrganisations(new ArrayList<String>());
                        }

                        if (UserInfoobj.has("text")) {
                            String temp = UserInfoobj.getString("text");
                            secret.setText(temp);

                        } else {
                            secret.setText("");
                        }

                        try {
                            if (UserInfoobj.has(Constants.SECRET_WMS)) {
                                String flags = UserInfoobj.getString(Constants.SECRET_WMS);
                                secret.setWms(Integer.parseInt(flags));
                            } else {
                                secret.setWms(0);
                            }
                        } catch (Exception e) {
                            secret.setWms(0);
                            e.printStackTrace();
                        }


                        if (UserInfoobj.has("objectId")) {
                            String temp = UserInfoobj.getString("objectId");
                            secret.setObjectId(temp);

                        } else {
                            secret.setObjectId("");
                        }


                        if (UserInfoobj.has("comment_count")) {
                            String temp = UserInfoobj.getString("comment_count");
                            if (temp != null && !temp.equalsIgnoreCase("") && !temp.equalsIgnoreCase("null"))
                                secret.setComment_count(temp);
                            else
                                secret.setComment_count("0");


                        } else {
                            secret.setComment_count("0");
                        }


                        try {
                            if (UserInfoobj.has("createdDate"))
                                secret.setCreatedDate(Utils.StringTodate(UserInfoobj.getString("createdDate")));
                        } catch (Exception e) {

                        }

                        try {
                            if (UserInfoobj.has(Constants.SECRET_FONT)) {
                                String flags = UserInfoobj.getString(Constants.SECRET_FONT);
                                secret.setFont(Integer.parseInt(flags));
                            } else {

                            }

                        } catch (Exception e) {
                            secret.setFont(0);
                            e.printStackTrace();
                        }

                        secret.setShowCoupon(i % 3);


                        list.add(secret);
                        //  secret.setUserFilled(data.getParseObject(Constants.INCLUDE_USER_POINTER) != null ? data.getParseObject(Constants.INCLUDE_USER_POINTER) : null);
                        // secret.setFont(data.get(Constants.SECRET_FONT) != null ? data.getInt(Constants.SECRET_FONT) : 1);


                    }



            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    private List<Secret> ParseHappy(String jsonResponse) {
        String username = "";
        String age = "", gender = "", pin = "", security_answer = "", security_question = "";
        List<Secret> list = new ArrayList<>();

        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    String userinfo = "";


                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json_array.length(); i++)
                    {
                        Secret secret = new Secret();
                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                        if (UserInfoobj.has("cladd01"))
                            secret.setAddress(UserInfoobj.getString("cladd01"));



                        if (UserInfoobj.has("hide_users"))
                        {

                            JSONArray flausers = UserInfoobj.getJSONArray("hide_users");
                            String flausers1[] = toStringArray(flausers);
                            ArrayList<String> flagusers = new ArrayList<String>();
                            for (int k = 0; k < flausers1.length; k++) {
                                flagusers.add(flausers1[k]);
                            }

                                if(flagusers.contains(MainActivity.enc_username))
                                    continue;
                        }

                        if (UserInfoobj.has("age"))
                            secret.setAge(UserInfoobj.getString("age"));
                        if (UserInfoobj.has("bg_image_name"))
                            secret.setBgImageName(UserInfoobj.getString("bg_image_name"));

                        if (UserInfoobj.has("category"))
                            secret.setCategory(UserInfoobj.getString("category"));

                        try {
                            if (UserInfoobj.has("clcbuser01"))
                                secret.setCreatedByUser(UserInfoobj.getString("clcbuser01"));
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                        try {
                            if (UserInfoobj.has("created_at"))
                                secret.setDate(Utils.StringTodate(UserInfoobj.getString("created_at")));
                        } catch (Exception e) {

                        }
                        if (UserInfoobj.has("feel"))
                            secret.setFeel(UserInfoobj.getString("feel"));


                        if (UserInfoobj.has(Constants.SECRET_FLAG_USERS)) {


                            JSONArray flausers = UserInfoobj.getJSONArray(Constants.SECRET_FLAG_USERS);

                            String flausers1[] = toStringArray(flausers);

                            ArrayList<String> flagusers = new ArrayList<String>();
                            for (int k = 0; k < flausers1.length; k++) {
                                flagusers.add(flausers1[k]);
                            }
                            secret.setFlagUsers(flagusers);


                        } else {
                            secret.setFlagUsers(new ArrayList<String>());
                        }
                        if (UserInfoobj.has("flags")) {
                            String flags = UserInfoobj.getString("flags");
                            secret.setFlags(Integer.parseInt(flags));
                        } else {
                            secret.setFlags(0);
                        }
                        if (UserInfoobj.has("gender")) {
                            gender = UserInfoobj.getString("gender");
                            secret.setGender(gender);

                        } else {
                            secret.setGender("");
                        }

                        if (UserInfoobj.has("has_notification")) {
                            String hasNotification = UserInfoobj.getString("has_notification");

                            if (hasNotification != null && hasNotification.equalsIgnoreCase("true"))
                                secret.setHasNotification(true);
                            else
                                secret.setHasNotification(false);
                        } else
                            secret.setHasNotification(false);


                        if (UserInfoobj.has(Constants.SECRET_HASH_TAGS)) {
                            String temp = UserInfoobj.getString(Constants.SECRET_HASH_TAGS);
                            secret.setHashtags(temp);

                        } else {
                            secret.setHashtags("");
                        }


                        if (UserInfoobj.has(Constants.SECRET_HEART_USERS)) {

                            JSONArray flausers = UserInfoobj.getJSONArray(Constants.SECRET_HEART_USERS);

                            String flausers1[] = toStringArray(flausers);

                            ArrayList<String> flagusers = new ArrayList<String>();
                            for (int k = 0; k < flausers1.length; k++) {
                                flagusers.add(flausers1[k]);
                            }
                            secret.setHeartUsers(flagusers);
                        } else {
                            secret.setHeartUsers(new ArrayList<String>());
                        }


                        if (UserInfoobj.has(Constants.SECRET_HEARTS)) {
                            String flags = UserInfoobj.getString(Constants.SECRET_HEARTS);
                            secret.setHearts(Integer.parseInt(flags));
                        } else {
                            secret.setHearts(0);
                        }

                        if (UserInfoobj.has(Constants.SECRET_HOME)) {
                            String temp = UserInfoobj.getString(Constants.SECRET_HOME);
                            secret.setHome(temp);

                        } else {
                            secret.setHome("");
                        }

                        if (UserInfoobj.has(Constants.SECRET_HUG_USERS)) {
                            JSONArray flausers = UserInfoobj.getJSONArray(Constants.SECRET_HUG_USERS);

                            String flausers1[] = toStringArray(flausers);

                            ArrayList<String> flagusers = new ArrayList<String>();
                            for (int k = 0; k < flausers1.length; k++) {
                                flagusers.add(flausers1[k]);
                            }
                            secret.setHugUsers(flagusers);


                        } else {
                            secret.setHugUsers(new ArrayList<String>());
                        }

                        try {
                            if (UserInfoobj.has(Constants.SECRET_HUGS)) {
                                String flags = UserInfoobj.getString(Constants.SECRET_HUGS);
                                secret.setHugs(Integer.parseInt(flags));
                            } else {
                                secret.setHugs(0);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            secret.setHugs(0);
                        }


                        //   secret.setImage(data.getParseFile(Constants.SECRET_IMAGE) != null ? data.getParseFile(Constants.SECRET_IMAGE) : null);

                        if (UserInfoobj.has(Constants.SECRET_LAT_LONG)) {
                            String temp = UserInfoobj.getString(Constants.SECRET_LAT_LONG);
                            secret.setLatLon(temp);

                        } else {
                            secret.setLatLon("");
                        }


                        try {
                            if (UserInfoobj.has(Constants.SECRET_LIKES)) {
                                String flags = UserInfoobj.getString(Constants.SECRET_LIKES);
                                secret.setLikes(Integer.parseInt(flags));
                            } else {
                                secret.setLikes(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            secret.setLikes(0);
                        }
                        if (UserInfoobj.has(Constants.SECRET_ME2USERS)) {
                            try {
                                JSONArray flausers = UserInfoobj.getJSONArray(Constants.SECRET_ME2USERS);

                                String flausers1[] = toStringArray(flausers);

                                ArrayList<String> flagusers = new ArrayList<String>();
                                for (int k = 0; k < flausers1.length; k++) {
                                    flagusers.add(flausers1[k]);
                                }
                                secret.setMe2Users(flagusers);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {
                            secret.setMe2Users(new ArrayList<String>());
                        }

                        try {
                            if (UserInfoobj.has(Constants.SECRET_ME2S)) {
                                String flags = UserInfoobj.getString(Constants.SECRET_ME2S);
                                secret.setMe2s(Integer.parseInt(flags));
                            } else {
                                secret.setMe2s(0);
                            }
                        } catch (Exception e) {
                            secret.setMe2s(0);
                            e.printStackTrace();
                        }


                        if (UserInfoobj.has(Constants.SECRET_NOTIFICATION_SENT)) {
                            String flags = UserInfoobj.getString(Constants.SECRET_NOTIFICATION_SENT);
                            secret.setNotificationSent(Integer.parseInt(flags));
                        } else {
                            secret.setNotificationSent(0);
                        }


                        if (UserInfoobj.has(Constants.SECRET_READ_NOTIFICATION))
                        {
                            String hasNotification = UserInfoobj.getString(Constants.SECRET_READ_NOTIFICATION);
                            if (hasNotification != null && hasNotification.equalsIgnoreCase("true"))
                                secret.setReadNotification(true);
                            else
                                secret.setReadNotification(false);
                        } else
                            secret.setReadNotification(false);


                        if (UserInfoobj.has(Constants.SECRET_RISK_STATE)) {
                            String temp = UserInfoobj.getString(Constants.SECRET_RISK_STATE);
                            secret.setRiskState(temp);

                        } else {
                            secret.setRiskState("");
                        }

                        if (UserInfoobj.has(Constants.SECRET_STRESS_LEVEL)) {
                            String temp = UserInfoobj.getString(Constants.SECRET_STRESS_LEVEL);
                            secret.setStressLevel(temp);

                        } else {
                            secret.setStressLevel("");
                        }

                        if (UserInfoobj.has(Constants.SECRET_SUPPORT_ORGANISATIONS)) {
                            String flausers = UserInfoobj.getString(Constants.SECRET_SUPPORT_ORGANISATIONS);
                            ArrayList<String> flagusers = new ArrayList<String>();
                            flagusers.add(flausers);
                            secret.setSupportOrganisations(flagusers);

                        } else {
                            secret.setSupportOrganisations(new ArrayList<String>());
                        }

                        if (UserInfoobj.has("cltxt01")) {
                            String temp = UserInfoobj.getString("cltxt01");
                            temp =  CryptLib.decrypt(temp);
                            secret.setText(temp);

                        } else {
                            secret.setText("");
                        }

                        try {
                            if (UserInfoobj.has(Constants.SECRET_WMS)) {
                                String flags = UserInfoobj.getString(Constants.SECRET_WMS);
                                secret.setWms(Integer.parseInt(flags));
                            } else {
                                secret.setWms(0);
                            }
                        } catch (Exception e) {
                            secret.setWms(0);
                            e.printStackTrace();
                        }


                        if (UserInfoobj.has("secret_id")) {
                            String temp = UserInfoobj.getString("secret_id");
                            secret.setObjectId(temp);

                        } else {
                            secret.setObjectId("");
                        }


                        if (UserInfoobj.has("comment_count")) {
                            String temp = UserInfoobj.getString("comment_count");
                            if (temp != null && !temp.equalsIgnoreCase("") && !temp.equalsIgnoreCase("null"))
                                secret.setComment_count(temp);
                            else
                                secret.setComment_count("0");


                        } else {
                            secret.setComment_count("0");
                        }


                        try {
                            if (UserInfoobj.has("created_at"))
                                secret.setCreatedDate(Utils.StringTodate(UserInfoobj.getString("created_at")));
                        } catch (Exception e) {

                        }

                        try {
                            if (UserInfoobj.has(Constants.SECRET_FONT)) {
                                String flags = UserInfoobj.getString(Constants.SECRET_FONT);
                                secret.setFont(Integer.parseInt(flags));
                            } else {

                            }

                        } catch (Exception e) {
                            secret.setFont(0);
                            e.printStackTrace();
                        }

                        secret.setShowCoupon(i % 3);


                        list.add(secret);
                        //  secret.setUserFilled(data.getParseObject(Constants.INCLUDE_USER_POINTER) != null ? data.getParseObject(Constants.INCLUDE_USER_POINTER) : null);
                        // secret.setFont(data.get(Constants.SECRET_FONT) != null ? data.getInt(Constants.SECRET_FONT) : 1);


                    }
                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public String AddComment(CommentObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseOnlySuccess(json);
    }


    public String AddSecret(SecretObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseOnlySuccess(json);
    }


    private String ParseOnlySuccess(String jsonResponse) {
        String username = "";
        String age = "", gender = "", pin = "", security_answer = "", security_question = "";


        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {

                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");

                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    public String UpdateToken(DeviceTokenObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")||  json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }


    public String ResetPassword(Object contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return json;
    }


    public String LoginUser(LoginbjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseLogin(json);
    }

    public String Getchatmessag(String jsonData) {

        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return json;
    }


    public String Sendmessage(String jsonData) {

        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return "";
    }

    private String ParseLogin(String jsonResponse) {
        String username = "";
        String age = "", gender = "", pin = "", security_answer = "", security_question = "";
        String me2s = "", hugs = "", hearts = "", pet_name = "",tandc = "",user_firstname = "",
                isverified = "", isflagged = "", secrets = "", birthday = "",safe_guard = "", disclaimer = "" , custom_pet_name = "";

        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    String userinfo = "";

                    if (jsonObject.has("data"))
                        userinfo = jsonObject.getString("data");

                    JSONArray json_array = null;
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json_array.length(); i++) {

                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));


                        if (UserInfoobj.has("clun01"))
                            username = UserInfoobj.getString("clun01");

                        if (username != null && !username.equalsIgnoreCase(""))
                            status = "success";
                        else
                            return "";


                        if (UserInfoobj.has("age"))
                            age = UserInfoobj.getString("age");


                        if (UserInfoobj.has("gender"))
                            gender = UserInfoobj.getString("gender");

                        if (UserInfoobj.has("pin"))
                            pin = UserInfoobj.getString("pin");

                        if (UserInfoobj.has("security_answer"))
                            security_answer = UserInfoobj.getString("security_answer");

                        if (UserInfoobj.has("security_question"))
                            security_question = UserInfoobj.getString("security_question");

                        Preference.saveUserAge(age);

                        Preference.saveUserName(CryptLib.decrypt(username));


                        if (UserInfoobj.has("me2s"))
                            me2s = UserInfoobj.getString("me2s");

                        if (UserInfoobj.has("hearts"))
                            hearts = UserInfoobj.getString("hearts");

                        if (UserInfoobj.has("hugs"))
                            hugs = UserInfoobj.getString("hugs");


                        if (UserInfoobj.has("myvirtual_pet"))
                            pet_name = UserInfoobj.getString("myvirtual_pet");

                        if (UserInfoobj.has("is_verified"))
                            isverified = UserInfoobj.getString("is_verified");

                        if (UserInfoobj.has("is_flaged"))
                            isflagged = UserInfoobj.getString("is_flaged");

                        if (UserInfoobj.has("secrets"))
                            secrets = UserInfoobj.getString("secrets");


                        if (UserInfoobj.has("birthday"))
                            birthday = UserInfoobj.getString("birthday");

                        if (UserInfoobj.has("tc_accepted"))
                            tandc = UserInfoobj.getString("tc_accepted");


                        if (UserInfoobj.has("disclaimer"))
                            disclaimer = UserInfoobj.getString("disclaimer");

                        if (UserInfoobj.has("safeguarding"))
                            safe_guard = UserInfoobj.getString("safeguarding");

                        if (UserInfoobj.has("user_firstname"))
                            user_firstname = UserInfoobj.getString("user_firstname");



                        if (UserInfoobj.has("custom_pet_name"))
                            custom_pet_name = UserInfoobj.getString("custom_pet_name");

                        if (disclaimer != null && !disclaimer.equalsIgnoreCase("") && disclaimer.equalsIgnoreCase("true"))
                        {
                            Preference.saveUserDisclaimer(true);

                        } else {
                            Preference.saveUserDisclaimer(false);
                        }

                        AppSession.save(mContext, Constants.USER_SAFE_GUARD, safe_guard);


                        AppSession.save(mContext, Constants.USER_ME2S, me2s);
                        AppSession.save(mContext, Constants.USER_HEARTS, hearts);
                        AppSession.save(mContext, Constants.USER_HUGS, hugs);
                        AppSession.save(mContext, Constants.USER_FIRST_NAME, user_firstname);
                        AppSession.save(mContext, Constants.USER_LOCAL_PET_NAME, custom_pet_name);

                        AppSession.save(mContext, Constants.USER_NAME, CryptLib.decrypt(username));
                        AppSession.save(mContext, Constants.USER_PIN, CryptLib.decrypt(pin));
                        AppSession.save(mContext, Constants.USER_SECURITY_QUESTION, CryptLib.decrypt(security_question));
                        AppSession.save(mContext, Constants.USER_SECURITY_ANSWER, CryptLib.decrypt(security_answer));
                        AppSession.save(mContext, Constants.USER_GENDER, gender);
                        AppSession.save(mContext, Constants.USER_AGE, age);
                        AppSession.save(mContext, Constants.USER_PET_NAME, pet_name);

                        if(birthday != null && !birthday.equalsIgnoreCase("")) {
                            birthday = birthday.substring(0,10);

                            AppSession.save(mContext, Constants.USER_DATE_OF_BIRTH, birthday);
                        }
                        AppSession.save(mContext,Constants.USER_TERMSCONDITION,tandc);

                        if (secrets != null && !secrets.equalsIgnoreCase("")) {
                            AppSession.save(mContext, Constants.USER_SECRETS, secrets);
                        } else {
                            AppSession.save(mContext, Constants.USER_SECRETS, "0");
                        }


                        if (isflagged != null && !isflagged.equalsIgnoreCase("")) {
                            AppSession.save(mContext, Constants.USER_FLAG, isflagged);
                        } else {
                            AppSession.save(mContext, Constants.USER_FLAG, "false");
                        }

                        if (isverified != null && !isverified.equalsIgnoreCase("")) {
                            AppSession.save(mContext, Constants.USER_VERIFIED, isverified);
                        } else {
                            AppSession.save(mContext, Constants.USER_VERIFIED, "false");
                        }

                    }
                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    public String RegistraterUser(RegistrationObjectDTO contact) {
        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseRegisration(json);
    }

    private String ParseRegisration(String jsonResponse) {
        String username = "";
        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);


                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    String userinfo = "";
                    if (jsonObject.has("data"))
                        userinfo = jsonObject.getString("data");

                    JSONObject UserInfoobj = new JSONObject(userinfo);

                    if (UserInfoobj.has("clun01"))
                        username = UserInfoobj.getString("clun01");

                    if (username != null && !username.equalsIgnoreCase(""))
                        status = "success";


                } else {
                    if (jsonObject.has("msg"))
                        status = jsonObject.getString("msg");
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    public List<Secret> GetMoodGraphSecret(GetMoodGraphObjectDTO contact) {

        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseHappy(json);

    }


    public int GetUnreadmessage(GetUnreadMessageObjectDTO contact) {

        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseUnreadCount(json);

    }

    private int ParseUnreadCount(String jsonResponse) {

        int unread_count = 0;
        List<ChatMessageDTO> chatMessageDTO = new ArrayList<>();
        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("count"))
                    status = jsonObject.getString("count");

                if (status != null && !status.equalsIgnoreCase(""))
                    unread_count = Integer.parseInt(status);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unread_count;
    }


    public List<IfriendListDTO> GetIfriendlist(IfriendRequestObjectDTO contact) {

        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseIfriendlist(json);
        //  return  "";
    }


    public String sendAcceptRequest(IfriendAcceptObjectDTO contact) {

        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }

        return ParseAccept(json);
        //  return  "";
    }


    public String GetDateTime(GetCurrentDateObjectDTO contact) {

        try {
            String jsonData = new Gson().toJson(contact);
            if (Constants.primary_server) {
                json = makeConnection(MainServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = false;
                    json = makeConnection(RecoveryServer, jsonData);
                }
            } else {
                json = makeConnection(RecoveryServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = true;
                    json = makeConnection(MainServer, jsonData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ParseDateTime(json);
        //  return  "";
    }


    public String sendrating(Object contact) {

        try {
            String jsonData = new Gson().toJson(contact);
            if (Constants.primary_server) {
                json = makeConnection(MainServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = false;
                    json = makeConnection(RecoveryServer, jsonData);
                }
            } else {
                json = makeConnection(RecoveryServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = true;
                    json = makeConnection(MainServer, jsonData);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }



    public String SendAttanduser(Object contact) {

        try {
            String jsonData = new Gson().toJson(contact);
            if (Constants.primary_server) {
                json = makeConnection(MainServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = false;
                    json = makeConnection(RecoveryServer, jsonData);
                }
            } else {
                json = makeConnection(RecoveryServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = true;
                    json = makeConnection(MainServer, jsonData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
        //  return  "";
    }


    public String SendRatingquestion(SentImpactratinganswerObjectDTO contact) {

        try {
            String jsonData = new Gson().toJson(contact);
            if (Constants.primary_server) {
                json = makeConnection(MainServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = false;
                    json = makeConnection(RecoveryServer, jsonData);
                }
            } else {
                json = makeConnection(RecoveryServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = true;
                    json = makeConnection(MainServer, jsonData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
        //  return  "";
    }


    public String SendmodGraph(Object contact) {

        try {
            String jsonData = new Gson().toJson(contact);
            if (Constants.primary_server) {
                json = makeConnection(MainServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = false;
                    json = makeConnection(RecoveryServer, jsonData);
                }
            } else {
                json = makeConnection(RecoveryServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = true;
                    json = makeConnection(MainServer, jsonData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
        //  return  "";
    }



    public List<SendImpactRatingDTO> GetImpactRatingquestion(GetImpactRatingObjectDTO contact) {

        try {
            String jsonData = new Gson().toJson(contact);
            if (Constants.primary_server) {
                json = makeConnection(MainServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = false;
                    json = makeConnection(RecoveryServer, jsonData);
                }
            } else {
                json = makeConnection(RecoveryServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = true;
                    json = makeConnection(MainServer, jsonData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ParseImpactRating(json);
        //  return  "";
    }


    public String sendIfriendsecretpush(IfriendsecrtpushobjectDTO contact) {

        try {
            String jsonData = new Gson().toJson(contact);
            if (Constants.primary_server) {
                json = makeConnection(MainServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = false;
                    json = makeConnection(RecoveryServer, jsonData);
                }
            } else {
                json = makeConnection(RecoveryServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = true;
                    json = makeConnection(MainServer, jsonData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ParseAccept(json);
        //  return  "";
    }


    /*public String sendmehuglike(LikeHugNotificationObjectDTO contact) {

        try {
            String jsonData = new Gson().toJson(contact);
            String serviceUrl = Baseurl;
            json = makeConnection(serviceUrl, jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ParseAccept(json);
        //  return  "";
    }
*/

   /* public String sendSharesecretpush(SecretPushNotificayionObjectDTO contact) {

        try {
            String jsonData = new Gson().toJson(contact);
            String serviceUrl = Baseurl;
            json = makeConnection(serviceUrl, jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ParseAccept(json);
        //  return  "";
    }*/


    public String Rejectifriend(IfriendRequestRejectObjectDTO contact) {

        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }

        return ParseAccept(json);
        //  return  "";
    }


   /* public String Removefriend(IfriendRemoveObjectDTO contact) {

        try {
            String jsonData = new Gson().toJson(contact);
            String serviceUrl = Baseurl;
            json = makeConnection(serviceUrl, jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ParseAccept(json);
        //  return  "";
    }*/


    public String Getallfriedname(AllIfriendobjectDTO contact) {

        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseAllifriend(json);
        //  return  "";
    }

    public String SendfriendReuest(IfriendSendRequestObjectDTO contact) {


        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }
        return ParseAccept(json);


        //   return ParseAccept(json);
        //  return  "";
    }


   /* public ArrayList<CallLogDTO> GetCallsession(GetCallsessionObjectDTO contact) {

        try {
            String jsonData = new Gson().toJson(contact);
            String serviceUrl = Baseurl;
            json = makeConnection(serviceUrl, jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ParseCallSession(json);
    }*/


    public String InsertSessopn(Object contact) {

        try {
            String jsonData = new Gson().toJson(contact);
            if (Constants.primary_server) {
                json = makeConnection(MainServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = false;
                    json = makeConnection(RecoveryServer, jsonData);
                }
            } else {
                json = makeConnection(RecoveryServer, jsonData);

                if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                    Constants.primary_server = true;
                    json = makeConnection(MainServer, jsonData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    public List<IfriendPendingRequestDTO> GetRequestIfriendlist(GetPendingFriendObjectDTO contact) {

        String jsonData = new Gson().toJson(contact);
        if (Constants.primary_server) {
            json = makeConnection(MainServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = false;
                json = makeConnection(RecoveryServer, jsonData);
            }
        } else {
            json = makeConnection(RecoveryServer, jsonData);

            if (json == null || json.equalsIgnoreCase("")|| json.contains(failer_response)) {
                Constants.primary_server = true;
                json = makeConnection(MainServer, jsonData);
            }
        }

        return ParsePendingIfriendlist(json);
    }


    private List<IfriendListDTO> ParseIfriendlist(String jsonResponse) {


        String username = "";
        String friend = "";
        String possition = "";

        String secretpn = "";
        String requestid = "";
        int profilepic = 0;
        String status = "";
        int i;
        List<IfriendListDTO> ifriendListDTO = new ArrayList<IfriendListDTO>();
        List<String> friendname = new ArrayList<String>();
        JSONArray json_array = null;
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);


                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (i = 0; i < json_array.length(); i++) {

                        JSONObject jsonObject1 = new JSONObject(json_array.getString(i));
                        username = jsonObject1.getString("clun01");
                        friend = jsonObject1.getString("friend");
                        possition = jsonObject1.getString("position");


                        try {
                            profilepic = Integer.parseInt(jsonObject1.getString("profilepic"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        status = jsonObject1.getString("status");



                        if(!friendname.contains(friend)) {
                            friendname.add(friend);
                            ifriendListDTO.add(new IfriendListDTO(username, friend, possition, secretpn, requestid
                                    , profilepic, status));
                        }

                    }
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ifriendListDTO;
    }


    private String ParseAllifriend(String jsonResponse) {

        String response = "";

        String status = "";


        JSONArray json_array = null;
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);


                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {

                    response = "success";
                    json_array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json_array.length(); i++) {
                        JSONObject jsonObject1 = new JSONObject(json_array.getString(i));


                        String senderW = jsonObject1.getString("sender");
                        MainActivity.userListNamesOnlyWaiting.add(senderW);
                        String targetW = jsonObject1.getString("target");
                        MainActivity.userListNamesOnlyWaiting.add(targetW);

                    }
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    private String ParseDateTime(String jsonResponse) {

        String response = "";
        String status = "";
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);


                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {

                    response = jsonObject.getString("date");

                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    private ArrayList<CallLogDTO> ParseCallSession(String jsonResponse) {

        String response = "";

        String status = "";
        int i;
        ArrayList<CallLogDTO> callLogDTOs = new ArrayList<CallLogDTO>();

        String appointmentid = "";
        String counsellorid = "";
        String userid = "";
        String callstatus = "";
        String duration = "";
        String callby = "";
        JSONArray json_array = null;
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);


                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("success")) {

                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (i = 0; i < json_array.length(); i++) {

                        JSONObject jsonObject1 = new JSONObject(json_array.getString(i));
                        appointmentid = jsonObject1.getString("appointmentid");
                        counsellorid = jsonObject1.getString("counsellorid");
                        callstatus = jsonObject1.getString("status");
                        callby = jsonObject1.getString("call_by");
                        userid = jsonObject1.getString("userid");
                        duration = jsonObject1.getString("duration");

                        callLogDTOs.add(new CallLogDTO(appointmentid, counsellorid, userid, callstatus,
                                callby, duration));

                    }

                } else {
                    callLogDTOs = null;


                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return callLogDTOs;
    }


    private String ParseAccept(String jsonResponse) {

        String response = "";

        String status = "";
        int i;


        JSONArray json_array = null;
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);


                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {

                    response = "success";

                } else {

                    if (jsonObject.has("error_type"))
                        response = jsonObject.getString("error_type");

                    if (response == null || response.equalsIgnoreCase("")) {
                        if (jsonObject.has("usertype"))
                            response = jsonObject.getString("usertype");

                    }


                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    private List<IfriendPendingRequestDTO> ParsePendingIfriendlist(String jsonResponse) {


        String sender = "";
        String target = "";
        String status = "";
        String sender_lng = "";
        String sender_lat = "";
        Date CreatedAt = null;
        String requestid = "";

        int i;
        List<IfriendPendingRequestDTO> ifriendListDTO = new ArrayList<IfriendPendingRequestDTO>();

        JSONArray json_array = null;
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (i = 0; i < json_array.length(); i++) {

                        JSONObject jsonObject1 = new JSONObject(json_array.getString(i));
                        if (jsonObject1.has("sender"))
                            sender = jsonObject1.getString("sender");
                        if (jsonObject1.has("target"))
                            target = jsonObject1.getString("target");

                        if (jsonObject1.has("status"))
                            status = jsonObject1.getString("status");

                        if (jsonObject1.has("sender_long"))
                            sender_lng = jsonObject1.getString("sender_long");

                        if (jsonObject1.has("sender_lat"))
                            sender_lat = jsonObject1.getString("sender_lat");

                        if (jsonObject1.has("_created_at"))
                            CreatedAt = Utils.StringTodate(jsonObject1.getString("_created_at"));

                        CreatedAt =  getLocalTime(CreatedAt);
                        ifriendListDTO.add(new IfriendPendingRequestDTO(sender, target, status, sender_lat, sender_lng, CreatedAt, requestid));

                    }
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ifriendListDTO;
    }


    private List<SendImpactRatingDTO> ParseImpactRating(String jsonResponse) {


        String question = "";
        String question_id = "";
        String status = "";


        int i;
        List<SendImpactRatingDTO> ifriendListDTO = new ArrayList<SendImpactRatingDTO>();

        JSONArray json_array = null;
        try {
            Object object = new JSONTokener(jsonResponse).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("status"))
                    status = jsonObject.getString("status");

                if (status != null && status.equalsIgnoreCase("true")) {
                    if (jsonObject.has("data"))
                        json_array = jsonObject.getJSONArray("data");

                    for (i = 0; i < json_array.length(); i++) {

                        JSONObject jsonObject1 = new JSONObject(json_array.getString(i));
                        question = jsonObject1.getString("clque01");
                        question_id = jsonObject1.getString("questionid");

                        ifriendListDTO.add(new SendImpactRatingDTO("", "", "", question, question_id, ""));

                    }
                }


            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ifriendListDTO;
    }

    public static String[] toStringArray(JSONArray array) {

        if (array == null)
            return null;

        String[] arr = new String[array.length()];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = array.optString(i);
        }
        return arr;
    }

    private Calendar current;
    private long miliSeconds;
    private Date resultdate;

    private Date getLocalTime(Date myDate) {

        Date resultdate1 = null;
        try {

            current = Calendar.getInstance();


            TimeZone tzCurrent = current.getTimeZone();
            int offset = tzCurrent.getRawOffset();
            if (tzCurrent.inDaylightTime(new Date())) {
                offset = offset + tzCurrent.getDSTSavings();
            }

            Calendar current1 = Calendar.getInstance();

            current1.setTime(myDate);
            miliSeconds = current1.getTimeInMillis();
            miliSeconds = miliSeconds + offset;
            resultdate = new Date(miliSeconds);

           /* TimeZone london = TimeZone.getTimeZone("Europe/London");
            long now = resultdate.getTime();
             resultdate1 = new Date(miliSeconds - london.getOffset(now));*/

        } catch (Exception e) {
            e.printStackTrace();
        }


        return resultdate;
    }


/*    private String getLocalTime(String datetime) {
        String result = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "MM/dd/yyyy HH:mm");
            Date myDate = null;
            try {
                myDate = dateFormat.parse(datetime);

            } catch (Exception e) {
                e.printStackTrace();
            }
            current = Calendar.getInstance();


            TimeZone tzCurrent = current.getTimeZone();
            int offset = tzCurrent.getRawOffset();
            if (tzCurrent.inDaylightTime(new Date())) {
                offset = offset + tzCurrent.getDSTSavings();
            }

            Calendar current1 = Calendar.getInstance();

            current1.setTime(myDate);
            miliSeconds = current1.getTimeInMillis();
            miliSeconds = miliSeconds + offset;
            resultdate = new Date(miliSeconds);
            SimpleDateFormat destFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            TimeZone london = TimeZone.getTimeZone("Europe/London");
            long now = resultdate.getTime();
            Date resultdate1 = new Date(miliSeconds - london.getOffset(now));
            result = destFormat.format(resultdate1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
    */
}