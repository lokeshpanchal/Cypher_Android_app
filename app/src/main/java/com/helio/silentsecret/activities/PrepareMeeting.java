package com.helio.silentsecret.activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.dialogs.CustomDateTimePicker;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.Preference;
import com.helio.silentsecret.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PrepareMeeting extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {
    LinearLayout main_bullet_layout = null;
    TextView back_iv = null, location_picker = null, map_back = null, done_location = null;
    int PLACE_PICKER_REQUEST = 1;
    private GoogleMap mMap;
    RelativeLayout mapview = null, add_note_layout = null, share_mood_layout = null;
    EditText address = null, edt_meeting_title = null;

    List<TextView> check_box_list = new ArrayList<>();
    //edt_meeting_title = null, edt_meeting_note = null;
    Context ct = this;
    CustomDateTimePicker custom;
    LinearLayout pick_date_time = null;
    TextView date_time = null, add_note = null, add_bullet = null, bullet_view = null, done_button = null, meeting_note_back = null;
    String meeting_date_time = "", meeting_title, meeting_address = "";

    String meeting_note = "";
    int index_counter = 0;
    int emotion_index = 0;

    List<TextView> format_textv_list = new ArrayList<>();
    List<TextView> emotion_list = new ArrayList<>();
    LinearLayout pre_meet_layout = null;
    List<Integer> remove_indexes = new ArrayList<>();
    List<EditText> format_edit_text_list = new ArrayList<>();
    List<LinearLayout> main_formate_layout_list = new ArrayList<>();
    List<String> emotion_name_list = new ArrayList<>();

    List<String> final_note_list = new ArrayList<>();
    List<String> final_mood_list = new ArrayList<>();
    int emogies_icon_array[] = {R.drawable.ic_scared, R.drawable.create_fml, R.drawable.ic_sad, R.drawable.create_lol,
            R.drawable.create_lonely, R.drawable.ic_happy, R.drawable.create_greatful, R.drawable.create_frustated,
            R.drawable.ic_love, R.drawable.ic_angry, R.drawable.ic_ashamed, R.drawable.create_anxious};

    /*String[] emotion_name_array = {"scared", "FML", "sad", "lol", "lonely", "happy", "grateful", "frustrated"
            , "love", "angry", "ashamed", "anxious"};*/
    ImageView mFeelingAnxious;
    ImageView mFeelingFML;
    ImageView mFeelingFrustrated;
    ImageView mFeelingGreatful;
    ImageView mFeelingLOL;
    ImageView mFeelingLonely;
    ImageView mFeelingAngry;
    ImageView mFeelingHappy;
    ImageView mFeelingLove;
    ImageView mFeelingSad;
    ImageView mFeelingScared;
    ImageView mFeelingAshamed;
    ImageView post_emotion_close;

    TextView prev_meet_title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_meeting);
        back_iv = (TextView) findViewById(R.id.back_iv);
        map_back = (TextView) findViewById(R.id.map_back);
        location_picker = (TextView) findViewById(R.id.location_picker);
        done_location = (TextView) findViewById(R.id.done_location);
        done_location = (TextView) findViewById(R.id.done_location);
        add_note = (TextView) findViewById(R.id.add_note);
        prev_meet_title = (TextView) findViewById(R.id.prev_meet_title);
        date_time = (TextView) findViewById(R.id.date_time);
        meeting_note_back = (TextView) findViewById(R.id.meeting_note_back);
        done_button = (TextView) findViewById(R.id.done_button);
        address = (EditText) findViewById(R.id.address);
       /* edt_meeting_note = (EditText) findViewById(R.id.edt_meeting_note);
        edt_meeting_title = (EditText) findViewById(R.id.edt_meeting_title);*/
        edt_meeting_title = (EditText) findViewById(R.id.edt_meeting_title);
        mapview = (RelativeLayout) findViewById(R.id.mapview);
        add_note_layout = (RelativeLayout) findViewById(R.id.add_note_layout);
        share_mood_layout = (RelativeLayout) findViewById(R.id.share_mood_layout);
        pick_date_time = (LinearLayout) findViewById(R.id.pick_date_time);
        pre_meet_layout = (LinearLayout) findViewById(R.id.pre_meet_layout);
        main_bullet_layout = (LinearLayout) findViewById(R.id.main_bullet_layout);


        mFeelingAngry = (ImageView) findViewById(R.id.feeling_angry);
        mFeelingAnxious = (ImageView) findViewById(R.id.feeling_anxious);
        mFeelingAshamed = (ImageView) findViewById(R.id.feeling_ashamed);
        mFeelingFML = (ImageView) findViewById(R.id.feeling_fml);
        mFeelingFrustrated = (ImageView) findViewById(R.id.feeling_frustrated);
        mFeelingGreatful = (ImageView) findViewById(R.id.feeling_greatful);
        mFeelingHappy = (ImageView) findViewById(R.id.feeling_happy);
        mFeelingLOL = (ImageView) findViewById(R.id.feeling_lol);
        mFeelingLonely = (ImageView) findViewById(R.id.feeling_lonely);
        mFeelingLove = (ImageView) findViewById(R.id.feeling_love);
        mFeelingSad = (ImageView) findViewById(R.id.feeling_sad);
        mFeelingScared = (ImageView) findViewById(R.id.feeling_scared);
        post_emotion_close = (ImageView) findViewById(R.id.post_emotion_close);


        mFeelingAngry.setOnClickListener(this);
        mFeelingAnxious.setOnClickListener(this);
        mFeelingAshamed.setOnClickListener(this);
        mFeelingFML.setOnClickListener(this);
        mFeelingFrustrated.setOnClickListener(this);
        mFeelingGreatful.setOnClickListener(this);
        mFeelingHappy.setOnClickListener(this);
        mFeelingLOL.setOnClickListener(this);
        mFeelingLonely.setOnClickListener(this);
        mFeelingLove.setOnClickListener(this);
        mFeelingSad.setOnClickListener(this);
        mFeelingScared.setOnClickListener(this);
        done_button.setOnClickListener(this);
        meeting_note_back.setOnClickListener(this);

        post_emotion_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share_mood_layout.setVisibility(View.GONE);
            }
        });

        add_note_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (Mediator_meeting.pre_meeting_title != null && Mediator_meeting.pre_meeting_title.size() > 0) {
            prev_meet_title.setVisibility(View.VISIBLE);
        }

        prev_meet_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pre_meet_layout.setVisibility(View.VISIBLE);
                ShowOldTitle();
            }
        });


        add_bullet = (TextView) findViewById(R.id.add_bullet);
        bullet_view = (TextView) findViewById(R.id.bullet_view);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        map_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapview.setVisibility(View.GONE);
            }
        });


        add_bullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (format_edit_text_list.size() == 0)
                    Add_new_bullet();
                else {
                    boolean add_new = true;
                    for (int i = 0; i < format_edit_text_list.size(); i++) {
                        String text = format_edit_text_list.get(i).getText().toString();
                        if (remove_indexes.size() > 0 && remove_indexes.contains(i)) {
                            continue;
                        } else {
                            if (text == null || text.equalsIgnoreCase("")) {
                                add_new = false;
                                break;
                            }
                        }
                    }
                    if (add_new) {
                        Add_new_bullet();
                    } else {
                        Toast.makeText(ct, "Added point should not be empty.", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });


        add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meeting_title = edt_meeting_title.getText().toString().trim();
                meeting_address = address.getText().toString().trim();


                if (meeting_title == null || meeting_title.equalsIgnoreCase("")) {
                    new ToastUtil(ct, "Please enter meeting title.");
                    return;
                } else if (meeting_address == null || meeting_address.equalsIgnoreCase("")) {
                    new ToastUtil(ct, "Please enter meeting address.");
                    return;
                } else if (meeting_date_time == null || meeting_date_time.equalsIgnoreCase("")) {
                    new ToastUtil(ct, "Please enter meeting date time.");
                    return;
                }

                if (main_formate_layout_list.size() == 0)
                    Add_new_bullet();

                add_note_layout.setVisibility(View.VISIBLE);
            }
        });

        done_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapview.setVisibility(View.GONE);

                try {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(ct, Locale.getDefault());

                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    String add = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    address.setText(add);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        location_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mapview.setVisibility(View.VISIBLE);
                    KeyboardUtil.hideKeyBoard(address, ct);
                    /*PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    startActivityForResult(builder.build(ct), PLACE_PICKER_REQUEST);*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        pick_date_time.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (custom != null)
                            custom = null;

                        initialize();
                        custom.showDialog();
                    }
                });


    }



    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
*/

    double latitude = 0, longitude = 0;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        latitude = Double.parseDouble(Preference.getUserLat());
        longitude = Double.parseDouble(Preference.getUserLon());

        // Add a marker in Sydney, Australia, and move the camera.

        //  LatLng sydney = new LatLng(latitude, longitude);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        try {
            final Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(ct, Locale.getDefault());

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String add = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()


            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(latitude, longitude)).title(add);

            LatLng sydney = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));


            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {
                    try {
                        mMap.clear();

                        latitude = point.latitude;
                        longitude = point.longitude;


                     /*   Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(ct, Locale.getDefault());*/
                        List<Address> addresses;
                        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                        String add = "";
                        if (addresses.size() > 0)
                            add = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()


                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(point.latitude, point.longitude)).title(add);

                        mMap.addMarker(marker);

                        System.out.println(point.latitude + "---" + point.longitude);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            mMap.addMarker(marker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        custom = new CustomDateTimePicker(this,
                new CustomDateTimePicker.ICustomDateTimeListener() {

                    @Override
                    public void onSet(Dialog dialog, Calendar calendarSelected,
                                      Date dateSelected, int year, String monthFullName,
                                      String monthShortName, int monthNumber, int date,
                                      String weekDayFullName, String weekDayShortName,
                                      int hour24, int hour12, int min, int sec,
                                      String AM_PM) {
                        //                        ((TextInputEditText) findViewById(R.id.edtEventDateTime))
                        date_time.setText("");
                        monthNumber = monthNumber + 1;
                        int day_num = calendarSelected.get(Calendar.DAY_OF_MONTH);
                        String month = "", day = "", hour = "", minute = "";

                        if (monthNumber > 9) {
                            month = "" + monthNumber;
                        } else {
                            month = "0" + monthNumber;
                        }


                        if (day_num > 9) {
                            day = "" + day_num;
                        } else {
                            day = "0" + day_num;
                        }


                        if (hour12 > 9) {
                            hour = "" + hour12;
                        } else {
                            hour = "0" + hour12;
                        }


                        if (min > 9) {
                            minute = "" + min;
                        } else {
                            minute = "0" + min;
                        }

                        meeting_date_time = month
                                + "/" + day + "/" + year
                                + " " + hour + ":" + minute;


                        date_time.setText(CommonFunction.ChangeDateFormat(meeting_date_time));
                    }

                    @Override
                    public void onCancel() {

                    }
                });
        /**
         * Pass Directly current time format it will return AM and PM if you set
         * false
         */
        custom.set24HourFormat(true);
        /**
         * Pass Directly current data and time to show when it pop up
         */
        custom.setDate(Calendar.getInstance());
    }


    private void Add_new_bullet() {


        int width = CommonFunction.getScreenWidth();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout mainlayout = new LinearLayout(ct);
        mainlayout.setOrientation(LinearLayout.HORIZONTAL);
        // lp.setMargins(width / 150, width / 100,width / 100, width / 100);
        mainlayout.setLayoutParams(lp);
        lp = new LinearLayout.LayoutParams(width / 25, width / 25);
        lp.setMargins(width / 400, width / 100, 0, 0);

        TextView dot = new TextView(ct);
        dot.setLayoutParams(lp);
        dot.setTextSize(13);
        dot.setGravity(Gravity.CENTER);
        dot.setBackgroundResource(R.drawable.dot);
        /*if (layout_format_state.equalsIgnoreCase("dot"))
            dot.setBackgroundResource(R.drawable.dot);
        else if (layout_format_state.equalsIgnoreCase("number")) {
            dot.setBackgroundColor(Color.parseColor("#ffffff"));
            int siz = format_textv_list.size() - remove_indexes.size() + 2;
            dot.setText("" + siz);
            dot.setTextColor(Color.parseColor("#000000"));

        } else {
            dot.setBackgroundResource(R.drawable.plus_sign);
            dot.setText("");
            dot.setTextColor(Color.parseColor("#000000"));

        }*/
        dot.setId(1 + 1);

        mainlayout.addView(dot);
        format_textv_list.add(dot);

        lp = new LinearLayout.LayoutParams(width / 2 + width / 6, LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText topic_desc = new EditText(ct);


        lp.setMargins(width / 40, 0, 0, 0);
        topic_desc.setLayoutParams(lp);
        topic_desc.setGravity(Gravity.LEFT);
        topic_desc.setTextColor(Color.parseColor("#000000"));
        topic_desc.setTextSize(16);
        topic_desc.setTypeface(null, Typeface.BOLD);
        topic_desc.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));

        topic_desc.setBackgroundColor(Color.parseColor("#ffffff"));
        format_edit_text_list.add(topic_desc);
        mainlayout.addView(topic_desc);


        //lp = new LinearLayout.LayoutParams(width / 20, width / 20);
        lp = new LinearLayout.LayoutParams(width / 17, width / 17);
        lp.setMargins(width / 100, 0, 0, 0);
        lp.gravity = Gravity.CENTER_VERTICAL;
        TextView delete_button = new TextView(ct);
        delete_button.setLayoutParams(lp);
        delete_button.setBackgroundResource(R.drawable.delete_topic);
        delete_button.setId(index_counter + 1);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = view.getId() - 1;
                main_formate_layout_list.get(index).setVisibility(View.GONE);
                //   main_formate_layout_list.remove(index);
                remove_indexes.add(index);


                /*if (layout_format_state.equalsIgnoreCase("number"))
                {
                    try {
                        layout_format_state = "number";

                        bullet_view.setBackgroundResource(R.drawable.number);

                        int number_counter = 2;
                        for (int i = 0; i < format_textv_list.size(); i++) {
                            if (remove_indexes.size() > 0 && remove_indexes.contains(i)) {

                            } else {
                                format_textv_list.get(i).setText("" + number_counter);
                                format_textv_list.get(i).setTextColor(Color.parseColor("#000000"));
                                format_textv_list.get(i).setBackgroundColor(Color.parseColor("#ffffff"));
                                number_counter++;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
            }
        });

        if (main_formate_layout_list.size() == 0)
            delete_button.setVisibility(View.INVISIBLE);

        lp = new LinearLayout.LayoutParams(width / 10, width / 10

        );
        lp.setMargins(width / 100, 0, 0, 0);
        lp.gravity = Gravity.CENTER_VERTICAL;
        TextView emotion_icon = new TextView(ct);
        emotion_icon.setLayoutParams(lp);

        emotion_icon.setBackgroundResource(R.drawable.ic_happy);
        emotion_icon.setId(index_counter + 2);
        emotion_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion_index = v.getId() - 2;
                share_mood_layout.setVisibility(View.VISIBLE);
                KeyboardUtil.hideKeyBoard(address, ct);
            }
        });

        emotion_name_list.add("happy");

        emotion_list.add(emotion_icon);

        mainlayout.addView(delete_button);
        mainlayout.addView(emotion_icon);

        topic_desc.setFocusable(true);
        topic_desc.requestFocus();

        main_formate_layout_list.add(mainlayout);
        main_bullet_layout.addView(mainlayout);
        index_counter++;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.feeling_angry:
                share_mood_layout.setVisibility(View.GONE);
                emotion_list.get(emotion_index).setBackgroundResource(emogies_icon_array[Constants.ANGRY]);
                emotion_name_list.set(emotion_index, Constants.emotion_name_array[Constants.ANGRY]);
                break;
            case R.id.feeling_anxious:
                share_mood_layout.setVisibility(View.GONE);
                emotion_list.get(emotion_index).setBackgroundResource(emogies_icon_array[Constants.ANXIOUS]);
                emotion_name_list.set(emotion_index, Constants.emotion_name_array[Constants.ANXIOUS]);
                break;
            case R.id.feeling_ashamed:
                emotion_list.get(emotion_index).setBackgroundResource(emogies_icon_array[Constants.ASHAMED]);
                emotion_name_list.set(emotion_index, Constants.emotion_name_array[Constants.ASHAMED]);
                share_mood_layout.setVisibility(View.GONE);
                break;
            case R.id.feeling_fml:
                emotion_list.get(emotion_index).setBackgroundResource(emogies_icon_array[Constants.FML]);
                emotion_name_list.set(emotion_index, Constants.emotion_name_array[Constants.FML]);
                share_mood_layout.setVisibility(View.GONE);
                break;
            case R.id.feeling_frustrated:
                emotion_list.get(emotion_index).setBackgroundResource(emogies_icon_array[Constants.FRUSTRATED]);
                emotion_name_list.set(emotion_index, Constants.emotion_name_array[Constants.FRUSTRATED]);
                share_mood_layout.setVisibility(View.GONE);

                break;
            case R.id.feeling_greatful:
                emotion_list.get(emotion_index).setBackgroundResource(emogies_icon_array[Constants.GRATEFUL]);
                emotion_name_list.set(emotion_index, Constants.emotion_name_array[Constants.GRATEFUL]);
                share_mood_layout.setVisibility(View.GONE);
                break;
            case R.id.feeling_happy:
                emotion_list.get(emotion_index).setBackgroundResource(emogies_icon_array[Constants.HAPPY]);
                emotion_name_list.set(emotion_index, Constants.emotion_name_array[Constants.HAPPY]);
                share_mood_layout.setVisibility(View.GONE);
                break;
            case R.id.feeling_lol:
                emotion_list.get(emotion_index).setBackgroundResource(emogies_icon_array[Constants.LOL]);
                emotion_name_list.set(emotion_index, Constants.emotion_name_array[Constants.LOL]);
                share_mood_layout.setVisibility(View.GONE);
                break;
            case R.id.feeling_lonely:

                emotion_list.get(emotion_index).setBackgroundResource(emogies_icon_array[Constants.LONELY]);
                emotion_name_list.set(emotion_index, Constants.emotion_name_array[Constants.LONELY]);
                share_mood_layout.setVisibility(View.GONE);
                break;
            case R.id.feeling_love:

                emotion_list.get(emotion_index).setBackgroundResource(emogies_icon_array[Constants.LOVE]);
                emotion_name_list.set(emotion_index, Constants.emotion_name_array[Constants.LOVE]);
                share_mood_layout.setVisibility(View.GONE);
                break;
            case R.id.feeling_sad:
                emotion_list.get(emotion_index).setBackgroundResource(emogies_icon_array[Constants.SAD]);
                emotion_name_list.set(emotion_index, Constants.emotion_name_array[Constants.SAD]);
                share_mood_layout.setVisibility(View.GONE);
                break;
            case R.id.feeling_scared:
                emotion_list.get(emotion_index).setBackgroundResource(emogies_icon_array[Constants.SCARED]);
                emotion_name_list.set(emotion_index, Constants.emotion_name_array[Constants.SCARED]);
                share_mood_layout.setVisibility(View.GONE);
                break;
            case R.id.done_button:
                KeyboardUtil.hideKeyBoard(address, ct);
                sugmitMetting();
                break;
            case R.id.meeting_note_back:
                add_note_layout.setVisibility(View.GONE);
                KeyboardUtil.hideKeyBoard(address, ct);
                break;

            default:
                break;
        }
    }

    private void sugmitMetting() {
        if (format_edit_text_list.size() == 0)
            new ToastUtil(this, "Please add note.");
        else {
            boolean add_new = true;
            for (int i = 0; i < format_edit_text_list.size(); i++) {
                String text = format_edit_text_list.get(i).getText().toString();
                if (remove_indexes.size() > 0 && remove_indexes.contains(i)) {
                    continue;
                } else {
                    if (text == null || text.equalsIgnoreCase("")) {
                        add_new = false;
                        break;
                    }
                }
            }
            if (add_new) {


                final_note_list.clear();
                final_mood_list.clear();

                for (int i = 0; i < format_edit_text_list.size(); i++) {
                    String text = format_edit_text_list.get(i).getText().toString();
                    if (remove_indexes.size() > 0 && remove_indexes.contains(i)) {
                        continue;
                    } else {
                        final_note_list.add(text);
                        final_mood_list.add(emotion_name_list.get(i));

                        /*banned_word = CommonFunction.checkBannedword(text);

                        if (banned_word != null && !banned_word.equalsIgnoreCase("")) {
                            Toast.makeText(getActivity(), "\"" + banned_word + "\" is an inappropriate word remove it.", Toast.LENGTH_SHORT).show();
                            return;
                        }*/

                    }

                    // room_topic = room_topic + "#" + text;
                }

                new CreateMeeting().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                Toast.makeText(ct, "Added point should not be empty.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean is_running = false;

    private class CreateMeeting extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {

                if (is_running == false)
                {
                    is_running = true;

                    IfriendRequest httpRequest = new IfriendRequest(ct);


                    JSONObject mJsonObjectSub = new JSONObject();
                    JSONObject requestdata = new JSONObject();
                    JSONObject main_object = new JSONObject();

                    JSONArray bullet_note = new JSONArray();
                    JSONArray bullet_mood = new JSONArray();


                    for (int i = 0; i < final_note_list.size(); i++) {
                        bullet_note.put(CryptLib.encrypt(final_note_list.get(i)));
                    }

                    for (int i = 0; i < final_mood_list.size(); i++) {
                        bullet_mood.put(final_mood_list.get(i));
                    }
                    meeting_date_time = CommonFunction.getGMTTime(meeting_date_time);


                    String mediator_name = AppSession.getValue(ct, Constants.MEDIATOR_SUPPORT_WORKER_NAME);
                    String mediator_id = AppSession.getValue(ct, Constants.MEDIATOR_AGENCY_ID);

                    mJsonObjectSub.put("lat", "" + latitude);
                    mJsonObjectSub.put("long", "" + longitude);
                    mJsonObjectSub.put("clmtngtime01", meeting_date_time);
                    mJsonObjectSub.put("clmtngtitle01", CryptLib.encrypt(meeting_title));
                    mJsonObjectSub.put("clmtngnote01", bullet_note);
                    mJsonObjectSub.put("clmediatorun01", mediator_name);
                    mJsonObjectSub.put("clun01", MainActivity.enc_username);
                    mJsonObjectSub.put("address", CryptLib.encrypt(meeting_address));
                    mJsonObjectSub.put("clmtngnotemood01", bullet_mood);
                    mJsonObjectSub.put("agency_unq_id", mediator_id);


                    requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                    requestdata.put("data", mJsonObjectSub);
                    requestdata.put("requestType", "createMeeting");

                    main_object.put("requestData", requestdata);
                    try {
                        response = httpRequest.Create_room(main_object.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                if (response != null)
                {
                    Mediator_meeting.is_from_create_meeting = true;
                    finish();
                }

                is_running = false;
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private void ShowOldTitle() {
        pre_meet_layout.removeAllViews();
        check_box_list.clear();
        for (int i = 0; i < Mediator_meeting.pre_meeting_title.size(); i++) {
            int width = CommonFunction.getScreenWidth();
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout mainlayout = new LinearLayout(ct);
            mainlayout.setOrientation(LinearLayout.HORIZONTAL);
            // lp.setMargins(width / 150, width / 100,width / 100, width / 100);
            mainlayout.setLayoutParams(lp);
            lp = new LinearLayout.LayoutParams(width / 10, width / 10);
            lp.setMargins(width / 400, width / 100, 0, 0);
            lp.gravity = Gravity.CENTER_VERTICAL;
            TextView dot = new TextView(ct);
            dot.setLayoutParams(lp);
            dot.setTextSize(13);
            dot.setGravity(Gravity.CENTER);
            dot.setBackgroundResource(R.drawable.unmark_checkbox);
            dot.setId(i + 1);
            dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        for (int i = 0; i < check_box_list.size(); i++) {
                            check_box_list.get(i).setBackgroundResource(R.drawable.unmark_checkbox);
                        }
                        v.setBackgroundResource(R.drawable.mark_check_box);
                        int index = v.getId() - 1;
                        edt_meeting_title.setText(Mediator_meeting.pre_meeting_title.get(index));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            check_box_list.add(dot);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER_VERTICAL;
            TextView text = new TextView(ct);
            text.setLayoutParams(lp);
            text.setTextSize(15);
            text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));
            text.setTextColor(getResources().getColor(R.color.homemenu));
            text.setGravity(Gravity.CENTER | Gravity.LEFT);
            text.setText(Mediator_meeting.pre_meeting_title.get(i));

            mainlayout.addView(dot);
            mainlayout.addView(text);
            pre_meet_layout.addView(mainlayout);

        }
    }
}
