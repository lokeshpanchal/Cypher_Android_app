package com.helio.silentsecret.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.SeenbyConditionDTO;
import com.helio.silentsecret.WebserviceDTO.SeenbyDTO;
import com.helio.silentsecret.WebserviceDTO.SeenbyObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SetFlagUnFlagCommentDataDTO;
import com.helio.silentsecret.activities.CommentRoomsActivity;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.RoomComment;
import com.helio.silentsecret.utils.FruitsRandom;
import com.helio.silentsecret.utils.ImageLoaderUtil;
import com.helio.silentsecret.utils.TimeUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomCommentsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<RoomComment> mDataList;
    private ImageLoaderUtil mLoader;
    private Random rnd;
    String newText = "";

    SetFlagUnFlagCommentDataDTO setFlagUnFlagCommentDataDTO = null;

    private String wordArray[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};

    List<String> seen_user_array = new ArrayList<>();
    String comment_id = "" , flag_comment_id = "";
    private boolean mViewMode = false;
    private Context mContext;



    int[] petimagearray = {R.drawable.monkey_face, R.drawable.panda_face,
            R.drawable.horse_face, R.drawable.rabbit_face,
            R.drawable.donkey_face, R.drawable.sheep_face,
            R.drawable.deer_face, R.drawable.tiger_face,
            R.drawable.parrot_face, R.drawable.meerkat_face ,
            R.drawable.mermain_face, R.drawable.cat_face ,
            R.drawable.dog_face,R.drawable.unicorn_face,
            R.drawable.dragon_face,R.drawable.dynasore_face};

    public RoomCommentsAdapter(LayoutInflater inflater, List<RoomComment> list, Context context) {
        init(list, context, inflater);
    }

    public RoomCommentsAdapter(LayoutInflater inflater, List<RoomComment> list, Context context, boolean mode) {
        init(list, context, inflater);
        mViewMode = mode;
    }

    private void init(List<RoomComment> list, Context context, LayoutInflater inflater) {
        mInflater = inflater;
        mDataList = list;
        rnd = new Random();
        mLoader = new ImageLoaderUtil(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public RoomComment getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        final RoomComment item = mDataList.get(position);

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.room_comment_item, parent, false);

            holder.fruitMe = (ImageView) convertView.findViewById(R.id.comment_item_fruit_me);
            holder.textMe = (TextView) convertView.findViewById(R.id.comment_item_text_me);


            holder.timeMe = (TextView) convertView.findViewById(R.id.comment_item_time_me);
            holder.flagMe = (ImageView) convertView.findViewById(R.id.comment_item_flag_me);

            holder.fruitOther = (ImageView) convertView.findViewById(R.id.comment_item_fruit_other);
            holder.textOther = (TextView) convertView.findViewById(R.id.comment_item_text_other);
            holder.timeOther = (TextView) convertView.findViewById(R.id.comment_item_time_other);
            holder.flagOther = (ImageView) convertView.findViewById(R.id.comment_item_flag_other);

            holder.meLayout = convertView.findViewById(R.id.comments_item_me);
            holder.otherLayout = convertView.findViewById(R.id.comments_item_other);

            holder.reply = convertView.findViewById(R.id.comment_item_reply);
            holder.padding = convertView.findViewById(R.id.comment_item_padding);


            holder.textMe.setId(position + 1);

            holder.textOther.setId(position + 2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final boolean isMe = item.getUser() != null
                ? item.getUser().equals(MainActivity.enc_username) && !item.isReply()
                : false;

        if (isMe) {
            holder.meLayout.setVisibility(View.VISIBLE);
            holder.otherLayout.setVisibility(View.GONE);
        } else {
            holder.otherLayout.setVisibility(View.VISIBLE);
            holder.meLayout.setVisibility(View.GONE);
        }

        try {
            (isMe ? holder.timeMe : holder.timeOther).setText(TimeUtil.timeCalculate(item.getCreatedAt().getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (item.getText() != null && item.getText().contains("iFriend")) {
                newText = item.getText();
                for (int j = 1; j <= 20; j++) {

                    if (item.getText().contains("iFriend" + wordArray[j - 1])) {
                        newText = newText.replace("iFriend" + wordArray[j - 1], "iFriend" + j);
                    }
                }
                (isMe ? holder.textMe : holder.textOther).setText(newText != null ? newText : "");

            } else {
                (isMe ? holder.textMe : holder.textOther).setText(item.getText() != null ? item.getText() : "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        holder.textMe.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/ubuntu.ttf"));

        holder.textOther.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/ubuntu.ttf"));





        /*else
        {
            (isMe ? holder.textMe : holder.textOther).setText(item.getText() != null ? item.getText() : "");
        }*/

        try {

            String pet_name = item.getPet_name();


            if (pet_name != null && !pet_name.equalsIgnoreCase("")) {


                if (isMe) {
                    for (int i = 0; i < MainActivity.petnamearray.length; i++) {
                        if (pet_name.equalsIgnoreCase(MainActivity.petnamearray[i])) {

                            holder.fruitMe.setImageResource(petimagearray[i]);


                        }
                    }
                } else {
                    for (int i = 0; i < MainActivity.petnamearray.length; i++) {
                        if (pet_name.equalsIgnoreCase(MainActivity.petnamearray[i])) {

                            holder.fruitOther.setImageResource(petimagearray[i]);


                        }
                    }

                }

            } else {
                mLoader.loadFruit(FruitsRandom.getFruit(rnd), (isMe ? holder.fruitMe : holder.fruitOther));
            }


        } catch (Exception e) {
            e.printStackTrace();
            mLoader.loadFruit(FruitsRandom.getFruit(rnd), (isMe ? holder.fruitMe : holder.fruitOther));
        }


        // mLoader.loadFruit(FruitsRandom.getFruit(rnd), (isMe ? holder.fruitMe : holder.fruitOther));

        if (!mViewMode)
        {

            if(item.getRoom_user().equalsIgnoreCase(MainActivity.enc_username))
            {
                holder.flagOther.setVisibility(View.VISIBLE);
                holder.flagMe.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.flagOther.setVisibility(View.GONE);
                holder.flagMe.setVisibility(View.GONE);
            }


            (isMe ? holder.flagMe : holder.flagOther).setImageResource(R.drawable.ic_not_flagged);
/*
            if (item.isFlagged())
            {
                (isMe ? holder.flagMe : holder.flagOther).setImageResource(R.drawable.ic_comment_flagged);
            } else {
                (isMe ? holder.flagMe : holder.flagOther).setImageResource(R.drawable.ic_not_flagged);
            }
*/

            (isMe ? holder.flagMe : holder.flagOther).
                    setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {



                            if (ConnectionDetector.isNetworkAvailable(mContext))
                            {


                                try {


                                    AlertDialog.Builder updateAlert = new AlertDialog.Builder(mContext);
                                    updateAlert.setIcon(R.drawable.ic_launcher);
                                    updateAlert.setTitle("Cypher");
                                    updateAlert.setMessage("Do you want to flag this comment?");

                                    updateAlert.setPositiveButton(
                                            "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();

                                                    try {

                                                        flag_comment_id = getItem(position).getComment_id();
                                                        new SetFlagUnflagSecret().execute();

                                                        mDataList.remove(position);
                                                        notifyDataSetChanged();
                                                    } catch (Exception e) {

                                                        e.printStackTrace();
                                                    }


                                                }
                                            });

                                    updateAlert.setNegativeButton(
                                            "No",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();



                                                }
                                            });

                                    AlertDialog alertUp = updateAlert.create();
                                    alertUp.setCanceledOnTouchOutside(false);
                                    alertUp.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }




                            }
                            else
                                Toast.makeText(mContext,"Please check your internet connection.",Toast.LENGTH_SHORT).show();

                        }


                    });

            holder.reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((CommentRoomsActivity) mContext).showReply(item);
                }
            });

        } else {
            holder.reply.setVisibility(View.GONE);
            holder.flagMe.setVisibility(View.INVISIBLE);
            holder.flagOther.setVisibility(View.INVISIBLE);
        }

        if (item.isReply()) {
            holder.reply.setVisibility(View.GONE);
            holder.padding.setVisibility(View.VISIBLE);
        } else {
            holder.padding.setVisibility(View.GONE);
        }


        return convertView;
    }

/*
    private void flag(Comment item, final int position, final ViewHolder holder, final boolean me) {

        final ParseObject biteObject = new ParseObject(Constants.COMMENTS_CLASS);
        biteObject.setObjectId(item.getObjectId());
        biteObject.put(Constants.COMMENTS_FLAGGED, true);
        biteObject.put(Constants.COMMENTS_FAG_USER, ParseUser.getCurrentUser());

        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        biteObject.setACL(acl);

        biteObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    getItem(position).setFlagged(true);
                    (me ? holder.flagMe : holder.flagOther).setImageResource(R.drawable.ic_comment_flagged);
                }
            }
        });
    }

    private void fixSeenBy(Comment item, final int position) {
        ParseObject comment = new ParseObject(Constants.COMMENTS_CLASS);
        comment.setObjectId(item.getObjectId());
        final List<String> listUser = item.getSeenBy();
        listUser.add(Preference.getUserName());
        comment.put(Constants.COMMENTS_SEEN_BY, listUser);

        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        comment.setACL(acl);

        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                getItem(position).setSeenBy(listUser);
            }
        });
    }
*/

    class ViewHolder {
        ImageView fruitMe;
        ImageView flagMe;
        TextView textMe;
        TextView timeMe;

        ImageView fruitOther;
        ImageView flagOther;
        TextView textOther;
        TextView timeOther;

        View meLayout;
        View otherLayout;

        View reply;
        View padding;
    }


    private class Updateseen extends android.os.AsyncTask<String, String, Bitmap> {

        ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {
                IfriendRequest http = new IfriendRequest(mContext);
                SeenbyConditionDTO findNameDTO = new SeenbyConditionDTO(comment_id, MainActivity.enc_username);
                SeenbyDTO seenbyDTO = new SeenbyDTO(findNameDTO);
                response = http.UpdateSeenby(new SeenbyObjectDTO(seenbyDTO));
                comment_id = "";
                seen_user_array.clear();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

        }
    }


    private class SetFlagUnflagSecret extends android.os.AsyncTask<String, String, Bitmap> {

        String data = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);

                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();


                mJsonObjectSub.put("cmnt_unq_id", flag_comment_id);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "flagroomComment");

                main_object.put("requestData", requestdata);
                try {
                     http.flagRoomComment(main_object.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {

            flag_comment_id = "";
        }
    }


}