package com.helio.cypher.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;

import com.helio.cypher.activities.ChatDetailsScreen;
import com.helio.cypher.activities.MainActivity;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Maroof Ahmed Siddique on 9/26/2016.
 */
public class CommonFunction
{
    private static String keys = "";
    public CommonFunction()
    {

    }

    public static  String gettime()
    {
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

    public static String getdate()
    {
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


    public static boolean isTriggered(String msg)
    {

        boolean istrigger = false;

        try
        {


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
        }
        catch (Exception e)
        {
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

    public static boolean isServiceRunning(Context mContext, Class<?> serviceClass)
    {

        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName())
                    && service.pid != 0)
            {
                //ShowLog("NotifyService", "ser name "+serviceClass.getName());
                return true;
            }
        }
        return false;
    }

   final static String TODAYS_STEPS = "todays_steps";
    public static int gettodaysstep(Context context)
    {
        int restoredText = 0;
        try
        {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getInt(TODAYS_STEPS, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return restoredText;
    }

    public static void storertodaystep(Context context,int steps)
    {
        try
        {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putInt(TODAYS_STEPS,steps);
            editor.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }






    final static String TODAYS_RECOMONDED_STEP = "todays_recomonded_steps";
    public static int gettodaysrecomndedstep(Context context)
    {
        int restoredText = 0;
        try
        {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getInt(TODAYS_RECOMONDED_STEP, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return restoredText;
    }

    public static void settodaysrecomndedstep(Context context,int steps)
    {
        try
        {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putInt(TODAYS_RECOMONDED_STEP,steps);
            editor.commit();
        }
        catch (Exception e)
        {
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
    public static int GetTargetPushDate(Context context)
    {
        int restoredText = 0;
        try
        {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getInt(TARGET_PUSH_DATE, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return restoredText;
    }

    public static void SetTargetPushDate(Context context,int steps)
    {
        try
        {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putInt(TARGET_PUSH_DATE,steps);
            editor.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }





    final static String FITNESS_OFF_ON = "fitnesss_off_on";
    public static String getfitnessonoff(Context context)
    {
        String restoredText = "";
        try
        {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getString(FITNESS_OFF_ON, "");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return restoredText;
    }

    public static void setfitnessonoff(Context context,String onoff)
    {
        try
        {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putString(FITNESS_OFF_ON,onoff);
            editor.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }




    final static String TODAYS_DATE = "todays_date";
    public static String gettodaysdate(Context context)
    {
        String restoredText = "";
        try
        {
            SharedPreferences prefs = context.getSharedPreferences("Silent", context.MODE_PRIVATE);
            restoredText = prefs.getString(TODAYS_DATE, "");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return restoredText;
    }

    public static void storertodaydate(Context context,String date)
    {
        try
        {
            SharedPreferences.Editor editor = context.getSharedPreferences("Silent", context.MODE_PRIVATE).edit();
            editor.putString(TODAYS_DATE,date);
            editor.commit();
        }
        catch (Exception e)
        {
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


    public static void water_maintain()
    {

                    try
                    {

                        String hme_water =   MainActivity.petAvtarInfoDTO.getM2_water();
                        if(hme_water!= null && !hme_water.equalsIgnoreCase("") && !hme_water.equalsIgnoreCase("null"))
                        {
                            MainActivity.water = Integer.parseInt(hme_water);
                        }
                        MainActivity.water_progress.setProgress(MainActivity.water);

                    } catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }

    }




    public static void food_maintain()
    {

                        try
                        {

                            String hme_food =   MainActivity.petAvtarInfoDTO.getHeart_food();
                            if(hme_food!= null && !hme_food.equalsIgnoreCase("") && !hme_food.equalsIgnoreCase("null"))
                            {
                                MainActivity.food = Integer.parseInt(hme_food);
                            }
                            MainActivity.food_progress.setProgress(MainActivity.food);

                        } catch (Exception e1)
                        {
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



    public static void oxygen_maintain()
    {



                        try {

                            String hhug_food =   MainActivity.petAvtarInfoDTO.getHug_oxygen();
                            if(hhug_food!= null && !hhug_food.equalsIgnoreCase("") && !hhug_food.equalsIgnoreCase("null")) {
                                MainActivity.oxygen = Integer.parseInt(hhug_food);
                            }

                            int oxyzen = (int) MainActivity.oxygen / 10;
                            MainActivity.oxygenlevel.setBackgroundColor(Color.parseColor(MainActivity.colorsking[oxyzen - 1]));

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

    public static Date StringtoDate(String stringdate)
    {
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
}
