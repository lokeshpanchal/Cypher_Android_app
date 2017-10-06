package com.helio.silentsecret.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.IfriendaDenyConditionDataDTO;
import com.helio.silentsecret.WebserviceDTO.RoomCommentConditionDTO;
import com.helio.silentsecret.adapters.RoomCommentsAdapter;
import com.helio.silentsecret.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.silentsecret.appCounsellingDTO.FinalObjectDTO;
import com.helio.silentsecret.callbacks.GPSCallback;
import com.helio.silentsecret.callbacks.KeywordCheckerCallback;
import com.helio.silentsecret.callbacks.RiskCheckerCallback;
import com.helio.silentsecret.checkers.KeywordsChecker;
import com.helio.silentsecret.checkers.RiskChecker;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.dialogs.AccessDialog;
import com.helio.silentsecret.dialogs.AttentionComments;
import com.helio.silentsecret.dialogs.ProgressDialog;
import com.helio.silentsecret.location.GPSCoordinateReceiver;
import com.helio.silentsecret.models.IfriendListDTO;
import com.helio.silentsecret.models.IfriendRequestDTO;
import com.helio.silentsecret.models.IfriendRequestObjectDTO;
import com.helio.silentsecret.models.RiskState;
import com.helio.silentsecret.models.RoomComment;
import com.helio.silentsecret.models.RoomsInfoDTO;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ImageLoaderUtil;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.Preference;
import com.helio.silentsecret.utils.ToastUtil;
import com.nirhart.parallaxscroll.views.ParallaxListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class CommentRoomsActivity extends BaseActivity
        implements View.OnClickListener, GPSCallback, ViewTreeObserver.OnGlobalLayoutListener {

    private TextView hintMessage;
    private EditText commentText;

    private int itemPosition = 0;
    private String itemValue = "";
    StringBuilder s;

    String FinalString = "";
    boolean is_fromdoowapp = false;

    boolean attantionPopup = false;

    CharSequence mText = "";
    public static ListView friendListView;

    private String requestuser = "";

    RoomCommentConditionDTO commentConditionDTO = null;
    private List<String> friendArrayList = null;
    private List<String> duplicateArrayList = null;
    private boolean TAGFRIEND = false;
    Activity mActivity = null;
    private ImageView close_comment;
    TextView mttext = null;

    private ParallaxListView mParallaxList;

    private ImageLoaderUtil mImageLoader;

    private List<RiskState> riskWords;


    private Handler mHider;
    private Runnable mHideRunnable;

    private List<String> keyWords;

    private RoomsInfoDTO roomObject;

    private RoomCommentsAdapter adapter;
    private List<RoomComment> mList;
    private String secretId;
    private GPSCoordinateReceiver GPS;

    private static AtomicBoolean doowapmReplyState = new AtomicBoolean(false);
    private static RoomComment doowappmReplyComment;


    private AtomicBoolean mReplyState = new AtomicBoolean(false);
    private RoomComment mReplyComment;


    private int mGlobalHeightDiff = 0;

    private String one[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
    private String word = "";


    public static RelativeLayout webview_layout = null;
    LinearLayout feed_view_back = null;
    static WebView mWebView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_rooms);

        mActivity = this;
        initVariables();

        startTracking(getString(R.string.analytics_Comments));

        friendListView = (ListView) findViewById(R.id.friendsList);

        mWebView = (WebView) findViewById(R.id.feed_view_vb);
        webview_layout = (RelativeLayout) findViewById(R.id.webview_layout);

        feed_view_back = (LinearLayout) findViewById(R.id.feed_view_back);

        is_fromdoowapp = false;

        try
        {
            if (MainActivity.stataicObjectDTO != null) {
                if (MainActivity.stataicObjectDTO.getKeywords() != null)
                {
                    if(keyWords == null)
                        keyWords = new ArrayList<>();
                    else
                        keyWords.clear();
                    keyWords.addAll(Arrays.asList(MainActivity.stataicObjectDTO.getKeywords().split(", ")));
                }
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

       /* if (ConnectionDetector.isNetworkAvailable(this))
        {
            new GetFriendlist().execute();
        }*/

        /*feed_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview_layout.setVisibility(View.GONE);

                if (Build.VERSION.SDK_INT < 18) {
                    mWebView.clearView();
                } else {
                    initWebView("about:blank");
                }
            }
        });*/

        mttext = (TextView) findViewById(R.id.mttext);
        mttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        close_comment = (ImageView) findViewById(R.id.close_comment);

        close_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                message = "";

                finish();
            }
        });

        if (getIntent().getExtras() != null && (getIntent().getExtras().getString(Constants.PUSH_SECRET_ID) != null)) {
            secretId = getIntent().getExtras().getString(Constants.PUSH_SECRET_ID);
            replaceDialog();
        } else {
            try {
                roomObject = Constants.roomComment;
                initViews(null);
            } catch (NullPointerException e) {
                finish();
            } catch (Exception e) {
                finish();
            }
        }


    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
    }

    public static void initWebView(String url) {

        mWebView.getSettings().setJavaScriptEnabled(true);


        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.contains("http://www.doowapp.me/play.php?id")) {
                    mWebView.loadUrl(url);
                    return false;
                } else
                    return true;
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


    private void initVariables() {
        mImageLoader = new ImageLoaderUtil(this);

        mHider = new Handler();
        mHideRunnable = new Runnable() {
            @Override
            public void run() {
                if (hintMessage != null) {
                    YoYo.with(Techniques.FadeOutDown).playOn(hintMessage);
                }
            }
        };

    }

   /* private void loadKeywords() {
        new KeywordsLoader(new HelperArrayCallback() {
            @Override
            public void onUpdate(List<String> data) {
                keyWords = new ArrayList<String>();
                if (data != null)
                    keyWords.addAll(data);
            }
        }).execute();
    }*/

  /*  private void loadRiskWords() {
        new RiskLoader(new HelperRiskCallback() {
            @Override
            public void onUpdate(List<RiskState> data) {
                riskWords = new ArrayList<RiskState>();
                if (data != null)
                    riskWords.addAll(data);
            }
        }).execute();
    }*/

    private void replaceDialog() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            transaction.replace(R.id.comments_content, AccessDialog.instance(Constants.ACCESS_COMMENTS_SECRET));
        } catch (IllegalStateException e) {
        }
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //ActionChecker.checkTheDailyCommentsCount();
    }

    private void initViews(String commentId) throws NullPointerException {
        mHider.postDelayed(mHideRunnable, 3000);

        commentText = (EditText) findViewById(R.id.comments_text);
        hintMessage = (TextView) findViewById(R.id.comments_hint_tv);

        findViewById(R.id.comment_progress_bg_iv).setOnClickListener(this);

        mParallaxList = (ParallaxListView) findViewById(R.id.comments_list_view);

        View secretView = getLayoutInflater().inflate(R.layout.comment_secret_item, null);
        secretView.findViewById(R.id.comment_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBack();
            }
        });


        mList = new ArrayList<>();
        adapter = new RoomCommentsAdapter(LayoutInflater.from(this), mList, this);
        mParallaxList.setAdapter(adapter);

        if (commentId == null) {
            findViewById(R.id.comments_root)
                    .getViewTreeObserver().addOnGlobalLayoutListener(this);
            initSendListener();
          //  loadComments();
            new GetComment().execute();
          //  checkCommentsState();
        } else {
            /*initReplyState(commentId);*/
        }


        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence text, int start, int before, int count)
            {

                try {
                    /*if (text.toString() != null && !text.toString().equalsIgnoreCase(""))
                    {

                        if (is_fromdoowapp == false)
                        {
                            if (text.toString().contains("@"))
                            {


                                List<String> temprorylist = new ArrayList<String>();

                                for (int k = 0; k < duplicateArrayList.size(); k++) {
                                    if (!text.toString().contains(duplicateArrayList.get(k))) {
                                        temprorylist.add(duplicateArrayList.get(k));
                                    }
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity,
                                        R.layout.friend_listitem, temprorylist);

                                friendListView.setAdapter(adapter);

                                friendListView.setVisibility(View.VISIBLE);

                                friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        itemPosition = position;

                                        itemValue = (String) friendListView.getItemAtPosition(position);


                                        s = new StringBuilder(text);
                                        s.append(itemValue);
                                        mText = text + itemValue;


                                        mText = mText.toString().replace("@", "");
                                        commentText.setText(mText);

                                        commentText.setSelection(commentText.getText().length());
                                        commentText.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                friendListView.setVisibility(View.GONE);

                                            }
                                        }, 200);

                                    }
                                });
                            }
                            else
                                friendListView.setVisibility(View.GONE);
                        }
                        else if(is_fromdoowapp)
                        {

                            {
                                try
                                {
                                    commentText.setText(FinalString);
                                    commentText.setSelection(commentText.getText().length());
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }


                            }
                        }
                        else
                        {
                            friendListView.setVisibility(View.GONE);
                        }
                    }
                    else
                        friendListView.setVisibility(View.GONE);
*/

                } catch (IndexOutOfBoundsException e) {
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /*private void initReplyState(String commentId) {
        findViewById(R.id.comments_bottom_bar).setVisibility(View.GONE);
        showProgress();
        new CommentLoader(new CommentsCallback() {
            @Override
            public void onUpdate(List<Comment> list) {
                stopProgress();
                mList.clear();
                mList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }).executeReply(commentId);
    }*/

    public void showReply(RoomComment data) {
        mReplyComment = data;
        mReplyState.set(true);

        doowapmReplyState.set(true);
        doowappmReplyComment = data;
        KeyboardUtil.showKeyBoard(commentText, this);
    }

    private void checkCommentsState() {
        /*new VerifyReviewLoader(new MineVerifyCallback() {
            @Override
            public void onUpdate(boolean isVerified, boolean isSuperVerified) {


                makeCommentTypeEnabled();

            }
        }).executeIsVerified();*/
    }

    private void initSendListener() {
        findViewById(R.id.comments_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message = commentText.getText().toString().trim();

                postComment(message);

               /* if (doowapplyrics != null && !doowapplyrics.equalsIgnoreCase("") && message.contains(doowapplyrics)) {
                    message = message.replace(doowapplyrics, " ");

                }

                if (CommonFunction.isTriggered(message) || CommonFunction.isTriggeredNew(message) || CommonFunction.is_numeric(message) || CommonFunction.isValidEmail(message)) {
                    AlertDialog.Builder buildervalid = new AlertDialog.Builder(CommentSecretActivity.this);

                    buildervalid.setTitle("Warning!");
                    buildervalid.setMessage("Your message contains some banned word(s), Please edit and send again.");

                    buildervalid.setPositiveButton(
                            "OK, Fine",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    buildervalid.setCancelable(false);
                    buildervalid.show();
                } else {
                    postComment(commentText.getText().toString().trim());
*/

                    mParallaxList.setStackFromBottom(false);

            }
        });

        findViewById(R.id.comments_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCommentsState();
            }
        });
    }

    private void makeCommentTypeEnabled() {
        findViewById(R.id.comments_send).setEnabled(true);
        findViewById(R.id.comments_text).setEnabled(true);
        findViewById(R.id.comments_send).setAlpha(1f);
        findViewById(R.id.comments_text).setAlpha(1f);
    }


    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    String message = "";

    public void postComment(final String text) {

        message = commentText.getText().toString().trim();

        if (message.isEmpty()) {
            new ToastUtil(this, getString(R.string.comment_warning));
            return;
        }

        if (!ConnectionDetector.isNetworkAvailable(this))
            return;

        showProgress();

        /*sendfriendList = new ArrayList<String>();
        for (int i = 1; i <= friendArrayList.size(); i++) {
            if (message.contains("iFriend" + i)) {
                message = message.replace("iFriend" + i, "iFriend" + one[i - 1]);
                sendfriendList.add(friendArrayList.get(i - 1));
                TAGFRIEND = true;
            }
        }*/

        commentText.setText("");

        String[] data = message.split(" ");
        String phone = null;
        for (String item : data) {
            if (item.length() > 5) {
                phone = item;
                item = item.replaceAll("[^0-9]", "");
                if (isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}")) {
                    continueCreation(true, message, null, phone);
                    return;
                }
            }
        }

        new RiskChecker(new RiskCheckerCallback() {
            @Override
            public void onDone(boolean result, String riskWord, String state) {
                if (result) {
                    continueCreation(false, message, null, null);
                } else {
                    continueCreation(true, message, state, riskWord);
                }
            }
        }).execute( message);
    }

    private void continueCreation(boolean isRisk, final String text, String riskState, String riskWord) {
        if (isRisk) {
            uploadToParse(text, true, riskState, riskWord);
        } else {
            new KeywordsChecker(new KeywordCheckerCallback() {
                @Override
                public void onDone(boolean result,String riskword) {
                    boolean flagged = false;
                    if (!result)
                        flagged = true;
                    uploadToParse(text, flagged, null, null);
                }
            }).execute(keyWords, text);
        }
    }

    private void uploadToParse(final String text, final boolean flagged, final String riskState, final String riskWord)
    {
        new AsyncTask<Void, Void, Boolean>()
        {

            @Override
            protected Boolean doInBackground(Void... voids) {
                if (flagged) {
                    return localResolve(text);
                }
                return false;
            }

            @Override
            protected void onPostExecute(final Boolean result) {
                super.onPostExecute(result);
                if (mReplyState.get() && mReplyComment != null)
                {
                    postComment(result, riskState, riskWord, text);

                } else {
                    postComment(result, riskState, riskWord, text);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void postComment(final boolean result, String riskState, String riskWord, String text) {



            attantionPopup = result;

            try {

                    String petname = AppSession.getValue(mActivity, Constants.USER_PET_NAME);
                long mseconds = System.currentTimeMillis();

                String reply_comment_id = "";
                if(doowapmReplyState.get())
                    reply_comment_id = doowappmReplyComment.getComment_id();

                String user_id = CryptLib.encrypt(AppSession.getValue(mActivity,Constants.USER_NAME) )+ mseconds;

                commentConditionDTO = new RoomCommentConditionDTO( roomObject.getRoom_id() ,   MainActivity.enc_username,  attantionPopup
                ,   doowappmReplyComment != null ? doowappmReplyComment.getUser(): null ,  doowapmReplyState.get(),  CryptLib.encrypt(text),  riskState ,  user_id ,  riskWord
                        , petname,  reply_comment_id,  roomObject.getCreated_user() );

               new CreatNewComment().execute();


            } catch (Exception e) {
                e.printStackTrace();
            }




    }

/*
    private void sendReplyNotification(final String commentId) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo(Constants.OBJECT_ID, mReplyComment.getUser());

        KeyboardUtil.hideKeyBoard(commentText, CommentSecretActivity.this);

        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                JSONObject dataType = new JSONObject();
                try {
                    dataType.put(Constants.PUSH_ALERT, getString(R.string.you_received_new_reply));
                    dataType.put(Constants.PUSH_SECRET_ID, secretObject.getObjectId());
                    dataType.put(Constants.PUSH_ACTION, Constants.PUSH_ACTION_COMMENT_REPLY);
                    dataType.put(Constants.PUSH_REPLY_COMMENT_ID, commentId);
                } catch (JSONException error) {
                    error.printStackTrace();
                }

                ParsePush push = new ParsePush();
                push.setData(dataType);
                push.setChannel(user.getUsername());
                push.sendInBackground();

            }
        });
    }
*/

    /*private void loadComments() {
        showProgress();
        new CommentLoader(new CommentsCallback() {
            @Override
            public void onUpdate(List<Comment> list) {
                stopProgress();
                mList.clear();
                if (list != null && list.size() > 0) {
                    mList.addAll(list);
                }
                adapter.notifyDataSetChanged();

            }
        }).execute(secretObject.getObject());
    }
*/
    @Override
    public void onBackPressed() {
        runBack();
    }

    public void runBack() {
        if (getIntent().getExtras() != null && (getIntent().getExtras().getString(Constants.PUSH_SECRET_ID) != null)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (commentText != null)
            KeyboardUtil.hideKeyBoard(commentText, this);
        if (mHider != null) {
            mHider.removeCallbacks(mHideRunnable);
            if (hintMessage != null) {
                hintMessage.setVisibility(View.INVISIBLE);
            }

        }
    }

    public void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.comment_progress_bg_iv), findViewById(R.id.comment_progress_pb));
    }

    public void stopProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.comment_progress_bg_iv), findViewById(R.id.comment_progress_pb));
    }

    @Override
    public void onClick(View v) {

    }

    /*public void loadData() {
        new AccessSecretLoader(new AccessSecretCallback() {
            @Override
            public void onReceive(Secret item) {
                if (item == null) {
                    return;
                }

                String commentId = null;

                if (getIntent().getExtras() != null
                        && getIntent().getExtras().containsKey(Constants.PUSH_REPLY_COMMENT_ID)) {
                    commentId = getIntent().getExtras().getString(Constants.PUSH_REPLY_COMMENT_ID);
                }

                secretObject = item;
                initViews(commentId);
            }
        }).execute(secretId);
    }
*/
/*
    private void sendPushData(String id, String user) {

        JSONObject dataType = new JSONObject();
        try {
            dataType.put(Constants.PUSH_ALERT, getString(R.string.you_got_new_commnet));
            dataType.put(Constants.PUSH_SECRET_ID, id);
            dataType.put(Constants.PUSH_ACTION, Constants.PUSH_ACTION_COMMENT);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ParsePush push = new ParsePush();
        push.setData(dataType);
        push.setChannel(user);
        push.sendInBackground(new SendCallback() {
            @Override
            public void done(ParseException e) {
                savePushToTheClass();
            }
        });
    }
*/



   /* private void sendReplyPushNotification(String id, ParseUser user) {

        ObjectMaker.formNewNotification(secretObject.getObject(), getString(R.string.you_got_reply_onComment), Constants.PUSH_ACTION_COMMENT, user);

    }



    private void savePushToTheClass()
    {
    ObjectMaker.formNewNotification(secretObject.getObject(), getString(R.string.you_got_new_commnet), Constants.PUSH_ACTION_COMMENT, secretObject.getUser());
                //.saveInBackground();

    }*/

    @Override
    public void onReceive(Location location) {
        if (location != null) {
            Preference.saveUserLat(String.valueOf(location.getLatitude()));
            Preference.saveUserLon(String.valueOf(location.getLongitude()));
            //ParseUser.getCurrentUser().put(Constants.USER_LOCATION, GeoHelper.returnAddressString(this));
           // ParseUser.getCurrentUser().saveInBackground();
        }

        try {
            // GPS.removeCallback();
        } catch (Exception e) {
        }
    }

    @Override
    public void onGlobalLayout() {
        View rootView = findViewById(R.id.comments_root);
        int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();

        if (mGlobalHeightDiff == heightDiff)
            return;

        mGlobalHeightDiff = heightDiff;

        if (heightDiff > getResources().getInteger(R.integer.keyboard_ths)) {
            if (mReplyState.get()) {
                commentText.setHint(R.string.write_your_reply);
            } else {
                commentText.setHint(R.string.comment_input_hint);
            }
        } else {
            commentText.setHint(R.string.comment_input_hint);
            mReplyState.set(false);
            mReplyComment = null;
        }
    }

    private  class GetFriendlist extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        List<IfriendListDTO> userList = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {

                if(userList!= null)
                    userList = null;

                if(friendArrayList!= null)
                    friendArrayList = null;

                if(duplicateArrayList!= null)
                    duplicateArrayList = null;

                friendArrayList = new ArrayList<String>();
                duplicateArrayList = new ArrayList<String>();

                IfriendRequest http = new IfriendRequest(mActivity);

                IfriendaDenyConditionDataDTO getAllfriedDTO = new IfriendaDenyConditionDataDTO(MainActivity.enc_username);

                IfriendRequestDTO ifriendRequestDTO = new IfriendRequestDTO(getAllfriedDTO, Constants.ENCRYPT_IFRIEND_CHAT_TABLE, Constants.ENCRYPT_FIND_METHOD);
                IfriendRequestObjectDTO ifriendRequestObjectDTO = new IfriendRequestObjectDTO(ifriendRequestDTO);

                userList = http.GetIfriendlist(ifriendRequestObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                if (userList != null)
                {
                    if (userList != null && userList.size() > 0)
                    {
                        if (friendArrayList.size() > 0) {
                            friendArrayList.clear();
                        }

                        for (int i = 0; i < userList.size(); i++)
                        {


                            requestuser = userList.get(i).getFriend();

                            int temp = i + 1;
                            friendArrayList.add(requestuser);
                            duplicateArrayList.add("iFriend" + temp);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class CreatNewComment extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mActivity);

                CommonRequestTypeDTO commentDTO = new CommonRequestTypeDTO(commentConditionDTO,  "roomComment");
                FinalObjectDTO loginbjectDTO = new FinalObjectDTO(commentDTO);
                status = http.AddRoomComment(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            try {
                try {

                    if (attantionPopup)
                        new AttentionComments(CommentRoomsActivity.this);

                    new GetComment().execute();
                    doowapmReplyState.set(false);

                } catch (Exception e2) {
                    e2.printStackTrace();
                }


            } catch (Exception e2) {
                e2.printStackTrace();
            }


        }
    }



    private class GetComment extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;

        List<RoomComment> reply = new ArrayList<>();
        List<RoomComment> temp = new ArrayList<>();
        List<RoomComment> data = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {

                reply.clear();
                temp.clear();
                data.clear();
                IfriendRequest http = new IfriendRequest(mActivity);

                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();


                mJsonObjectSub.put("cmnt_unq_id", roomObject.getRoom_id());

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "getRoomComemnt");

                main_object.put("requestData", requestdata);
                try {
                    data = http.GetRoomComment(main_object.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


               // data =  http.GetComment(getPetInfoObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            try {

                mList.clear();

                for (RoomComment item : data) {
                    if (item.isReply())
                        reply.add(item);
                }

                for (RoomComment item : data)
                {
                    if (!item.isReply()) {
                        temp.add(item);
                        for (RoomComment replyItem : reply)
                        {
                            if (replyItem.getReply_cmnt_unq_id().equals(item.getComment_id()))
                                temp.add(replyItem);
                        }
                    }
                }
                mList.addAll(temp);
                adapter.notifyDataSetChanged();
                stopProgress();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }



}
