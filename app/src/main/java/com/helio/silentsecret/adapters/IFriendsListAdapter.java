package com.helio.silentsecret.adapters;

import android.app.Activity;
import android.content.Context;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.fragments.IFriendsFragment;
import com.helio.silentsecret.models.IFriendsBean;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Maroof Ahmed Siddique on 8/2/2016.
 */
public class IFriendsListAdapter extends BaseAdapter {

    private ArrayList<IFriendsBean> iFriendList  = new ArrayList<>();

    private Activity mActivity;

List <String> duble_name = null;

    public  IFriendsListAdapter(Activity mActivity,ArrayList<IFriendsBean> iFriendList){
        this.iFriendList = iFriendList;
        this.mActivity = mActivity;
        if(duble_name!= null)
            duble_name= null;
        duble_name = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return iFriendList.size();
    }

    @Override
    public Object getItem(int position) {
        return iFriendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public TextView iFriendName;
        public TextView dummyName;
        private ImageView mFriendAvatar;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView==null){
            /***********  Layout inflator to call external xml layout () ***********/
            LayoutInflater inflater = (LayoutInflater)mActivity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            convertView = inflater.inflate(R.layout.ifriends_row_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.iFriendName = (TextView) convertView.findViewById(R.id.name_of_ifriends);
            holder.dummyName = (TextView) convertView.findViewById(R.id.dummy_name_of_ifriends);
            holder.mFriendAvatar = (ImageView) convertView.findViewById(R.id.chat_avatar);

            /************  Set holder with LayoutInflater ************/
            convertView.setTag( holder );
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        final IFriendsBean bean = iFriendList.get(position);

       // holder.iFriendName.setTextColor(mActivity.getResources().getColor(R.color.white));
        if (bean.getUnreadcount() > 0 ){
            holder.iFriendName.setTextColor(mActivity.getResources().getColor(R.color.red));
        }
        else {
            holder.iFriendName.setTextColor(mActivity.getResources().getColor(R.color.black));
        }

        int tempPos = position + 1;
        holder.iFriendName.setText(bean.getiFriendName() + tempPos);
        holder.dummyName.setText(bean.getDummyName());
        String drawableName = "";
        if (bean.getProfilepic()>0)
        {
            if(duble_name.contains(""+bean.getProfilepic()))
            {
                int newrandnam = randInt(1,20);
                drawableName = "photo" +newrandnam;
                duble_name.add("" + newrandnam);
            }
            else
            {
                drawableName = "photo" + bean.getProfilepic();

                duble_name.add("" + bean.getProfilepic());
            }
        }

        else
        {
             drawableName = "photo"+ 2;

        }
        Resources res = mActivity.getResources();
       int resID = res.getIdentifier(drawableName, "drawable", mActivity.getPackageName());
        holder.mFriendAvatar.setImageResource(resID);
        return convertView;
    }

    public  void removeItemFromList(int position){
        iFriendList.remove(position);
        notifyDataSetChanged();

    }



    public static int randInt(int min, int max) {

        int  randomNum   =0;
        Random rand = new Random();

        randomNum = rand.nextInt((max - min) + 1) + min;



        if(IFriendsFragment.iFriendList!= null && IFriendsFragment.iFriendList.size()>0)
        {
            for (int k = 0; k < IFriendsFragment.iFriendList.size(); k++) {
                if (randomNum == IFriendsFragment.iFriendList.get(k).getProfilepic())
                {
                    randomNum = 0;

                    break;
                }
            }
        }

        if(randomNum == 0)
            return  randInt(1,20);
        else
            return randomNum;
    }

}
