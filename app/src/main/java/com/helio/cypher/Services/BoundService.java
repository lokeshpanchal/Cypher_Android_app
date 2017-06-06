package com.helio.cypher.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.helio.cypher.EncryptionDecryption.CryptLib;
import com.helio.cypher.WebserviceDTO.GetUnreadMessageDataDTO;
import com.helio.cypher.WebserviceDTO.GetUnreadMessageObjectDTO;
import com.helio.cypher.WebserviceDTO.GetUnreadtChatMessageDTO;
import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.Constants;

/**
 * Created by ABC on 9/12/2016.
 */
public class BoundService extends Service {

    int chatcount1 = 0;
    Context ct = null;

    TextView refreshcount = null;

    static String friendname;

    @Override
    public void onCreate() {
        super.onCreate();
        try
        {
            refreshcount = new TextView(this);
            ct = this;
            new GetunreadMessage().execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private  class GetunreadMessage extends android.os.AsyncTask<String, String, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);
                String enc_username = "";
                try {
                    enc_username = CryptLib.encrypt(AppSession.getValue(ct, Constants.USER_NAME));
                } catch (Exception e) {}


                    GetUnreadMessageDataDTO getUnreadMessageDataDTO = new GetUnreadMessageDataDTO(enc_username,"");

                GetUnreadMessageObjectDTO getUnreadMessageObjectDTO = new GetUnreadMessageObjectDTO(new GetUnreadtChatMessageDTO(getUnreadMessageDataDTO,"getUnreadMesssage", Constants.ENCRYPT_IFRIEND_CHAT_TABLE, Constants.ENCRYPT_FIND_METHOD));

                MainActivity.chatcount = http.GetUnreadmessage(getUnreadMessageObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            try
            {
                if(MainActivity.is_running )

                {
                    refreshcount.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new GetunreadMessage().execute();
                        }
                    }, 5000);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }


}
