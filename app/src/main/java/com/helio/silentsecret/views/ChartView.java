package com.helio.silentsecret.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.MoodActivity;

import com.helio.silentsecret.adapters.MoodGraphSecretAdapter;
import com.helio.silentsecret.callbacks.TrendingCallback;
import com.helio.silentsecret.models.Mood;
import com.helio.silentsecret.models.Point;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChartView extends View {
    private static final int FULL_COLUMNS_COUNT = 10;
    private static final int FULL_ROWS_COUNT = 14;
    private static final int ROW_START_COUNT = 2;
    public static final int ONE_DAY = 86400000;
    Context ct = null;
    private int cellWidth;
    private int cellHeight;
    private int maxRadius;

    SimpleDateFormat outFormat = new SimpleDateFormat("EEE");

    public static String Dayname = "";
/*    private Paint whitePaint = new Paint();
    private Paint greyPaint = new Paint();
    private Paint moodPaint = new Paint();
    private Paint weekPaint = new Paint();
    private Paint backPaint = new Paint();*/

    private Paint whitePaint;
    private Paint greyPaint;
    private Paint moodPaint;
    private Paint weekPaint ;
    private Paint backPaint;

    private String[] weekList = new String[7];

    HashMap<String, Mood> moodMap = new HashMap<String, Mood>();

    private HashMap<String, Point> mPointMap = null;

    private List<Secret> mData = new ArrayList<>();
    public static List<Secret> newSecrets = new ArrayList<>();

    public ChartView(Context context) {

        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ct = context;
        whitePaint = null;
        greyPaint = null;
        moodPaint = null;
        weekPaint = null;
        backPaint = null;

         whitePaint = new Paint();
         greyPaint = new Paint();
         moodPaint = new Paint();
         weekPaint = new Paint();
         backPaint = new Paint();



        whitePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        whitePaint.setColor(Color.WHITE);
        greyPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        greyPaint.setColor(Color.WHITE);

        moodPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        moodPaint.setColor(Color.WHITE);
        moodPaint.setTextSize(getResources().getDimension(R.dimen.mood_chart_ts));
        moodPaint.setTextAlign(Paint.Align.RIGHT);
        weekPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        weekPaint.setColor(Color.WHITE);
        weekPaint.setTextSize(getResources().getDimension(R.dimen.mood_chart_ts));
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setColor(getResources().getColor(R.color.homemenu));
    }

    private void countDays(TrendingCallback callback) {

        long curTime = System.currentTimeMillis();
        int iterator = 6;

        for (int i = 0; i < weekList.length; i++) {
            weekList[i] = TimeUtil.dayStringFormat(curTime - (ONE_DAY * iterator));
            iterator--;
        }

        callback.onDone();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        countDays(new TrendingCallback() {
            @Override
            public void onDone() {
                calculateDimensions();
            }
        });
    }

    public void update(List<Secret> items) {
        try {
            mData.clear();
            mData.addAll(items);
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void calculateDimensions() {
        try {
            cellWidth = getWidth() / FULL_COLUMNS_COUNT;
            cellHeight = getHeight() / FULL_ROWS_COUNT;
            if(cellWidth>0) {
                maxRadius = (cellWidth / 2) - (cellWidth / 10);
                setupPointMap();
                invalidate();
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
                    mPointMap.put(key, new Point((cellWidth * ROW_START_COUNT) + (week * xStart), mood * yStart));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawField(canvas);
        drawSecrets(canvas);
    }

    public void drawSecrets(Canvas canvas) {
        if (mData.isEmpty())
            return;


        int maxCount = 0;
        float ratio;
        try {


            for (Secret object : mData)
            {
                Mood item = new Mood();
                StringBuilder builder = new StringBuilder();
                builder.append(object.getFeel().toUpperCase());
                builder.append(TimeUtil.dayStringFormat(object.getCreatedDate().getTime()));

                item.setPoint(mPointMap.get(builder.toString()));

                if (!moodMap.containsKey(builder.toString()))
                    moodMap.put(builder.toString(), item);
                else {
                    Mood obj = moodMap.get(builder.toString());
                    obj.setCount(obj.getCount() + 1);
                    if (obj.getCount() > maxCount) {
                        maxCount = obj.getCount();
                    }
                }

                builder.setLength(0);
            }

            for (Mood item : moodMap.values())
            {
                ratio = maxCount == 0 ? maxRadius : maxRadius / maxCount;
                canvas.drawCircle(item.getPoint().getX(), item.getPoint().getY(), (int) (ratio * item.getCount()), whitePaint);

            }

        } catch (Exception e) {

        }
    }

    private void drawField(Canvas canvas) {
        try {


            canvas.drawRect(0, 0, getWidth(), getHeight(), backPaint);

            int width = getWidth();
            int height = getHeight();

            int margin = cellWidth / 3;

            for (int i = ROW_START_COUNT; i < FULL_COLUMNS_COUNT; i++) {
                Rect rectText = new Rect();

                if (i > ROW_START_COUNT) {
                    String valueText = weekList[i - 3].substring(0, 1);
                    weekPaint.getTextBounds(valueText, 0, valueText.length(), rectText);
                    canvas.drawText(valueText, (i * cellWidth) - (rectText.width() / 2), height - (cellHeight + (rectText.height() / 2)), weekPaint);
                }

                if (i != ROW_START_COUNT)
                    canvas.drawLine(i * cellWidth, margin, i * cellWidth, height - (cellHeight + (margin * 2)), greyPaint);
            }

            for (int i = 1; i < FULL_ROWS_COUNT; i++) {
                Rect rectText = new Rect();

                if (i <= Constants.emoList.length) {
                    String valueText = Constants.emoList[i - 1];
                    moodPaint.getTextBounds(valueText, 0, valueText.length(), rectText);
                    canvas.drawText(valueText, cellWidth * 2, i * cellHeight + (rectText.height() / 2), moodPaint);
                }

                if (i != FULL_ROWS_COUNT - 1)
                    canvas.drawLine((cellWidth * 2) + margin, i * cellHeight, width - margin, i * cellHeight, greyPaint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                try {
                    for (Mood item : moodMap.values())
                    {

                        //Check if the x and y position of the touch is inside the bitmap
                        if (x > item.getPoint().getX() && x < item.getPoint().getX() + cellWidth && y >= item.getPoint().getY() - cellHeight/3 && y <= item.getPoint().getY() + cellHeight) {

                            if (newSecrets.size() > 0) {
                                newSecrets.clear();
                            }

                            ((MoodActivity) ct).showProgress();
                            int positon = (item.getPoint().getY() +cellHeight/3)/ cellHeight;

                            int weekposition = item.getPoint().getX() / cellWidth - 2;

                            String feelname = Constants.emoList[positon -1].toLowerCase();
                            MoodActivity.secrets_rl.setVisibility(VISIBLE);

                            MoodActivity.headert_text.setText(feelname);
                            for (int i = 0; i < mData.size(); i++)
                            {
                                Dayname = outFormat.format(mData.get(i).getCreatedDate()).toUpperCase();
                                if (Dayname.contains(weekList[weekposition - 1]) && feelname.equalsIgnoreCase(mData.get(i).getFeel())) {
                                    newSecrets.add(mData.get(i));
                                }
                            }
                            if (newSecrets.size() > 0)
                            {

                                MoodActivity.adapter = new MoodGraphSecretAdapter(LayoutInflater.from(ct), newSecrets, ct);
                                MoodActivity.secrets_listview.setAdapter(MoodActivity.adapter);
                                ((MoodActivity) ct).stopProgress();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
        }
        return false;
    }
}
