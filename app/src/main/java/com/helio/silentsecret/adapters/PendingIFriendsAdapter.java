package com.helio.silentsecret.adapters;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.DeniedFriendRequestDTO;
import com.helio.silentsecret.WebserviceDTO.IfriendSecretpushNotiDataDTO;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.fragments.IFriendsFragment;
import com.helio.silentsecret.fragments.PendingIFriendsFragment;
import com.helio.silentsecret.models.IfriendAcceptObjectDTO;
import com.helio.silentsecret.models.IfriendAcceptRequestDTO;
import com.helio.silentsecret.models.IfriendAcceptRequesttDataDTO;
import com.helio.silentsecret.models.IfriendRequestRejectDTO;
import com.helio.silentsecret.models.IfriendRequestRejectObjectDTO;
import com.helio.silentsecret.models.IfriendSecretPushDTO;
import com.helio.silentsecret.models.IfriendsecrtpushobjectDTO;
import com.helio.silentsecret.models.PendingIFriendsBean;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.Preference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


/**
 * Created by Maroof Ahmed Siddique on 8/5/2016.
 */
public class PendingIFriendsAdapter extends BaseAdapter {


    public static ArrayList<PendingIFriendsBean> pendingIFriendList = new ArrayList<>();

    private Activity mActivity;

  //  IfriendAcceptRequestDTO ifriendAcceptRequestDTO = null;

    IfriendAcceptRequesttDataDTO ifriendAcceptRequesttDataDTO = null;
    LocationManager locationManager;

    String SenderName = "";


    private static int temp = 0;
    Location location;

    private double latitudeTarget;
    private double longitudeTarget;
    static int senderFriendCount = 0;
    private double latitudeSend1;
    private double longitudeSend1;

    private String newobjectID = "";

    private double distanceInMeters = 0.0;

    TextView refresh_list = null;
    TextView deny_list = null;
   // IfriendRequestRejectDTO ifriendRequestRejectDTO = null;

    DeniedFriendRequestDTO deniedFriendRequestDTO = null;
    private ViewHolder holder;

    private boolean isClick = false;
    private boolean isNoti_Click = false;


    public PendingIFriendsAdapter(Activity mActivity, ArrayList<PendingIFriendsBean> pendingIFriendList) {
        this.pendingIFriendList = pendingIFriendList;
        this.mActivity = mActivity;


    }

    @Override
    public int getCount() {
        return pendingIFriendList.size();
    }

    @Override
    public Object getItem(int position) {
        return pendingIFriendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /*********
     * Create a holder Class to contain inflated xml file elements
     *********/
    public static class ViewHolder {

        public TextView pendingIFriendName;
        public TextView send_request_ifriends;
        public TextView mSendDate;

        public ImageView accept_request;
        public ImageView reject_request;

        public TextView mPendingDate;

    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        PendingIFriendsBean bean = pendingIFriendList.get(position);
        return bean.getRowType();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        int itemPosition = getItemViewType(position);

        if (convertView == null) {
            /***********  Layout inflator to call external xml layout () ***********/
            LayoutInflater inflater = (LayoutInflater) mActivity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/

            holder = new ViewHolder();
            if (itemPosition == 1)//send layout
            {
                convertView = inflater.inflate(R.layout.pending_friends_row_item, null);
                /****** View Holder Object to contain tabitem.xml file elements ******/

                holder.pendingIFriendName = (TextView) convertView.findViewById(R.id.name_of_pending_ifriends);
                holder.mSendDate = (TextView) convertView.findViewById(R.id.date_sent);


            } else if (itemPosition == 2) {

                convertView = inflater.inflate(R.layout.send_request_row_item, null);
                /****** View Holder Object to contain tabitem.xml file elements ******/


                holder.send_request_ifriends = (TextView) convertView.findViewById(R.id.send_request_ifriends);
                holder.accept_request = (ImageView) convertView.findViewById(R.id.accept_request);
                holder.reject_request = (ImageView) convertView.findViewById(R.id.reject_request);
                holder.mPendingDate = (TextView) convertView.findViewById(R.id.date_pending);


                holder.accept_request.setId(position + 1);
                holder.reject_request.setId(position + 2);

                holder.accept_request.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        int index = v.getId() - 1;
                        final PendingIFriendsBean bean = pendingIFriendList.get(index);
                        if (ConnectionDetector.isNetworkAvailable(mActivity) && !isClick) {

                            // refresh_list = new TextView(mActivity);

                            PendingIFriendsFragment.progress_bar.setVisibility(View.VISIBLE);

                            isClick = true;


                            try {
                                locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);

                                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, new android.location.LocationListener() {
                                    @Override
                                    public void onLocationChanged(Location location) {
                                        longitudeTarget = location.getLongitude();
                                        latitudeTarget = location.getLatitude();

                                    }

                                    @Override
                                    public void onStatusChanged(String provider, int status, Bundle extras) {

                                    }

                                    @Override
                                    public void onProviderEnabled(String provider) {

                                    }

                                    @Override
                                    public void onProviderDisabled(String provider) {

                                    }
                                });


                                if (location != null) {

                                    longitudeTarget = location.getLongitude();
                                    latitudeTarget = location.getLatitude();

                                }else
                                {
                                    try
                                    {
                                        latitudeTarget =  Double.parseDouble(Preference.getUserLat())  ;
                                        longitudeTarget =   Double.parseDouble( Preference.getUserLon()) ;

                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }


                                locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
                                boolean gps_enabled = false;
                                boolean network_enabled = false;

                                try {
                                    gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                                } catch (Exception ex) {
                                }

                                try {
                                    network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                                } catch (Exception ex) {
                                }

                                if ( longitudeTarget != 0.0 && latitudeTarget != 0.0)
                                {
                                    gps_enabled = true;
                                    network_enabled = true;
                                }


                                if (!gps_enabled && !network_enabled) {

                                    PendingIFriendsFragment.progress_bar.setVisibility(View.GONE);
                                    isClick = false;
                                    // notify user
                                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(v.getRootView().getContext());
                                    dialog2.setMessage(mActivity.getResources().getString(R.string.gps_network_accept_not_enabled));
                                    dialog2.setPositiveButton(mActivity.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                            // TODO Auto-generated method stub
                                            PendingIFriendsFragment.progress_bar.setVisibility(View.GONE);

                                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            mActivity.startActivity(myIntent);
                                            //get gps
                                        }
                                    });

                                    dialog2.setNegativeButton(mActivity.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                            // TODO Auto-generated method stub

                                            PendingIFriendsFragment.progress_bar.setVisibility(View.GONE);
                                        }
                                    });


                                    AlertDialog alert2 = dialog2.create();
                                    alert2.setCanceledOnTouchOutside(false);
                                    alert2.show();

                                } else

                                {

                                    if (longitudeTarget != 0.0 && latitudeTarget != 0.0) {


                                        latitudeSend1 = bean.getLatitudeSend();
                                        longitudeSend1 = bean.getLongitudeSend();


                                        Location senderLoc = new Location("");
                                        senderLoc.setLatitude(latitudeSend1);
                                        senderLoc.setLongitude(longitudeSend1);

                                        Location targetLoc = new Location("");
                                        targetLoc.setLatitude(latitudeTarget);
                                        targetLoc.setLongitude(longitudeTarget);

                                        distanceInMeters = senderLoc.distanceTo(targetLoc);

                                        int profile_pic_name = randInt(1, 20);

                                        String userlat = "" + latitudeTarget;
                                        String userlang = "" + longitudeTarget;
                                       // userlat = "28.7041";
                                       // userlang = "77.1025";
                                        String sendarlat = "" + bean.getLatitudeSend();
                                        String sendarlang = "" + bean.getLongitudeSend();

                                        SenderName = bean.getSender();

                                        ifriendAcceptRequesttDataDTO = new IfriendAcceptRequesttDataDTO(MainActivity.enc_username, bean.getSender(), sendarlat, sendarlang,
                                                userlat, userlang, "" + profile_pic_name);
                                        new Acceptrequest().execute();

                                        MainActivity.userListNamesOnlyWaiting.add( bean.getSender());


                                    } else

                                    {

                                        isClick = false;
                                        PendingIFriendsFragment.progress_bar.setVisibility(View.GONE);
                                        AlertDialog.Builder location = new AlertDialog.Builder(v.getRootView().getContext());
                                        location.setMessage("Location not found. Please try again to accept request.");

                                        location.setPositiveButton(
                                                "Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();

                                                    }
                                                });


                                        AlertDialog locationalert = location.create();
                                        locationalert.setCanceledOnTouchOutside(false);
                                        locationalert.show();
                                    }
                                }

                                // }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                               /* }
                            }, 1000);*/

                        }
                    }
                });


                holder.reject_request.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        int index = v.getId() - 2;
                        final PendingIFriendsBean bean = pendingIFriendList.get(index);

                        if (ConnectionDetector.isNetworkAvailable(mActivity) && !isClick) {

                            AlertDialog.Builder rejectrequest = new AlertDialog.Builder(v.getRootView().getContext());
                            rejectrequest.setMessage("Are you sure you want to deny this request?");

                            rejectrequest.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            PendingIFriendsFragment.progress_bar.setVisibility(View.VISIBLE);
                                            isClick = true;
                                            deniedFriendRequestDTO = new DeniedFriendRequestDTO( bean.getSender(), MainActivity.enc_username);
                                            new IFirendDeniedRequest().execute();

                                        }
                                    });

                            rejectrequest.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = rejectrequest.create();
                            alert11.setCanceledOnTouchOutside(false);
                            alert11.show();
                        }


                    }
                });


            }

            if (convertView != null) {
                /************  Set holder with LayoutInflater ************/
                convertView.setTag(holder);
            }


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (itemPosition == 1) {//send layout
            final PendingIFriendsBean bean = pendingIFriendList.get(position);
            //int tempPos = position + 1;
            holder.pendingIFriendName.setText(bean.getPendingIFriendName());
            holder.mSendDate.setText(bean.getSendDate());
        } else if (itemPosition == 2) {//accept reject layout
            final PendingIFriendsBean bean = pendingIFriendList.get(position);
           if(PendingIFriendsFragment.noOfDays == 1)
           {
                   holder.send_request_ifriends.setText(bean.getPendingIFriendName());
                   holder.mPendingDate.setText(bean.getPendingDate());
                   holder.mPendingDate.setTextColor(Color.parseColor("#FF304C"));
                   holder.send_request_ifriends.setTextColor(Color.parseColor("#FF304C"));

           }
            else
           {
               if (PendingIFriendsFragment.noOfDays > show(bean.getCreatedDate())) {

                   holder.send_request_ifriends.setText(bean.getPendingIFriendName());
                   holder.mPendingDate.setText(bean.getPendingDate());
                   holder.mPendingDate.setTextColor(Color.parseColor("#000000"));
                   holder.send_request_ifriends.setTextColor(Color.parseColor("#000000"));
               } else {

                   holder.send_request_ifriends.setText(bean.getPendingIFriendName());
                   holder.mPendingDate.setText(bean.getPendingDate());
                   holder.mPendingDate.setTextColor(Color.parseColor("#FF304C"));
                   holder.send_request_ifriends.setTextColor(Color.parseColor("#FF304C"));
               }
           }
        }

        return convertView;
    }

    private void tooNearToAdd() {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(mActivity);
        builder2.setMessage("Internet Friend is too near to add.");

        builder2.setPositiveButton(
                "Ok, Fine",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PendingIFriendsFragment.callPendingSending();
                    }
                });

        AlertDialog alert12 = builder2.create();
        alert12.setCanceledOnTouchOutside(false);
        alert12.show();
    }

    private void senderFriends() {

        AlertDialog.Builder senderFriends = new AlertDialog.Builder(mActivity);
        senderFriends.setMessage("You are too late to accept iFriend request.");

        senderFriends.setPositiveButton(
                "Ok, Fine",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert1senderFriends = senderFriends.create();
        alert1senderFriends.setCanceledOnTouchOutside(false);
        alert1senderFriends.show();
    }

    private void myFriendsAllowed() {

        AlertDialog.Builder senderFriends = new AlertDialog.Builder(mActivity);
        senderFriends.setMessage("Maximum iFriends allowed limit reached. Please remove any existing iFriend to add new.");


        senderFriends.setPositiveButton(
                "Ok, Fine",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert1senderFriends = senderFriends.create();
        alert1senderFriends.setCanceledOnTouchOutside(false);
        alert1senderFriends.show();
    }


    public static int randInt(int min, int max) {

        int randomNum = 0;
        Random rand = new Random();

        randomNum = rand.nextInt((max - min) + 1) + min;


        if (IFriendsFragment.iFriendList != null && IFriendsFragment.iFriendList.size() > 0) {
            for (int k = 0; k < IFriendsFragment.iFriendList.size(); k++) {
                if (randomNum == IFriendsFragment.iFriendList.get(k).getProfilepic()) {
                    randomNum = 0;

                    break;
                }
            }
        }

        if (randomNum == 0)
            return randInt(1, 20);
        else
            return randomNum;
    }


    public void removeItemFromList(int position) {
        pendingIFriendList.remove(position);
        notifyDataSetChanged();
    }


    public static int show(String time) {
        int day = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");


            Date Date1 = format.parse(CommonFunction.getdate() + " " + CommonFunction.gettime());
            Date Date2 = format.parse(time);
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


    private class Acceptrequest extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(mActivity);


                IfriendAcceptObjectDTO ifriendAcceptObjectDTO = new IfriendAcceptObjectDTO(new IfriendAcceptRequestDTO(ifriendAcceptRequesttDataDTO));
                response = http.sendAcceptRequest(ifriendAcceptObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                PendingIFriendsFragment.progress_bar.setVisibility(View.GONE);
                isClick = false;

                if (response != null && !response.equalsIgnoreCase("") && response.equalsIgnoreCase("success"))
                {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mActivity);
                    builder1.setMessage("Congratulations!! iFriend successfully added. Would you like us to notify you when your internet friend shares a secret?");

                    builder1.setPositiveButton(
                            "Yes, Please",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    PendingIFriendsFragment.progress_bar.setVisibility(View.VISIBLE);
                                    new IFirendShareSecretNotification().execute();
                                    dialog.cancel();

                                }
                            });

                    builder1.setNegativeButton(
                            "No, Thanks",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    isClick = false;
                                    PendingIFriendsFragment.callPendingSending();
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.setCanceledOnTouchOutside(false);
                    alert11.show();

                } else if (response != null && !response.equalsIgnoreCase("") && response.equalsIgnoreCase("nearby")) {
                    tooNearToAdd();
                } else
                {
                    if (response != null && !response.equalsIgnoreCase("") && response.equalsIgnoreCase("friend"))
                        senderFriends();
                    else
                        myFriendsAllowed();

                    /*String[] errorarray = response.split("#");

                    if (errorarray != null && errorarray[1] != null && errorarray[1].equalsIgnoreCase("user")) {

                        myFriendsAllowed();
                    } else {
                        senderFriends();
                    }
*/

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class IFirendShareSecretNotification extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(mActivity);
                IfriendSecretpushNotiDataDTO ifriendSecretpushNotiDataDTO = new IfriendSecretpushNotiDataDTO(MainActivity.enc_username,SenderName);
                IfriendSecretPushDTO ifriendSecretPushDTO = new IfriendSecretPushDTO(ifriendSecretpushNotiDataDTO);
                IfriendsecrtpushobjectDTO ifriendAcceptObjectDTO = new IfriendsecrtpushobjectDTO(ifriendSecretPushDTO);


                response = http.sendIfriendsecretpush(ifriendAcceptObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                PendingIFriendsFragment.progress_bar.setVisibility(View.GONE);

                PendingIFriendsFragment.callPendingSending();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class IFirendDeniedRequest extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(mActivity);
                IfriendRequestRejectDTO ifriendRequestRejectDTO = new IfriendRequestRejectDTO(deniedFriendRequestDTO,Constants.ENCRYPT_IFRIEND_CHAT_TABLE,Constants.ENCRYPT_FIND_METHOD,"deniedFriendRequest");
                IfriendRequestRejectObjectDTO ifriendRequestRejectObjectDTO = new IfriendRequestRejectObjectDTO(ifriendRequestRejectDTO);
                response = http.Rejectifriend(ifriendRequestRejectObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                isClick = false;
                PendingIFriendsFragment.progress_bar.setVisibility(View.GONE);
                PendingIFriendsFragment.callPendingSending();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
