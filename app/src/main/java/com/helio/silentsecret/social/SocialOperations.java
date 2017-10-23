package com.helio.silentsecret.social;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.AddShortSentense;
import com.helio.silentsecret.WebserviceDTO.Me2HufLikeActionDTO;
import com.helio.silentsecret.WebserviceDTO.Me2HugLikeConditionalDTO;
import com.helio.silentsecret.WebserviceDTO.Me2HugLikeObjectDTO;
import com.helio.silentsecret.WebserviceDTO.Me2HugLikeRoomDTO;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.adapters.FeedAdapter;
import com.helio.silentsecret.adapters.RoomListAdapter;
import com.helio.silentsecret.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.silentsecret.appCounsellingDTO.FinalObjectDTO;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.RoomsInfoDTO;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SocialOperations {

    private Context mContext;

    Calendar c = Calendar.getInstance();

    Me2HugLikeConditionalDTO me2HugLikeConditionalDTO = null;
    Me2HugLikeRoomDTO me2HugLikeRoomDTO = null;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
    String formattedDate = df.format(c.getTime());

    public SocialOperations(Context context) {
        mContext = context;
    }



    /*public ArrayList<String> flag(final Secret item, final FeedAdapter.ViewHolder holder) {
        ParseObject biteObject = new ParseObject(Constants.SECRET_CLASS);
        biteObject.setObjectId(item.getObjectId());
        biteObject.put(Constants.SECRET_FLAGS, item.getFlags() + 1);
        ArrayList<String> flagUsers = item.getFlagUsers();
        flagUsers.add(Preference.getUserName());
        biteObject.put(Constants.SECRET_FLAG_USERS, flagUsers);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        biteObject.setACL(acl);

        ((MainActivity) mContext).showProgress();
        biteObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                ((MainActivity) mContext).stopProgress();
                if (e == null) {
                    holder.flag.setImageResource(R.drawable.ic_flagged);
                    item.setFlags(item.getFlags() + 1);
                }
            }
        });

        return flagUsers;
    }*/

    /*    public ArrayList<String> unFlag(final Secret item, final FeedAdapter.ViewHolder holder)
        {
            final ParseObject biteObject = new ParseObject(Constants.SECRET_CLASS);
            biteObject.setObjectId(item.getObjectId());
            biteObject.put(Constants.SECRET_FLAGS, item.getFlags() - 1);
            final ArrayList<String> flagUsers = item.getFlagUsers();
            flagUsers.remove(Preference.getUserName());
            biteObject.put(Constants.SECRET_FLAG_USERS, flagUsers);
            ParseACL acl = new ParseACL();
            acl.setPublicReadAccess(true);
            acl.setPublicWriteAccess(true);
            biteObject.setACL(acl);

            ((MainActivity) mContext).showProgress();
            biteObject.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {
                    ((MainActivity) mContext).stopProgress();
                    if (e == null) {
                        holder.flag.setImageResource(R.drawable.ic_flag);
                    }
                }
            });

            return flagUsers;
        }*/
    AddShortSentense addShortSentense = null;

    public void addShortSenetense(final Secret item, String short_sentense, String type) {
        if (addShortSentense != null) {
            addShortSentense = null;
        }

        addShortSentense = new AddShortSentense(MainActivity.enc_username, item.getObjectId(), short_sentense, type);
        new AddShortsent().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public ArrayList<String> hug(final Secret item, final FeedAdapter.ViewHolder holder, String short_senetece) {


        final ArrayList<String> hugUsers = item.getHugUsers();


        try {
            hugUsers.add(MainActivity.enc_username);

            int hougcount = 0;
            String hug = AppSession.getValue(mContext, Constants.USER_HUGS);
            if (hug != null && !hug.equalsIgnoreCase("")) {
                try {
                    hougcount = Integer.parseInt(hug);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            hougcount = hougcount + 1;

            AppSession.save(mContext, Constants.USER_HUGS, "" + hougcount);


            if (me2HugLikeConditionalDTO != null)
                me2HugLikeConditionalDTO = null;


            int hug_oxygen = 10;
            /*if (MainActivity.petAvtarInfoDTO != null) {
                try {
                    String hhug_food = MainActivity.petAvtarInfoDTO.getHug_oxygen();
                    if (hhug_food != null && !hhug_food.equalsIgnoreCase("") && !hhug_food.equalsIgnoreCase("null")) {
                        hug_oxygen = Integer.parseInt(hhug_food);
                        if (hug_oxygen <= 90)
                            hug_oxygen = hug_oxygen + 10;


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainActivity.petAvtarInfoDTO.setHug_oxygen("" + hug_oxygen);
                CommonFunction.oxygen_maintain();
            }*/

            String sendpush = "true";

            //  String total_count = "";
            int total_scrcount = 0;
//            if (MainActivity.petAvtarInfoDTO != null) {
//                try {
//                    total_count = MainActivity.petAvtarInfoDTO.getTotal_scratch_count();
//                    if (total_count != null && !total_count.equalsIgnoreCase("")) {
//                        total_scrcount = Integer.parseInt(total_count);
//
//                    }
//
//                    MainActivity.petAvtarInfoDTO.setTotal_scratch_count("" + total_scrcount);
//                } catch (Exception e) {
//                    MainActivity.petAvtarInfoDTO.setTotal_scratch_count("1");
//                    total_scrcount = 1;
//                    e.printStackTrace();
//                }
//            }

          /*  if(item.getCreatedByUser()!= null && !item.getCreatedByUser().equalsIgnoreCase(MainActivity.enc_username))
            {
                sendpush = "true";
            }*/

            me2HugLikeConditionalDTO = new Me2HugLikeConditionalDTO(MainActivity.enc_username, item.getObjectId(), item.getCreatedByUser(), hugUsers,
                    hugUsers, hougcount, item.getHugs() + 1, sendpush, 0, "" + hug_oxygen, "", "", "" + total_scrcount, short_senetece);
            holder.hug.setImageResource(R.drawable.ic_hug);
            item.setHugs(item.getHugs() + 1);


            if (item.getHugs() > 0)
                holder.hugs.setText(String.valueOf(item.getHugs()));
            else
                holder.hugs.setText(String.valueOf("1"));


            new HugAction().execute();

            String userna = AppSession.getValue(mContext, Constants.USER_NAME);
            if (userna != null && !userna.equalsIgnoreCase("")) {
                ((MainActivity) mContext).updateActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


       /* ParseObject biteObject = new ParseObject(Constants.SECRET_CLASS);
        biteObject.setObjectId(item.getObjectId());
        biteObject.put(Constants.SECRET_HUGS, item.getHugs() + 1);
       final ArrayList<String> hugUsers = item.getHugUsers();
        hugUsers.add(Preference.getUserName());
        biteObject.put(Constants.SECRET_HUG_USERS, hugUsers);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        biteObject.setACL(acl);

        biteObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e)
            {
                if (e == null)
                {
                    try
                    {
                        CommonFunction.increaehug_oxygen();
                        holder.hug.setImageResource(R.drawable.ic_hug);
                        item.setHugs(item.getHugs() + 1);
                        holder.hugs.setText(String.valueOf(item.getHugs()));

                        //if (ActionChecker.checker(item.getHugs()))


                        try
                        {
                            if(!item.getUser().getUsername().equalsIgnoreCase(ParseUser.getCurrentUser().getUsername()))
                                sendPushData(item.getObjectId(), item.getHugs(), item.getCreatedByUser(), Constants.PUSH_TEXT_HUGGED, item, Constants.NOTIFICATION_TYPE_HUG);

                        }
                        catch (Exception e1)
                        {
                            e1.printStackTrace();
                        }

                        createSocialItem(item.getUser(), Constants.HUG_CLASS, item.getObject());

                        ParseUser.getCurrentUser().put(Constants.USER_HUGS, ParseUser.getCurrentUser().getInt(Constants.USER_HUGS) + 1);
                        ParseUser.getCurrentUser().put("lastactivitydate", formattedDate);
                        ParseUser.getCurrentUser().put(Constants.SECRET_HUG_USERS, hugUsers);
                        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                try
                                {
                                    ((MainActivity) mContext).updateActivity();
                                }
                                catch (Exception e2)
                                {
                                    e2.printStackTrace();
                                }

                            }
                        });
                    }
                    catch (Exception e2)
                    {
                     e2.printStackTrace();
                    }

                }
            }
        });
*/
        return hugUsers;
    }

    public ArrayList<String> unHug(final Secret item, final FeedAdapter.ViewHolder holder) {


        final ArrayList<String> hugUsers = item.getHugUsers();


        try {


            hugUsers.remove(MainActivity.enc_username);
            int hougcount = 0;
            String hug = AppSession.getValue(mContext, Constants.USER_HUGS);
            if (hug != null && !hug.equalsIgnoreCase("")) {
                try {
                    hougcount = Integer.parseInt(hug);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (hougcount > 0)
                hougcount = hougcount - 1;

            AppSession.save(mContext, Constants.USER_HUGS, "" + hougcount);

            if (me2HugLikeConditionalDTO != null)
                me2HugLikeConditionalDTO = null;

            int hug_oxygen = 10;

            /*if (MainActivity.petAvtarInfoDTO != null) {

                try {
                    String hhug_food = MainActivity.petAvtarInfoDTO.getHug_oxygen();
                    if (hhug_food != null && !hhug_food.equalsIgnoreCase("") && !hhug_food.equalsIgnoreCase("null")) {
                        hug_oxygen = Integer.parseInt(hhug_food);
                        if (hug_oxygen >= 20)
                            hug_oxygen = hug_oxygen - 10;


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                MainActivity.petAvtarInfoDTO.setHug_oxygen("" + hug_oxygen);

                CommonFunction.oxygen_maintain();
            }*/

            //String total_count = "";
            int total_scrcount = 0;
            /*if (MainActivity.petAvtarInfoDTO != null) {


                try {
                    total_count = MainActivity.petAvtarInfoDTO.getTotal_scratch_count();
                    if (total_count != null && !total_count.equalsIgnoreCase("")) {
                        total_scrcount = Integer.parseInt(total_count);

                    }

                    MainActivity.petAvtarInfoDTO.setTotal_scratch_count("" + total_scrcount);
                } catch (Exception e) {
                    MainActivity.petAvtarInfoDTO.setTotal_scratch_count("1");
                    total_scrcount = 1;
                    e.printStackTrace();
                }
            }*/
            me2HugLikeConditionalDTO = new Me2HugLikeConditionalDTO(MainActivity.enc_username, item.getObjectId(), item.getCreatedByUser(), hugUsers,
                    hugUsers, hougcount, item.getHugs() - 1, "false", 0, "" + hug_oxygen, "", "", "" + total_scrcount, "");

            try {
                int kk = item.getHugs();

                if (kk > 0) {
                    item.setHugs(kk - 1);
                } else {
                    item.setHugs(0);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            holder.hug.setImageResource(R.drawable.ic_not_hug);

            holder.hugs.setText(String.valueOf(item.getHugs()));
            new HugAction().execute();

            String userna = AppSession.getValue(mContext, Constants.USER_NAME);
            if (userna != null && !userna.equalsIgnoreCase("")) {
                ((MainActivity) mContext).updateActivity();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        /*ParseObject biteObject = new ParseObject(Constants.SECRET_CLASS);
        biteObject.setObjectId(item.getObjectId());
        biteObject.put(Constants.SECRET_HUGS, item.getHugs() - 1);
       final ArrayList<String> hugUsers = item.getHugUsers();
        hugUsers.remove(Preference.getUserName());
        biteObject.put(Constants.SECRET_HUG_USERS, hugUsers);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        biteObject.setACL(acl);

        biteObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {

                    try
                    {
                        holder.hug.setImageResource(R.drawable.ic_not_hug);



                        try {

                            CommonFunction.decreasehug_oxygen();

                            int kk = item.getHugs();

                            if (kk > 0) {
                                item.setHugs(item.getHugs() - 1);
                            } else {
                                item.setHearts(0);
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }


                        holder.hugs.setText(String.valueOf(item.getHugs()));
                        ParseUser.getCurrentUser().put(Constants.USER_HUGS, ParseUser.getCurrentUser().getInt(Constants.USER_HUGS) - 1);
                        ParseUser.getCurrentUser().put("lastactivitydate", formattedDate);

                        ParseUser.getCurrentUser().put(Constants.SECRET_HUG_USERS, hugUsers);

                        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                try
                                {
                                    ((MainActivity) mContext).updateActivity();
                                }
                                catch (Exception e2)
                                {
                                    e2.printStackTrace();
                                }

                            }
                        });
                    }
                     catch (Exception e2)
                     {
                         e2.printStackTrace();
                     }

                }
            }
        });*/

        return hugUsers;
    }

    public ArrayList<String> heart(final Secret item, final FeedAdapter.ViewHolder holder, String short_sentence) {

        //username/userid/notification_status


        final ArrayList<String> heartsUsers = item.getHeartUsers();


        try {

            heartsUsers.add(MainActivity.enc_username);

            int heartgcount = 0;
            String hug = AppSession.getValue(mContext, Constants.USER_HEARTS);
            if (hug != null && !hug.equalsIgnoreCase("")) {
                try {
                    heartgcount = Integer.parseInt(hug);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            heartgcount = heartgcount + 1;

            AppSession.save(mContext, Constants.USER_HEARTS, "" + heartgcount);

            if (me2HugLikeConditionalDTO != null)
                me2HugLikeConditionalDTO = null;

            int heart_food = 10;
            /*if (MainActivity.petAvtarInfoDTO != null) {
                try {
                    String hheart_food = MainActivity.petAvtarInfoDTO.getHeart_food();
                    if (hheart_food != null && !hheart_food.equalsIgnoreCase("") && !hheart_food.equalsIgnoreCase("null")) {
                        heart_food = Integer.parseInt(hheart_food);
                        if (heart_food <= 90)
                            heart_food = heart_food + 10;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainActivity.petAvtarInfoDTO.setHeart_food("" + heart_food);
                CommonFunction.food_maintain();
            }*/

            String sendpush = "true";
            //String total_count = "";

            int total_scrcount = 0;
           /* if (MainActivity.petAvtarInfoDTO != null) {
                try {
                    total_count = MainActivity.petAvtarInfoDTO.getTotal_scratch_count();
                    if (total_count != null && !total_count.equalsIgnoreCase(""))
                    {
                        total_scrcount = Integer.parseInt(total_count);
                    }
                    MainActivity.petAvtarInfoDTO.setTotal_scratch_count("" + total_scrcount);
                } catch (Exception e) {
                      MainActivity.petAvtarInfoDTO.setTotal_scratch_count("1");
                    total_scrcount = 1;
                    e.printStackTrace();
                }
            }*/


           /* if(item.getCreatedByUser()!= null && !item.getCreatedByUser().equalsIgnoreCase(MainActivity.enc_username))
            {
                sendpush = "true";
            }*/

            me2HugLikeConditionalDTO = new Me2HugLikeConditionalDTO(MainActivity.enc_username, item.getObjectId(), item.getCreatedByUser(), heartsUsers,
                    heartsUsers, heartgcount, item.getHearts() + 1, sendpush, 0, "", "" + heart_food, "", "" + total_scrcount, short_sentence);
            holder.heart.setImageResource(R.drawable.ic_hearted);
            item.setHearts(item.getHearts() + 1);


            if (item.getHearts() > 0)
                holder.hearts.setText(String.valueOf(item.getHearts()));
            else
                holder.hearts.setText(String.valueOf("1"));


            new HeartsAction().execute();

            String userna = AppSession.getValue(mContext, Constants.USER_NAME);
            if (userna != null && !userna.equalsIgnoreCase("")) {
                ((MainActivity) mContext).updateActivity();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

     /*   ParseObject biteObject = new ParseObject(Constants.SECRET_CLASS);
        biteObject.setObjectId(item.getObjectId());
        biteObject.put(Constants.SECRET_HEARTS, item.getHearts() + 1);
       final ArrayList<String> heartsUsers = item.getHeartUsers();
        heartsUsers.add(Preference.getUserName());
        biteObject.put(Constants.SECRET_HEART_USERS, heartsUsers);

        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        biteObject.setACL(acl);

        biteObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null)
                {
                    try
                    {
                        CommonFunction.increaehearfood();
                        holder.heart.setImageResource(R.drawable.ic_hearted);
                        item.setHearts(item.getHearts() + 1);
                        holder.hearts.setText(String.valueOf(item.getHearts()));

                        //  if (ActionChecker.checker(item.getHearts()))


                        try
                        {
                            if(!item.getUser().getUsername().equalsIgnoreCase(ParseUser.getCurrentUser().getUsername()))
                                sendPushData(item.getObjectId(), item.getHearts(), item.getCreatedByUser(), Constants.PUSH_TEXT_HEARTED, item, Constants.NOTIFICATION_TYPE_HEART);

                        }
                        catch (Exception e1)
                        {
                            e1.printStackTrace();
                        }

                        createSocialItem(item.getUser(), Constants.HEART_CLASS, item.getObject());

                        ParseUser.getCurrentUser().put(Constants.USER_HEARTS, ParseUser.getCurrentUser().getInt(Constants.USER_HEARTS) + 1);
                        ParseUser.getCurrentUser().put("lastactivitydate", formattedDate);
                        ParseUser.getCurrentUser().put(Constants.SECRET_HEART_USERS, heartsUsers);
                        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                try
                                {
                                    ((MainActivity) mContext).updateActivity();
                                }
                                catch (Exception e2)
                                {
                                    e2.printStackTrace();
                                }

                            }
                        });
                    }
                    catch (Exception e2)
                    {
                        e2.printStackTrace();
                    }

                }
            }
        });*/

        return heartsUsers;
    }

    public ArrayList<String> unHeart(final Secret item, final FeedAdapter.ViewHolder holder) {

        final ArrayList<String> heartsUsers = item.getHeartUsers();


        try {

            heartsUsers.remove(MainActivity.enc_username);

            int heartgcount = 0;
            String hug = AppSession.getValue(mContext, Constants.USER_HEARTS);
            if (hug != null && !hug.equalsIgnoreCase("")) {
                try {
                    heartgcount = Integer.parseInt(hug);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (heartgcount > 0)
                heartgcount = heartgcount - 1;

            AppSession.save(mContext, Constants.USER_HEARTS, "" + heartgcount);


            if (me2HugLikeConditionalDTO != null)
                me2HugLikeConditionalDTO = null;

            int heart_food = 10;
            /*if (MainActivity.petAvtarInfoDTO != null) {
                try {
                    String hheart_food = MainActivity.petAvtarInfoDTO.getHeart_food();
                    if (hheart_food != null && !hheart_food.equalsIgnoreCase("") && !hheart_food.equalsIgnoreCase("null")) {
                        heart_food = Integer.parseInt(hheart_food);
                        if (heart_food >= 20)
                            heart_food = heart_food - 10;


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                MainActivity.petAvtarInfoDTO.setHeart_food("" + heart_food);
                CommonFunction.food_maintain();
            }*/


            int total_scrcount = 0;
            //  String total_count = "";
//            if (MainActivity.petAvtarInfoDTO != null) {
//                try {
//                    total_count = MainActivity.petAvtarInfoDTO.getTotal_scratch_count();
//                    if (total_count != null && !total_count.equalsIgnoreCase("")) {
//                        total_scrcount = Integer.parseInt(total_count);
//
//                    }
//
//                    MainActivity.petAvtarInfoDTO.setTotal_scratch_count("" + total_scrcount);
//                } catch (Exception e) {
//                     MainActivity.petAvtarInfoDTO.setTotal_scratch_count("1");
//                    total_scrcount = 1;
//                    e.printStackTrace();
//                }
//            }

            me2HugLikeConditionalDTO = new Me2HugLikeConditionalDTO(MainActivity.enc_username, item.getObjectId(), item.getCreatedByUser(), heartsUsers,
                    heartsUsers, heartgcount, item.getHearts() - 1, "false", 0, "", "" + heart_food, "", total_scrcount + "", "");
            holder.heart.setImageResource(R.drawable.ic_heart);

            try {
                int kk = item.getHearts();

                if (kk > 0) {
                    item.setHearts(kk - 1);
                } else {
                    item.setHearts(0);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            holder.hearts.setText(String.valueOf(item.getHearts()));

            new HeartsAction().execute();

            String userna = AppSession.getValue(mContext, Constants.USER_NAME);
            if (userna != null && !userna.equalsIgnoreCase("")) {
                ((MainActivity) mContext).updateActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
/*

        ParseObject biteObject = new ParseObject(Constants.SECRET_CLASS);
        biteObject.setObjectId(item.getObjectId());
        biteObject.put(Constants.SECRET_HEARTS, item.getHearts() - 1);
      final  ArrayList<String> heartsUsers = item.getHeartUsers();
        heartsUsers.remove(Preference.getUserName());
        biteObject.put(Constants.SECRET_HEART_USERS, heartsUsers);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        biteObject.setACL(acl);

        biteObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {

                    try{
                        holder.heart.setImageResource(R.drawable.ic_heart);


                        try {
                            CommonFunction.decreasehearfood();
                            int kk = item.getHearts();

                            if (kk > 0) {
                                item.setHearts(item.getHearts() - 1);
                            } else {
                                item.setHearts(0);
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }


                        holder.hearts.setText(String.valueOf(item.getHearts()));
                        ParseUser.getCurrentUser().put(Constants.USER_HEARTS, ParseUser.getCurrentUser().getInt(Constants.USER_HEARTS) - 1);
                        ParseUser.getCurrentUser().put("lastactivitydate", formattedDate);
                        ParseUser.getCurrentUser().put(Constants.SECRET_HEART_USERS, heartsUsers);
                        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                try
                                {
                                    ((MainActivity) mContext).updateActivity();
                                }
                                catch (Exception e2)
                                {
                                    e2.printStackTrace();
                                }

                            }
                        });
                    }
                    catch (Exception e2)
                    {
                        e2.printStackTrace();
                    }

                }
            }
        });*/

        return heartsUsers;
    }

    public ArrayList<String> me2(final Secret item, final FeedAdapter.ViewHolder holder, String short_sentence) {
        final ArrayList<String> me2Users = item.getMe2Users();

        try {

            me2Users.add(MainActivity.enc_username);

            int me2gcount = 0;
            String hug = AppSession.getValue(mContext, Constants.USER_ME2S);
            if (hug != null && !hug.equalsIgnoreCase("")) {
                try {
                    me2gcount = Integer.parseInt(hug);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            me2gcount = me2gcount + 1;

            AppSession.save(mContext, Constants.USER_ME2S, "" + me2gcount);


            if (me2HugLikeConditionalDTO != null)
                me2HugLikeConditionalDTO = null;


            int vertual_commnet_cont = 1;
            /*if (MainActivity.petAvtarInfoDTO != null) {
                try {
                    String ver_com_count = MainActivity.petAvtarInfoDTO.getComments_count();
                    if (ver_com_count != null && !ver_com_count.equalsIgnoreCase("")) {
                        vertual_commnet_cont = Integer.parseInt(ver_com_count);
                        vertual_commnet_cont = vertual_commnet_cont + 1;
                        MainActivity.petAvtarInfoDTO.setComments_count("" + vertual_commnet_cont);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/

            String sendpush = "true";


            int total_scrcount = 0;
            /*if (MainActivity.petAvtarInfoDTO != null) {
                try {
                    String total_count = MainActivity.petAvtarInfoDTO.getTotal_scratch_count();
                    if (total_count != null && !total_count.equalsIgnoreCase("")) {
                        total_scrcount = Integer.parseInt(total_count);
                    }

                    MainActivity.petAvtarInfoDTO.setTotal_scratch_count("" + total_scrcount);
                } catch (Exception e) {
                      MainActivity.petAvtarInfoDTO.setTotal_scratch_count("1");
                    total_scrcount = 1;
                    e.printStackTrace();
                }
            }*/
           /* if(item.getCreatedByUser()!= null && !item.getCreatedByUser().equalsIgnoreCase(MainActivity.enc_username))
            {
                sendpush = "true";
            }*/
            me2HugLikeConditionalDTO = new Me2HugLikeConditionalDTO(MainActivity.enc_username, item.getObjectId(), item.getCreatedByUser(), me2Users,
                    me2Users, me2gcount, item.getMe2s() + 1, sendpush, vertual_commnet_cont, "", "", "", "" + total_scrcount, short_sentence);
            holder.me2.setImageResource(R.drawable.ic_me);
            item.setMe2s(item.getMe2s() + 1);


            if (item.getMe2s() > 0)
                holder.me2s.setText(String.valueOf(item.getMe2s()));
            else
                holder.me2s.setText(String.valueOf("1"));

            new Me2Action().execute();

            String userna = AppSession.getValue(mContext, Constants.USER_NAME);
            if (userna != null && !userna.equalsIgnoreCase("")) {
                ((MainActivity) mContext).updateActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




       /* ParseObject biteObject = new ParseObject(Constants.SECRET_CLASS);
        biteObject.setObjectId(item.getObjectId());
        biteObject.put(Constants.SECRET_ME2S, item.getMe2s() + 1);
       final ArrayList<String> me2Users = item.getMe2Users();
        me2Users.add(Preference.getUserName());
        biteObject.put(Constants.SECRET_ME2USERS, me2Users);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        biteObject.setACL(acl);




        biteObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {

                    try
                    {


                        CommonFunction.increaem2water();

                        holder.me2.setImageResource(R.drawable.ic_me);
                        item.setMe2s(item.getMe2s() + 1);
                        holder.me2s.setText(String.valueOf(item.getMe2s()));

                        //if (ActionChecker.checker(item.getMe2s()))
                        try
                        {
                            if(!item.getUser().getUsername().equalsIgnoreCase(ParseUser.getCurrentUser().getUsername()))
                                sendPushData(item.getObjectId(), item.getMe2s(), item.getCreatedByUser(), Constants.PUSH_TEXT_ME2S, item, Constants.NOTIFICATION_TYPE_ME2);

                        }
                        catch (Exception e1)
                        {
                            e1.printStackTrace();
                        }

                        ParseUser.getCurrentUser().put(Constants.USER_ME2S, ParseUser.getCurrentUser().getInt(Constants.USER_ME2S) + 1);
                        ParseUser.getCurrentUser().put("lastactivitydate", formattedDate);
                        ParseUser.getCurrentUser().put(Constants.SECRET_ME2USERS, me2Users);
                        createSocialItem(item.getUser(), Constants.ME2_CLASS, item.getObject());

                        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                try
                                {
                                    ((MainActivity) mContext).updateActivity();
                                }
                                catch (Exception e2)
                                {
                                    e2.printStackTrace();
                                }

                            }
                        });
                    }
                    catch (Exception e2)
                    {
                        e2.printStackTrace();
                    }

                }
            }
        });
*/
        return me2Users;
    }

    public ArrayList<String> unMe2(final Secret item, final FeedAdapter.ViewHolder holder) {

        final ArrayList<String> me2Users = item.getMe2Users();


        try {

            me2Users.remove(MainActivity.enc_username);

            int me2gcount = 0;
            String hug = AppSession.getValue(mContext, Constants.USER_ME2S);
            if (hug != null && !hug.equalsIgnoreCase("")) {
                try {
                    me2gcount = Integer.parseInt(hug);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (me2gcount > 0)
                me2gcount = me2gcount - 1;

            AppSession.save(mContext, Constants.USER_ME2S, "" + me2gcount);

            int vertual_commnet_cont = 1;
            /*if (MainActivity.petAvtarInfoDTO != null) {
                try {
                    String ver_com_count = MainActivity.petAvtarInfoDTO.getComments_count();
                    if (ver_com_count != null && !ver_com_count.equalsIgnoreCase("")) {
                        vertual_commnet_cont = Integer.parseInt(ver_com_count);
                        vertual_commnet_cont = vertual_commnet_cont - 1;
                        MainActivity.petAvtarInfoDTO.setComments_count("" + vertual_commnet_cont);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/


            //    int total_scrcount = 0;
            String total_count = "";
            /*String total_count = "";
            if (MainActivity.petAvtarInfoDTO != null) {
                try {
                    total_count = MainActivity.petAvtarInfoDTO.getTotal_scratch_count();
                    if (total_count != null && !total_count.equalsIgnoreCase("")) {
                        total_scrcount = Integer.parseInt(total_count);

                    }

                    MainActivity.petAvtarInfoDTO.setTotal_scratch_count("" + total_scrcount);
                } catch (Exception e) {
                    MainActivity.petAvtarInfoDTO.setTotal_scratch_count("1");
                    total_scrcount = 1;
                    e.printStackTrace();
                }
            }*/
           /* int water = 10;
            if(MainActivity.petAvtarInfoDTO!= null) {
                try {
                    String me2_water = MainActivity.petAvtarInfoDTO.getM2_water();
                    if (me2_water != null && !me2_water.equalsIgnoreCase("") && !me2_water.equalsIgnoreCase("null")) {
                        water = Integer.parseInt(me2_water);
                        if (water >= 20)
                            water = water - 10;


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                MainActivity.petAvtarInfoDTO.setM2_water("" + water);
                CommonFunction.water_maintain();
            }*/

            if (me2HugLikeConditionalDTO != null)
                me2HugLikeConditionalDTO = null;

            me2HugLikeConditionalDTO = new Me2HugLikeConditionalDTO(MainActivity.enc_username, item.getObjectId(), item.getCreatedByUser(), me2Users,
                    me2Users, me2gcount, item.getMe2s() - 1, "false", vertual_commnet_cont, "", "", "", "" + total_count, "");
            holder.me2.setImageResource(R.drawable.ic_me_off);

            try {
                int kk = item.getMe2s();

                if (kk > 0) {
                    item.setMe2s(item.getMe2s() - 1);
                } else {
                    item.setMe2s(0);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            holder.me2s.setText(String.valueOf(item.getMe2s()));

            new Me2Action().execute();
            String userna = AppSession.getValue(mContext, Constants.USER_NAME);
            if (userna != null && !userna.equalsIgnoreCase("")) {
                ((MainActivity) mContext).updateActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
        ParseObject biteObject = new ParseObject(Constants.SECRET_CLASS);
        biteObject.setObjectId(item.getObjectId());
        biteObject.put(Constants.SECRET_ME2S, item.getMe2s() - 1);
       final ArrayList<String> me2Users = item.getMe2Users();
        me2Users.remove(Preference.getUserName());
        biteObject.put(Constants.SECRET_ME2USERS, me2Users);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        biteObject.setACL(acl);

        biteObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {

                    try
                    {


                        CommonFunction.decreasem2water();

                        holder.me2.setImageResource(R.drawable.ic_me_off);

                        try {
                            int kk = item.getMe2s();

                            if (kk > 0) {
                                item.setMe2s(item.getMe2s() - 1);
                            } else {
                                item.setMe2s(0);
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }



                        holder.me2s.setText(String.valueOf(item.getMe2s()));


                        ParseUser.getCurrentUser().put(Constants.USER_ME2S, ParseUser.getCurrentUser().getInt(Constants.USER_ME2S) - 1);
                        ParseUser.getCurrentUser().put("lastactivitydate", formattedDate);
                        ParseUser.getCurrentUser().put(Constants.SECRET_ME2USERS, me2Users);
                        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                try
                                {
                                    ((MainActivity) mContext).updateActivity();
                                }
                                catch (Exception e2)
                                {
                                    e2.printStackTrace();
                                }

                            }
                        });
                    }
                    catch (Exception e2)
                    {
                        e2.printStackTrace();
                    }

                }
            }
        });*/

        return me2Users;
    }


   /* private void sendPushData(String id, int count, String user, String keyword, final Secret item, final String type) {
        JSONObject dataType = new JSONObject();
        final String text;

        if (type.equals(Constants.NOTIFICATION_TYPE_ME2)) {
            text = mContext.getString(R.string.you_have_shared) + count + mContext.getString(R.string.people_);
        } else {
            text = count + mContext.getString(R.string.people_have) + keyword + mContext.getString(R.string.your_secret);
        }

        try {
            dataType.put(Constants.PUSH_ALERT, text);
            dataType.put(Constants.PUSH_SECRET_ID, id);
            dataType.put(Constants.PUSH_ACTION, Constants.PUSH_ACTION_SOCIAL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ParsePush push = new ParsePush();
        push.setData(dataType);
        push.setChannel(user);
        push.sendInBackground(new SendCallback() {
            @Override
            public void done(ParseException e) {
                savePushToTheClass(item.getObject(), type, text, item.getUser());
            }
        });
    }

    private void savePushToTheClass(ParseObject item, String type, String text, ParseUser user) {
        try {
            ObjectMaker.formNewNotification(item, text, type, user);


        } catch (IllegalArgumentException ill)
        {
        } catch (NullPointerException n) {
        }
    }

    private void createSocialItem(ParseUser receiver, String className, ParseObject secret) {
        try {
            ObjectMaker.formSocial(ParseUser.getCurrentUser(), receiver, className, secret).saveInBackground();
        } catch (IllegalArgumentException ill) {
        } catch (NullPointerException n) {
        }
    }
*/


    private class Me2Action extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);


                Me2HufLikeActionDTO loginDTO = new Me2HufLikeActionDTO(Constants.ENCRYPT_SECRET_TABLE, Constants.ENCRYPT_ME2ACTION_METHOD, me2HugLikeConditionalDTO);
                Me2HugLikeObjectDTO loginbjectDTO = new Me2HugLikeObjectDTO(loginDTO);

                http.Me2HuglikeAction(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

        }
    }


    private class HeartsAction extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);


                Me2HufLikeActionDTO loginDTO = new Me2HufLikeActionDTO(Constants.ENCRYPT_SECRET_TABLE, Constants.ENCRYPT_HEARTACTION_METHOD, me2HugLikeConditionalDTO);
                Me2HugLikeObjectDTO loginbjectDTO = new Me2HugLikeObjectDTO(loginDTO);

                http.Me2HuglikeAction(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

        }
    }


    private class HugAction extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);


                Me2HufLikeActionDTO loginDTO = new Me2HufLikeActionDTO(Constants.ENCRYPT_SECRET_TABLE, Constants.ENCRYPT_HUGACTION_METHOD, me2HugLikeConditionalDTO);
                Me2HugLikeObjectDTO loginbjectDTO = new Me2HugLikeObjectDTO(loginDTO);

                http.Me2HuglikeAction(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

        }
    }


    public List<String> unme2Room(final RoomsInfoDTO item, final RoomListAdapter.ViewHolder holder) {


        final List<String> me2_users = item.getMe2_users();


        try {


            me2_users.remove(MainActivity.enc_username);


            if (me2HugLikeRoomDTO != null)
                me2HugLikeRoomDTO = null;


            me2HugLikeRoomDTO = new Me2HugLikeRoomDTO(MainActivity.enc_username, item.getRoom_id(), "me2s", "true");

            try {
                // item.setMe2_count(me2_users.size());
                holder.me2_count.setText("" + me2_users.size());

            } catch (Exception e2) {
                e2.printStackTrace();
            }

            holder.me2.setImageResource(R.drawable.ic_me_off);

            // holder.hugs.setText(String.valueOf(item.getHugs()));
            new Hughearme2RoomAction().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return me2_users;
    }


    public List<String> me2Room(final RoomsInfoDTO item, final RoomListAdapter.ViewHolder holder) {


        final List<String> me2_users = item.getMe2_users();


        try {


            me2_users.add(MainActivity.enc_username);


            if (me2HugLikeRoomDTO != null)
                me2HugLikeRoomDTO = null;


            me2HugLikeRoomDTO = new Me2HugLikeRoomDTO(MainActivity.enc_username, item.getRoom_id(), "me2s", "false");

            try {
                //   item.setMe2_count(me2_users.size());
                holder.me2_count.setText("" + me2_users.size());

            } catch (Exception e2) {
                e2.printStackTrace();
            }

            holder.me2.setImageResource(R.drawable.ic_me);

            // holder.hugs.setText(String.valueOf(item.getHugs()));
            new Hughearme2RoomAction().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return me2_users;
    }


    public List<String> unHeartRoom(final RoomsInfoDTO item, final RoomListAdapter.ViewHolder holder) {


        final List<String> heartUsers = item.getHeart_users();


        try {


            heartUsers.remove(MainActivity.enc_username);


            if (me2HugLikeRoomDTO != null)
                me2HugLikeRoomDTO = null;


            me2HugLikeRoomDTO = new Me2HugLikeRoomDTO(MainActivity.enc_username, item.getRoom_id(), "heart", "true");

            try {
                // item.setHeart_count(heartUsers.size());
                holder.heart_count.setText("" + heartUsers.size());

            } catch (Exception e2) {
                e2.printStackTrace();
            }

            holder.heart.setImageResource(R.drawable.ic_heart);

            // holder.hugs.setText(String.valueOf(item.getHugs()));
            new Hughearme2RoomAction().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return heartUsers;
    }


    public List<String> HeartRoom(final RoomsInfoDTO item, final RoomListAdapter.ViewHolder holder) {


        final List<String> heartUsers = item.getHeart_users();


        try {


            heartUsers.add(MainActivity.enc_username);


            if (me2HugLikeRoomDTO != null)
                me2HugLikeRoomDTO = null;

            me2HugLikeRoomDTO = new Me2HugLikeRoomDTO(MainActivity.enc_username, item.getRoom_id(), "heart", "false");

            try {

                holder.heart_count.setText("" + heartUsers.size());

            } catch (Exception e2) {
                e2.printStackTrace();
            }

            holder.heart.setImageResource(R.drawable.ic_hearted);

            // holder.hugs.setText(String.valueOf(item.getHugs()));
            new Hughearme2RoomAction().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return heartUsers;
    }

    public List<String> unHugRoom(final RoomsInfoDTO item, final RoomListAdapter.ViewHolder holder) {


        final List<String> hugUsers = item.getHug_users();


        try {


            hugUsers.remove(MainActivity.enc_username);


            if (me2HugLikeRoomDTO != null)
                me2HugLikeRoomDTO = null;


            me2HugLikeRoomDTO = new Me2HugLikeRoomDTO(MainActivity.enc_username, item.getRoom_id(), "hug", "true");

            try {
                //    item.setHug_count(hugUsers.size());
                holder.hug_count.setText("" + hugUsers.size());

            } catch (Exception e2) {
                e2.printStackTrace();
            }
            holder.hug.setImageResource(R.drawable.ic_not_hug);

            // holder.hugs.setText(String.valueOf(item.getHugs()));
            new Hughearme2RoomAction().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return hugUsers;
    }


    public List<String> HugRoom(final RoomsInfoDTO item, final RoomListAdapter.ViewHolder holder) {


        final List<String> hugUsers = item.getHug_users();


        try {


            hugUsers.add(MainActivity.enc_username);


            if (me2HugLikeRoomDTO != null)
                me2HugLikeRoomDTO = null;


            me2HugLikeRoomDTO = new Me2HugLikeRoomDTO(MainActivity.enc_username, item.getRoom_id(), "hug", "false");

            try {
                holder.hug_count.setText("" + hugUsers.size());

            } catch (Exception e2) {
                e2.printStackTrace();
            }
            holder.hug.setImageResource(R.drawable.ic_hug);


            new Hughearme2RoomAction().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return hugUsers;
    }


    private class Hughearme2RoomAction extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);


                CommonRequestTypeDTO commonRequestTypeDTO = new CommonRequestTypeDTO(me2HugLikeRoomDTO, "roomAction");
                FinalObjectDTO finalObjectDTO = new FinalObjectDTO(commonRequestTypeDTO);
                http.AcceptAppointment(finalObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

        }
    }


    private class AddShortsent extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String response = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);


                CommonRequestTypeDTO commonRequestTypeDTO = new CommonRequestTypeDTO(addShortSentense, "Addshortsentence");
                FinalObjectDTO finalObjectDTO = new FinalObjectDTO(commonRequestTypeDTO);
                response = http.AcceptAppointment(finalObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            if (response != null) {

            }
        }
    }


}
