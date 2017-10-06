package com.helio.silentsecret.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.adapters.RoomListAdapter;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.fragments.Create_rooms_fragement;
import com.helio.silentsecret.models.RoomsInfoDTO;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RoomsActivity extends BaseActivity implements View.OnClickListener {




    TextView back_iv = null, create_room_back = null;
    RelativeLayout progress_bar = null;
    private File outPutFile = null;
    boolean is_from_forammting = false;
    Context ct = this;
    LinearLayout rooms_home = null, bottombar = null;
    LinearLayout create_room = null, search_room = null, my_rooms = null;
    TextView rooms_home_bar = null, create_room_bar = null, search_room_bar = null, my_rooms_bar = null;
    RelativeLayout  my_room_layout = null;
    TextView create_room_button = null, search_room_button = null, private_room = null;

 EditText edt_search = null;
    Bitmap room_icon = null;
    Bitmap topic_bitmap = null;
    LinearLayout  search_room_layout = null;
    ListView my_room_list = null, room_list = null;
    TextView search_room_text = null, no_result_found = null , myroom_no_result_found = null;
    List<RoomsInfoDTO> roomsInfoDTOs = new ArrayList<>();

    String search_string = "";
    boolean is_first_search = false;
    String layout_format_state = "dot";
    List<TextView> format_textv_list = new ArrayList<>();
    TextView done_buton = null;
    List<RoomsInfoDTO> search_result_list = new ArrayList<>();
    String first_name = "";
    TextView submit_fname = null;
    FrameLayout content_fram = null;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        back_iv = (TextView) findViewById(R.id.back_iv);

        search_room_text = (TextView) findViewById(R.id.search_room_text);
        no_result_found = (TextView) findViewById(R.id.no_result_found);
        myroom_no_result_found = (TextView) findViewById(R.id.myroom_no_result_found);

        bottombar = (LinearLayout) findViewById(R.id.bottombar);
        content_fram = (FrameLayout) findViewById(R.id.content_frame);
        rooms_home = (LinearLayout) findViewById(R.id.rooms_home);
        create_room = (LinearLayout) findViewById(R.id.create_room);
        search_room = (LinearLayout) findViewById(R.id.search_room);
        my_rooms = (LinearLayout) findViewById(R.id.my_rooms);

        search_room_layout = (LinearLayout) findViewById(R.id.search_room_layout);
        edt_search = (EditText) findViewById(R.id.edt_search);

        my_room_layout = (RelativeLayout) findViewById(R.id.my_room_layout);
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);

        rooms_home_bar = (TextView) findViewById(R.id.rooms_home_bar);
        create_room_bar = (TextView) findViewById(R.id.create_room_bar);
        search_room_bar = (TextView) findViewById(R.id.search_room_bar);
        my_rooms_bar = (TextView) findViewById(R.id.my_rooms_bar);
        search_room_button = (TextView) findViewById(R.id.search_room_button);
        create_room_button = (TextView) findViewById(R.id.create_room_button);
        private_room = (TextView) findViewById(R.id.private_room);
        create_room_back = (TextView) findViewById(R.id.create_room_back);
        my_room_list = (ListView) findViewById(R.id.my_room_list);
        room_list = (ListView) findViewById(R.id.room_list);
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtil.hideKeyBoard(edt_search, ct);
                finish();
            }
        });

        roomsInfoDTOs.clear();

        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    Show_search_result();

                    return true;
                }
                return false;
            }
        });

        progress_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        my_room_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    RoomDetailActivity.roomsInfoDTO = roomsInfoDTOs.get(i);
                    Intent intent = new Intent(ct, RoomDetailActivity.class);
                    startActivity(intent);
                } catch (Exception e)

                {
                    e.printStackTrace();

                }
            }
        });

        room_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    RoomDetailActivity.roomsInfoDTO = roomsInfoDTOs.get(i);
                    Intent intent = new Intent(ct, RoomDetailActivity.class);
                    startActivity(intent);
                } catch (Exception e)

                {
                    e.printStackTrace();

                }
            }
        });


        search_room_button.setOnClickListener(this);
        rooms_home.setOnClickListener(this);
        create_room.setOnClickListener(this);
        search_room.setOnClickListener(this);
        my_rooms.setOnClickListener(this);

        create_room_button.setOnClickListener(this);


        private_room.setOnClickListener(this);

        search_room_text.setOnClickListener(this);


        final View activityRootView = findViewById(R.id.app_root);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 200) {
                    bottombar.setVisibility(View.GONE);
                } else {
                    bottombar.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rooms_home:

                content_fram.setVisibility(View.GONE);
                my_room_layout.setVisibility(View.GONE);
                create_room_bar.setVisibility(View.GONE);
                search_room_bar.setVisibility(View.GONE);
                my_rooms_bar.setVisibility(View.GONE);
                rooms_home_bar.setVisibility(View.VISIBLE);
                search_room_layout.setVisibility(View.GONE);
                break;
            case R.id.create_room:

                my_room_layout.setVisibility(View.GONE);
                search_room_bar.setVisibility(View.GONE);
                my_rooms_bar.setVisibility(View.GONE);
                rooms_home_bar.setVisibility(View.GONE);
                create_room_bar.setVisibility(View.VISIBLE);
                search_room_layout.setVisibility(View.GONE);

                replaceRate();
                break;

            case R.id.private_room:
                startActivity(new Intent(this, PrivateRoomsActivity.class));
                break;
            case R.id.search_room_text:
                is_first_search = true;
                Show_search_result();
                break;

            case R.id.search_room:
            case R.id.search_room_button:
                content_fram.setVisibility(View.GONE);
                my_room_layout.setVisibility(View.GONE);
                create_room_bar.setVisibility(View.GONE);
                my_rooms_bar.setVisibility(View.GONE);
                rooms_home_bar.setVisibility(View.GONE);
                search_room_bar.setVisibility(View.VISIBLE);
                search_room_layout.setVisibility(View.VISIBLE);

                room_list.setVisibility(View.VISIBLE);
                no_result_found.setVisibility(View.GONE);
                new GetRoomAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                break;
            case R.id.my_rooms:

                search_room_layout.setVisibility(View.GONE);
                create_room_bar.setVisibility(View.GONE);
                search_room_bar.setVisibility(View.GONE);
                rooms_home_bar.setVisibility(View.GONE);
                my_rooms_bar.setVisibility(View.VISIBLE);


                new GetmYRoomAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                break;
            case R.id.create_room_button:

                search_room_layout.setVisibility(View.GONE);
                my_room_layout.setVisibility(View.GONE);
                search_room_bar.setVisibility(View.GONE);
                my_rooms_bar.setVisibility(View.GONE);
                rooms_home_bar.setVisibility(View.GONE);
                create_room_bar.setVisibility(View.VISIBLE);
                replaceRate();
                break;






        }
    }

    private void replaceRate() {
        content_fram.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            transaction.replace(R.id.content_frame, Create_rooms_fragement.instance());
        } catch (IllegalStateException e) {
        }
       // transaction.addToBackStack(null);
        transaction.commit();
    }

    private void Show_search_result() {
        try {
            search_result_list.clear();
            search_string = "";
            search_string = edt_search.getText().toString();


            if (search_string != null && !search_string.equalsIgnoreCase(""))
            {
                search_string = search_string.trim().toLowerCase();
                search_string = CryptLib.encrypt(search_string);
                search_string = search_string.replace("+", "");
                is_first_search = false;
                new SearchRoomAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else if(is_first_search == false)
            {
                is_first_search = true;
                new GetRoomAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
            else
            {
                Toast.makeText(ct,"Please enter search text",Toast.LENGTH_SHORT).show();
            }


            KeyboardUtil.hideKeyBoard(edt_search, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    boolean is_from_camera = false;

    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private static final int CAMERA_CROPING_CODE = 4;

    private AlertDialog showOptionsDig() {
        final String[] items = new String[]{"From Camera", "From Gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ct,
                android.R.layout.select_dialog_item, items);

        try {
            String path1 = CommonFunction.getIntenalSDCardPath();
            if (path1 != null) {
                outPutFile = new File(path1 + "/", 1
                        + ".jpg");
            } else {
                outPutFile = new File(Environment.getExternalStorageDirectory() + File.separator +
                        "Android" + File.separator + "data" + File.separator + "com.bahamainrenter" + File.separator +
                        "bahamas/" + 1 + ".jpg");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                try {
                    if (item == 0) {


                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);
                        mImageCaptureUri = Uri.fromFile(outPutFile);
                        try {
                            is_from_camera = true;
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    mImageCaptureUri);
                            intent.putExtra("return-data", true);
                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.cancel();


                    } else {
                        is_from_camera = false;
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(
                                Intent.createChooser(intent, "Complete action using"),
                                PICK_FROM_FILE);

/*						Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, PICK_FROM_FILE);*/

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        final AlertDialog dialog = builder.create();
        return dialog;
    }




    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    protected int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }




public void GetMyrooms()
{
    content_fram.setVisibility(View.GONE);
    new GetmYRoomAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

}


    private class GetRoomAsync extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest httpRequest = new IfriendRequest(ct);

                String age = AppSession.getValue(ct, Constants.USER_AGE);


                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();

                mJsonObjectSub.put("user_age", age);


                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "publicRooms");

                main_object.put("requestData", requestdata);
                try {
                    roomsInfoDTOs = httpRequest.GetRooms(main_object.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                progress_bar.setVisibility(View.GONE);

                try {


                    if (roomsInfoDTOs != null && roomsInfoDTOs.size() > 0)
                    {
                        no_result_found.setVisibility(View.GONE);


                    final     RoomListAdapter roomListAdapter = new RoomListAdapter(ct, roomsInfoDTOs);
                        room_list.setAdapter(roomListAdapter);


                        room_list.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                roomListAdapter.notifyDataSetChanged();


                                room_list.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        room_list.setVisibility(View.VISIBLE);
                                        progress_bar.setVisibility(View.GONE);
                                    }
                                },1500);


                            }
                        },1000);


                    } else {
                        progress_bar.setVisibility(View.GONE);
                        no_result_found.setVisibility(View.VISIBLE);
                        room_list.setVisibility(View.GONE);
                    }
                    search_room_layout.setVisibility(View.VISIBLE);
                    /*
                    if (roomsInfoDTOs != null && roomsInfoDTOs.size() > 0) {
                        RoomListAdapter roomListAdapter = new RoomListAdapter(ct, roomsInfoDTOs);
                        room_list.setAdapter(roomListAdapter);
                        search_room_layout.setVisibility(View.VISIBLE);
                    } else {
                        search_room_layout.setVisibility(View.VISIBLE);
                        new ToastUtil(ct, "No rooms found");
                    }*/
                } catch (Exception e) {
                    progress_bar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private class SearchRoomAsync extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest httpRequest = new IfriendRequest(ct);

                String age = AppSession.getValue(ct, Constants.USER_AGE);


                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();

                mJsonObjectSub.put("user_age", age);
                mJsonObjectSub.put("clsearchrmnm", search_string);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "searchRooms");

                main_object.put("requestData", requestdata);
                try {
                    roomsInfoDTOs = httpRequest.GetRooms(main_object.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {


                try {
                    if (roomsInfoDTOs != null && roomsInfoDTOs.size() > 0)
                    {
                        search_string = "";

                      final  RoomListAdapter roomListAdapter = new RoomListAdapter(ct, roomsInfoDTOs);
                        room_list.setAdapter(roomListAdapter);

                        room_list.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                roomListAdapter.notifyDataSetChanged();


                                room_list.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        room_list.setVisibility(View.VISIBLE);
                                        progress_bar.setVisibility(View.GONE);
                                    }
                                },1500);


                            }
                        },1000);

                        no_result_found.setVisibility(View.GONE);

                    } else {
                        progress_bar.setVisibility(View.GONE);
                        no_result_found.setVisibility(View.VISIBLE);
                        room_list.setVisibility(View.GONE);

                        new ToastUtil(ct, "No rooms found");
                    }
                    search_room_layout.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progress_bar.setVisibility(View.GONE);
            }


        }
    }


    private class GetmYRoomAsync extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest httpRequest = new IfriendRequest(ct);

                String age = AppSession.getValue(ct, Constants.USER_AGE);


                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();


                mJsonObjectSub.put("clun01", MainActivity.enc_username);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "getUsersRoom");

                main_object.put("requestData", requestdata);
                try {
                    roomsInfoDTOs = httpRequest.GetRooms(main_object.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {


                try {

                    if (roomsInfoDTOs != null && roomsInfoDTOs.size() > 0)
                    {
                        final   RoomListAdapter roomListAdapter = new RoomListAdapter(ct, roomsInfoDTOs);
                        my_room_list.setAdapter(roomListAdapter);

                        my_room_list.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                roomListAdapter.notifyDataSetChanged();
                                my_room_list.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        my_room_layout.setVisibility(View.VISIBLE);
                                        progress_bar.setVisibility(View.GONE);
                                    }
                                },1500);

                            }
                        },1000);





                        myroom_no_result_found.setVisibility(View.GONE);
                    }
                    else
                    {
                        progress_bar.setVisibility(View.GONE);
                        my_room_layout.setVisibility(View.VISIBLE);
                        myroom_no_result_found.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    progress_bar.setVisibility(View.GONE);
                }


                create_room_bar.setVisibility(View.GONE);
                search_room_bar.setVisibility(View.GONE);
                rooms_home_bar.setVisibility(View.GONE);
                my_rooms_bar.setVisibility(View.VISIBLE);


                my_room_layout.setVisibility(View.VISIBLE);


            } catch (Exception e) {
                e.printStackTrace();
                progress_bar.setVisibility(View.GONE);
            }


        }
    }






    private class SendFirst_name extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest httpRequest = new IfriendRequest(ct);
                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();

                mJsonObjectSub.put("clun01", MainActivity.enc_username);
                mJsonObjectSub.put("first_name", first_name);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "updateUserFirstName");

                main_object.put("requestData", requestdata);
                try {
                     httpRequest.UpdateUserFirstName(main_object.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                progress_bar.setVisibility(View.GONE);
                AppSession.save(ct, Constants.USER_FIRST_NAME, first_name);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }*/
}
