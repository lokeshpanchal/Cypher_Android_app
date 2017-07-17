package com.helio.silentsecret.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.ChatMessageDTO;
import com.helio.silentsecret.WebserviceDTO.GetIfreindMessageReqDTO;
import com.helio.silentsecret.WebserviceDTO.GetIfriendMessageObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetUnreadMessageDataDTO;
import com.helio.silentsecret.WebserviceDTO.GetUnreadMessageObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetUnreadtChatMessageDTO;
import com.helio.silentsecret.WebserviceDTO.GetifriendMessageDTO;
import com.helio.silentsecret.WebserviceDTO.SendMessageIfriendDTO;
import com.helio.silentsecret.WebserviceDTO.SendMessageObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SendMessagetofriedDataDTO;
import com.helio.silentsecret.adapters.ChatDetailsAdapter;
import com.helio.silentsecret.connection.ConnectionDetector;

import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.creator.ObjectMaker;
import com.helio.silentsecret.models.ChatDetailBean;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ToastUtil;
import com.helio.silentsecret.utils.Utils;



import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class ChatDetailsScreen extends BaseActivity implements View.OnClickListener {


    private ListView mChatList = null;
    private ImageView back_iv = null;
    LinearLayout feed_view_back = null;
    private TextView friendName_tv = null;
    String FinalString = "";
    boolean is_fromdoowapp = false;
    public static String friendname = "";
    private EditText msg_et = null;
    private ImageView send_btn = null;

    SendMessagetofriedDataDTO sendMessagetofriedDataDTO = null;

    String MainServer = "https://dev1.eu-gb.mybluemix.net/api/service";
   // String MainServer = "https://dev2.eu-gb.mybluemix.net/api/service";
    String RecoveryServer = "https://dev1.eu-gb.mybluemix.net/api/service";


    private ChatDetailsAdapter mChatDetailsAdapter;
    private ArrayList<ChatDetailBean> mChatArrayList = new ArrayList<ChatDetailBean>();
    static WebView mWebView = null;
    /*    private List<RiskState> riskWords;
        private List<String> keyWords;*/
    String Tag = "ChatDetail";
    String SendTagTag = "ChatDetail";

    List<String> chat_id = new ArrayList<>();
    boolean first_time = true;
    public static String TriggerWord = "";
    private String message = "";
    private String messageFetch = "";
    private String currentSender = "";
    private String receiverFriend = "";
    public static String chat_username = "";

    private boolean isRunning;

    public static Date date;
    public static String systemDate = "";

    static Context ct = null;
    //private ImageView mGlimpseView;

    private String dateFormat = "EEE, d MMM yyyy hh:mm aa";
    public static DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aa");

    DateFormat datetimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String NAME = "";
    public static String NAMEFRIEND = "";

    private static Handler handler;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isAlready = false;
    public static RelativeLayout webview_layout = null;
    public static String mUrl = "";


    private String keys = "";


    // private boolean is_new = false;
    //private int list_size;
    ProgressBar progressBar = null;
    //private boolean is_click = false;
    String last_msg_datetime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_profile);
        ct = this;
        mWebView = (WebView) findViewById(R.id.feed_view_vb);
        webview_layout = (RelativeLayout) findViewById(R.id.webview_layout);
        feed_view_back = (LinearLayout) findViewById(R.id.feed_view_back);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        is_fromdoowapp = false;


        feed_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview_layout.setVisibility(View.GONE);

                if (Build.VERSION.SDK_INT < 18) {
                    mWebView.clearView();
                } else {
                    initWebView("about:blank");
                }
            }
        });

        mSharedPreferences = getSharedPreferences(Constants.PREF, MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        boolean switchOff = mSharedPreferences.getBoolean(Constants.Switch_off, false);

        if (!switchOff)
            chatAlert();

        mChatList = (ListView) findViewById(R.id.chat_list);
        friendName_tv = (TextView) findViewById(R.id.friendName_tv);
        back_iv = (ImageView) findViewById(R.id.back_iv);


        if (getIntent() != null) {
            if (getIntent().hasExtra("NAME")) {
                NAME = getIntent().getStringExtra("NAME");

            }

            if (getIntent().hasExtra("NAMEFRIEND")) {
                NAMEFRIEND = getIntent().getStringExtra("NAMEFRIEND");

            }
        }


        if (friendname != null && !friendname.equalsIgnoreCase(""))
            friendName_tv.setText(friendname);

        chat_username = NAMEFRIEND;
        // new SetAsRead().execute();

        mChatList.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mChatList.setStackFromBottom(true);
        msg_et = (EditText) findViewById(R.id.msg_et);
        send_btn = (ImageView) findViewById(R.id.send_btn);
        send_btn.setOnClickListener(this);
        back_iv.setOnClickListener(this);


        msg_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {


                try {
                    if (text.toString() != null && !text.toString().equalsIgnoreCase("")) {


                    }
                } catch (IndexOutOfBoundsException e) {
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if (ConnectionDetector.isNetworkAvailable(this)) {
            try {


                new GetAllmessage().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            new ToastUtil(this, "Please check your internet connection.");
        }


        handler = new Handler();
    }


    @Override
    protected void onDestroy() {
        chat_username = "";

        super.onDestroy();
    }


/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {

            }
        }
    }*/

  /*  private void fetchReadStatus() {


        ParseQuery myQuery1 = new ParseQuery(MainActivity.friend_table_name);//DONE
        myQuery1.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        myQuery1.whereEqualTo("friend", NAMEFRIEND);

        ParseQuery myQuery2 = new ParseQuery(MainActivity.friend_table_name);//DONE
        myQuery2.whereEqualTo("username", NAMEFRIEND);
        myQuery2.whereEqualTo("friend", ParseUser.getCurrentUser().getUsername());

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(myQuery1);
        queries.add(myQuery2);

        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    if (objects != null && objects.size() > 0) {
                        for (int i = 0; i < objects.size(); i++) {
                            ParseObject isReadObject = objects.get(i);
                            isReadObject.put("read", "true");
                            ParseACL isReadAcl = new ParseACL();
                            isReadAcl.setPublicWriteAccess(true);
                            isReadAcl.setPublicReadAccess(true);
                            isReadObject.setACL(isReadAcl);


                            isReadObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if (e == null) {

                                        System.out.println("Read fetch successfully");
                                    } else {
                                        System.out.println("Read false warning ----> " + e.getMessage());
                                    }
                                }
                            });

                        }


                    }
                } else {
                    System.out.println("Warning fetching ----> " + e.getMessage());
                }
            }
        });


    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        // fetchOnlyAboutMe();
    }




    public void chatAlert() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(ChatDetailsScreen.this);
        builder1.setIcon(R.drawable.ic_launcher);
        builder1.setTitle("Must Read");
        builder1.setMessage("1) Do not share your names or initials.\n2) Do not share your address like city, town, postcode.\n3) Do not share your contact details.\n4) Do not attempt to meet offline.\n5) Don't be unkind with others.");

        builder1.setPositiveButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Switch Off",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        editor.putBoolean(Constants.Switch_off, true);
                        editor.commit();
                        dialog.cancel();

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn:
                if (ConnectionDetector.isNetworkAvailable(this)) {

                    addMessage();
                } else {
                    new ToastUtil(this, "Please check your internet connection.");
                }
                break;
            case R.id.back_iv:
                Intent i = new Intent(this, ChatFriends.class);
                startActivity(i);
                finish();
                break;
            default:
                break;
        }
    }


    private void addMessage() {

        message = msg_et.getText().toString().trim();



        if (message != null && !message.equalsIgnoreCase(""))
        {

            String[] data = message.split(" ");
            String phone = null;

            for (String item : data) {
                if (item.length() > 0) {
                    phone = item;
                    item = item.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                    if (ChatDetailsScreen.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}")) {
                        postmessage("true", phone);
                        return;
                    }
                }
            }

            if (CommonFunction.isTriggered(message) || isValidEmail(message) || CommonFunction.isTriggeredNew(message)) {
                postmessage("true", TriggerWord);
            } else {


                postmessage("false", "");
            }

        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }

    private void postmessage(final String risk, String riskworkd) {
        try {

            msg_et.setText("");

            SendMessagetofriedDataDTO sendMessagetofriedDataDTO = null;

            long mseconds = System.currentTimeMillis();
            String unique_id = MainActivity.enc_username + mseconds;

                if (risk.equalsIgnoreCase("true"))
                    sendMessagetofriedDataDTO = new SendMessagetofriedDataDTO(MainActivity.enc_username, NAMEFRIEND, riskworkd, CryptLib.encrypt(message), "7", risk, true , unique_id);
                else
                    sendMessagetofriedDataDTO = new SendMessagetofriedDataDTO(MainActivity.enc_username, NAMEFRIEND, "", CryptLib.encrypt(message), "0", risk, false , unique_id);

            SendMessageObjectDTO  SendfindbynameObjectDTO = new SendMessageObjectDTO(new SendMessageIfriendDTO(sendMessagetofriedDataDTO));

            sendMessageObjectDTOs.add(SendfindbynameObjectDTO);

            Date d = new Date();
            systemDate = df.format(d);
            ChatDetailBean me = new ChatDetailBean();
            me.setYourMsg(message);
            me.setSentTime(systemDate);
            me.setMine(true);
            chat_id.add(unique_id);
            mChatArrayList.add(me);
            if (mChatDetailsAdapter == null)
            {
                mChatDetailsAdapter = new ChatDetailsAdapter((Activity) ct, mChatArrayList);
                mChatList.setAdapter(mChatDetailsAdapter);

            } else {
                mChatDetailsAdapter.notifyDataSetChanged();
            }

            /* else {
                SendfindbynameObjectDTO.getRequestData().getData().setRisk_word(riskworkd);
                SendfindbynameObjectDTO.getRequestData().getData().setMessage(CryptLib.encrypt(message));
                if (risk.equalsIgnoreCase("true")) {
                    SendfindbynameObjectDTO.getRequestData().getData().setFlag_risk("7");
                    SendfindbynameObjectDTO.getRequestData().getData().setRead(true);
                } else {
                    SendfindbynameObjectDTO.getRequestData().getData().setFlag_risk("0");
                    SendfindbynameObjectDTO.getRequestData().getData().setRead(false);
                }

            }*/


            //new SendMessage().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    public static void initWebView(String url) {

        mWebView.getSettings().setJavaScriptEnabled(true);


        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                mWebView.loadUrl(url);
                return false;
               /* if (url.contains("www.doowapp.me/")) {
                    mWebView.loadUrl(url);
                    return false;
                } else
                    return true;*/
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, final String url) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean is_numeric(String msg) {
        boolean is_num = false;
        for (int i = 0; i < 10; i++) {
            if (msg.contains("" + i)) {
                is_num = true;
                break;
            }
        }
        return is_num;
    }


    GetIfriendMessageObjectDTO findbynameObjectDTO = null;

    String jsonDatasetrread = null;

    private class GetAllmessage extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        String response = "";

        List<ChatMessageDTO> chatMessageDTO = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {
                chatMessageDTO.clear();
                IfriendRequest httpRequest = new IfriendRequest(ct);
                try {

                    if(sendMessageObjectDTOs.size()>0) {
                        String SenmessagejsonData = new Gson().toJson(sendMessageObjectDTOs.get(0));
                        httpRequest.Sendmessage(SenmessagejsonData);
                        sendMessageObjectDTOs.remove(0);

                    }

                } catch (Exception e) {

                    return null;
                }


                if (findbynameObjectDTO == null)
                    findbynameObjectDTO = new GetIfriendMessageObjectDTO(new GetIfreindMessageReqDTO(new GetifriendMessageDTO(MainActivity.enc_username, chat_username, last_msg_datetime)));

                String jsonData = new Gson().toJson(findbynameObjectDTO);

                try {
                    response =   httpRequest.Getchatmessag(jsonData);

                    String sender = "", chatid = "", receiver = "", risk_word = "", message = "", flags = "", flag_risk = "", read = "";
                    Date created_at = null, curretime = null;


                    String status = "";
                    try {
                        Object object = new JSONTokener(response).nextValue();
                        if (object instanceof JSONObject) {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.has("status"))
                                status = jsonObject.getString("status");

                            if (status != null && status.equalsIgnoreCase("true")) {
                                JSONArray json_array = null;
                                if (jsonObject.has("data"))
                                    json_array = jsonObject.getJSONArray("data");

                                if (jsonObject.has("currenttime"))
                                    curretime = Utils.StringTodate(jsonObject.getString("currenttime"));

                                if (json_array != null && json_array.length() > 0) {
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

                                        if (UserInfoobj.has("unique_id"))
                                            chatid = UserInfoobj.getString("unique_id");


                                        if (UserInfoobj.has("created_at")) {
                                            try {
                                                Calendar current;
                                                long miliSeconds;

                                                current = Calendar.getInstance();


                                                TimeZone tzCurrent = current.getTimeZone();
                                                int offset = tzCurrent.getRawOffset();
                                                if (tzCurrent.inDaylightTime(new Date())) {
                                                    offset = offset + tzCurrent.getDSTSavings();
                                                }
                                                Calendar current1 = Calendar.getInstance();

                                                current1.setTime(Utils.StringTodate(UserInfoobj.getString("created_at")));
                                                miliSeconds = current1.getTimeInMillis();
                                                miliSeconds = miliSeconds + offset;
                                                created_at = new Date(miliSeconds);

                                             /*   TimeZone london = TimeZone.getTimeZone("Europe/London");
                                                long now = resultdate.getTime();
                                                created_at = new Date(miliSeconds - london.getOffset(now));*/

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }
/*
                                        if (!first_time )
                                        {*/
                                            if(chat_id.size() ==0)
                                            {
                                                chat_id.add(chatid);
                                                chatMessageDTO.add(new ChatMessageDTO(sender, receiver, risk_word, CryptLib.decrypt(message), flags, flag_risk, read, created_at, curretime));

                                            }
                                            else if(!chat_id.contains(chatid))
                                            {
                                                chat_id.add(chatid);
                                                chatMessageDTO.add(new ChatMessageDTO(sender, receiver, risk_word, CryptLib.decrypt(message), flags, flag_risk, read, created_at, curretime));

                                            }
                                        /*} else
                                        {
                                            chatMessageDTO.add(new ChatMessageDTO(sender, receiver, risk_word, CryptLib.decrypt(message), flags, flag_risk, read, created_at, curretime));
                                        }*/
                                    }

                                    if (chatMessageDTO != null && chatMessageDTO.size() > 0)
                                    {
                                        last_msg_datetime = datetimeformat.format(chatMessageDTO.get(0).getCurrenttime());
                                        if (findbynameObjectDTO != null)
                                            findbynameObjectDTO.getRequestData().getData().setDatetime(last_msg_datetime);

                                        if (jsonDatasetrread == null)
                                        {
                                            GetUnreadMessageDataDTO getUnreadMessageDataDTO = new GetUnreadMessageDataDTO(MainActivity.enc_username, chat_username);

                                            GetUnreadMessageObjectDTO getUnreadMessageObjectDTO = new GetUnreadMessageObjectDTO(new GetUnreadtChatMessageDTO(getUnreadMessageDataDTO, "setIfriendMessageRead", Constants.ENCRYPT_IFRIEND_CHAT_TABLE, Constants.ENCRYPT_FIND_METHOD));

                                            jsonDatasetrread = new Gson().toJson(getUnreadMessageObjectDTO);


                                        }

                                        httpRequest.Sendmessage(jsonDatasetrread);

                                    }
                                }

                            }
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {

                    return null;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            progressBar.setVisibility(View.GONE);


            if (chatMessageDTO != null && chatMessageDTO.size() > 0) {
                first_time = false;

                Log.d(Tag, "" + last_msg_datetime);
                try {
                    if (chatMessageDTO != null && chatMessageDTO.size() > 0) {
                        for (int i = 0; i < chatMessageDTO.size(); i++) {
                            currentSender = chatMessageDTO.get(i).getSender();
                            receiverFriend = chatMessageDTO.get(i).getReceiver();
                            messageFetch = chatMessageDTO.get(i).getMessage();
                            date = chatMessageDTO.get(i).getCreated_at();
                            systemDate = date.toString();
                            systemDate = df.format(date);

                            ChatDetailBean me = new ChatDetailBean();
                            if (currentSender.equalsIgnoreCase(MainActivity.enc_username)) {
                                me.setMine(true);
                            } else

                            {
                                me.setMine(false);
                            }
                            me.setYourMsg(messageFetch);
                            me.setSentTime(systemDate);

                            if (chatMessageDTO.get(i).getFlag_risk().equalsIgnoreCase("false"))
                                mChatArrayList.add(me);
                            else
                            {
                                if (chatMessageDTO.get(i).getFlag_risk().equalsIgnoreCase("true") && me.isMine())
                                    mChatArrayList.add(me);
                            }
                        }


                        if (mChatArrayList.size() > 0) {
                            if (mChatDetailsAdapter == null) {
                                mChatDetailsAdapter = new ChatDetailsAdapter(ChatDetailsScreen.this, mChatArrayList);
                                mChatList.setAdapter(mChatDetailsAdapter);

                            } else {
                                mChatDetailsAdapter.notifyDataSetChanged();
                            }

                        }

                    }


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }


            chatMessageDTO.clear();

            mChatList.postDelayed(new Runnable()
            {
                @Override
                public void run() {
                    if(isRunning)
                    new GetAllmessage().execute();
                }
            }, 100);

        }
    }



    static  List<SendMessageObjectDTO> sendMessageObjectDTOs = new ArrayList<>();



}
