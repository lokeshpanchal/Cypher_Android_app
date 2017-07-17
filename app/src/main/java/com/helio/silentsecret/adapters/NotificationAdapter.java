package com.helio.silentsecret.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.SetNotificationReadDataDTO;
import com.helio.silentsecret.activities.ActionSecretActivity;
import com.helio.silentsecret.activities.MySecretsActivity;
import com.helio.silentsecret.activities.NotificationActivity;
import com.helio.silentsecret.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.silentsecret.appCounsellingDTO.FinalObjectDTO;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.creator.ObjectMaker;
import com.helio.silentsecret.models.Notification;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.IOJsonEditor;
import com.helio.silentsecret.utils.Preference;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Notification> mDataList;
    private Context mContext;
    private List<String> mReadArray = new ArrayList<>();
    SetNotificationReadDataDTO setNotificationReadDataDTO = null;

    public NotificationAdapter(LayoutInflater inflater, List<Notification> list, Context context) {
        this.inflater = inflater;
        mDataList = list;
        mContext = context;
        mReadArray.clear();
        mReadArray.addAll(IOJsonEditor.makeArray(IOJsonEditor.stringToJSONArray(Preference.getNotificationCheckout())));
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Notification getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.notification_item, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.notification_item_text);
            holder.checkout = (ImageView) convertView.findViewById(R.id.notification_check_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Notification item = mDataList.get(position);

        holder.text.setText(item.getText() != null ? item.getText() : ObjectMaker.EMPTY);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(convertView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    item.setRead_status("true");
                    holder.checkout.setImageResource(R.drawable.fill_notification_shape);
                    if (setNotificationReadDataDTO != null)
                        setNotificationReadDataDTO = null;
                    setNotificationReadDataDTO = new SetNotificationReadDataDTO(item.getObjectId());
                    // readItem(item.getObjectId(), holder);
                    if (item.getType().equals(Constants.NOTIFICATION_TYPE_HEART) || item.getType().equals(Constants.NOTIFICATION_TYPE_HUG) || item.getType().equals(Constants.NOTIFICATION_TYPE_ME2)) {
                        runAccessSecret(item);
                    } else if (item.getType().equals(Constants.PUSH_ACTION_COMMENT)) {
                        runComments(item);
                    } else if (item.getType().equals(Constants.PUSH_BACKEND)) {
                        runMine();
                    } else if (item.getType().equals(Constants.PUSH_VERIFY)) {
                        ((NotificationActivity) mContext).replacePrompt();
                    }
                    new SetNotificationRead().execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        if (item.getRead_status() != null && item.getRead_status().equalsIgnoreCase("true")) {
            holder.checkout.setImageResource(R.drawable.fill_notification_shape);
        } else {
            holder.checkout.setImageResource(R.drawable.empty_notification_shape);
        }

        return convertView;
    }

    private void readItem(String objectId, ViewHolder holder) {
        JSONArray array = IOJsonEditor.stringToJSONArray(Preference.getNotificationCheckout());

        if (IOJsonEditor.makeArray(array).contains(objectId))
            return;

        array.put(objectId);
        Preference.saveNotificationList(array);
        mReadArray.add(objectId);
        holder.checkout.setImageResource(R.drawable.fill_notification_shape);
    }

    private void runMine() {
        try {
            Intent intent = new Intent(mContext, MySecretsActivity.class);
            intent.putExtra(Constants.FROM_NOTIFICATIONS_STATE, true);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runComments(Notification item) {
        try {
            ((NotificationActivity) mContext).showProgress();
            /*new AccessSecretLoader(new AccessSecretCallback() {
                @Override
                public void onReceive(Secret result) {
                    ((NotificationActivity) mContext).hideProgress();

                    if (result == null)
                        return;
                    Intent comments = new Intent(mContext, CommentSecretActivity.class);
                    Constants.secretComment = result;
                    mContext.startActivity(comments);
                }
            }).execute(item.getObjectId());*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void runAccessSecret(Notification item) {
        try {

            Intent secret = new Intent(mContext, ActionSecretActivity.class);
            secret.putExtra(Constants.PUSH_SECRET_ID, item.getSecret_id());
            mContext.startActivity(secret);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class ViewHolder {
        TextView text;
        ImageView checkout;
    }


    private class SetNotificationRead extends android.os.AsyncTask<String, String, Bitmap> {

        String data = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(mContext);

                CommonRequestTypeDTO findNameDTO = new CommonRequestTypeDTO(setNotificationReadDataDTO, "markReadNotification");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(findNameDTO);
                data = http.SetNotificationRead(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }


}