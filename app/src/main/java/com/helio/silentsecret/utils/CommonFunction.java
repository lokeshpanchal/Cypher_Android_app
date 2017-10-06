package com.helio.silentsecret.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.helio.silentsecret.activities.ChatDetailsScreen;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.models.DisplaySizes;
import com.helio.silentsecret.models.PhotoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Maroof Ahmed Siddique on 9/26/2016.
 */
public class CommonFunction {
    private static String keys = "";

    public CommonFunction() {

    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static String gettime() {
        String time = "";
        try {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH-mm-ss");
            time = df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return time;

    }

    public static int getDevicewidth(Context ct) {
        DisplayMetrics displayMetrics = ct.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getDeviceHeight(Context ct) {
        DisplayMetrics displayMetrics = ct.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static String getdate() {
        String time = "";
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            time = df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return time;

    }


   /* public static void fetchTriggerWords()
    {

        ParseQuery<ParseObject> fetchTrigger = ParseQuery.getQuery("keywords");
        fetchTrigger.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null && objects.size() > 0) {
                    ParseObject mFetchObject = objects.get(0);
                    keys = mFetchObject.getString("keys");
                }
            }
        });
    }*/

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean is_numeric(String msg) {
        boolean is_num = false;
        for (int i = 0; i < 10; i++) {
            if (msg.contains("" + i)) {
                is_num = true;
                break;
            }
        }
        return is_num;
    }


    public static boolean isTriggered(String msg) {

        boolean istrigger = false;

        try {


            if (!MainActivity.stataicObjectDTO.getKeywords().equalsIgnoreCase("")) {

                String[] separated = MainActivity.stataicObjectDTO.getKeywords().split(";\\s*");
                for (int i = 0; i < separated.length; i++) {
                    String valueAfterSplit = separated[i];
                    if (valueAfterSplit.equalsIgnoreCase(""))
                        continue;

                    if (msg.toLowerCase().contains(valueAfterSplit.toLowerCase())) {

                        ChatDetailsScreen.TriggerWord = valueAfterSplit;
                        istrigger = true;
                        System.out.println("I found the trigger");
                        break;
                    } else {
                        istrigger = false;
                        System.out.println("trigger not found");

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return istrigger;
    }


    public static boolean isTriggeredNew(String msg) {

        boolean istrigger = false;

        try {
            if (!MainActivity.stataicObjectDTO.getKeywords().equalsIgnoreCase("")) {

                String[] separated = MainActivity.stataicObjectDTO.getKeywords().split(";\\s*");

                for (int i = 0; i < separated.length; i++) {
                    String valueAfterSplit = separated[separated.length - 1];

                    String[] separatedNew = valueAfterSplit.split(",\\s*");

                    System.out.println("separatedNew Length -----> " + separatedNew.length);
                    for (int j = 0; j < separatedNew.length; j++) {
                        String valueAfterSplitNew = separatedNew[j];

                        if (valueAfterSplitNew.equalsIgnoreCase(""))
                            continue;

                        if (msg.toLowerCase().contains(valueAfterSplitNew.toLowerCase())) {
                            istrigger = true;
                            ChatDetailsScreen.TriggerWord = valueAfterSplitNew;
                            break;
                        } else {
                            istrigger = false;

                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return istrigger;
    }

    public static boolean isServiceRunning(Context mContext, Class<?> serviceClass) {

        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())
                    && service.pid != 0) {
                //ShowLog("NotifyService", "ser name "+serviceClass.getName());
                return true;
            }
        }
        return false;
    }

    final static String TODAYS_STEPS = "todays_steps";

    public static int gettodaysstep(Context context) {
        int restoredText = 0;
        try {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getInt(TODAYS_STEPS, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restoredText;
    }

    public static void storertodaystep(Context context, int steps) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putInt(TODAYS_STEPS, steps);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    final static String TODAYS_RECOMONDED_STEP = "todays_recomonded_steps";

    public static int gettodaysrecomndedstep(Context context) {
        int restoredText = 0;
        try {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getInt(TODAYS_RECOMONDED_STEP, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restoredText;
    }

    public static void settodaysrecomndedstep(Context context, int steps) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putInt(TODAYS_RECOMONDED_STEP, steps);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*final static String LOCAL_LATLANG = "local_lat_lang";
    public static int GetLatLang(Context context)
    {
        int restoredText = 0;
        try
        {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getInt(LOCAL_LATLANG, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return restoredText;
    }

    public static void SetLatLang(Context context,int steps)
    {
        try
        {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putInt(LOCAL_LATLANG,steps);
            editor.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }*/


    public static int Getdiffbettwodate(String newtime, String oldtime) {
        int day = 0;
        try {
            String outputPattern = "MM/dd/yyyy";
            SimpleDateFormat format = new SimpleDateFormat(outputPattern);


            Date Date1 = format.parse(newtime);
            Date Date2 = format.parse(oldtime);
            long mills = Date1.getTime() - Date2.getTime();
            long Day1 = mills / (1000 * 60 * 60);

            day = (int) Day1 / 24;


            if (day < 0)
                day = 0;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }


    final static String TARGET_PUSH_DATE = "target_push_date";

    public static int GetTargetPushDate(Context context) {
        int restoredText = 0;
        try {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getInt(TARGET_PUSH_DATE, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restoredText;
    }

    public static void SetTargetPushDate(Context context, int steps) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putInt(TARGET_PUSH_DATE, steps);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    final static String FITNESS_OFF_ON = "fitnesss_off_on";

    public static String getfitnessonoff(Context context) {
        String restoredText = "";
        try {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getString(FITNESS_OFF_ON, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restoredText;
    }

    public static void setfitnessonoff(Context context, String onoff) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putString(FITNESS_OFF_ON, onoff);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    final static String TODAYS_DATE = "todays_date";

    public static String gettodaysdate(Context context) {
        String restoredText = "";
        try {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getString(TODAYS_DATE, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restoredText;
    }

    public static void storertodaydate(Context context, String date) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putString(TODAYS_DATE, date);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


 /*   public static void increaem2water()
    {
        ParseQuery<ParseObject> qrcode = ParseQuery.getQuery("VirtualAvatarStats");
        qrcode.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        qrcode.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {


                if (objects != null || objects.size() > 0)
                {
                    try
                    {

                        ParseObject vertualinfo = objects.get(0);
                        int waterpercent = vertualinfo.getInt("me2_water");

                        int total_scratch_count =0;

                        try
                        {
                            total_scratch_count = vertualinfo.getInt("total_scratch_count");

                            total_scratch_count = total_scratch_count +1;


                            if(waterpercent <= 90)
                            {
                                MainActivity.water  = waterpercent  + 10;
                            }



                        }
                        catch (Exception e3)
                        {
                            e3.printStackTrace();
                        }


                        if(waterpercent <= 90)
                        {
                            waterpercent = waterpercent + 10;

                            MainActivity.water_progress.setProgress(waterpercent);
                            vertualinfo.put("me2_water",waterpercent);
                            vertualinfo.put("scratch_count",MainActivity.scratch_count);
                            vertualinfo.put("total_scratch_count",total_scratch_count);
                            ParseACL acl = new ParseACL();
                            acl.setPublicReadAccess(true);
                            acl.setPublicWriteAccess(true);
                            vertualinfo.setACL(acl);

                            vertualinfo.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                }
                            });
                        }
                        else
                        {

                            vertualinfo.put("scratch_count",MainActivity.scratch_count);
                            vertualinfo.put("total_scratch_count",total_scratch_count);
                            ParseACL acl = new ParseACL();
                            acl.setPublicReadAccess(true);
                            acl.setPublicWriteAccess(true);
                            vertualinfo.setACL(acl);

                            vertualinfo.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                }
                            });
                        }

                    } catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                }

            }
        });
    }
*/


    public static void water_maintain() {

        try {

            String hme_water = MainActivity.petAvtarInfoDTO.getM2_water();
            if (hme_water != null && !hme_water.equalsIgnoreCase("") && !hme_water.equalsIgnoreCase("null")) {
                MainActivity.water = Integer.parseInt(hme_water);
            }
            MainActivity.water_progress.setProgress(MainActivity.water);

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }


    public static void food_maintain() {

        try {

            String hme_food = MainActivity.petAvtarInfoDTO.getHeart_food();
            if (hme_food != null && !hme_food.equalsIgnoreCase("") && !hme_food.equalsIgnoreCase("null")) {
                MainActivity.food = Integer.parseInt(hme_food);
            }
            MainActivity.food_progress.setProgress(MainActivity.food);

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }



   /* public static void decreasehearfood()
    {
        ParseQuery<ParseObject> qrcode = ParseQuery.getQuery("VirtualAvatarStats");
        qrcode.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        qrcode.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {


                if (objects != null || objects.size() > 0)
                {
                    try
                    {

                        ParseObject vertualinfo = objects.get(0);
                        int waterpercent = vertualinfo.getInt("heart_food");


                        try
                        {


                            if(waterpercent >= 20)
                            {
                                MainActivity.food  = waterpercent  - 10;
                            }



                        }
                        catch (Exception e3)
                        {
                            e3.printStackTrace();
                        }



                        if(waterpercent >= 20)
                        {
                            waterpercent = waterpercent - 10;
                            vertualinfo.put("heart_food",waterpercent);

                            MainActivity.food_progress.setProgress(waterpercent);

                            ParseACL acl = new ParseACL();
                            acl.setPublicReadAccess(true);
                            acl.setPublicWriteAccess(true);
                            vertualinfo.setACL(acl);

                            vertualinfo.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                }
                            });
                        }

                    } catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                }

            }
        });
    }*/




   /* public static void increaehug_oxygen()
    {
        ParseQuery<ParseObject> qrcode = ParseQuery.getQuery("VirtualAvatarStats");
        qrcode.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        qrcode.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {


                if (objects != null || objects.size() > 0)
                {
                    try
                    {

                        ParseObject vertualinfo = objects.get(0);
                        int waterpercent = vertualinfo.getInt("hug_oxygen");

                        int total_scratch_count =0;

                        try
                        {
                             total_scratch_count = vertualinfo.getInt("total_scratch_count");

                            total_scratch_count = total_scratch_count +1;

                            if(waterpercent <= 90) {
                                MainActivity.oxygen  = waterpercent  + 10;
                            }

                        }
                        catch (Exception e3)
                        {
                            e3.printStackTrace();
                        }

                        if(waterpercent <= 90)
                        {
                            waterpercent = waterpercent + 10;
                            vertualinfo.put("hug_oxygen",waterpercent);
                            vertualinfo.put("total_scratch_count", total_scratch_count);
                            vertualinfo.put("scratch_count",MainActivity.scratch_count);
                            int waterpercent1 = (int) waterpercent/10;
                            MainActivity.oxygenlevel.setBackgroundColor(Color.parseColor(MainActivity.colorsking[waterpercent1-1]));


                            ParseACL acl = new ParseACL();
                            acl.setPublicReadAccess(true);
                            acl.setPublicWriteAccess(true);
                            vertualinfo.setACL(acl);

                            vertualinfo.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                }
                            });
                        }
                        else
                        {
                            vertualinfo.put("scratch_count",MainActivity.scratch_count);
                            vertualinfo.put("total_scratch_count", total_scratch_count);
                            ParseACL acl = new ParseACL();
                            acl.setPublicReadAccess(true);
                            acl.setPublicWriteAccess(true);
                            vertualinfo.setACL(acl);

                            vertualinfo.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                }
                            });
                        }

                    } catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                }

            }
        });
    }*/


    public static void oxygen_maintain() {


        try {

            String hhug_food = MainActivity.petAvtarInfoDTO.getHug_oxygen();
            if (hhug_food != null && !hhug_food.equalsIgnoreCase("") && !hhug_food.equalsIgnoreCase("null")) {
                MainActivity.oxygen = Integer.parseInt(hhug_food);
            }

            int oxyzen = (int) MainActivity.oxygen / 10;
           // MainActivity.oxygenlevel.setBackgroundResource(MainActivity.colorsking[oxyzen - 1]);

        } catch (Exception e1) {
            e1.printStackTrace();
        }


    }





    /*public static void increaehug_comments_count()
    {
        ParseQuery<ParseObject> qrcode = ParseQuery.getQuery("VirtualAvatarStats");
        qrcode.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        qrcode.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {


                if (objects != null || objects.size() > 0)
                {
                    try
                    {
                        MainActivity.comments_count = MainActivity.comments_count + 1;
                            ParseObject vertualinfo = objects.get(0);
                            vertualinfo.put("comments_count",MainActivity.comments_count);
                            ParseACL acl = new ParseACL();
                            acl.setPublicReadAccess(true);
                            acl.setPublicWriteAccess(true);
                            vertualinfo.setACL(acl);
                            vertualinfo.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                }
                            });


                    } catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                }

            }
        });
    }


*/

    public static Date StringtoDate(String stringdate) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            date = format.parse(stringdate);
            System.out.println(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }


    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output;

        /*if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }*/
        output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;


        r = bitmap.getWidth() / 2;


        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap get_bitmap_image(String path) {
        Bitmap bmp = null;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            bmp = BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return bmp;
    }

    public static String getIntenalSDCardPath() {
        String Path = "";
        Path = "/storage/sdcard1/Android/data/com.helio.silentsecret/Cypher";
        File fPath = new File(Path);
        if (null != fPath) {
            if (!fPath.exists()) {
                Path = "";
                //Path = "/storage/sdcard0/Android/data/com.connectmore.ActivityClasses/m-AdCall";
                Path = "/storage/sdcard0/Android/data/com.helio.silentsecret/Cypher";

                fPath = null;
                fPath = new File(Path);
                if (!fPath.exists()) {
                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    String TARGET_BASE_PATH = extStorageDirectory + File.separator +
                            "Android" + File.separator + "data" + File.separator + "com.helio.silentsecret" + File.separator +
                            "Cypher";

                    fPath = null;
                    fPath = new File(TARGET_BASE_PATH);
                    fPath.mkdirs();
                    Path = TARGET_BASE_PATH;
                }
            }
        }
        fPath = null;
        //ShowLog("CollectionFunctions", "path :: "+Path);
        return Path;
    }


    List<PhotoModel> PhotoModels = new ArrayList<>();
    int selected_image = 0;
    public static final int PAGE_SIZE = 15;
    private static final long CACHE_SIZE_IN_MB = 10 * 1024 * 1024;
    // private static final String API_KEY = "df55fff2316b6d7f87c44ca2ad277d7a";
    // private static final String API_KEY = "0f9ea75f0657368612c304e5920f2e41"; // ios developer
    //private static final String API_KEY = "915a8359d070fdea5fb41cae8edee71e"; // my key
  //  private static final String API_KEY = "n88pxentayj653upyb8476z8"; // my key
    private static final String API_KEY = "js54xr27j2aa57r8snrwv7vs"; // my key

    private static final String URL_BASE = "https://api.flickr.com/services/rest/"
            + "?format=json&nojsoncallback=1&api_key=" + API_KEY + "&safe_search=1";

    private static final String URL_SEARCH = "&method=flickr.photos.search&tags=";
    private static final String PAGE_INFO = "&per_page=" + PAGE_SIZE + "&page=";


    private static final String URL_DETAIL = "&method=flickr.photos.getInfo&photo_id=";
    private static String CACHE_PATH = "";
    private static final String COLUMN_PHOTO = "images";
    private static final String COLUMN_PHOTOS = "photos";

    public static List<DisplaySizes> search(String room_title, int page, Context ct) throws IOException, JSONException {


        //   String API_KEY = "n88pxentayj653upyb8476z8";
        String url = "https://api.gettyimages.com/v3/search/images?fields=comp&phrase=" + room_title + "&page_size=30&page="+page;;
      //  String url = "https://api.gettyimages.com/v3/search/images?fields=thumb&phrase=" + room_title;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("api-key", API_KEY)
                .build();


        Response response = getClient().newCall(request).execute();
        String json = response.body().string();

        JSONArray jsonArray = new JSONObject(json).getJSONArray(COLUMN_PHOTO);
        List<DisplaySizes> displaySizes = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++)
            {
                try
                {
                    JSONObject UserInfoobj = new JSONObject(jsonArray.getString(i));
                    DisplaySizes displaySizes1 = new DisplaySizes();

                    JSONArray jsonArray1 = UserInfoobj.getJSONArray("display_sizes");


                    JSONObject UserInfoobj1 = new JSONObject(jsonArray1.getString(0));
                    if (UserInfoobj1.has("uri"))
                        displaySizes1.setUri(UserInfoobj1.getString("uri"));

                    if(displaySizes1!= null && !displaySizes1.equals(""))
                    displaySizes.add(displaySizes1);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

        }

        return displaySizes;

       /* url = URL_BASE + URL_SEARCH + room_title + PAGE_INFO + page;


        HttpRequest httpRequest = new HttpRequest(ct);
        String json = httpRequest.makeconnection(url);
        ;
        JSONArray jsonArray = new JSONObject(json).getJSONObject(COLUMN_PHOTOS).getJSONArray(COLUMN_PHOTO);
        Type listType = new TypeToken<List<PhotoModel>>() {
        }.getType();
        return getGson().fromJson(jsonArray.toString(), listType);*/
    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

                .build();
    }

    public static Gson getGson() {
        return new GsonBuilder().create();
    }

    public static String checkBannedword(String room_title) {
        String[] banned_word = null, post_words = null;

        String risk_word = "";

        try {
            room_title = room_title.toLowerCase().trim();
            if (MainActivity.stataicObjectDTO != null && MainActivity.stataicObjectDTO.getiFriendSettingDTO() != null) {
                if (MainActivity.stataicObjectDTO.getiFriendSettingDTO().getBanned_word() != null) {
                    banned_word = MainActivity.stataicObjectDTO.getiFriendSettingDTO().getBanned_word().split(",");
                }
            }

            if (banned_word != null && banned_word.length > 0) {
                post_words = room_title.split(" ");
                for (int j = 0; j < post_words.length; j++) {
                    for (int i = 0; i < banned_word.length; i++) {
                        if (post_words[j].equalsIgnoreCase(banned_word[i].toLowerCase().trim())) {
                            risk_word = banned_word[i];
                            break;
                        }
                    }
                    if (risk_word != null && !risk_word.equalsIgnoreCase(""))
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return risk_word;
    }


    public static String GetdefualtWord() {
        String[] default_word = null;

        String risk_word = "Happy";


        try {
            if (MainActivity.stataicObjectDTO != null && MainActivity.stataicObjectDTO.getiFriendSettingDTO() != null) {
                if (MainActivity.stataicObjectDTO.getiFriendSettingDTO().getDefault_word() != null) {

                    default_word = MainActivity.stataicObjectDTO.getiFriendSettingDTO().getDefault_word().split(",");
                }
            }


            if (default_word != null && default_word.length > 0) {
                final int min = 0;
                final int max = default_word.length;

                Random r = new Random();
                int i1 = r.nextInt(max - min) + min;

                risk_word = default_word[i1].trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return risk_word;
    }

}
