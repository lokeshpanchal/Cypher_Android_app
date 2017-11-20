package com.helio.silentsecret.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.ChatMessageDTO;
import com.helio.silentsecret.WebserviceDTO.GetMediatorMessageDTO;
import com.helio.silentsecret.WebserviceDTO.GetMediatorMessageObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetMediatorMessageReqDTO;
import com.helio.silentsecret.WebserviceDTO.SendMediatorChatDTO;
import com.helio.silentsecret.WebserviceDTO.SendMediatorChatDataDTO;
import com.helio.silentsecret.WebserviceDTO.SendMediatorChatObjectDTO;
import com.helio.silentsecret.adapters.CounsellingChatDetailsAdapter;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.ChatDetailBean;
import com.helio.silentsecret.utils.AppSession;
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

import static com.helio.silentsecret.activities.ChatDetailsScreen.NAMEFRIEND;
import static com.helio.silentsecret.activities.MainActivity.is_running;


/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class MediatorChating extends BaseActivity implements View.OnClickListener {


    private ListView mChatList = null;
    private ImageView back_iv = null;
    LinearLayout feed_view_back = null;
    private TextView friendName_tv = null;

    boolean is_fromdoowapp = false;
    public static String friendname = "";
    private EditText msg_et = null;
    private ImageView send_btn = null;


    private CounsellingChatDetailsAdapter mChatDetailsAdapter;
    private ArrayList<ChatDetailBean> mChatArrayList = new ArrayList<ChatDetailBean>();


    String Tag = "ChatDetail";
    String SendTagTag = "ChatDetail";

    List<String> chat_id = new ArrayList<>();
    boolean first_time = true;
    public static String TriggerWord = "";
    private String message = "";
    private String messageFetch = "";
    private String currentSender = "";
    private String receiverFriend = "";
    private String send_by = "";

    public static String chat_username = "";

    private boolean isRunning;

    public static Date date;
    public static String systemDate = "";

    static Context ct = null;


    private String dateFormat = "EEE, d MMM yyyy hh:mm aa";
    public static DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aa");

    DateFormat datetimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;


    // private boolean is_new = false;
    //private int list_size;
    ProgressBar progressBar = null;
    //private boolean is_click = false;
    String last_msg_datetime = "";

    long old_milisecond = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediator_chat);
        ct = this;


        feed_view_back = (LinearLayout) findViewById(R.id.feed_view_back);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        is_fromdoowapp = false;


        mSharedPreferences = getSharedPreferences(Constants.PREF, MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        boolean switchOff = mSharedPreferences.getBoolean(Constants.Switch_off, false);


        mChatList = (ListView) findViewById(R.id.chat_list);
        friendName_tv = (TextView) findViewById(R.id.friendName_tv);
        back_iv = (ImageView) findViewById(R.id.back_iv);


        friendName_tv.setText("Chat");

        NAMEFRIEND = AppSession.getValue(this, Constants.MEDIATOR_SUPPORT_WORKER_NAME);
        mChatList.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mChatList.setStackFromBottom(true);
        msg_et = (EditText) findViewById(R.id.msg_et);
        send_btn = (ImageView) findViewById(R.id.send_btn);
        send_btn.setOnClickListener(this);
        back_iv.setOnClickListener(this);


        /*msg_et.addTextChangedListener(new TextWatcher() {
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
        });*/


        if (ConnectionDetector.isNetworkAvailable(this)) {
            try {


                new GetAllmessage().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            new ToastUtil(this, "Please check your internet connection.");
        }


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


    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        // fetchOnlyAboutMe();
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
            postmessage();
        }
        else
            {
            new ToastUtil(this, "Please enter message");
        }

       /* if (message != null && !message.equalsIgnoreCase(""))
        {

            String[] data = message.split(" ");
            String phone = null;

            for (String item : data) {
                if (item.length() > 0) {
                    phone = item;
                    item = item.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                    if (MediatorChating.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}")) {
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

        }*/


    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }

    private void postmessage() {
        try {

            msg_et.setText("");

            SendMediatorChatDataDTO sendMessagetofriedDataDTO = null;

            long mseconds = System.currentTimeMillis();
            String unique_id = MainActivity.enc_username + mseconds;


            sendMessagetofriedDataDTO = new SendMediatorChatDataDTO(MainActivity.enc_username, NAMEFRIEND, "", CryptLib.encrypt(message), "0", "", false, unique_id, "user");
            SendMediatorChatObjectDTO SendfindbynameObjectDTO = new SendMediatorChatObjectDTO(new SendMediatorChatDTO(sendMessagetofriedDataDTO));

            sendMessageObjectDTOs.add(SendfindbynameObjectDTO);

            Date d = new Date();
            systemDate = df.format(d);
            ChatDetailBean me = new ChatDetailBean();
            me.setYourMsg(message);
            me.setSentTime(systemDate);
            me.setMine(true);
            chat_id.add(unique_id);
            mChatArrayList.add(me);
            if (mChatDetailsAdapter == null) {
                mChatDetailsAdapter = new CounsellingChatDetailsAdapter((Activity) ct, mChatArrayList);
                mChatList.setAdapter(mChatDetailsAdapter);

            } else {
                mChatDetailsAdapter.notifyDataSetChanged();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        is_running = false;
    }

    GetMediatorMessageObjectDTO findbynameObjectDTO = null;

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

                    if (sendMessageObjectDTOs.size() > 0) {
                        String SenmessagejsonData = new Gson().toJson(sendMessageObjectDTOs.get(0));
                        httpRequest.Sendmessage(SenmessagejsonData);
                        sendMessageObjectDTOs.remove(0);

                    }

                } catch (Exception e) {

                    return null;
                }


                if (findbynameObjectDTO == null)
                    findbynameObjectDTO = new GetMediatorMessageObjectDTO(new GetMediatorMessageReqDTO(new GetMediatorMessageDTO(MainActivity.enc_username, NAMEFRIEND, last_msg_datetime)));

                String jsonData = new Gson().toJson(findbynameObjectDTO);

                try {


                    response = httpRequest.Getchatmessag(jsonData);

                    /*String sender = "", chatid = "", receiver = "", risk_word = "", message = "", flags = "", flag_risk = "", read = "";
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

                                        if (UserInfoobj.has("clun01"))
                                            sender = UserInfoobj.getString("clun01");

                                        if (UserInfoobj.has("clmediatorun01"))
                                            receiver = UserInfoobj.getString("clmediatorun01");

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

                                             *//*   TimeZone london = TimeZone.getTimeZone("Europe/London");
                                                long now = resultdate.getTime();
                                                created_at = new Date(miliSeconds - london.getOffset(now));*//*

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }
*//*
                                        if (!first_time )
                                        {*//*
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
                                        *//*} else
                                        {
                                            chatMessageDTO.add(new ChatMessageDTO(sender, receiver, risk_word, CryptLib.decrypt(message), flags, flag_risk, read, created_at, curretime));
                                        }*//*
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
                    }*/


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


            Date curretime = null;

            boolean is_new_message = false;
            String status = "";
            try {


                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");

                    if (status != null && status.equalsIgnoreCase("true")) {
                        JSONArray json_array = null;
                        try {
                            if (jsonObject.has("data"))
                                json_array = jsonObject.getJSONArray("data");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        if (json_array != null && json_array.length() > 0) {
                            for (int i = 0; i < json_array.length(); i++) {

                                JSONObject UserInfoobj = new JSONObject(json_array.getString(i));
                                String chatid = "";
                                boolean seen = false;

                                if (UserInfoobj.has("clun01"))
                                    currentSender = UserInfoobj.getString("clun01");
                                if (UserInfoobj.has("send_by"))
                                    send_by = UserInfoobj.getString("send_by");
                                if (UserInfoobj.has("clmediatorun01"))
                                    receiverFriend = UserInfoobj.getString("clmediatorun01");
                                if (UserInfoobj.has("message"))
                                    messageFetch = UserInfoobj.getString("message");

                                if (UserInfoobj.has("read"))
                                    seen = UserInfoobj.getBoolean("read");


                                if (UserInfoobj.has("unique_id"))
                                    chatid = UserInfoobj.getString("unique_id");


                                if (UserInfoobj.has("created_at")) {
                                    try {
                                        Calendar current;
                                        long miliSeconds;
                                        Date resultdate;
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
                                        resultdate = new Date(miliSeconds);
                                        systemDate = resultdate.toString();
                                        systemDate = df.format(resultdate);

                                        if (UserInfoobj.has("updated_at"))
                                            curretime = Utils.StringTodate(UserInfoobj.getString("updated_at"));


                                        long now = curretime.getTime();
                                        if (old_milisecond == 0) {
                                            old_milisecond = now;
                                            last_msg_datetime = datetimeformat.format(curretime);
                                            findbynameObjectDTO.getRequestData().getData().setDatetime(UserInfoobj.getString("updated_at"));

                                        } else if (now > old_milisecond)
                                        {
                                            old_milisecond = now;
                                            last_msg_datetime = datetimeformat.format(curretime);
                                            findbynameObjectDTO.getRequestData().getData().setDatetime(UserInfoobj.getString("updated_at"));

                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                                String decrypt_message = "";
                                try
                                {
                                    if (messageFetch != null && !messageFetch.equalsIgnoreCase(""))
                                        decrypt_message = CryptLib.decrypt(messageFetch);
                                    else
                                        decrypt_message = "cypher";
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                    continue;

                                }


                                ChatDetailBean me = new ChatDetailBean();
                                if (send_by.equalsIgnoreCase("user")) {
                                    me.setMine(true);


                                } else {


                                    me.setMine(false);

                                }

                                me.setYourMsg(decrypt_message);
                                me.setSentTime(systemDate);
                                me.setSeen(seen);

                                   /* if (messageFetch.equalsIgnoreCase("ImpactRatingDone"))
                                        is_userimapctratind_done = true;*/


                                if (!messageFetch.equalsIgnoreCase(currentSender) && !messageFetch.equalsIgnoreCase(receiverFriend) && !messageFetch.equalsIgnoreCase("ImpactRatingDone")) {

                                    try {
                                        if (chat_id.size() == 0) {
                                            is_new_message = true;
                                            chat_id.add(chatid);
                                            mChatArrayList.add(me);
                                        } else if (!chat_id.contains(chatid)) {
                                            is_new_message = true;
                                            chat_id.add(chatid);
                                            mChatArrayList.add(me);
                                        } else {
                                            int index = chat_id.indexOf(chatid);
                                            if (!mChatArrayList.get(index).isSeen() && seen) {
                                                is_new_message = true;
                                                mChatArrayList.get(index).setSeen(seen);
                                            }

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }

                            }


                            if (mChatArrayList.size() > 0 && is_new_message) {
                                if (mChatDetailsAdapter == null) {
                                    mChatDetailsAdapter = new CounsellingChatDetailsAdapter((Activity) ct, mChatArrayList);
                                    mChatList.setAdapter(mChatDetailsAdapter);

                                } else {
                                    mChatDetailsAdapter.notifyDataSetChanged();
                                }

                            }
                        }


                    }
                }


            } catch (Exception e) {
                e.printStackTrace();

            }

            mChatList.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isRunning)
                        new GetAllmessage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }, 100);

            /*if (chatMessageDTO != null && chatMessageDTO.size() > 0) {
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

                            mChatArrayList.add(me);

                            *//*if (chatMessageDTO.get(i).getFlag_risk().equalsIgnoreCase("false"))
                                mChatArrayList.add(me);
                            else
                            {
                                if (chatMessageDTO.get(i).getFlag_risk().equalsIgnoreCase("true") && me.isMine())
                                    mChatArrayList.add(me);
                            }*//*
                        }


                        if (mChatArrayList.size() > 0) {
                            if (mChatDetailsAdapter == null) {
                                mChatDetailsAdapter = new CounsellingChatDetailsAdapter(MediatorChating.this, mChatArrayList);
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
                    new MediatorChating.GetAllmessage().execute();
                }
            }, 100);*/

        }
    }


    static List<SendMediatorChatObjectDTO> sendMessageObjectDTOs = new ArrayList<>();


}
