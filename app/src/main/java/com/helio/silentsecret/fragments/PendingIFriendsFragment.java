package com.helio.silentsecret.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.GetPendingFriendDTO;
import com.helio.silentsecret.WebserviceDTO.GetPendingFriendObjectDTO;
import com.helio.silentsecret.WebserviceDTO.GetPetConditionDTO;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.adapters.PendingIFriendsAdapter;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.IfriendPendingRequestDTO;
import com.helio.silentsecret.models.PendingIFriendsBean;
import com.helio.silentsecret.utils.ToastUtil;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/2/2016.
 */
public class PendingIFriendsFragment extends Fragment {

    public static PendingIFriendsAdapter mPendingIFriendsAdapter;
    public static ArrayList<PendingIFriendsBean> pendingIFriendsList = new ArrayList<>();
    public static int noOfDays;
    public static ListView pending_iFriends_listview;
    public static ProgressBar progress_bar;
    //private ImageView ic_home;
    public static View view;
    public static String sender = "";
    public static String target = "";
    public static  String requestid = "";
    public static Date date;
    public static String systemDate = "";
    public static String datepen = "";
    public static TextView emptyview = null;
    public static double latitudeSend;
    public static double longitudeSend;
static boolean  is_proceesing = false;
    public static DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aa");
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");


    public static Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mActivity = getActivity();

        view = inflater.inflate(R.layout.pending_ifriends_frag, container, false);

        pending_iFriends_listview = (ListView) view.findViewById(R.id.pending_iFriends_listview);
        emptyview = (TextView) view.findViewById(R.id.emptyrequest);
        progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
       /* ic_home = (ImageView) view.findViewById(R.id.ic_home);
        ic_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });*/

        is_proceesing = false;

        if (ConnectionDetector.isNetworkAvailable(mActivity))
        {
            try
            {
                if(MainActivity.stataicObjectDTO.getiFriendSettingDTO().getRequestduration()!= null && !MainActivity.stataicObjectDTO.getiFriendSettingDTO().getRequestduration().equalsIgnoreCase(""))
                {
                    noOfDays = Integer.parseInt(MainActivity.stataicObjectDTO.getiFriendSettingDTO().getRequestduration());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            /*if (pendingIFriendsList != null && pendingIFriendsList.size() > 0)
            {
                mPendingIFriendsAdapter = new PendingIFriendsAdapter(mActivity, pendingIFriendsList);
                pending_iFriends_listview.setAdapter(mPendingIFriendsAdapter);


            } else
            {

                //callPendingSending();

            }*/

        } else {
            new ToastUtil(mActivity, "Please check your internet connection.");
        }

        return view;
    }




    private static class GetRequestFriendlist extends android.os.AsyncTask<String, String, Bitmap> {

        ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        List<IfriendPendingRequestDTO> userList = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {




                IfriendRequest http = new IfriendRequest(mActivity);
                GetPetConditionDTO getAllfriedDTO = new GetPetConditionDTO(MainActivity.enc_username);
                GetPendingFriendDTO allIfriendDTO = new GetPendingFriendDTO(getAllfriedDTO);
                GetPendingFriendObjectDTO allIfriendobjectDTO = new GetPendingFriendObjectDTO(allIfriendDTO);

                userList =     http.GetRequestIfriendlist(allIfriendobjectDTO);






            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            try
            {
                is_proceesing = false;
                if(userList!= null)
                {
                    if (userList != null && userList.size() > 0)
                    {

                        if (pendingIFriendsList.size() > 0)
                        {
                            pendingIFriendsList.clear();
                        }

                        for (int i = 0; i < userList.size(); i++)
                        {


                            sender = userList.get(i).getSender();
                            target = userList.get(i).getTarget();

                            //requestid = userList.get(i).getRequestid();
                            date  =  userList.get(i).getCreatedAt();




                                    /*try {
                                          Calendar current;
                                          long miliSeconds;


                                        current = Calendar.getInstance();
                                        TimeZone tzCurrent = current.getTimeZone();
                                        int offset = tzCurrent.getRawOffset();
                                        if (tzCurrent.inDaylightTime(new Date())) {
                                            offset = offset + tzCurrent.getDSTSavings();
                                        }
                                        Calendar current1 = Calendar.getInstance();
                                        current1.setTime(date);
                                        miliSeconds = current1.getTimeInMillis();
                                        miliSeconds = miliSeconds + offset;
                                        date = new Date(miliSeconds);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }*/









                            latitudeSend = Double.parseDouble(userList.get(i).getSender_lat());
                            longitudeSend = Double.parseDouble(userList.get(i).getSender_lng());

                           // systemDate = date.toString();
                            systemDate = df.format(date);
                            //datepen = date.toString();
                            datepen = dateFormat.format(date);

                            PendingIFriendsBean bean1 = new PendingIFriendsBean();
                            bean1.setSender(sender);
                            bean1.setLatitudeSend(latitudeSend);
                            bean1.setLongitudeSend(longitudeSend);
                            bean1.setTarget(target);
                            bean1.setSendDate(systemDate);
                            bean1.setPendingDate(systemDate);
                            bean1.setCreatedDate(datepen);
                           // bean1.setRequestid(requestid);

                            if (sender.equalsIgnoreCase(MainActivity.enc_username))
                            {
                                bean1.setRequestedByMe(true);
                                bean1.setRowType(1);//send row item
                                bean1.setPendingIFriendName("Sent Request ");

                                pendingIFriendsList.add(bean1);
                            } else
                            {
                                if (target.equalsIgnoreCase(MainActivity.enc_username)) {

                                    bean1.setRequestedByMe(false);
                                    bean1.setRowType(2);// accept reject row item
                                    bean1.setPendingIFriendName("Pending Request ");
                                    int number = PendingIFriendsAdapter.show(datepen);
                                    if (PendingIFriendsFragment.noOfDays <= number)
                                    {
                                       // setexpire();
                                    } else {
                                        pendingIFriendsList.add(bean1);
                                    }
                                }
                            }
                        }

                        if (pendingIFriendsList != null && pendingIFriendsList.size() > 0)
                        {
                            emptyview.setVisibility(View.GONE);
                            pending_iFriends_listview.setVisibility(View.VISIBLE);
                            progress_bar.setVisibility(View.GONE);

                            mPendingIFriendsAdapter = new PendingIFriendsAdapter(mActivity, pendingIFriendsList);
                            pending_iFriends_listview.setAdapter(mPendingIFriendsAdapter);

                        } else {
                            progress_bar.setVisibility(View.GONE);
                            pending_iFriends_listview.setEmptyView(view.findViewById(R.id.emptyrequest));
                        }
                    } else
                    {

                        pending_iFriends_listview.setVisibility(View.GONE);
                        emptyview.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    pending_iFriends_listview.setVisibility(View.GONE);
                    emptyview.setVisibility(View.VISIBLE);
                }

                progress_bar.setVisibility(View.GONE);
            }
            catch (Exception e)
            {
                progress_bar.setVisibility(View.GONE);
                e.printStackTrace();
            }
            //else
            // Toast.makeText(ct, "No More data found", Toast.LENGTH_SHORT).show();

            // pDialog.dismiss();
        }
    }




    public static void callPendingSending() {

        try {

             if(!is_proceesing)
            {
                is_proceesing = true;

                progress_bar.setVisibility(View.VISIBLE);
                pending_iFriends_listview.setVisibility(View.INVISIBLE);

                new GetRequestFriendlist().execute();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


/*
    private void dateDifference() {

        ParseQuery<ParseObject> maxAllowedquery = ParseQuery.getQuery("IfriendSetting");
        maxAllowedquery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> userList, ParseException e) {

                if (e == null) {
                    if (userList != null && userList.size() > 0) {

                        ParseObject maxAllowedObject = userList.get(0);
                        noOfDays = maxAllowedObject.getInt("requestduration");

                    }

                } else {
                    System.out.println("Something wrong ----> " + e.getMessage());

                }
            }
        });
    }
*/

/*
    public static void setexpire() {
        ParseQuery deleteReq = new ParseQuery("IFriendRequest");
        deleteReq.whereEqualTo("target", ParseUser.getCurrentUser().getUsername());
        deleteReq.whereEqualTo("sender", sender);
        deleteReq.whereEqualTo("status", "waiting");

        deleteReq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    if (objects != null && objects.size() > 0) {

                        for (int i = 0; i < objects.size(); i++) {
                            ParseObject deleteObject = objects.get(i);
                            deleteObject.put("status", "expire");

                            ParseACL acl = new ParseACL();
                            acl.setPublicWriteAccess(true);
                            acl.setPublicReadAccess(true);
                            deleteObject.setACL(acl);

                            deleteObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                }
                            });
                        }
                    }

                } else {
                    System.out.println("Something wrong ----> " + e.getMessage());

                }
            }


        });
    }
*/


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}