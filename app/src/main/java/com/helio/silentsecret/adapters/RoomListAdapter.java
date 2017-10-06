package com.helio.silentsecret.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.CommentRoomsActivity;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.models.FlickerImagload;
import com.helio.silentsecret.models.RoomsInfoDTO;
import com.helio.silentsecret.social.SocialOperations;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/2/2016.
 */
public class RoomListAdapter extends BaseAdapter {

    private List<RoomsInfoDTO> roomsInfoDTOs  = new ArrayList<>();

    private Context mActivity;

    private SocialOperations mOperations;
    List<FlickerImagload> flickerImagloads = new ArrayList<>();

    RoomsInfoDTO roomsInfoDTO = null;
    public RoomListAdapter(Context mActivity, List<RoomsInfoDTO> iFriendList){
        this.roomsInfoDTOs = iFriendList;
        this.mActivity = mActivity;
        this.mOperations = new SocialOperations(mActivity);
        flickerImagloads.clear();
    }

    @Override
    public int getCount() {
        return roomsInfoDTOs.size();
    }

    @Override
    public RoomsInfoDTO getItem(int position) {
        return roomsInfoDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public TextView room_title;
        private ImageView room_icon;
        public ImageView hug;
        public ImageView heart;
        public ImageView me2;
        public ImageView comment;

        public TextView me2_count;
        public TextView heart_count;
        public TextView hug_count;
        public TextView room_owner;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

       final  ViewHolder holder;

       try
       {
           if (convertView==null)
           {
               holder = new ViewHolder();
               /***********  Layout inflator to call external xml layout () ***********/
               LayoutInflater inflater = (LayoutInflater)mActivity.
                       getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
               convertView = inflater.inflate(R.layout.room_row_item, null);

               /****** View Holder Object to contain tabitem.xml file elements ******/


               holder.room_title = (TextView) convertView.findViewById(R.id.room_title);
               holder.room_icon = (ImageView) convertView.findViewById(R.id.room_icon);
               holder.hug = (ImageView) convertView.findViewById(R.id.hug);
               holder.heart = (ImageView) convertView.findViewById(R.id.heart);
               holder.me2 = (ImageView) convertView.findViewById(R.id.me2);
               holder.comment = (ImageView) convertView.findViewById(R.id.comment);
               holder.me2_count = (TextView) convertView.findViewById(R.id.me2_count);
               holder.heart_count = (TextView) convertView.findViewById(R.id.heart_count);
               holder.hug_count = (TextView) convertView.findViewById(R.id.hug_count);
               holder.room_owner = (TextView) convertView.findViewById(R.id.room_owner);

               /************  Set holder with LayoutInflater ************/
               convertView.setTag( holder );
           }else{
               holder =(ViewHolder)convertView.getTag();

           }


           final RoomsInfoDTO bean = roomsInfoDTOs.get(position);

           holder.hug_count.setText(""+bean.getHug_users().size());
           holder.me2_count.setText(""+bean.getMe2_users().size());
           holder.heart_count.setText(""+bean.getHeart_users().size());

           holder.room_title.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/ubuntu.ttf"));

            /*if(bean.getFirst_name()!= null && !bean.getFirst_name().equalsIgnoreCase(""))
           holder.room_owner.setText("~"+CryptLib.decrypt(bean.getFirst_name()));*/

           if (bean.getHug_users() != null)
               if (bean.getHug_users().contains(MainActivity.enc_username)) {
                   holder.hug.setImageResource(R.drawable.ic_hug);
               } else {
                   holder.hug.setImageResource(R.drawable.ic_not_hug);
               }

           if (bean.getHeart_users() != null)
               if (bean.getHeart_users().contains(MainActivity.enc_username)) {
                   holder.heart.setImageResource(R.drawable.ic_hearted);
               } else {
                   holder.heart.setImageResource(R.drawable.ic_heart);
               }

           if (bean.getMe2_users() != null)
               if (bean.getMe2_users().contains(MainActivity.enc_username)) {
                   holder.me2.setImageResource(R.drawable.ic_me);
               } else {
                   holder.me2.setImageResource(R.drawable.ic_me_off);
               }



           holder.hug.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View view)
               {

                   try {
                       if (!ConnectionDetector.isNetworkAvailable(mActivity))
                       {
                           new ToastUtil(mActivity, "Please check your internet connection.");
                           return ;
                       }
                       roomsInfoDTO = getItem(position);
                       if (roomsInfoDTO.getHug_users() != null)
                           if (roomsInfoDTO.getHug_users().contains(MainActivity.enc_username))
                           {
                               roomsInfoDTOs.get(position).setHug_users(mOperations.unHugRoom(roomsInfoDTO, holder));

                           } else {
                               roomsInfoDTOs.get(position).setHug_users(mOperations.HugRoom(roomsInfoDTO, holder));
                           }
                   } catch (Exception e) {
                       e.printStackTrace();
                   }

               }
           });

           holder.heart.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   try {
                       if (!ConnectionDetector.isNetworkAvailable(mActivity))
                       {
                           new ToastUtil(mActivity, "Please check your internet connection.");
                           return ;
                       }
                       roomsInfoDTO = getItem(position);
                       if (roomsInfoDTO.getHeart_users() != null)
                           if (roomsInfoDTO.getHeart_users().contains(MainActivity.enc_username))
                           {
                               roomsInfoDTOs.get(position).setHeart_users(mOperations.unHeartRoom(roomsInfoDTO, holder));

                           } else {
                               roomsInfoDTOs.get(position).setHeart_users(mOperations.HeartRoom(roomsInfoDTO, holder));
                           }
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           });

           holder.me2.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   try {
                       if (!ConnectionDetector.isNetworkAvailable(mActivity))
                       {
                           new ToastUtil(mActivity, "Please check your internet connection.");
                           return ;
                       }
                       roomsInfoDTO = getItem(position);
                       if (roomsInfoDTO.getMe2_users() != null)
                           if (roomsInfoDTO.getMe2_users().contains(MainActivity.enc_username))
                           {
                               roomsInfoDTOs.get(position).setMe2_users(mOperations.unme2Room(roomsInfoDTO, holder));

                           } else {
                               roomsInfoDTOs.get(position).setMe2_users(mOperations.me2Room(roomsInfoDTO, holder));
                           }
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           });

           holder.comment.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   runComments( getItem(position));
               }
           });
           holder.room_title.setText(CryptLib.decrypt(bean.getRoom_name()));

           if(bean.getRoom_image_url()!= null && !bean.getRoom_image_url().equalsIgnoreCase("") && bean.getRoom_image_url().contains("http"))
           {
               final String img_url = bean.getRoom_image_url();
               if (img_url != null && !img_url.isEmpty())
               {
                   boolean is_download = false;
                   if (flickerImagloads.size() > 0)
                   {
                       for (int i = 0; i < flickerImagloads.size(); i++)
                       {
                           if (img_url.equalsIgnoreCase(flickerImagloads.get(i).getFliker_image_url()))
                           {
                               is_download = true;
                               holder.room_icon.setImageBitmap(flickerImagloads.get(i).getFliker_image_bitmap());
                               break;
                           }

                       }
                   }

                   if (!is_download)
                   {
                       Glide.with(mActivity)
                               .load(img_url).asBitmap()
                               .placeholder(R.drawable.bg0)
                               .into(new SimpleTarget<Bitmap>(200, 200)
                               {
                                   @Override
                                   public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
                                   {

                                       flickerImagloads.add(new FlickerImagload(img_url, resource));
                                       holder.room_icon.setImageBitmap(resource);

                                   }
                               });
                   }



               }
              // new ImageLoaderUtil(mActivity).loadImageAlphaCache(bean.getRoom_image_url(), holder.room_icon);
           }
           else {
               holder.room_icon.setImageBitmap(bean.getRoom_image());
           }

       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
        return convertView;
    }




    private void runComments(RoomsInfoDTO item) {
        try {
            Intent comments = new Intent(mActivity, CommentRoomsActivity.class);

            comments.putExtra(Constants.MAIN_STATE_KEY, Constants.MAIN_STATE_VALUE);

            Constants.roomComment = item;
            mActivity.startActivity(comments);
            ((Activity) mActivity).overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
