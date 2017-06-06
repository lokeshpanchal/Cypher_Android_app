package com.helio.cypher.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.helio.cypher.EncryptionDecryption.CryptLib;
import com.helio.cypher.R;
import com.helio.cypher.adapters.ReferFriendAdapter;
import com.helio.cypher.appCounsellingDTO.CheckUserSessionDataDTO;
import com.helio.cypher.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.cypher.appCounsellingDTO.DeleteInviteDataDTO;
import com.helio.cypher.appCounsellingDTO.FinalObjectDTO;
import com.helio.cypher.appCounsellingDTO.SendInviteDataDTO;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.creator.ObjectMaker;
import com.helio.cypher.dialogs.ProgressDialog;
import com.helio.cypher.models.Invite;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.Preference;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


public class ReferFriendActivity extends BaseActivity {

    private SwipeMenuListView mListView;
    private ReferFriendAdapter adapter;
    private List<Invite> mInviteList = new ArrayList<>();
    private static final int PICK_CONTACT = 13;
    private static final int SMS_RESULT = 12;
    Context ct = null;
    SendInviteDataDTO sendInviteDataDTO = null;
    String remove_unique_id = "";
    private String name;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);
        ct = this;
        startTracking(getString(R.string.invite_android));

        mListView = (SwipeMenuListView) findViewById(R.id.refer_list);
        adapter = new ReferFriendAdapter(LayoutInflater.from(this), mInviteList, this);
        mListView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem message = new SwipeMenuItem(
                        getApplicationContext());
                message.setWidth((int) getResources().getDimension(R.dimen.refer_item_width));
                message.setIcon(R.drawable.ic_message);
                message.setBackground(R.color.refer_item);
                menu.addMenuItem(message);
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setWidth((int) getResources().getDimension(R.dimen.refer_item_width));
                deleteItem.setIcon(R.drawable.ic_close_user);
                deleteItem.setBackground(R.color.refer_item);
                menu.addMenuItem(deleteItem);
            }
        };
        mListView.setMenuCreator(creator);
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        message(mInviteList.get(position).getPhoneNo());
                        break;
                    case 1:
                        remove(position);
                        break;
                }
                return false;
            }
        });

        findViewById(R.id.refer_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.refer_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickContact();
            }
        });

        findViewById(R.id.get_support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReferFriendActivity.this, SupportActivity.class));
            }
        });
        new GetInvite().execute();
        //queryInvite();
        pickContact();
    }

    private void pickContact() {
        try {
            Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
            pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(pickContactIntent, PICK_CONTACT);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_CONTACT:
                if (resultCode == Activity.RESULT_OK)
                {
                    Uri contactUri = data.getData();
                    String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
                    Cursor cursor = getContentResolver()
                            .query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();
                    int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    number = cursor.getString(column);
                    column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    name = cursor.getString(column);
                    message(number);
                }
                break;

            case SMS_RESULT:

                if (checkList(name, number) && !Preference.getSharedPreferences().contains(name) && !Preference.getSharedPreferences().getString(name, ObjectMaker.EMPTY).equals(number)) {

                    try
                    {
                        long mseconds = System.currentTimeMillis();
                        String unique_id = MainActivity.enc_username + mseconds;
                        sendInviteDataDTO = new SendInviteDataDTO(MainActivity.enc_username, CryptLib.encrypt(number),CryptLib.encrypt(name),unique_id) ;
                    new SendinviteUser().execute();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

    private boolean checkList(String name, String number) {
        for (Invite item : mInviteList)
            if (item.getName().equals(name) && item.getPhoneNo().equals(number))
                return false;
        return true;
    }

    private void queryInvite() {
        // showProgress();

       /* new InviteLoader(new InviteCallback() {
            @Override
            public void onUpdate(List<Invite> data) {
                hideProgress();
                mInviteList.clear();
                mInviteList.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }).execute();*/
    }

    private void message(String number) {
        String message = Html.fromHtml(getString(R.string.invite_message)).toString();
        Uri uri = Uri.parse("smsto:" + number);
        Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
        smsSIntent.putExtra("sms_body", message);
        try {
            startActivityForResult(smsSIntent, SMS_RESULT);
        } catch (Exception ex) {
        }
    }

    private void remove(int position)
    {
        remove_unique_id = mInviteList.get(position).getUnique_id();
        new DeleteInvite().execute();
      /*  showProgress();
        mInviteList.get(position).getObject().deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                queryInvite();
            }
        });*/
    }

    private void create(final String name, final String number)
    {
       /* final ParseUser user = ParseUser.getCurrentUser();
        showProgress();
        ObjectMaker.formInvite(ParseUser.getCurrentUser(), number, name).saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                Preference.getSharedPreferences().edit().putString(name, number).commit();

                user.put(Constants.USER_INVITES, (user.get(Constants.USER_INVITES) != null
                        ? user.getInt(Constants.USER_INVITES) : 0) + 1);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        int invites = user.get(Constants.USER_INVITES) != null
                                ? user.getInt(Constants.USER_INVITES) : 0;

                        if (user.get(Constants.USER_SCHOOLS) != null && user.getList(Constants.USER_SCHOOLS).size() > 0) {
                            int schoolInvites = user.getList(Constants.USER_SCHOOLS).size() * 30;
                            if (invites > schoolInvites) {
                                invites = invites - schoolInvites;
                            }
                        }

                       // showMessageIfApplied(invites);
                    }
                });

                queryInvite();
            }
        });*/
    }

    private void showMessageIfApplied(int i) {
        int status = i % Constants.INVITE_THRESHOLD;
       /* if (status == 0)
        {
            String text = getString(R.string.u_have_unlocked_one);
            PushBroadcastReceiver.generateActionPush(this, text, null, null, null);
        }*/
    }

    public void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.invite_progress_bg_iv), findViewById(R.id.invite_progress_pb));
    }

    public void hideProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.invite_progress_bg_iv), findViewById(R.id.invite_progress_pb));
    }


    private class GetInvite extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }


        protected Bitmap doInBackground(String... args) {
            try {
                IfriendRequest http = new IfriendRequest(ct);
                CheckUserSessionDataDTO findbyNameDTO = new CheckUserSessionDataDTO(MainActivity.enc_username);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(findbyNameDTO,"getInvite");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                hideProgress();
                String status = "", name = "", phone_number = "", unique_id = "", Session_left = "";



                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true"))
                    {
                        JSONArray json_array = null;
                        if (jsonObject.has("data"))
                            json_array = jsonObject.getJSONArray("data");

                        if(json_array!= null && json_array.length()>0)
                        {
                            mInviteList.clear();

                            for (int i = 0; i < json_array.length(); i++)
                            {

                                JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                                if (UserInfoobj.has("name"))
                                    name = UserInfoobj.getString("name");

                                if (UserInfoobj.has("phone_number"))
                                    phone_number = UserInfoobj.getString("phone_number");

                                if (UserInfoobj.has("unique_id"))
                                    unique_id = UserInfoobj.getString("unique_id");

                                Invite data  = new Invite();
                                data.setName(CryptLib.decrypt(name));
                                data.setPhoneNo(CryptLib.decrypt(phone_number));
                                data.setUnique_id(unique_id);
                                mInviteList.add(data);




                            }

                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            pickContact();
                        }

                    } else
                    {
                        pickContact();
                    }
                }

            } catch (Exception e) {
                hideProgress();
                e.printStackTrace();
            }

        }
    }



    private class SendinviteUser extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }


        protected Bitmap doInBackground(String... args) {
            try {
                IfriendRequest http = new IfriendRequest(ct);
            //    CheckUserSessionDataDTO findbyNameDTO = new CheckUserSessionDataDTO(MainActivity.enc_username);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(sendInviteDataDTO,"insertInvite");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                hideProgress();
                String status = "", name = "", phone_number = "", agent_unq_id = "", Session_left = "";



                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true"))
                    {
                        new GetInvite().execute();

                    }
                }

            } catch (Exception e) {
                hideProgress();
                e.printStackTrace();
            }

        }
    }



    private class DeleteInvite extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }


        protected Bitmap doInBackground(String... args) {
            try {
                IfriendRequest http = new IfriendRequest(ct);
                    DeleteInviteDataDTO findbyNameDTO = new DeleteInviteDataDTO(remove_unique_id);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(findbyNameDTO,"deleteInvite");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                hideProgress();
                String status = "", name = "", phone_number = "", agent_unq_id = "", Session_left = "";



                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true"))
                    {
                        new GetInvite().execute();

                    }
                }

            } catch (Exception e) {
                hideProgress();
                e.printStackTrace();
            }

        }
    }

}
