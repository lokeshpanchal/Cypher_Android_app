package com.helio.cypher.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.helio.cypher.R;
import com.helio.cypher.WebserviceDTO.DeniedFriendRequestDTO;
import com.helio.cypher.WebserviceDTO.GetUnreadMessageDataDTO;
import com.helio.cypher.WebserviceDTO.GetUnreadMessageObjectDTO;
import com.helio.cypher.WebserviceDTO.GetUnreadtChatMessageDTO;
import com.helio.cypher.WebserviceDTO.IfriendaDenyConditionDataDTO;
import com.helio.cypher.activities.ChatDetailsScreen;
import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.adapters.IFriendsListAdapter;
import com.helio.cypher.connection.ConnectionDetector;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.models.IFriendsBean;
import com.helio.cypher.models.IfriendListDTO;
import com.helio.cypher.models.IfriendRequestDTO;
import com.helio.cypher.models.IfriendRequestObjectDTO;
import com.helio.cypher.models.IfriendRequestRejectDTO;
import com.helio.cypher.models.IfriendRequestRejectObjectDTO;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.ToastUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/2/2016.
 */
public class IFriendsFragment extends Fragment {

    public static IFriendsListAdapter mIFriendsListAdapter;
    public static ArrayList<IFriendsBean> iFriendList = new ArrayList<>();
    public static ListView iFriends_listview;
   // private ImageView ic_home;
    public static View view;


    // IfriendRemovehDTO ifriendRemovehDTO = null;
    DeniedFriendRequestDTO deniedFriendRequestDTO;
    static boolean is_proceesing = false;

    public static TextView Emptyview = null;
    private static int temp = 0;
    public static String currentuser = "";
    public static String requestuser = "";
    public static String requestId = "";

    public static ProgressBar progress_bar_friends;

    static int selected_index = 0;


    public static Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mActivity = getActivity();
        view = inflater.inflate(R.layout.ifriends_list_frag, container, false);
        iFriends_listview = (ListView) view.findViewById(R.id.iFriends_listview);
        progress_bar_friends = (ProgressBar) view.findViewById(R.id.progress_bar_friends);
        Emptyview = (TextView) view.findViewById(R.id.emptyfriends);
        iFriends_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                is_proceesing = false;
                unfollowAlert(position);

                return true;
            }
        });


        try {

            if (ConnectionDetector.isNetworkAvailable(mActivity)) {
                callWS();

            } else {
                new ToastUtil(mActivity, "Please check your internet connection.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        iFriends_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int temp = position + 1;
                ChatDetailsScreen.friendname = "iFriend" + temp;
                IFriendsBean bean = iFriendList.get(position);
                iFriendList.get(position).setUnreadcount(0);

                Intent mIntentToChatDetails = new Intent(getActivity(), ChatDetailsScreen.class);


                mIntentToChatDetails.putExtra("NAME", bean.getiFriendName() + temp);
                mIntentToChatDetails.putExtra("NAMEFRIEND", bean.getDummyName());
                startActivity(mIntentToChatDetails);
                mActivity.finish();


            }
        });


        return view;
    }


    @Override
    public void onPause() {


        super.onPause();
    }

    static List<IfriendListDTO> userList = null;

    private static class GetFriendlist extends android.os.AsyncTask<String, String, Bitmap> {

        ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {
                if(userList!= null)
                    userList.clear();
                IfriendRequest http = new IfriendRequest(mActivity);
                IfriendaDenyConditionDataDTO getAllfriedDTO = new IfriendaDenyConditionDataDTO(MainActivity.enc_username);
                IfriendRequestDTO ifriendRequestDTO = new IfriendRequestDTO(getAllfriedDTO, Constants.ENCRYPT_IFRIEND_CHAT_TABLE, Constants.ENCRYPT_FIND_METHOD);
                IfriendRequestObjectDTO ifriendRequestObjectDTO = new IfriendRequestObjectDTO(ifriendRequestDTO);
                userList = http.GetIfriendlist(ifriendRequestObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {


                if (userList != null)
                {
                    if (userList != null && userList.size() > 0)
                    {

                        new GetunreadMessage().execute();

                    } else {
                        is_proceesing = false;
                        progress_bar_friends.setVisibility(View.GONE);
                        iFriends_listview.setVisibility(View.GONE);
                        Emptyview.setVisibility(View.VISIBLE);

                    }

                } else {
                    progress_bar_friends.setVisibility(View.GONE);
                    is_proceesing = false;
                    iFriends_listview.setVisibility(View.GONE);
                    Emptyview.setVisibility(View.VISIBLE);
                }


            } catch (Exception e) {
                progress_bar_friends.setVisibility(View.GONE);
                e.printStackTrace();
            }

        }
    }


    private static class GetunreadMessage extends android.os.AsyncTask<String, String, Bitmap> {

        ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;

        //  List<IfriendListDTO> userList = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mActivity);

                if (iFriendList.size() > 0) {
                    iFriendList.clear();
                }

                for (int i = 0; i < userList.size(); i++) {

                    currentuser = userList.get(i).getUsername();
                    requestuser = userList.get(i).getFriend();
                    requestId = userList.get(i).getRequestid();
                    int profilepic = userList.get(i).getProfilepic();

                    final IFriendsBean bean = new IFriendsBean();
                    bean.setUsername(currentuser);
                    bean.setFriend(requestuser);
                    bean.setProfilepic(profilepic);
                    bean.setMe(true);
                    bean.setiFriendName("iFriend ");
                    bean.setDummyName(requestuser);
                    GetUnreadMessageDataDTO getUnreadMessageDataDTO = new GetUnreadMessageDataDTO(MainActivity.enc_username, requestuser);
                    GetUnreadMessageObjectDTO getUnreadMessageObjectDTO = new GetUnreadMessageObjectDTO(new GetUnreadtChatMessageDTO(getUnreadMessageDataDTO, "getUnreadMesssage", Constants.ENCRYPT_IFRIEND_CHAT_TABLE, Constants.ENCRYPT_FIND_METHOD));
                    bean.setUnreadcount(http.GetUnreadmessage(getUnreadMessageObjectDTO));
                    iFriendList.add(bean);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            try {
                is_proceesing = false;
                if (iFriendList.size() > 0)
                {

                    Emptyview.setVisibility(View.GONE);
                    iFriends_listview.setVisibility(View.VISIBLE);
                    mIFriendsListAdapter = new IFriendsListAdapter(mActivity, iFriendList);
                    GlimpseFragment.count = iFriendList.size();
                    iFriends_listview.setAdapter(mIFriendsListAdapter);
                } else {

                    iFriends_listview.setVisibility(View.GONE);
                    Emptyview.setVisibility(View.VISIBLE);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            progress_bar_friends.setVisibility(View.GONE);

        }
    }


    public static void callWS() {

        try {
            if (!is_proceesing) {
                is_proceesing = true;

                progress_bar_friends.setVisibility(View.VISIBLE);
                iFriends_listview.setVisibility(View.INVISIBLE);
                new GetFriendlist().execute();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void unfollowAlert(final int position) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Do you want to unfriend your iFriend?");

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int id) {

                        progress_bar_friends.setVisibility(View.VISIBLE);

                        IFriendsBean bean = iFriendList.get(position);
                        String mCUrrentUser = bean.getUsername();
                        String mREqUser = bean.getFriend();

                        deniedFriendRequestDTO = new DeniedFriendRequestDTO(mREqUser, MainActivity.enc_username);
                        new IFirendRemoveRequest().execute();


                               /* if (mCUrrentUser.equalsIgnoreCase(ParseUser.getCurrentUser().getUsername()) || mREqUser.equalsIgnoreCase(ParseUser.getCurrentUser().getUsername()))
                                {

                                    currentfriendRemove(mCUrrentUser, mREqUser);

                                }*/


                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();
    }

/*

    public static void currentfriendRemove(final String mCUrrentFriend, final String mREqUser) {

        ParseQuery<ParseObject> deleteQ1 = ParseQuery.getQuery(MainActivity.friend_table_name);//DONE
        deleteQ1.whereEqualTo("username", mCUrrentFriend);
        deleteQ1.whereEqualTo("friend", mREqUser);

        ParseQuery<ParseObject> deleteQ2 = ParseQuery.getQuery(MainActivity.friend_table_name);//DONE
        deleteQ2.whereEqualTo("username", mREqUser);
        deleteQ2.whereEqualTo("friend", mCUrrentFriend);

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(deleteQ1);
        queries.add(deleteQ2);

        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);

        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> userList, ParseException e) {


                if (e == null) {
                    if (userList != null && userList.size() > 0) {

                        final int sizelist = userList.size();
                        for (int i = 0; i < sizelist; i++) {
                            ParseObject currentFriendUnfriend = userList.get(i);

                            ParseACL currentFriendacl = new ParseACL();
                            currentFriendacl.setPublicWriteAccess(true);
                            currentFriendacl.setPublicReadAccess(true);
                            currentFriendUnfriend.setACL(currentFriendacl);

                            currentFriendUnfriend.deleteInBackground(new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        temp++;
                                        if (temp >= sizelist) {
                                            temp = 0;
                                            callWS();
                                        }


                                        System.out.println("Successfully Unfriend");
                                    } else {

                                        System.out.println("Warning delete: " + e.getMessage());

                                    }
                                }
                            });

                        }


                    }


                } else {

                    System.out.println("Something wrong ----> " + e.getMessage());
                }
            }
        });


        ParseQuery<ParseObject> currentFriendRequest = ParseQuery.getQuery("IFriendRequest");
        currentFriendRequest.whereEqualTo("status", "approved");
        currentFriendRequest.whereEqualTo("sender", mREqUser);
        currentFriendRequest.whereEqualTo("target", mCUrrentFriend);
        currentFriendRequest.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> userList, ParseException e) {


                if (e == null) {
                    if (userList != null && userList.size() > 0) {


                        ParseObject currentFriendRequestobject = userList.get(0);
                        currentFriendRequestobject.put("status", "unfriend");
                        currentFriendRequestobject.put("unfriend_notification", "false");
                        currentFriendRequestobject.put("unfriendBy", ParseUser.getCurrentUser().getUsername());

                        ParseACL currentFriendRequestacl = new ParseACL();
                        currentFriendRequestacl.setPublicWriteAccess(true);
                        currentFriendRequestacl.setPublicReadAccess(true);
                        currentFriendRequestobject.setACL(currentFriendRequestacl);

                        currentFriendRequestobject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e == null) {
                                    System.out.println("Successfully Unfriend Request");

                                } else {

                                    System.out.println("Warning objectnearby: " + e.getMessage());

                                }
                            }
                        });

                    }

                } else {
                    System.out.println("Something wrong ----> " + e.getMessage());
                }
            }
        });


    }

*/
/*
    Runnable referesh_list = new Runnable() {
        @Override
        public void run() {

            try {

                Emptyview.removeCallbacks(referesh_list);
                Emptyview.postDelayed(referesh_list, 5000);
                if (iFriendList != null && iFriendList.size() >= 0) {

                    mIFriendsListAdapter = new IFriendsListAdapter(mActivity, iFriendList);
                    GlimpseFragment.count = iFriendList.size();
                    iFriends_listview.setAdapter(mIFriendsListAdapter);

                    if (iFriendList.size() == 0) {
                        iFriends_listview.setEmptyView(Emptyview);
                    }
                } else {

                    if (Emptyview != null)
                        iFriends_listview.setEmptyView(Emptyview);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };*/

    public static int getUnreadCount(String freindnaem) {

        int chatcount = 0;
       /* ParseQuery chatUnreadCount = new ParseQuery("Chat");
        chatUnreadCount.whereEqualTo("sender", freindnaem);
        chatUnreadCount.whereEqualTo("receiver", ParseUser.getCurrentUser().getUsername());
        chatUnreadCount.whereEqualTo("read", "false");

        try {
            chatcount = chatUnreadCount.count();
        } catch (ParseException e) {
            e.printStackTrace();
        }
*/
        return chatcount;
    }


    private class IFirendRemoveRequest extends android.os.AsyncTask<String, String, Bitmap> {

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
                IfriendRequestRejectDTO ifriendRequestRejectDTO = new IfriendRequestRejectDTO(deniedFriendRequestDTO, Constants.ENCRYPT_IFRIEND_CHAT_TABLE, Constants.ENCRYPT_FIND_METHOD, "Unfriend");
                IfriendRequestRejectObjectDTO ifriendRequestRejectObjectDTO = new IfriendRequestRejectObjectDTO(ifriendRequestRejectDTO);
                response = http.Rejectifriend(ifriendRequestRejectObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                progress_bar_friends.setVisibility(View.GONE);
                callWS();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
