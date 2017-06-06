package com.helio.cypher.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helio.cypher.R;
import com.helio.cypher.WebserviceDTO.GetMoodGraphObjectDTO;
import com.helio.cypher.WebserviceDTO.GetMoodgraphConditionDTO;
import com.helio.cypher.WebserviceDTO.GetMoodgraphDTO;
import com.helio.cypher.adapters.MoodGraphSecretAdapter;
import com.helio.cypher.connection.ConnectionDetector;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.dialogs.ProgressDialog;
import com.helio.cypher.models.Mood;
import com.helio.cypher.models.Point;
import com.helio.cypher.models.Secret;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.Utils;
import com.helio.cypher.views.ChartView;
import com.helio.cypher.views.MonthMoodgraph;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MoodActivity extends BaseActivity {

    public static boolean is_from_auto = false;
    private ChartView moodChart;

    private MonthMoodgraph monthmoodChart;


    String show_mood = "week";
    /*    HorizontalScrollView horizontalScrollView1 = null;*/
/*    List<ParseObject> stephistrylist = new ArrayList<>();*/

    boolean is_click = false;
    public static RelativeLayout secrets_rl;
    RelativeLayout graph_layout = null;
    public static ImageView close;
    public static ListView secrets_listview;
    Context ct = null;
    public static MoodGraphSecretAdapter adapter;
    public static RelativeLayout webview_layout = null;
    LinearLayout feed_view_back = null;
    static WebView mWebView = null;

    TextView week = null, month = null, year = null;
    public static TextView headert_text = null;
    //  TextView cancel_book = null;

    public interface ShareCallback {
        void onShare();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        startTracking(getString(R.string.mood_android));

        ct = this;
        //moodChart = (ChartView) findViewById(R.id.mood_chart);
        graph_layout = (RelativeLayout) findViewById(R.id.graph_layout);
        secrets_rl = (RelativeLayout) findViewById(R.id.secrets_rl);
        headert_text = (TextView) findViewById(R.id.headert_text);
        close = (ImageView) findViewById(R.id.close);
        secrets_listview = (ListView) findViewById(R.id.secrets_listview);
        mWebView = (WebView) findViewById(R.id.feed_view_vb);
        webview_layout = (RelativeLayout) findViewById(R.id.webview_layout);


        week = (TextView) findViewById(R.id.week);
        month = (TextView) findViewById(R.id.month);
        year = (TextView) findViewById(R.id.year);

        try {
            moodChart = new ChartView(ct);
            graph_layout.removeAllViews();
            graph_layout.addView(moodChart);

        } catch (Exception e) {
            e.printStackTrace();
        }

       /* cancel_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                graph_layout.setVisibility(View.VISIBLE);


            }
        });*/


        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (is_click == false && ConnectionDetector.isNetworkAvailable(ct))
                    {
                        showProgress();

                        is_click = true;

                        week.setBackgroundResource(R.drawable.orange_left_corner);
                        month.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                        year.setBackgroundResource(R.drawable.grey_right_corner);

                        week.setTextColor(getResources().getColor(R.color.white));
                        month.setTextColor(getResources().getColor(R.color.black));
                        year.setTextColor(getResources().getColor(R.color.black));

                        show_mood = "week";
                        graph_layout.removeAllViews();

                        if (moodChart != null)
                            moodChart = null;

                        moodChart = new ChartView(ct);
                        graph_layout.addView(moodChart);

                        if (ConnectionDetector.isNetworkAvailable(ct))
                            new GetSecondWeekMood().execute();

                    } else {
                        week.setBackgroundResource(R.drawable.orange_left_corner);
                        month.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                        year.setBackgroundResource(R.drawable.grey_right_corner);

                        week.setTextColor(getResources().getColor(R.color.white));
                        month.setTextColor(getResources().getColor(R.color.black));
                        year.setTextColor(getResources().getColor(R.color.black));

                        show_mood = "";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (is_click == false && ConnectionDetector.isNetworkAvailable(ct)) {
                        is_click = true;


                        week.setBackgroundResource(R.drawable.grey_left_corner);
                        month.setBackgroundColor(getResources().getColor(R.color.orange));
                        year.setBackgroundResource(R.drawable.grey_right_corner);

                        week.setTextColor(getResources().getColor(R.color.black));
                        month.setTextColor(getResources().getColor(R.color.white));
                        year.setTextColor(getResources().getColor(R.color.black));


                        graph_layout.setVisibility(View.VISIBLE);
                        graph_layout.removeAllViews();
                        show_mood = "month";

                        if (ConnectionDetector.isNetworkAvailable(ct))
                         new GetMonthMood().execute();


                    } else {
                        week.setBackgroundResource(R.drawable.grey_left_corner);
                        month.setBackgroundColor(getResources().getColor(R.color.orange));
                        year.setBackgroundResource(R.drawable.grey_right_corner);

                        week.setTextColor(getResources().getColor(R.color.black));
                        month.setTextColor(getResources().getColor(R.color.white));
                        year.setTextColor(getResources().getColor(R.color.black));

                        show_mood = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (is_click == false && ConnectionDetector.isNetworkAvailable(ct)) {
                        is_click = true;

                        week.setBackgroundResource(R.drawable.grey_left_corner);
                        month.setBackgroundColor(getResources().getColor(R.color.glimpse_gray));
                        year.setBackgroundResource(R.drawable.orange_right_corner);

                        week.setTextColor(getResources().getColor(R.color.black));
                        month.setTextColor(getResources().getColor(R.color.black));
                        year.setTextColor(getResources().getColor(R.color.white));


                        graph_layout.setVisibility(View.VISIBLE);


                        graph_layout.removeAllViews();
                        show_mood = "year";

                        new GetYearMood().execute();
                        // makeyearquery();
                    } else {
                        week.setBackgroundResource(R.drawable.grey_left_corner);
                        month.setBackgroundColor(getResources().getColor(R.color.buller_color));
                        year.setBackgroundResource(R.drawable.orange_right_corner);

                        week.setTextColor(getResources().getColor(R.color.black));
                        month.setTextColor(getResources().getColor(R.color.black));
                        year.setTextColor(getResources().getColor(R.color.white));

                        show_mood = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


        feed_view_back = (LinearLayout) findViewById(R.id.feed_view_back);
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
        findViewById(R.id.mood_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.mood_export).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (show_mood != null && !show_mood.equalsIgnoreCase("") && show_mood.equalsIgnoreCase("week")) {
                    showProgress();
                    Utils.shareDataOfSecret(findViewById(R.id.graph_layout), MoodActivity.this, 2f, new ShareCallback() {
                        @Override
                        public void onShare() {
                            stopProgress();
                        }
                    });
                } else if (show_mood != null && !show_mood.equalsIgnoreCase("") && show_mood.equalsIgnoreCase("month")) {
                    showProgress();

                    Utils.shareDataOfSecretMonth(RowcloumLayout, Emogiesnamelist, MoodActivity.this, 2f, new ShareCallback() {
                        @Override
                        public void onShare() {
                            stopProgress();
                        }
                    });

                } else {
                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(ct);
                    dialog1.setTitle("Mood Graph");

                    dialog1.setMessage("You can only share week and month graph");
                    dialog1.setPositiveButton(ct.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        }
                    });

                    AlertDialog alert = dialog1.create();
                    alert.setCanceledOnTouchOutside(false);
                    alert.show();

                }

            }
        });

        secrets_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    secrets_rl.setVisibility(View.GONE);

                    if (show_mood.equalsIgnoreCase("week"))
                    {

                        moodChart = new ChartView(ct);
                        graph_layout.removeAllViews();
                        graph_layout.addView(moodChart);
                        //makeQuery();
                        new GetSecondWeekMood().execute();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        graph_layout.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {



                show_mood = "week";
                graph_layout.removeAllViews();

                if (moodChart != null)
                    moodChart = null;

                moodChart = new ChartView(ct);
                graph_layout.addView(moodChart);

                if (ConnectionDetector.isNetworkAvailable(ct))
                    new GetSecondWeekMood().execute();

            }
        },2000);

        if (ConnectionDetector.isNetworkAvailable(ct))
            new GetWeekMood().execute();



        //makeQuery();
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        secrets_rl.setVisibility(View.GONE);
    }

/*
    private void makeQuery() {
        showProgress();
        new MoodLoader(new UpdateCallback() {
            @Override
            public void onUpdate(List<Secret> list) {
                try {
                    stopProgress();

                    moodChart.update(list);

                    if (is_from_auto) {
                        moodChart.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Utils.AutoshareDataOfSecret(findViewById(R.id.graph_layout), MoodActivity.this, 2f);
                                is_from_auto = false;
                            }
                        }, 2000);
                    }

                    is_click = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).execute();
    }

*/

/*
    private void makemonthquery() {
        showProgress();

        new MoodLoader(new UpdateCallback() {
            @Override
            public void onUpdate(List<Secret> list) {
                try {

                    if (list != null && list.size() > 0) {
                        mData = list;

                    }
                    drawmonthgraph();
                    is_click = false;
                    stopProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).executemonth();
    }

*/

 /*   private void makeyearquery() {


        int daycount = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd");
            String formattedDate = " ";

            Calendar c = Calendar.getInstance();
            formattedDate = df.format(c.getTime());
            daycount = Integer.parseInt(formattedDate);
        } catch (Exception e) {

        }
        int iterator = 365 + daycount;

        showProgress();

        new MoodLoader(new UpdateCallback() {
            @Override
            public void onUpdate(List<Secret> list) {
                try {

                    if (list != null && list.size() > 0) {
                        mData = list;
                    }
                    drawyeargraph();
                    is_click = false;
                    stopProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).executeyear(iterator);
    }

*/
    public void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.mood_progress_bg_iv), findViewById(R.id.mood_progress_pb));
    }

    public void stopProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.mood_progress_bg_iv), findViewById(R.id.mood_progress_pb));
    }


    private static final int FULL_COLUMNS_COUNT = 10;
    private static final int FULL_ROWS_COUNT = 14;
    private static final int ROW_START_COUNT = 2;

    private int cellWidth;
    private int cellHeight;
    private int maxRadius;

    SimpleDateFormat outFormat = new SimpleDateFormat("EEE");

    public static String Dayname = "";

    public static List<Secret> newSecrets = new ArrayList<>();

    HashMap<String, Mood> moodMap = new HashMap<String, Mood>();

    RelativeLayout RowcloumLayout = null;
    private HashMap<String, Point> mPointMap = null;
    private String[] weekList = new String[31];


    //  private String[] yearlist = null;
    private String[] yearlistname = null;

    //ArrayList<String> keylist = new ArrayList<>();
    private List<Secret> mData = new ArrayList<>();


    private void drawmonthgraph() {
        countDays();
        calculateDimensions();
        drawField();
    }


    private void drawyeargraph() {
        countyearDays();
        calculateyearDimensions();
        drawyearField();
    }


    private void calculateDimensions() {
        try {
            cellWidth = graph_layout.getWidth() / FULL_COLUMNS_COUNT;
            cellHeight = graph_layout.getHeight() / FULL_ROWS_COUNT;
            if (cellWidth > 0) {
                maxRadius = (cellWidth / 2) - (cellWidth / 10);
                setupPointMap();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void calculateyearDimensions() {
        try {
            cellWidth = graph_layout.getWidth() / FULL_COLUMNS_COUNT;
            cellHeight = graph_layout.getHeight() / FULL_ROWS_COUNT;
            if (cellWidth > 0) {
                maxRadius = (cellWidth / 2) - (cellWidth / 10);
                setupyearPointMap();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setupyearPointMap() {
        try {
            mPointMap = null;
            mPointMap = new HashMap<>();

            int xStart = cellWidth;
            int yStart = cellHeight;
            for (int mood = 1; mood <= Constants.emoList.length; mood++) {
                for (int week = 1; week <= yearlistname.length; week++) {
                    String key = Constants.emoList[mood - 1] + yearlistname[week - 1];
                    mPointMap.put(key, new Point((cellWidth) + week * 2 + (week * xStart), mood * yStart));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setupPointMap() {
        try {
            mPointMap = null;
            mPointMap = new HashMap<>();

            int xStart = cellWidth;
            int yStart = cellHeight;
            for (int mood = 1; mood <= Constants.emoList.length; mood++) {
                for (int week = 1; week <= weekList.length; week++) {
                    String key = Constants.emoList[mood - 1] + weekList[week - 1];
                    mPointMap.put(key, new Point((cellWidth) + week * 2 + (week * xStart), mood * yStart));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void countDays() {


        int iterator = 30;

        for (int i = 0; i < weekList.length; i++) {
            Calendar nextday = Calendar.getInstance();
            nextday.add(Calendar.DAY_OF_MONTH, -iterator);
            Date date = nextday.getTime();
            weekList[i] = (String) android.text.format.DateFormat.format("dd", date) + " " + (String) android.text.format.DateFormat.format("MMM", date);  //20

            iterator--;
        }

    }


    private void countyearDays() {


        SimpleDateFormat df = new SimpleDateFormat("dd");
        String formattedDate = " ";

        Calendar c = Calendar.getInstance();
        formattedDate = df.format(c.getTime());
        int daycount = 0;
        try {
            daycount = Integer.parseInt(formattedDate);
        } catch (Exception e) {

        }
        int iterator = 365 + daycount;


        if (yearlistname != null)
            yearlistname = null;

        yearlistname = new String[iterator + 1];


        try {

            for (int i = 0; i < yearlistname.length; i++) {
                Calendar nextday = Calendar.getInstance();
                nextday.add(Calendar.DAY_OF_MONTH, -iterator);
                Date date = nextday.getTime();
                //  yearlist[i] = (String) android.text.format.DateFormat.format("dd", date) + " " + (String) android.text.format.DateFormat.format("MMM", date);  //20
                yearlistname[i] = (String) android.text.format.DateFormat.format("dd", date) + " " + (String) android.text.format.DateFormat.format("MMM", date) + " " + (String) android.text.format.DateFormat.format("yyyy", date);  //20
                iterator--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    LinearLayout Emogiesnamelist = null;

    private void drawField() {
        try {
            if (RowcloumLayout != null)
                RowcloumLayout = null;


            if (Emogiesnamelist != null)
                Emogiesnamelist = null;


            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout Mainhorizontalayout = new LinearLayout(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            Mainhorizontalayout.setLayoutParams(lp);
            Mainhorizontalayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout rowhorizontallayout = new LinearLayout(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, cellHeight, 0, 0);


            rowhorizontallayout.setLayoutParams(lp);
            rowhorizontallayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int i = 0; i <= weekList.length; i++) {

                LinearLayout verticaldaytextlayout = new LinearLayout(ct);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                verticaldaytextlayout.setLayoutParams(lp);
                verticaldaytextlayout.setOrientation(LinearLayout.VERTICAL);


                TextView emogiestname = new TextView(ct);
                lp = new LinearLayout.LayoutParams(1, graph_layout.getHeight() - cellHeight * 2/*+ (rectText.height() / 2)*/);

                if (i == 0)
                    lp.setMargins(cellWidth / 2 + cellWidth / 10, cellHeight / 2 + cellHeight / 8, 0, 0);
                else
                    lp.setMargins(cellWidth + 1, cellHeight / 2 + cellHeight / 8, 0, 0);

                emogiestname.setLayoutParams(lp);


                if (i != (weekList.length)) {
                    emogiestname.setBackgroundColor(Color.parseColor("#ffffff"));

                }

                verticaldaytextlayout.addView(emogiestname);
                TextView firgy = new TextView(ct);
                lp = new LinearLayout.LayoutParams(cellWidth / 2 + cellWidth / 4, LinearLayout.LayoutParams.WRAP_CONTENT);

                if (i != 0) {
                    firgy.setLayoutParams(lp);
                    firgy.setText(weekList[i - 1]);
                    firgy.setGravity(Gravity.LEFT);
                    firgy.setTextColor(Color.parseColor("#ffffff"));
                    firgy.setTextSize(12);
                    verticaldaytextlayout.addView(firgy);
                }

                rowhorizontallayout.addView(verticaldaytextlayout);


            }


            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            horizontalScrollView.setLayoutParams(lp);

            RowcloumLayout = new RelativeLayout(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            RowcloumLayout.setLayoutParams(lp);
            RowcloumLayout.setBackgroundColor(getResources().getColor(R.color.homemenu));

            Emogiesnamelist = new LinearLayout(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //lp.setMargins(0, cellHeight, 0, 0);
            Emogiesnamelist.setLayoutParams(lp);
            Emogiesnamelist.setOrientation(LinearLayout.VERTICAL);
            Emogiesnamelist.setBackgroundColor(getResources().getColor(R.color.homemenu));


            TextView emogiestname1 = new TextView(ct);
            lp = new LinearLayout.LayoutParams(cellWidth * 2, cellHeight /*+ (rectText.height() / 2)*/);
            emogiestname1.setLayoutParams(lp);
            emogiestname1.setTextSize(11);
            Emogiesnamelist.addView(emogiestname1);

            LinearLayout rowverticallayout = new LinearLayout(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, cellHeight, 0, 0);
            rowverticallayout.setLayoutParams(lp);

            rowverticallayout.setOrientation(LinearLayout.VERTICAL);

            for (int i = 1; i < FULL_ROWS_COUNT; i++) {


                if (i <= Constants.emoList.length) {
                    TextView emogiestname = new TextView(ct);
                    lp = new LinearLayout.LayoutParams(cellWidth * 2, cellHeight /*+ (rectText.height() / 2)*/);
                    emogiestname.setLayoutParams(lp);
                    emogiestname.setTextSize(11);
                    emogiestname.setGravity(Gravity.RIGHT);
                    emogiestname.setText(Constants.emoList[i - 1]);
                    emogiestname.setTextColor(Color.parseColor("#ffffff"));
                    Emogiesnamelist.addView(emogiestname);
                }
                if (i <= Constants.emoList.length) {
                    TextView firgy = new TextView(ct);
                    if (i == 1) {
                        lp = new LinearLayout.LayoutParams(cellWidth, cellHeight + cellHeight / 6 + 2);
                    } else {
                        lp = new LinearLayout.LayoutParams(cellWidth, cellHeight - 1);
                    }
                    firgy.setLayoutParams(lp);
                    rowverticallayout.addView(firgy);
                    TextView emogiestname = new TextView(ct);
                    lp = new LinearLayout.LayoutParams(cellWidth + cellWidth * weekList.length, 1 /*+ (rectText.height() / 2)*/);
                    emogiestname.setLayoutParams(lp);
                    emogiestname.setBackgroundColor(Color.parseColor("#ffffff"));
                    rowverticallayout.addView(emogiestname);

                }


            }

            TextView emogiestname2 = new TextView(ct);
            lp = new LinearLayout.LayoutParams(cellWidth * 2, cellHeight / 2 + cellHeight / 7/*+ (rectText.height() / 2)*/);
            emogiestname2.setLayoutParams(lp);
            emogiestname2.setTextSize(11);
            Emogiesnamelist.addView(emogiestname2);


            int maxCount = 0;
            float ratio;
            RowcloumLayout.addView(rowhorizontallayout);
            RowcloumLayout.addView(rowverticallayout);


            // keylist.clear();

            // moodMap = new HashMap<String, Mood>();
            moodMap.clear();
            for (Secret object : mData) {
                Mood item = new Mood();
                StringBuilder builder = new StringBuilder();
                builder.append(object.getFeel().toUpperCase());

                Date date = object.getCreatedDate();
                String createdate = (String) android.text.format.DateFormat.format("dd", date) + " " + (String) android.text.format.DateFormat.format("MMM", date);  //20
                String createdate1 = "#" + (String) android.text.format.DateFormat.format("dd", date) + " " + (String) android.text.format.DateFormat.format("MMM", date);  //20


                builder.append(createdate);

                item.setPoint(mPointMap.get(builder.toString()));

                item.setkey(object.getFeel().toUpperCase() + createdate1);

                if (!moodMap.containsKey(builder.toString())) {
                    moodMap.put(builder.toString(), item);

                } else {
                    Mood obj = moodMap.get(builder.toString());
                    obj.setCount(obj.getCount() + 1);
                    if (obj.getCount() > maxCount) {
                        maxCount = obj.getCount();
                    }
                }

                builder.setLength(0);
            }

            int counter = 0;
            for (final Mood item : moodMap.values()) {
                ImageView drawcricle = new ImageView(ct);
                ratio = maxCount == 0 ? maxRadius : maxRadius / maxCount;

                RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(cellWidth  /*+ cellWidth / 5*/, cellWidth  /*+ cellWidth / 5*/);
                try {
                    lp2.setMargins(item.getPoint().getX() - cellWidth * 2 + cellWidth / 13, item.getPoint().getY() - cellHeight / 3 + cellHeight / 25, 0, 0);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                drawcricle.setImageResource(R.drawable.secret_circle);
                lp2.addRule(RelativeLayout.RIGHT_OF, Emogiesnamelist.getId());
                drawcricle.setLayoutParams(lp2);
                drawcricle.setPadding(cellWidth / 2 - (int) (ratio * item.getCount()), cellWidth / 2 - (int) (ratio * item.getCount()), cellWidth / 2 - (int) (ratio * item.getCount()), cellWidth / 2 - (int) (ratio * item.getCount()));
                drawcricle.setId(counter + 1);

                drawcricle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = v.getId();
                        String key = item.getkey();

                        if (newSecrets.size() > 0) {
                            newSecrets.clear();
                        }

                        ((MoodActivity) ct).showProgress();
                        /*int positon = item.getPoint().getY() / cellHeight;

                        int weekposition = item.getPoint().getX() / cellWidth - 2;*/

                        String[] keyaray = key.split("#");
                        String feelname = keyaray[0];


                        // String feelname = Constants.emoList[index - 1].toLowerCase();
                        MoodActivity.secrets_rl.setVisibility(View.VISIBLE);

                        for (int i = 0; i < mData.size(); i++) {
                            //Dayname = outFormat.format(mData.get(i).getCreatedDate()).toUpperCase();

                            //  Date date = mData.get(i).getCreatedDate();
                            //  String createdate = (String) android.text.format.DateFormat.format("dd", date)+ " " + (String) android.text.format.DateFormat.format("MMM", date) ;  //20


                            if (feelname.equalsIgnoreCase(mData.get(i).getFeel().toUpperCase())) {

                                Date date = mData.get(i).getCreatedDate();
                                String createdate = (String) android.text.format.DateFormat.format("dd", date) + " " + (String) android.text.format.DateFormat.format("MMM", date);  //20


                                //for(int j=0; j<weekList.length; j++)
                                {
                                    if (keyaray[1].equalsIgnoreCase(createdate)) {
                                        newSecrets.add(mData.get(i));
                                        // break;

                                    }
                                }
                            }


                        }

                        if (newSecrets.size() > 0) {
                            headert_text.setText(feelname);
                            MoodActivity.adapter = new MoodGraphSecretAdapter(LayoutInflater.from(ct), newSecrets, ct);
                            MoodActivity.secrets_listview.setAdapter(MoodActivity.adapter);
                            ((MoodActivity) ct).stopProgress();
                        }
                    }
                });


                counter++;
                RowcloumLayout.addView(drawcricle);

                //  canvas.drawCircle(item.getPoint().getX(), item.getPoint().getY(), (int) (ratio * item.getCount()), whitePaint);

            }


            horizontalScrollView.addView(RowcloumLayout);
            horizontalScrollView.setHorizontalScrollBarEnabled(false);
            Mainhorizontalayout.addView(Emogiesnamelist);

            TextView firgy = new TextView(ct);
            lp = new LinearLayout.LayoutParams(cellWidth / 3, cellHeight - 10);
            firgy.setLayoutParams(lp);
            Mainhorizontalayout.addView(firgy);
            Mainhorizontalayout.addView(horizontalScrollView);


            graph_layout.addView(Mainhorizontalayout);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void drawyearField() {
        try {

            if (RowcloumLayout != null)
                RowcloumLayout = null;

            LinearLayout Mainhorizontalayout = new LinearLayout(ct);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            Mainhorizontalayout.setLayoutParams(lp);
            Mainhorizontalayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout rowhorizontallayout = new LinearLayout(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, cellHeight, 0, 0);
            rowhorizontallayout.setLayoutParams(lp);
            rowhorizontallayout.setOrientation(LinearLayout.HORIZONTAL);
            try {
                for (int i = 0; i <= yearlistname.length; i++) {

                    LinearLayout verticaldaytextlayout = new LinearLayout(ct);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    verticaldaytextlayout.setLayoutParams(lp);
                    verticaldaytextlayout.setOrientation(LinearLayout.VERTICAL);


                    TextView emogiestname = new TextView(ct);
                    lp = new LinearLayout.LayoutParams(1, graph_layout.getHeight() - cellHeight * 2/*+ (rectText.height() / 2)*/);

                    if (i == 0)
                        lp.setMargins(cellWidth / 2 + cellWidth / 10, cellHeight / 2 + cellHeight / 8, 0, 0);
                    else
                        lp.setMargins(cellWidth + 1, cellHeight / 2 + cellHeight / 8, 0, 0);

                    emogiestname.setLayoutParams(lp);


                    if (i != (yearlistname.length)) {
                        emogiestname.setBackgroundColor(Color.parseColor("#ffffff"));

                    }

                    verticaldaytextlayout.addView(emogiestname);

                    TextView firgy = new TextView(ct);

                    lp = new LinearLayout.LayoutParams(cellWidth / 2 + cellWidth / 4, LinearLayout.LayoutParams.WRAP_CONTENT);

                    if (i != 0) {
                        firgy.setLayoutParams(lp);
                        firgy.setText(yearlistname[i - 1]);
                        firgy.setGravity(Gravity.LEFT);
                        firgy.setTextColor(Color.parseColor("#ffffff"));
                        firgy.setTextSize(12);
                        verticaldaytextlayout.addView(firgy);
                    }

                    rowhorizontallayout.addView(verticaldaytextlayout);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            LinearLayout Emogiesnamelist = new LinearLayout(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, cellHeight, 0, 0);
            Emogiesnamelist.setLayoutParams(lp);
            Emogiesnamelist.setOrientation(LinearLayout.VERTICAL);

            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            horizontalScrollView.setLayoutParams(lp);

            RowcloumLayout = new RelativeLayout(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            RowcloumLayout.setLayoutParams(lp);
            RowcloumLayout.setBackgroundColor(getResources().getColor(R.color.homemenu));
            LinearLayout rowverticallayout = new LinearLayout(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, cellHeight, 0, 0);
            rowverticallayout.setLayoutParams(lp);
            rowverticallayout.setOrientation(LinearLayout.VERTICAL);
            for (int i = 1; i < FULL_ROWS_COUNT; i++) {
                if (i <= Constants.emoList.length) {
                    TextView emogiestname = new TextView(ct);
                    lp = new LinearLayout.LayoutParams(cellWidth * 2, cellHeight /*+ (rectText.height() / 2)*/);
                    emogiestname.setLayoutParams(lp);
                    emogiestname.setTextSize(11);
                    emogiestname.setGravity(Gravity.RIGHT);
                    emogiestname.setText(Constants.emoList[i - 1]);
                    emogiestname.setTextColor(Color.parseColor("#ffffff"));
                    Emogiesnamelist.addView(emogiestname);
                }
                if (i <= Constants.emoList.length) {
                    TextView firgy = new TextView(ct);
                    if (i == 1) {
                        lp = new LinearLayout.LayoutParams(cellWidth, cellHeight + cellHeight / 6 + 2);
                    } else {
                        lp = new LinearLayout.LayoutParams(cellWidth, cellHeight - 1);
                    }
                    firgy.setLayoutParams(lp);
                    rowverticallayout.addView(firgy);
                    TextView emogiestname = new TextView(ct);
                    lp = new LinearLayout.LayoutParams(cellWidth + cellWidth * (yearlistname.length - 1) + yearlistname.length * 2, 1 /*+ (rectText.height() / 2)*/);
                    emogiestname.setLayoutParams(lp);

                    emogiestname.setBackgroundColor(Color.parseColor("#ffffff"));
                    rowverticallayout.addView(emogiestname);

                }


            }

            int maxCount = 0;
            float ratio;


            RowcloumLayout.addView(rowhorizontallayout);
            RowcloumLayout.addView(rowverticallayout);

            moodMap.clear();
            for (Secret object : mData) {
                Mood item = new Mood();
                StringBuilder builder = new StringBuilder();
                builder.append(object.getFeel().toUpperCase());


                Date date = object.getCreatedDate();
                String createdate = (String) android.text.format.DateFormat.format("dd", date) + " " + (String) android.text.format.DateFormat.format("MMM", date) + " " + (String) android.text.format.DateFormat.format("yyyy", date); //20
                String createdate1 = "#" + (String) android.text.format.DateFormat.format("dd", date) + " " + (String) android.text.format.DateFormat.format("MMM", date);  //20


                builder.append(createdate);

                item.setPoint(mPointMap.get(builder.toString()));

                item.setkey(object.getFeel().toUpperCase() + createdate1);

                if (!moodMap.containsKey(builder.toString())) {
                    moodMap.put(builder.toString(), item);

                } else {
                    Mood obj = moodMap.get(builder.toString());
                    obj.setCount(obj.getCount() + 1);
                    if (obj.getCount() > maxCount) {
                        maxCount = obj.getCount();
                    }
                }

                builder.setLength(0);
            }

            int counter = 0;
            for (final Mood item : moodMap.values()) {
                ImageView drawcricle = new ImageView(ct);

                //keylist.add(item.getkey());
               /* if (item.getCount() > 1)
                {
                    RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(cellWidth  *//*+ cellWidth / 5*//*, cellWidth  *//*+ cellWidth / 5*//*);
                    lp2.setMargins(item.getPoint().getX() - cellWidth * 2 +cellWidth / 10 , item.getPoint().getY() -cellHeight/3, 0, 0);
                    drawcricle.setImageResource(R.drawable.secret_circle);
                    drawcricle.setLayoutParams(lp2);
                    drawcricle.setPadding(cellWidth /(item.getCount()+1),cellWidth / (item.getCount()+1),cellWidth / (item.getCount()+1),cellWidth / (item.getCount()+1));
                } else {

                    RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(cellWidth  *//*+ cellWidth / 5*//*, cellWidth  *//*+ cellWidth / 5*//*);
                    lp2.setMargins(item.getPoint().getX() - cellWidth * 2 +cellWidth / 10 , item.getPoint().getY() -cellHeight/3, 0, 0);
                    drawcricle.setImageResource(R.drawable.secret_circle);
                    drawcricle.setLayoutParams(lp2);
                    drawcricle.setPadding(cellWidth /2 - 4*item.getCount()    ,cellWidth /2 - 4*item.getCount(),cellWidth /2 - 4*item.getCount() ,cellWidth /2  - 4*item.getCount() );

                }*/
                ratio = maxCount == 0 ? maxRadius : maxRadius / maxCount;
                RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(cellWidth  /*+ cellWidth / 5*/, cellWidth  /*+ cellWidth / 5*/);

                try {
                    lp2.setMargins(item.getPoint().getX() - cellWidth * 2 + cellWidth / 13, item.getPoint().getY() - cellHeight / 3 + cellHeight / 25, 0, 0);
                    //lp2.setMargins(item.getPoint().getX() - cellWidth * 2 +cellWidth / 10 , item.getPoint().getY() -cellHeight/3 +2, 0, 0);
                    drawcricle.setImageResource(R.drawable.secret_circle);
                    drawcricle.setLayoutParams(lp2);
                    drawcricle.setPadding(cellWidth / 2 - (int) (ratio * item.getCount()), cellWidth / 2 - (int) (ratio * item.getCount()), cellWidth / 2 - (int) (ratio * item.getCount()), cellWidth / 2 - (int) (ratio * item.getCount()));

                } catch (Exception e) {
                    e.printStackTrace();
                }


                drawcricle.setId(counter + 1);

                drawcricle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = v.getId();
                        String key = item.getkey();

                        if (newSecrets.size() > 0) {
                            newSecrets.clear();
                        }

                        ((MoodActivity) ct).showProgress();


                        String[] keyaray = key.split("#");
                        String feelname = keyaray[0];

                        MoodActivity.secrets_rl.setVisibility(View.VISIBLE);

                        for (int i = 0; i < mData.size(); i++) {

                            if (feelname.equalsIgnoreCase(mData.get(i).getFeel().toUpperCase())) {

                                Date date = mData.get(i).getCreatedDate();
                                String createdate = (String) android.text.format.DateFormat.format("dd", date) + " " + (String) android.text.format.DateFormat.format("MMM", date);

                                if (keyaray[1].equalsIgnoreCase(createdate)) {
                                    newSecrets.add(mData.get(i));


                                }

                            }


                        }

                        if (newSecrets.size() > 0) {
                            headert_text.setText(feelname);
                            MoodActivity.adapter = new MoodGraphSecretAdapter(LayoutInflater.from(ct), newSecrets, ct);
                            MoodActivity.secrets_listview.setAdapter(MoodActivity.adapter);
                            ((MoodActivity) ct).stopProgress();
                        }
                    }
                });

                counter++;
                RowcloumLayout.addView(drawcricle);

            }


            horizontalScrollView.addView(RowcloumLayout);
            horizontalScrollView.setHorizontalScrollBarEnabled(false);
            Mainhorizontalayout.addView(Emogiesnamelist);

            TextView firgy = new TextView(ct);
            lp = new LinearLayout.LayoutParams(cellWidth / 3, cellHeight - 10);
            firgy.setLayoutParams(lp);
            Mainhorizontalayout.addView(firgy);
            Mainhorizontalayout.addView(horizontalScrollView);


            graph_layout.addView(Mainhorizontalayout);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int show(String time) {
        int day = 0;
        try {
            String outputPattern = "MM/dd/yyyy";
            SimpleDateFormat format = new SimpleDateFormat(outputPattern);


            Date Date1 = format.parse(getdate());
            Date Date2 = format.parse(time);
            long mills = Date1.getTime() - Date2.getTime();
            long Day1 = mills / (1000 * 60 * 60);

            day = (int) Day1 / 24;


    /*        if (day < 0)
                day = 0;*/


        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    public static String getdate() {
        String time = "";
        try {
            String outputPattern = "MM/dd/yyyy";

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat(outputPattern);
            time = df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return time;

    }


    private class GetWeekMood extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        List<Secret> list = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                GetMoodgraphConditionDTO getMoodgraphConditionDTO = new GetMoodgraphConditionDTO(MainActivity.enc_username);

                GetMoodGraphObjectDTO getUnreadMessageObjectDTO = new GetMoodGraphObjectDTO(new GetMoodgraphDTO(getMoodgraphConditionDTO, Constants.ENCRYPT_WEEK_METHOD));

                list = http.GetMoodGraphSecret(getUnreadMessageObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            try {
              //  is_click = false;
                //stopProgress();

                try {
                    graph_layout.setVisibility(View.GONE);
                }
                catch (Exception e)
                {

                }

                if (list != null && list.size() > 0)
                {
                    moodChart.update(list);
                }

               /* if (is_from_auto) {
                    moodChart.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Utils.AutoshareDataOfSecret(findViewById(R.id.graph_layout), MoodActivity.this, 2f);
                            is_from_auto = false;
                        }
                    }, 2000);
                }
*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class GetSecondWeekMood extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        List<Secret> list = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                GetMoodgraphConditionDTO getMoodgraphConditionDTO = new GetMoodgraphConditionDTO(MainActivity.enc_username);

                GetMoodGraphObjectDTO getUnreadMessageObjectDTO = new GetMoodGraphObjectDTO(new GetMoodgraphDTO(getMoodgraphConditionDTO, Constants.ENCRYPT_WEEK_METHOD));

                list = http.GetMoodGraphSecret(getUnreadMessageObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            try {
                is_click = false;
                stopProgress();

                try {
                    graph_layout.setVisibility(View.VISIBLE);
                }
                catch (Exception e)
                {

                }

                if (list != null && list.size() > 0)
                {
                    moodChart.update(list);



                }

                if (is_from_auto) {
                    moodChart.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Utils.AutoshareDataOfSecret(findViewById(R.id.graph_layout), MoodActivity.this, 2f);
                            is_from_auto = false;
                        }
                    }, 2000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private class GetYearMood extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        List<Secret> list = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                GetMoodgraphConditionDTO getMoodgraphConditionDTO = new GetMoodgraphConditionDTO(MainActivity.enc_username);

                GetMoodGraphObjectDTO getUnreadMessageObjectDTO = new GetMoodGraphObjectDTO(new GetMoodgraphDTO(getMoodgraphConditionDTO, Constants.ENCRYPT_YEAR_METHOD));

                list = http.GetMoodGraphSecret(getUnreadMessageObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            try {
                is_click = false;
                stopProgress();

                try {

                    if (list != null && list.size() > 0) {
                        mData = list;
                    }
                    drawyeargraph();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private class GetMonthMood extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        List<Secret> list = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                GetMoodgraphConditionDTO getMoodgraphConditionDTO = new GetMoodgraphConditionDTO(MainActivity.enc_username);

                GetMoodGraphObjectDTO getUnreadMessageObjectDTO = new GetMoodGraphObjectDTO(new GetMoodgraphDTO(getMoodgraphConditionDTO, Constants.ENCRYPT_MONTH_METHOD));

                list = http.GetMoodGraphSecret(getUnreadMessageObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            is_click = false;
            try {
                stopProgress();

                try {

                    if (list != null && list.size() > 0) {
                        mData = list;

                    }
                    drawmonthgraph();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}



