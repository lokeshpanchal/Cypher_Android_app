package com.helio.silentsecret.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.FindbyNameDTO;
import com.helio.silentsecret.appCounsellingDTO.AcceptTermsConditionDataDTO;
import com.helio.silentsecret.appCounsellingDTO.AppointmentCancelDTO;
import com.helio.silentsecret.appCounsellingDTO.BookAppointmentDataDTO;
import com.helio.silentsecret.appCounsellingDTO.CheckUserSessionDTO;
import com.helio.silentsecret.appCounsellingDTO.CheckUserSessionDataDTO;
import com.helio.silentsecret.appCounsellingDTO.CheckUserSessionObjectDTO;
import com.helio.silentsecret.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.silentsecret.appCounsellingDTO.FinalObjectDTO;
import com.helio.silentsecret.appCounsellingDTO.GetCounsellorAppointmentDTO;
import com.helio.silentsecret.appCounsellingDTO.GetUnavailableDataDTO;
import com.helio.silentsecret.appCounsellingDTO.SetAvailabilityDataDTO;
import com.helio.silentsecret.appCounsellingDTO.SetDaywiseAvailabilityDataDTO;
import com.helio.silentsecret.appCounsellingDTO.SuggestNewTimeDTO;
import com.helio.silentsecret.application.SilentSecretApplication;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.fragments.PendingIFriendsFragment;
import com.helio.silentsecret.models.GetCurrentDateObjectDTO;
import com.helio.silentsecret.models.GetCurrentDateTimeDTO;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;

import com.squareup.timessquare.CalendarPickerView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CounsellerBooking extends Activity {

    TextView cancel_button = null, session_left = null, show_bookdate = null, show_date = null, select_checkbox = null,
            accepttermsandc = null, submit_name = null, unselect = null, book_counsel = null, cancel_time = null, time_ok = null;
    Context ct = null;
    LinearLayout hours, minutes, ampm;
    RelativeLayout show_counsellor_list = null;

    public List<SetAvailabilityDataDTO> mondaylist = new ArrayList<>();
    public List<SetAvailabilityDataDTO> tuesdaylist = new ArrayList<>();
    public List<SetAvailabilityDataDTO> wednesdaylist = new ArrayList<>();
    public List<SetAvailabilityDataDTO> thursdaylist = new ArrayList<>();
    public List<SetAvailabilityDataDTO> fridaylist = new ArrayList<>();
    public List<SetAvailabilityDataDTO> saturdaylist = new ArrayList<>();
    public List<SetAvailabilityDataDTO> Sundaylist = new ArrayList<>();
    List<SetAvailabilityDataDTO> final_list = new ArrayList<>();

    List<SetAvailabilityDataDTO> final_list_with_same_councel = new ArrayList<>();

    SetDaywiseAvailabilityDataDTO setDaywiseAvailabilityDataDTO = null;
    LinearLayout counsellor_main = null;
    public static Date currentdatetime;
    CalendarPickerView calendar_view = null;
    int noofdays = 0;
    RelativeLayout time_layout = null, first_name_layout = null, time_lyout_bg, booking_appoint_layout = null, booking_lyout = null, start_booking_layout = null;
    LinearLayout chat_layout = null, audio_layout = null, video_layout = null;
    String Hour = "", minufordiff = "", Minute = "", Ampmtext = "AM", Dayname = "", mood_graph = "false", Mode = "Chat", daymont = "", MonthName = "", YearName = "", Final_date = "", sent_time = "";
    int intHour, intMinute, intsecond, am_pm, selected_hourindex = -1, selected_minuteundex = -1;

    boolean is_counsellor_unavailable = false;
    int select_counce_index = -1;
    String Selected_hours = "", SelectedMinute = "", counselor_unaval_time_form = "", counselunaval_timeto = "";
    SuggestNewTimeDTO suggestNewTimeDTO = null;
    String Agency_id = "", agent_id = "", user_firstname = "";
    static String CompareDate = "";

    LocationManager locationManager;
    private static int temp = 0;
    Location location;

    boolean is_block_all = false;

    private double latitudeTarget;
    private double longitudeTarget;

    BookAppointmentDataDTO bookAppointmentDataDTO = null;
    TextView chat_text, moodgraphtext = null, request_status = null, book_appointment, counsellingmode = null, counseldate_time = null, text_chat_text, audio_calling, audio_calling_text, video_calling, video_calling_text;
    boolean is_select = true;
    List<String> hourslist, minutelist;
    String is_samecounseller = "false", counseller_id = "", counsellor_name = "" , selected_counsellor_name = "";
    List<TextView> hourtextview = null;
    List<TextView> minutetextview = null;
    RelativeLayout progress_bar = null;
    int selehour, selectminute, width, height;
    //private TimeAdapter timeAdapter;

    boolean is_testing = false;

    List<String> counselor_unavail_list = null;
    List<String> blocked_minutelist = null;
    List<String> blocked_hourlist = null;


    EditText edt_first_name = null;
    boolean is_tandC_submit = false;
    AppointmentCancelDTO appointmentCancelDTO = null;
    List<String> counselleracceptlist = null;

    List<String> counsellor_available_time_list = new ArrayList<>();

    //TextView cancel_counce_list= null , select_counsellor = null;
    WebView webview = null;
    RelativeLayout tandclayout = null;
    String mytime = "";
    JSONObject Suggested_Object = null;
    TextView app_text = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counseller_booking);

        ct = this;
        cancel_button = (TextView) findViewById(R.id.cancel_book);
        unselect = (TextView) findViewById(R.id.unselect);
        select_checkbox = (TextView) findViewById(R.id.select_checkbox);
        session_left = (TextView) findViewById(R.id.session_left);
        book_counsel = (TextView) findViewById(R.id.book_counsel);
        cancel_time = (TextView) findViewById(R.id.cancel_time);
        show_date = (TextView) findViewById(R.id.show_date);
        time_ok = (TextView) findViewById(R.id.time_ok);
        moodgraphtext = (TextView) findViewById(R.id.moodgraphtext);
        app_text = (TextView) findViewById(R.id.app_text);
        accepttermsandc = (TextView) findViewById(R.id.accepttermsandc);
        submit_name = (TextView) findViewById(R.id.submit_name);

        edt_first_name = (EditText) findViewById(R.id.edt_first_name);
        webview = (WebView) findViewById(R.id.webview);

        counsellingmode = (TextView) findViewById(R.id.counsellingmode);
        counseldate_time = (TextView) findViewById(R.id.counseldate_time);
        show_bookdate = (TextView) findViewById(R.id.show_bookdate);
        time_layout = (RelativeLayout) findViewById(R.id.time_layout);
        tandclayout = (RelativeLayout) findViewById(R.id.tandclayout);
        show_counsellor_list = (RelativeLayout) findViewById(R.id.show_counsellor_list);
        start_booking_layout = (RelativeLayout) findViewById(R.id.start_booking_layout);
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);
        time_lyout_bg = (RelativeLayout) findViewById(R.id.time_lyout_bg);
        first_name_layout = (RelativeLayout) findViewById(R.id.first_name_layout);
        booking_lyout = (RelativeLayout) findViewById(R.id.booking_lyout);
        booking_appoint_layout = (RelativeLayout) findViewById(R.id.booking_appoint_layout);
        chat_layout = (LinearLayout) findViewById(R.id.chat_layout);
        audio_layout = (LinearLayout) findViewById(R.id.audio_layout);
        counsellor_main = (LinearLayout) findViewById(R.id.counsellor_main);
        video_layout = (LinearLayout) findViewById(R.id.video_layout);
        request_status = (TextView) findViewById(R.id.request_status);
        book_appointment = (TextView) findViewById(R.id.book_appointment);
        chat_text = (TextView) findViewById(R.id.text_chat);
        text_chat_text = (TextView) findViewById(R.id.text_chat_text);
        audio_calling = (TextView) findViewById(R.id.audio_calling);
        audio_calling_text = (TextView) findViewById(R.id.audio_calling_text);
        video_calling = (TextView) findViewById(R.id.video_calling);
        video_calling_text = (TextView) findViewById(R.id.video_calling_text);



        if (!LiveCounselling.is_from_fivr_min_booking) {
            LiveCounselling.submited_impact = false;
            new ChecUserSessionfirsttime().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        first_name_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        show_counsellor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        submit_name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                user_firstname = edt_first_name.getText().toString();
                user_firstname = user_firstname.trim();

                if (user_firstname != null && !user_firstname.equalsIgnoreCase(""))
                {

                    try {
                        user_firstname = user_firstname.trim();

                        user_firstname = CryptLib.encrypt(user_firstname);
                        AppSession.save(ct, Constants.USER_FIRST_NAME, user_firstname);
                        first_name_layout.setVisibility(View.GONE);
                        ConfirmPopup();
                        KeyboardUtil.hideKeyBoard(edt_first_name, CounsellerBooking.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else
                    new ToastUtil(ct, "Please enter your first name.");
            }
        });
        Agency_id = AppSession.getValue(this, Constants.AGENCY_ID);
        agent_id = AppSession.getValue(this, Constants.AGENT_UNIQUE_ID);
        try {
            String terms_condit = AppSession.getValue(ct, Constants.USER_TERMSCONDITION);
            if (terms_condit == null || terms_condit.equalsIgnoreCase("") || terms_condit.equalsIgnoreCase("false"))
                is_tandC_submit = false;
            else
                is_tandC_submit = true;


        } catch (Exception e) {
            e.printStackTrace();
        }

        is_block_all = false;


        initWebView("http://getcypherapp.com/termcondition.html");

        accepttermsandc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tandclayout.setVisibility(View.GONE);

                try {
                    new AcceptTermsCondition().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                    if (counseller_id != null && !counseller_id.equalsIgnoreCase("")) {
                        isSameCounseller();
                    } else {
                        start_booking_layout.setVisibility(View.GONE);
                        booking_appoint_layout.setVisibility(View.GONE);
                        booking_lyout.setVisibility(View.VISIBLE);
                        sethourslist();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        calendar_view = (CalendarPickerView) findViewById(R.id.calendar_view);
        hours = (LinearLayout) findViewById(R.id.hours);
        minutes = (LinearLayout) findViewById(R.id.minute);

        startTracking(getString(R.string.analytics_appCounselling));

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        if (moodgraphtext != null)
            moodgraphtext.setVisibility(View.GONE);


        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        Calendar nextday = Calendar.getInstance();
        nextday.add(Calendar.DAY_OF_MONTH, 1);
        calendar_view.init(nextday.getTime(), cal.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE);


        //  setdate_time();

        progress_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        book_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Final_date != null && !Final_date.equalsIgnoreCase(""))
                {
                    if (Selected_hours != null && !Selected_hours.equalsIgnoreCase("") && SelectedMinute != null && !SelectedMinute.equalsIgnoreCase(""))
                    {
                        user_firstname = AppSession.getValue(ct, Constants.USER_FIRST_NAME);

                        if (user_firstname != null && !user_firstname.equalsIgnoreCase(""))
                        {
                            ConfirmPopup();
                        } else
                            first_name_layout.setVisibility(View.VISIBLE);


                    } else
                        Toast.makeText(ct, "Please Select a time-slot to book an appointment.", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ct, "Please Select a date and a time-slot to book an appointment.", Toast.LENGTH_SHORT).show();
            }
        });
        chat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Suggested_Object == null) {
                    Mode = "Chat";
                    chat_text.setBackgroundResource(R.drawable.text_chat);
                    text_chat_text.setTextColor(getResources().getColor(R.color.chat_color));

                    audio_calling.setBackgroundResource(R.drawable.audio_calling_buller);
                    audio_calling_text.setTextColor(getResources().getColor(R.color.buller_color));

                    video_calling.setBackgroundResource(R.drawable.video_calling_buller);
                    video_calling_text.setTextColor(getResources().getColor(R.color.buller_color));
                }
            }
        });

        audio_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Suggested_Object == null) {
                    Mode = "Audio";
                    chat_text.setBackgroundResource(R.drawable.text_chat_buller);
                    text_chat_text.setTextColor(getResources().getColor(R.color.buller_color));

                    audio_calling.setBackgroundResource(R.drawable.audio_calling);
                    audio_calling_text.setTextColor(getResources().getColor(R.color.audio_color));

                    video_calling.setBackgroundResource(R.drawable.video_calling_buller);
                    video_calling_text.setTextColor(getResources().getColor(R.color.buller_color));
                }
            }
        });

        video_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Suggested_Object == null) {
                    Mode = "Video";
                    chat_text.setBackgroundResource(R.drawable.text_chat_buller);
                    text_chat_text.setTextColor(getResources().getColor(R.color.buller_color));

                    audio_calling.setBackgroundResource(R.drawable.audio_calling_buller);
                    audio_calling_text.setTextColor(getResources().getColor(R.color.buller_color));

                    video_calling.setBackgroundResource(R.drawable.video_calling);
                    video_calling_text.setTextColor(getResources().getColor(R.color.video_color));
                }
            }
        });


        //   sethourslist();
        // setminutelist();


      /*  am_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Ampmtext != null && Ampmtext.equalsIgnoreCase("PM")) {
                    Ampmtext = "AM";
                    am_textview.setBackgroundResource(R.drawable.ic_web_bg);
                    pm_textview.setBackgroundColor(Color.parseColor("#000000"));
                    sethourslist();
                }

            }
        });


        pm_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Ampmtext != null && Ampmtext.equalsIgnoreCase("AM")) {
                    Ampmtext = "PM";
                    am_textview.setBackgroundColor(Color.parseColor("#000000"));
                    pm_textview.setBackgroundResource(R.drawable.ic_web_bg);
                    sethourslist();
                }
            }
        });
*/

       /* if (Ampmtext != null && Ampmtext.equalsIgnoreCase("AM")) {

            Ampmtext = "AM";
            am_textview.setBackgroundResource(R.drawable.ic_web_bg);
            pm_textview.setBackgroundColor(Color.parseColor("#000000"));

        } else {
            Ampmtext = "PM";
            am_textview.setBackgroundColor(Color.parseColor("#000000"));
            pm_textview.setBackgroundResource(R.drawable.ic_web_bg);

        }*/


    /*    unselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_select) {
                    mood_graph = "true";
                    select_checkbox.setVisibility(View.VISIBLE);
                    is_select = true;
                } else {
                    is_select = false;
                    mood_graph = "false";
                    select_checkbox.setVisibility(View.GONE);
                }
            }
        });*/

        time_lyout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        book_counsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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

                    }


                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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

                    if (!gps_enabled && !network_enabled) {

                        PendingIFriendsFragment.progress_bar.setVisibility(View.GONE);

                        // notify user
                        AlertDialog.Builder dialog2 = new AlertDialog.Builder(v.getRootView().getContext());
                        dialog2.setMessage(getResources().getString(R.string.gps_network_accept_not_enabled));
                        dialog2.setPositiveButton(getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                // TODO Auto-generated method stub
                                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(myIntent);
                                //get gps
                            }
                        });

                        dialog2.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                // TODO Auto-generated method stub


                            }
                        });


                        AlertDialog alert2 = dialog2.create();
                        alert2.setCanceledOnTouchOutside(false);
                        alert2.show();

                    } else

                    {

                        if (longitudeTarget != 0.0 && latitudeTarget != 0.0) {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {

                    if (setDaywiseAvailabilityDataDTO != null) {
                        Mode = "Chat";

                        if (is_tandC_submit) {
                            if (LiveCounselling.is_from_fivr_min_booking) {
                                counseller_id = LiveCounselling.counsellor_booking_id;
                                counsellor_name = LiveCounselling.counsellor_booking_name;
                            }

                            if (counseller_id != null && !counseller_id.equalsIgnoreCase("")) {
                                isSameCounseller();
                            } else {
                                start_booking_layout.setVisibility(View.GONE);
                                booking_appoint_layout.setVisibility(View.GONE);
                                booking_lyout.setVisibility(View.VISIBLE);
                                sethourslist();
                            }
                        } else
                            tandclayout.setVisibility(View.VISIBLE);

                    } else {
                        new ToastUtil(ct, "No counsellor available");
                    }
                    // makecall();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        if (MainActivity.session_left <= 0 && MainActivity.is_booking == false) {
            Intent intent = new Intent(this, CameraTestActivity.class);
            startActivity(intent);
            finish();
            /*MainActivity.session_left = MainActivity.session_left+1;
            session_left.setText("You have " + MainActivity.session_left + " session left");*/
        } else {
            session_left.setText("You have " + MainActivity.session_left + " session left");
        }


        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LiveCounselling.is_from_fivr_min_booking) {
                    Intent intent = new Intent(CounsellerBooking.this, LiveCounselling.class);
                    startActivity(intent);
                }
                finish();
            }
        });

        cancel_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_lyout_bg.setVisibility(View.GONE);
            }
        });

        time_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (Selected_hours != null && !Selected_hours.equalsIgnoreCase("")) {
                        if (SelectedMinute != null && !SelectedMinute.equals("")) {

                            int selecthours = Integer.parseInt(Selected_hours);

                            if (selecthours > 11) {
                                Ampmtext = "PM";
                                if (selecthours != 12) {
                                    selecthours = selecthours - 12;
                                    Selected_hours = "" + selecthours;

                                }

                            } else {
                                if (selecthours == 0)
                                    Selected_hours = "0";
                                Ampmtext = "AM";
                            }

                            Hour = Selected_hours;
                            Minute = SelectedMinute;

                            int inthour = Integer.parseInt(Hour);

                            if (inthour < 10) {
                                Hour = "0" + inthour;
                            } else {
                                Hour = "" + inthour;
                            }

                            int intmin = Integer.parseInt(Minute);

                            if (intmin < 10) {
                                Minute = "0" + intmin;
                            } else {
                                Minute = "" + intmin;
                            }

                            show_bookdate.setText(Dayname + " " + daymont + " " + MonthName + ", " + YearName + " " + Hour + ":" + Minute + " " + Ampmtext);
                            time_lyout_bg.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(ct, "Please Select Minute first", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ct, "Please Select Hour first", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


        calendar_view.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {

            @Override
            public void onDateUnselected(Date date) {

            }


            @Override
            public void onDateSelected(Date date) {

                String outputPattern = "MM/dd/yyyy";
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                try {


                    SimpleDateFormat month_date = new SimpleDateFormat("MMMM");

                    SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");

                    Dayname = outFormat.format(date);
                    //  if (Dayname != null && !Dayname.equalsIgnoreCase("Sunday"))
                    //{
                    if (setDaywiseAvailabilityDataDTO != null) {


                        Selected_hours = "";



                        show_counsellor_list.setVisibility(View.VISIBLE);
                        showCounsellorList(Dayname);


                        MonthName = month_date.format(date);

                        Final_date = outputFormat.format(date);

                        if (Suggested_Object != null)
                            noofdays = getcounselldatediff(date_suggestby_counsel, Final_date);
                        else {
                            //  noofdays = 5;
                            noofdays = show(Final_date);
                        }


                        String selecteddates = null;

                        selecteddates = outputFormat.format(date);

                        book_appointment.setBackgroundResource(R.drawable.theme_shape);
                        book_appointment.setTextColor(Color.parseColor("#ffffff"));
                        String daymont1[] = selecteddates.split("/");


                        if (daymont != null && daymont1.length > 1) {
                            daymont = daymont1[1];
                            YearName = daymont1[2];
                            Dayname = Dayname.substring(0, 3);
                            MonthName = MonthName.substring(0, 3);

                            show_date.setText(Dayname + " " + daymont + " " + MonthName + ", " + YearName);

                            show_bookdate.setText(Dayname + " " + daymont + " " + MonthName + ", " + YearName + " " + Hour + ":" + Minute + " " + Ampmtext);
                        }


                    } else {
                        time_lyout_bg.setVisibility(View.VISIBLE);
                        MonthName = month_date.format(date);

                        Final_date = outputFormat.format(date);

                        if (Suggested_Object != null)
                            noofdays = getcounselldatediff(date_suggestby_counsel, Final_date);
                        else {
                            //  noofdays = 5;
                            noofdays = show(Final_date);
                        }


                        String selecteddates = null;

                        selecteddates = outputFormat.format(date);

                        book_appointment.setBackgroundResource(R.drawable.theme_shape);
                        book_appointment.setTextColor(Color.parseColor("#ffffff"));
                        String daymont1[] = selecteddates.split("/");


                        if (daymont != null && daymont1.length > 1) {
                            daymont = daymont1[1];
                            YearName = daymont1[2];
                            Dayname = Dayname.substring(0, 3);
                            MonthName = MonthName.substring(0, 3);

                            show_date.setText(Dayname + " " + daymont + " " + MonthName + ", " + YearName);

                            show_bookdate.setText(Dayname + " " + daymont + " " + MonthName + ", " + YearName + " " + Hour + ":" + Minute + " " + Ampmtext);
                        }
/*
                        sethourslist();
                        blockminuteto = 55;
                        setminutelist();*/
                    }

                   /* } else {
                        Toast.makeText(ct, "Counsellor is not available on Sunday.", Toast.LENGTH_SHORT).show();
                    }*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });

    }

    private void setdate_time() {

        try {
            String format = new SimpleDateFormat("E d MMM, yyyy").format(currentdatetime);
            intHour = currentdatetime.getHours();
            intMinute = currentdatetime.getMinutes();


            if (intMinute < 10) {
                Minute = "0" + intMinute;
            } else {
                Minute = "" + intMinute;
            }


            if (intHour < 10) {

                Hour = "0" + intHour;


            } else {
                Hour = "" + intHour;
            }
            mytime = "" + Hour + ":" + intMinute;

            intMinute = (intMinute / 5) * 5;


            selectminute = intMinute;

            if (intHour >= 12) {
                Ampmtext = "PM";
                am_pm = 1;
            } else {
                Ampmtext = "AM";
                am_pm = 0;
            }


            if (intHour > 9) {
                if (intHour == 12) {
                    Hour = "" + intHour;
                } else if (intHour > 12) {
                    intHour = intHour - 12;
                    if (intHour < 10)
                        Hour = "0" + intHour;
                    else
                        Hour = "" + intHour;
                } else
                    Hour = "" + intHour;
            }

            selehour = intHour;


            Calendar nextYear = Calendar.getInstance();
            nextYear.add(Calendar.YEAR, 1);

            Calendar nextday = Calendar.getInstance();

            Calendar c = Calendar.getInstance();
            nextday.setTime(currentdatetime);

            if (am_pm == 0) {
                nextday.add(Calendar.DAY_OF_MONTH, 1);
            } else {
                nextday.add(Calendar.DAY_OF_MONTH, 2);
            }

            calendar_view.init(nextday.getTime(), nextYear.getTime())
                    .inMode(CalendarPickerView.SelectionMode.SINGLE);
            show_bookdate.setText("" + format + " " + Hour + ":" + Minute + " " + Ampmtext);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void ConfirmPopup() {


        AlertDialog.Builder updateAlert = new AlertDialog.Builder(ct);
        updateAlert.setIcon(R.drawable.ic_launcher);
        updateAlert.setTitle("Cypher");
        if (Suggested_Object == null)
            updateAlert.setMessage("Are you sure you want to send a request for counselling using \"" + Mode + "\" at \"" + Dayname + " " + daymont + " " + MonthName + ", " + YearName + " " + Hour + ":" + Minute + " " + Ampmtext + "\"?");
        else
            updateAlert.setMessage("Are you sure you want to suggest new time for counselling using \"" + suggestedby_counseller_chat_mode + "\" at \"" + Dayname + " " + daymont + " " + MonthName + ", " + YearName + " " + Hour + ":" + Minute + " " + Ampmtext + "\"?");
            updateAlert.setPositiveButton
                (
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        try {

                            try {
                                if (Ampmtext != null && Ampmtext.equalsIgnoreCase("AM")) {
                                    sent_time = Final_date + " " + Hour + ":" + Minute;
                                } else {
                                    int hour = Integer.parseInt(Hour);

                                    if (hour == 12)
                                        sent_time = Final_date + " " + Hour + ":" + Minute;
                                    else {
                                        hour = hour + 12;
                                        sent_time = Final_date + " " + hour + ":" + Minute;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            AppSession.save(ct, Constants.IMPACT_REMINDER, "true");


                            progress_bar.setVisibility(View.VISIBLE);
                            sent_time = getGMTTime(sent_time);

                            String[] appointhours = sent_time.split(" ");

                            if (Suggested_Object != null) {
                                try {

                                    progress_bar.setVisibility(View.VISIBLE);
                                    String counsellor_f_name = "";
                                    try {
                                        counsellor_f_name = Suggested_Object.getString("counsellor_firstname");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                    suggestNewTimeDTO = new SuggestNewTimeDTO(MainActivity.enc_username, Suggested_Object.getString("clcnslrun01"), Suggested_Object.getString("apntmnt_id"), "User", sent_time, Suggested_Object.getString("same_cnslr"), counsellor_f_name,appointhours[1], final_list.get(select_counce_index).getDay());
                                    new SuggestNewTime().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                } catch (
                                        Exception e
                                        ) {
                                    progress_bar.setVisibility(View.GONE);
                                    e.printStackTrace();
                                }

                            } else {



                                long mseconds = System.currentTimeMillis();
                                String appintmentid = MainActivity.enc_username + mseconds;
                                int session_duration = 30;
                                if (MainActivity.stataicObjectDTO.getiFriendSettingDTO().getSession_duration() != null && !MainActivity.stataicObjectDTO.getiFriendSettingDTO().getSession_duration().equalsIgnoreCase(""))
                                    session_duration = Integer.parseInt(MainActivity.stataicObjectDTO.getiFriendSettingDTO().getSession_duration());
                                String qrCode = AppSession.getValue(ct, Constants.USED_QR_CODE);
                                bookAppointmentDataDTO = new BookAppointmentDataDTO(MainActivity.enc_username, Mode, is_samecounseller, sent_time, qrCode, Agency_id
                                        , "" + MainActivity.session_left, appintmentid, "" + latitudeTarget, "" + longitudeTarget, "User", "", final_list.get(select_counce_index).getCounc_unq_id(), final_list.get(select_counce_index).getClcnslrun01(), session_duration, agent_id, user_firstname, appointhours[1], final_list.get(select_counce_index).getCounce_firstname(), final_list.get(select_counce_index).getDay());

                                if (LiveCounselling.is_from_fivr_min_booking)
                                    new BookNextAppointment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                else
                                    new BookFreshAppointment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                            }

                        } catch (Exception e) {
                            progress_bar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }

                    }
                });

        updateAlert.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        try {


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

        AlertDialog alertUp = updateAlert.create();
        alertUp.setCanceledOnTouchOutside(false);
        alertUp.show();
    }

    String date_suggestby_counsel = "", suggestedby_counseller_chat_mode = "", day_suugested_bycouselor = "", Month_suggested_bycounsellor = "", year_suggested_bycounselor = "";
    String time_suggestby_counseller, hour_suggestby_counseller = "", minut_suugestby_counseller = "", ampmtext_suggestby_counseller = "";


    static int blockat = -1; //  use for block hours
    static int blockminute = -1; // use for block minute
    static int block_min_from = -1; // use for block minute from the minute
    static int blockminuteto = -1; // block minute upto the minute

    static int blockhourfrom = -1;
    static int blockhourto = -1;

    private void sethourslist() {
        try {

            Selected_hours = "";
            is_from_block = false;
            is_from_block_from = false;
            is_from_block_to = false;
            blockat = -1;
            blockminute = -1;
            block_min_from = -1;
            blockminuteto = -1;
            if (Suggested_Object != null && noofdays <= 0) {
                if (ampmtext_suggestby_counseller != null && ampmtext_suggestby_counseller.equalsIgnoreCase("AM")) {
                    if (Integer.parseInt(minut_suugestby_counseller) == 0) {
                        blockminute = Integer.parseInt(minut_suugestby_counseller);
                        blockat = Integer.parseInt(hour_suggestby_counseller) - 1;
                    } else {
                        if (Integer.parseInt(minut_suugestby_counseller) >= 55) {
                            blockat = Integer.parseInt(hour_suggestby_counseller);
                            blockminute = -1;
                        } else {
                            blockat = Integer.parseInt(hour_suggestby_counseller) - 1;
                            blockminute = Integer.parseInt(minut_suugestby_counseller);
                        }
                    }

                } else {

                    String hour_suggestby_counseller1 = "12";
                    if (hour_suggestby_counseller != null && !hour_suggestby_counseller.equalsIgnoreCase("12")) {
                        int local = Integer.parseInt(hour_suggestby_counseller);
                        local = 12 + local;
                        hour_suggestby_counseller1 = "" + local;
                    }

                    if (Integer.parseInt(minut_suugestby_counseller) == 0) {
                        blockminute = Integer.parseInt(minut_suugestby_counseller);
                        blockat = Integer.parseInt(hour_suggestby_counseller1) - 1;
                    } else {
                        if (Integer.parseInt(minut_suugestby_counseller) >= 55) {
                            blockat = Integer.parseInt(hour_suggestby_counseller1);
                            blockminute = -1;
                        } else {
                            blockat = Integer.parseInt(hour_suggestby_counseller1) - 1;
                            blockminute = Integer.parseInt(minut_suugestby_counseller);
                        }
                    }
                        /*blockat = 12;
                        blockminute = 60;
*/
                }

                is_from_block = true;

                setminutelist();


            } else {


                if (noofdays < 2 && Suggested_Object == null) {
                    if (am_pm == 0) {
                        is_from_block = true;
                        blockminute = 60;
                        setminutelist();

                        int hourcount = 0;

                        if (selectminute >= 55) {
                            hourcount = intHour;
                        } else {
                            hourcount = intHour - 1;
                            blockminute = selectminute;
                        }

                        blockat = 12 + hourcount;


                    }

                } else if (noofdays < 3 && Suggested_Object == null) {
                    if (am_pm == 1) {
                        is_from_block = true;
                        blockminute = 60;
                        setminutelist();

                        if (selectminute >= 55) {
                            blockat = intHour;

                        } else {
                            blockat = intHour - 1;
                            blockminute = selectminute;
                            // setminutelist();
                        }


                    }
                } else {
                    is_from_block = false;
                    blockminute = -1;
                    setminutelist();
                }
            }
            try {


                if (counselleracceptlist != null && counselleracceptlist.size() > 0) {
                    if (blocked_hourlist != null)
                        blocked_hourlist = null;

                    if (blocked_minutelist != null)
                        blocked_minutelist = null;

                    blocked_hourlist = new ArrayList<String>();
                    blocked_minutelist = new ArrayList<String>();
                    for (int j = 0; j < counselleracceptlist.size(); j++)
                    {
                        if (counselleracceptlist.get(j).contains(Final_date)) {
                            String Date_sel = "", time_sell, hour = "", minut = "", ampmtext = "";

                            try {
                                String appointmentdate = counselleracceptlist.get(j);
                                String appint_array[] = appointmentdate.split(" ");

                                if (appint_array != null && appint_array.length > 0)
                                {

                                    time_sell = appint_array[1];

                                    String sephourmi[] = time_sell.split(":");
                                    if (sephourmi != null && sephourmi.length > 0)
                                    {
                                        hour = sephourmi[0];
                                        minut = sephourmi[1];

                                        int hourint = Integer.parseInt(hour);
                                        int minutint = Integer.parseInt(minut);

                                        if (hourint > 9)
                                            hour = "" + hourint;
                                        else
                                            hour = "0" + hourint;

                                                if(minutint ==0)
                                                {
                                                    int blhr = 0;
                                                    if(hourint>0)
                                                     blhr =  hourint -1;

                                                    blocked_hourlist.add("" + blhr);
                                                    blocked_minutelist.add(""+minutint);
                                                }
                                        blocked_hourlist.add("" + hour);
                                        blocked_minutelist.add(minut);


                                    }

                                }

                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                        } else {
                            String Date_sel = "", time_sell, hour = "", minut = "", ampmtext = "";
                            String appointmentdate = counselleracceptlist.get(j);
                            String appint_array[] = appointmentdate.split(" ");

                            if (appint_array != null && appint_array.length > 0) {
                                int datediff = getcounselldatediff(Final_date, appint_array[0]);
                                if (datediff == 1) {
                                    time_sell = appint_array[1];

                                    String sephourmi[] = time_sell.split(":");
                                    if (sephourmi != null && sephourmi.length > 0) {
                                        hour = sephourmi[0];
                                        minut = sephourmi[1];

                                        int hourint = Integer.parseInt(hour);

                                        if (hourint == 0)
                                            hour = "" + 24;
                                        else
                                            hour = "0" + hourint;

                                        blocked_hourlist.add("" + hour);

                                        int minutt = Integer.parseInt(minut);
                                        if (minutt == 0)
                                            minutt = 55;
                                        blocked_minutelist.add(minut);


                                    }
                                }


                            }


                        }

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            /*int offsethout = getOffsetTime();

            if (timeoffset != null)
                timeoffset = null;

            if (block_day != null && block_day.equalsIgnoreCase("Monday") && Dayname != null && Dayname.equalsIgnoreCase("Mon")) {

                if (timeoffset != null)
                    timeoffset = null;

                timeoffset = new ArrayList<String>();

                for (int i = 0; i <= offsethout; i++) {
                    if (i < 10)
                        timeoffset.add("0" + i);
                    else
                        timeoffset.add("" + i);
                }
            } else if (block_day != null && block_day.equalsIgnoreCase("Saturday") && Dayname != null && Dayname.equalsIgnoreCase("Sat")) {
                if (timeoffset != null)
                    timeoffset = null;

                timeoffset = new ArrayList<String>();

                for (int i = 0; i < offsethout; i++) {
                    int j = 23 - i;
                    if (j < 10)
                        timeoffset.add("0" + j);
                    else
                        timeoffset.add("" + j);
                }
            }*/


            hourslist = new ArrayList<String>();

            for (int i = 0; i < 24; i++) {

                if (i < 10)
                    hourslist.add("0" + i);
                else
                    hourslist.add("" + i);


            }


            if (hours != null)
                hours.removeAllViews();

            if (hourtextview != null)
                hourtextview = null;

            if (is_block_all) {
                if (counselor_unavail_list != null)
                    counselor_unavail_list = null;


                counselor_unavail_list = new ArrayList<String>();

                for (int i = 0; i < 24; i++) {
                    if (i < 10)
                        counselor_unavail_list.add("0" + i);
                    else
                        counselor_unavail_list.add("" + i);


                }
            }

            if (is_counsellor_unavailable) {
                try {

                    if (counselor_unavail_list != null)
                        counselor_unavail_list = null;


                    counselor_unavail_list = new ArrayList<String>();
                    String hour = "", hourto = "", minto = "";
                    String sephourmi[] = counselor_unaval_time_form.split(":");

                    if (sephourmi != null && sephourmi.length > 0) {
                        hour = sephourmi[0];
                        int hourintfrom = Integer.parseInt(hour);


                        String sephourto[] = counselunaval_timeto.split(":");
                        if (sephourto != null && sephourto.length > 0)
                            hourto = sephourto[0];

                        int hourtoint = Integer.parseInt(hourto);

                        blockhourto = hourtoint;

                        if (hourintfrom > 0 && hourtoint <= hourintfrom) {


                            if (sephourto != null && sephourto.length > 0)
                                minto = sephourmi[1];

                            int minfrom = Integer.parseInt(minto);


                            if (minfrom == 0) {
                                blockhourfrom = hourintfrom - 1;
                                for (int i = hourintfrom - 1; i < 24; i++) {
                                    if (i < 10)
                                        counselor_unavail_list.add("0" + i);
                                    else
                                        counselor_unavail_list.add("" + i);

                                    System.out.println("Booking.. block hour" + i);
                                }
                            } else {
                                blockhourfrom = hourintfrom - 1;
                                for (int i = hourintfrom; i < 24; i++) {
                                    if (i < 10)
                                        counselor_unavail_list.add("0" + i);
                                    else
                                        counselor_unavail_list.add("" + i);

                                    System.out.println("Booking.. block hour" + i);
                                }
                            }

                            for (int i = 0; i < hourtoint; i++) {
                                if (i < 10)
                                    counselor_unavail_list.add("0" + i);
                                else
                                    counselor_unavail_list.add("" + i);

                                System.out.println("Booking.. block hour" + i);
                            }


                        } else {

                            if (hourintfrom == 0) {
                                blockhourfrom = 23;
                                counselor_unavail_list.add("23");
                                for (int i = 0; i < hourtoint; i++) {
                                    if (i < 10)
                                        counselor_unavail_list.add("0" + i);
                                    else
                                        counselor_unavail_list.add("" + i);

                                    System.out.println("Booking.. block hour" + i);
                                }
                            } else {
                                if (sephourto != null && sephourto.length > 0)
                                    minto = sephourmi[1];

                                int minfrom = Integer.parseInt(minto);


                                if (minfrom == 0) {
                                    blockhourfrom = hourintfrom - 1;
                                    for (int i = hourintfrom - 1; i < hourtoint; i++) {
                                        if (i < 10)
                                            counselor_unavail_list.add("0" + i);
                                        else
                                            counselor_unavail_list.add("" + i);

                                        System.out.println("Booking.. block hour" + i);
                                    }
                                } else {
                                    blockhourfrom = hourintfrom - 1;
                                    for (int i = hourintfrom; i < hourtoint; i++) {
                                        if (i < 10)
                                            counselor_unavail_list.add("0" + i);
                                        else
                                            counselor_unavail_list.add("" + i);

                                        System.out.println("Booking.. block hour" + i);
                                    }
                                }
                            }
                        }


                    }


                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            if (blocked_hourlist != null && blocked_hourlist.size() > 0 /*|| ( counselor_unavail_list!= null && counselor_unavail_list.size()>0)*/) {
                is_from_block = true;
                blockminute = 60;
                setminutelist();
            }

            hourtextview = new ArrayList<TextView>();

            for (int i = 0; i < hourslist.size(); i++) {

                TextView hourtext = new TextView(ct);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 9);

                hourtext.setLayoutParams(lp);
                hourtext.setTextColor(Color.parseColor("#ffffff"));
                hourtext.setTextSize(18);
                hourtext.setPadding(width / 50, width / 50, width / 50, width / 50);
                hourtext.setGravity(Gravity.CENTER);

               /* if(selehour == i)
                    hourtext.setBackgroundResource(R.drawable.ic_web_bg);
                else*/
                if (blockat >= i)
                    hourtext.setBackgroundColor(Color.parseColor("#999999"));
                else
                    hourtext.setBackgroundColor(Color.parseColor("#111111"));

                if (blocked_hourlist != null && blocked_hourlist.size() > 0) {

                    for (int sp = 0; sp < blocked_hourlist.size(); sp++) {
                        if (blocked_hourlist.get(sp) != null && blocked_hourlist.get(sp).equalsIgnoreCase(hourslist.get(i))) {
                            hourtext.setBackgroundColor(Color.parseColor("#999999"));
                            break;
                        }
                    }
                }


               /* if (timeoffset != null && timeoffset.size() > 0) {

                    for (int spd = 0; spd < timeoffset.size(); spd++) {
                        if (timeoffset.get(spd) != null && timeoffset.get(spd).equalsIgnoreCase(hourslist.get(i))) {
                            hourtext.setBackgroundColor(Color.parseColor("#999999"));
                            break;
                        }
                    }
                }*/


                if (counselor_unavail_list != null && counselor_unavail_list.size() > 0) {

                    for (int sp = 0; sp < counselor_unavail_list.size(); sp++) {
                        if (counselor_unavail_list.get(sp) != null && counselor_unavail_list.get(sp).equalsIgnoreCase(hourslist.get(i))) {
                            hourtext.setBackgroundColor(Color.parseColor("#999999"));
                            break;
                        }
                    }
                }


                hourtext.setText(hourslist.get(i));
                hourtext.setId(i + 1);
                hourtext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int index = v.getId() - 1;
                            /*if (timeoffset != null && timeoffset.size() > 0) {
                                for (int spd = 0; spd < timeoffset.size(); spd++) {
                                    if (timeoffset.get(spd) != null && timeoffset.get(spd).equalsIgnoreCase(hourslist.get(index))) {
                                        Toast.makeText(ct, "Sorry time slot is not available", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
*/

                            if (blocked_hourlist != null && blocked_hourlist.size() > 0) {
                                for (int sp = 0; sp < blocked_hourlist.size(); sp++) {
                                    if (blocked_hourlist.get(sp) != null && blocked_hourlist.get(sp).equalsIgnoreCase(hourslist.get(index))) {
                                        {
                                            Toast.makeText(ct, "Sorry time slot is not available", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                }
                            }

                            if (counselor_unavail_list != null && counselor_unavail_list.size() > 0) {
                                for (int sp = 0; sp < counselor_unavail_list.size(); sp++) {
                                    if (counselor_unavail_list.get(sp) != null && counselor_unavail_list.get(sp).equalsIgnoreCase(hourslist.get(index))) {
                                        {
                                            Toast.makeText(ct, "Sorry time slot is not available", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                }
                            }

                            if (blockat + 1 < index) {
                                is_from_block_from = false;
                                is_from_block_to = false;
                                block_min_from = -1;
                                blockminuteto = -1;

                                Selected_hours = hourslist.get(index);


                                is_from_block = false;
                                setminutelist();

                                for (int j = 0; j < hourtextview.size(); j++)
                                {
                                    if (blockat < j)
                                    {
                                        if (j == index)
                                            hourtextview.get(j).setBackgroundResource(R.drawable.ic_web_bg);
                                        else
                                            hourtextview.get(j).setBackgroundColor(Color.parseColor("#111111"));
                                    }

                                    if (blocked_hourlist != null && blocked_hourlist.size() > 0)
                                    {
                                        for (int sp = 0; sp < blocked_hourlist.size(); sp++) {
                                            if (blocked_hourlist.get(sp) != null && blocked_hourlist.get(sp).equalsIgnoreCase(hourslist.get(j))) {
                                                {
                                                    hourtextview.get(j).setBackgroundColor(Color.parseColor("#999999"));
                                                }
                                            }
                                        }
                                    }

                                    /*if (timeoffset != null && timeoffset.size() > 0) {

                                        for (int spd = 0; spd < timeoffset.size(); spd++) {
                                            if (timeoffset.get(spd) != null && timeoffset.get(spd).equalsIgnoreCase(hourslist.get(j))) {
                                                hourtextview.get(j).setBackgroundColor(Color.parseColor("#999999"));
                                                break;
                                            }
                                        }
                                    }*/

                                    if (counselor_unavail_list != null && counselor_unavail_list.size() > 0)
                                    {
                                        for (int sp = 0; sp < counselor_unavail_list.size(); sp++) {
                                            if (counselor_unavail_list.get(sp) != null && counselor_unavail_list.get(sp).equalsIgnoreCase(hourslist.get(j))) {
                                                {
                                                    hourtextview.get(j).setBackgroundColor(Color.parseColor("#999999"));
                                                }
                                            }
                                        }
                                    }


                                }

                                // int selected_hour = Integer.parseInt(Hour);
                                int selected_hour = index;
                                boolean is_already_block = false;
                                if (blocked_hourlist != null && blocked_hourlist.size() > 0)
                                {
                                    for (int sp = 0; sp < blocked_hourlist.size(); sp++) {
                                        int block_hour = Integer.parseInt(blocked_hourlist.get(sp));

                                        if (selected_hour == block_hour - 1) {
                                            is_already_block = true;
                                            int blockmi = Integer.parseInt(blocked_minutelist.get(sp));

                                            is_from_block_from = true;
                                            is_from_block_to = false;
                                            block_min_from = blockmi;
                                            blockminuteto = -1;


                                            if (blockhourfrom == index) {
                                                String sephourmi[] = counselor_unaval_time_form.split(":");
                                                int blockminute1 = Integer.parseInt(sephourmi[1]);
                                                if (block_min_from > blockminute1)
                                                    block_min_from = blockminute1;
                                            }


                                            if (blockhourto == index) {
                                                is_from_block_to = true;
                                                String sephourmi[] = counselunaval_timeto.split(":");
                                                int blockminute1 = Integer.parseInt(sephourmi[1]);
                                                blockminuteto = blockminute1;
                                            }

                                            setminutelist();
                                            break;
                                            // blockminute =
                                        } else if (selected_hour == block_hour + 1) {
                                            is_already_block = true;
                                            int blockmi = Integer.parseInt(blocked_minutelist.get(sp));

                                            is_from_block_to = true;
                                            is_from_block_from = false;
                                            block_min_from = -1;
                                            blockminuteto = blockmi;


                                            if (blockhourfrom == index) {
                                                String sephourmi[] = counselor_unaval_time_form.split(":");
                                                is_from_block_from = true;
                                                block_min_from = Integer.parseInt(sephourmi[1]);

                                            }

                                            if (blockhourto == index) {

                                                String sephourmi[] = counselunaval_timeto.split(":");
                                                int blockminute1 = Integer.parseInt(sephourmi[1]);
                                                if (blockhourto < blockminute1)
                                                    blockminuteto = blockminute1;
                                            }
                                            setminutelist();
                                            break;
                                        }
                                    }

                                    if (!is_already_block) {
                                        if (blockhourfrom == index) {
                                            String sephourmi[] = counselor_unaval_time_form.split(":");
                                            is_from_block_from = true;

                                            block_min_from = Integer.parseInt(sephourmi[1]);
                                            if (block_min_from == 0)
                                                block_min_from = -1;


                                        }

                                        if (blockhourto == index) {
                                            is_from_block_to = true;
                                            String sephourmi[] = counselunaval_timeto.split(":");
                                            int blockminute1 = Integer.parseInt(sephourmi[1]);

                                            if (blockminute1 == 0)
                                                blockminute1 = -1;

                                            blockminuteto = blockminute1;
                                        }

                                        setminutelist();
                                    }
                                } else {
                                    if (blockhourfrom == index) {
                                        String sephourmi[] = counselor_unaval_time_form.split(":");
                                        is_from_block_from = true;
                                        block_min_from = Integer.parseInt(sephourmi[1]);
                                        if (block_min_from == 0)
                                            block_min_from = -1;
                                        setminutelist();
                                    } else if (blockhourto == index) {
                                        String sephourmi[] = counselunaval_timeto.split(":");
                                        is_from_block_to = true;
                                        blockminuteto = Integer.parseInt(sephourmi[1]);
                                        if (blockminuteto == 0)
                                            blockminuteto = -1;
                                        setminutelist();
                                    }
                                }

                            } else if (blockat + 1 == index) {
                                is_from_block_from = false;
                                is_from_block_to = false;
                                block_min_from = -1;
                                blockminuteto = -1;

                                Selected_hours = hourslist.get(index);
                                is_from_block = true;
                                setminutelist();

                                for (int j = 0; j < hourtextview.size(); j++) {
                                    if (blockat < j) {
                                        if (j == index)
                                            hourtextview.get(j).setBackgroundResource(R.drawable.ic_web_bg);
                                        else
                                            hourtextview.get(j).setBackgroundColor(Color.parseColor("#111111"));
                                    }

                                    if (blocked_hourlist != null && blocked_hourlist.size() > 0) {
                                        for (int sp = 0; sp < blocked_hourlist.size(); sp++) {
                                            if (blocked_hourlist.get(sp) != null && blocked_hourlist.get(sp).equalsIgnoreCase(hourslist.get(j))) {
                                                {
                                                    hourtextview.get(j).setBackgroundColor(Color.parseColor("#999999"));
                                                }
                                            }
                                        }
                                    }

                                    /*if (timeoffset != null && timeoffset.size() > 0) {

                                        for (int spd = 0; spd < timeoffset.size(); spd++) {
                                            if (timeoffset.get(spd) != null && timeoffset.get(spd).equalsIgnoreCase(hourslist.get(j))) {
                                                hourtextview.get(j).setBackgroundColor(Color.parseColor("#999999"));
                                                break;
                                            }
                                        }
                                    }*/

                                    if (counselor_unavail_list != null && counselor_unavail_list.size() > 0) {
                                        for (int sp = 0; sp < counselor_unavail_list.size(); sp++) {
                                            if (counselor_unavail_list.get(sp) != null && counselor_unavail_list.get(sp).equalsIgnoreCase(hourslist.get(j))) {
                                                {
                                                    hourtextview.get(j).setBackgroundColor(Color.parseColor("#999999"));
                                                }
                                            }
                                        }
                                    }

                                }


                                if (blockhourfrom == index) {
                                    String sephourmi[] = counselor_unaval_time_form.split(":");

                                    is_from_block = true;
                                    blockminute = Integer.parseInt(sephourmi[1]);

                                    if (blockminute == 0)
                                        blockminute = -1;

                                    setminutelist();

                                } else if (blockhourto == index) {
                                    String sephourmi[] = counselunaval_timeto.split(":");
                                    is_from_block_to = true;
                                    blockminuteto = Integer.parseInt(sephourmi[1]);
                                    if (blockminuteto == 0)
                                        blockminuteto = -1;
                                    setminutelist();
                                }

                            } else
                                Toast.makeText(ct, "Sorry time slot is not available", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                hourtextview.add(hourtext);
                TextView marginlaout = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 200);
                marginlaout.setLayoutParams(lp);
                hours.addView(hourtext);
                hours.addView(marginlaout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    boolean is_from_block = false;

    boolean is_from_block_from = false;
    boolean is_from_block_to = false;

    private void setminutelist() {
        try {

            SelectedMinute = "";

            if (minutes != null)
                minutes.removeAllViews();

            if (minutetextview != null)
                minutetextview = null;

            minutetextview = new ArrayList<TextView>();
            minutelist = new ArrayList<String>();

            for (int i = 0; i < 60; ) {


                if (i < 10)
                    minutelist.add("0" + i);
                else
                    minutelist.add("" + i);

                i = i + 5;

            }
            for (int i = 0; i < minutelist.size(); i++) {
                TextView hourtext = new TextView(ct);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 9);
                lp.setMargins(0, 1, 0, 0);
                hourtext.setLayoutParams(lp);
                hourtext.setPadding(width / 50, width / 50, width / 50, width / 50);
                hourtext.setTextColor(Color.parseColor("#ffffff"));
                hourtext.setTextSize(18);
                hourtext.setGravity(Gravity.CENTER);

                /*if(selectminute/5 == i)
                    hourtext.setBackgroundResource(R.drawable.ic_web_bg);
                else*/
                if (is_from_block && blockminute >= Integer.parseInt(minutelist.get(i)))
                    hourtext.setBackgroundColor(Color.parseColor("#999999"));
                else
                    hourtext.setBackgroundColor(Color.parseColor("#111111"));


                if (is_from_block_from && block_min_from <= Integer.parseInt(minutelist.get(i)))
                    hourtext.setBackgroundColor(Color.parseColor("#999999"));

                if (is_from_block_to && blockminuteto >= Integer.parseInt(minutelist.get(i)))
                    hourtext.setBackgroundColor(Color.parseColor("#999999"));


                hourtext.setId(i + 1);
                hourtext.setText(minutelist.get(i));

                hourtext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int index = v.getId() - 1;
                        try {


                            if (is_from_block_from && block_min_from <= Integer.parseInt(minutelist.get(index))) {
                                Toast.makeText(ct, "Sorry time slot is not available", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (is_from_block_to && blockminuteto >= Integer.parseInt(minutelist.get(index))) {
                                Toast.makeText(ct, "Sorry time slot is not available", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (is_from_block && (blockminute < Integer.parseInt(minutelist.get(index)))) {
                                //Minute = minutelist.get(index);
                                SelectedMinute = minutelist.get(index);
                                for (int j = 0; j < minutetextview.size(); j++) {
                                    if (blockminute < Integer.parseInt(minutelist.get(j))) {
                                        if (j == index)
                                            minutetextview.get(j).setBackgroundResource(R.drawable.ic_web_bg);
                                        else
                                            minutetextview.get(j).setBackgroundColor(Color.parseColor("#111111"));
                                    }

                                }
                            } else if (is_from_block) {
                                Toast.makeText(ct, "Sorry time slot is not available", Toast.LENGTH_SHORT).show();
                            } else {
                                SelectedMinute = minutelist.get(index);
                                for (int j = 0; j < minutetextview.size(); j++) {
                                    if (j == index)
                                        minutetextview.get(j).setBackgroundResource(R.drawable.ic_web_bg);
                                    else
                                        minutetextview.get(j).setBackgroundColor(Color.parseColor("#111111"));

                                    if (is_from_block_from && block_min_from <= Integer.parseInt(minutelist.get(j)))
                                        minutetextview.get(j).setBackgroundColor(Color.parseColor("#999999"));

                                    if (is_from_block_to && blockminuteto >= Integer.parseInt(minutelist.get(j)))
                                        minutetextview.get(j).setBackgroundColor(Color.parseColor("#999999"));

                                }


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                minutetextview.add(hourtext);

                TextView marginlaout = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 200);
                // marginlaout.setMargins(0, 1, 0, 0);
                marginlaout.setLayoutParams(lp);

                minutes.addView(hourtext);
                minutes.addView(marginlaout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static int getcounselldatediff(String counselldate, String selecteddate) {
        int day = 0;
        try {
            String outputPattern = "MM/dd/yyyy";
            SimpleDateFormat format = new SimpleDateFormat(outputPattern);


            Date Date1 = format.parse(counselldate);
            Date Date2 = format.parse(selecteddate);
            long mills = Date2.getTime() - Date1.getTime();
            long Day1 = mills / (1000 * 60 * 60);

            day = (int) Day1 / 24;


            if (day < 0)
                day = 0;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }


    public static int Getdatediff(String time) {
        int day = 0;
        try {
            String outputPattern = "MM/dd/yyyy";
            SimpleDateFormat format = new SimpleDateFormat(outputPattern);


            Date Date1 = format.parse(CompareDate);
            Date Date2 = format.parse(time);
            long mills = Date2.getTime() - Date1.getTime();
            long Day1 = mills / (1000 * 60 * 60);

            day = (int) Day1 / 24;


            if (day < 0)
                day = 0;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }


    public static int show(String time) {
        int day = 0;
        try {
            String outputPattern = "MM/dd/yyyy";
            SimpleDateFormat format = new SimpleDateFormat(outputPattern);


            Date Date1 = format.parse(getdate());
            Date Date2 = format.parse(time);
            long mills = Date2.getTime() - Date1.getTime();
            long Day1 = mills / (1000 * 60 * 60);

            day = (int) Day1 / 24;


            if (day < 0)
                day = 0;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }


    public static String getdate() {
        String time = "";
        try {
            String outputPattern = "MM/dd/yyyy";

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat(outputPattern);
            time = df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return time;

    }


    private void isSameCounseller() {

        //show_bookdate.setText(Dayname + " " + daymont + " " + MonthName + ", " + YearName + " " + Hour + ":" + Minute + " " + Ampmtext);
        try {


            AlertDialog.Builder updateAlert = new AlertDialog.Builder(ct);
            updateAlert.setIcon(R.drawable.ic_launcher);
            updateAlert.setTitle("Cypher");
            updateAlert.setMessage("Do you want counselling with last Counsellor?");

            updateAlert.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            try {
                                // progress_bar.setVisibility(View.VISIBLE);

                                is_samecounseller = "true";
                                start_booking_layout.setVisibility(View.GONE);
                                booking_appoint_layout.setVisibility(View.GONE);
                                booking_lyout.setVisibility(View.VISIBLE);
                                new GetCounsellorAppointment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                /*ParseQuery<ParseObject> qrcode1 = ParseQuery.getQuery("Appointment");
                                qrcode1.whereEqualTo("Counsellorid", counseller_id);
                                qrcode1.whereEqualTo("Status", "Accepted");


                                ParseQuery qrcode = ParseQuery.getQuery("Appointment");
                                qrcode.whereEqualTo("Counsellorid", counseller_id);
                                qrcode.whereEqualTo("Status", "Pending");


                                List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

                                queries.add(qrcode1);
                                queries.add(qrcode);

                                ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);


                                mainQuery.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        progress_bar.setVisibility(View.GONE);


                                        if (objects != null && objects.size() > 0) {

                                            if (counselleracceptlist != null)
                                                counselleracceptlist = null;

                                            counselleracceptlist = new ArrayList<ParseObject>();

                                            for (int k = 0; k < objects.size(); k++) {
                                                counselleracceptlist.add(objects.get(k));
                                                counselleracceptlist.get(k).put("appointmentdate", getLocalTime(objects.get(k).getString("appointmentdate")));

                                            }

                                        }


                                    }
                                });*/


                            } catch (Exception e) {
                                progress_bar.setVisibility(View.GONE);
                                e.printStackTrace();
                            }


                        }
                    });

            updateAlert.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            is_samecounseller = "false";
                            counseller_id = "";
                            start_booking_layout.setVisibility(View.GONE);
                            booking_appoint_layout.setVisibility(View.GONE);
                            booking_lyout.setVisibility(View.VISIBLE);


                        }
                    });

            AlertDialog alertUp = updateAlert.create();
            alertUp.setCanceledOnTouchOutside(false);
            alertUp.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int Gettimediff() {
        int Mins = 0;
        try {

            if (time_suggestby_counseller != null && !time_suggestby_counseller.equalsIgnoreCase("")) {
                if (time_suggestby_counseller.length() > 5) {
                    String time[] = time_suggestby_counseller.split(":");
                    if (time != null && time.length > 0) {
                        time_suggestby_counseller = time[0] + ":" + time[1];
                    }
                }
                LiveCounselling.bookingtime = time_suggestby_counseller;


                SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                Date Date2 = format.parse(mytime);

                Date Date1 = format.parse(time_suggestby_counseller);

                if (Date2.getTime() > Date1.getTime()) {


                    return -20;

                } else {
                    long differenceInMins = Math.abs(Date1.getTime() - Date2.getTime()) / 1000 / 60;
                    Mins = (int) differenceInMins;

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Mins;
    }


    private void getcounselorunavailable() {
        try {

            // progress_bar.setVisibility(View.VISIBLE);
            // new GetAppointment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            /*ParseQuery<ParseObject> qrcode1 = ParseQuery.getQuery("Agencies");
            qrcode1.whereEqualTo("objectId", MainActivity.AgencyId);

            qrcode1.findInBackground(new FindCallback<ParseObject>()
            {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {


                    if (objects != null && objects.size() > 0) {

                        try {
                            ParseObject counseller = objects.get(0);

                            String tp = counseller.getString("to");
                            String from = counseller.getString("from");

                            counselunaval_timeto = getLocalTimewithsecond(counseller.getString("to"));

                            counselor_unaval_time_form = getLocalTimewithsecond(counseller.getString("from"));

                            boolean is_counsel = counseller.getBoolean("isUnavailable");


                            if(tp!= null && tp.equalsIgnoreCase("23:00") && from!= null && from.equalsIgnoreCase("00:00") )
                            {
                                is_block_all = true;

                            }
                            else
                            {
                                if (is_counsel)
                                {
                                    is_counsellor_unavailable = true;
                                    System.out.println("Booking.. from : " + counselor_unaval_time_form);
                                    System.out.println("Booking.. timeto : " + counselunaval_timeto);

                                }

                            }





                        } catch (Exception e1) {
                            e1.printStackTrace();

                        }

                    }

                    getAppintment();


                }
            });*/

            //getAppintment();
        } catch (Exception e) {
            progress_bar.setVisibility(View.GONE);
            // getAppintment();
            e.printStackTrace();
        }

    }


    private Calendar current;
    private long miliSeconds;
    private Date resultdate;

    private String getGMTTime(String datetime) {
        String result = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "MM/dd/yyyy HH:mm");
            Date myDate = null;
            try {
                myDate = dateFormat.parse(datetime);

            } catch (Exception e) {
                e.printStackTrace();
            }
            current = Calendar.getInstance();
            current.setTime(myDate);
            miliSeconds = current.getTimeInMillis();
            TimeZone tzCurrent = current.getTimeZone();
            int offset = tzCurrent.getRawOffset();
            if (tzCurrent.inDaylightTime(new Date())) {
                offset = offset + tzCurrent.getDSTSavings();
            }
            miliSeconds = miliSeconds - offset;
            resultdate = new Date(miliSeconds);
            SimpleDateFormat destFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            TimeZone london = TimeZone.getTimeZone("Europe/London");
            long now = resultdate.getTime();
            Date resultdate1 = new Date(miliSeconds + london.getOffset(now));
            result = destFormat.format(resultdate1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    private String getLocalTime(String datetime) {
        String result = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "MM/dd/yyyy HH:mm");
            Date myDate = null;
            try {
                myDate = dateFormat.parse(datetime);

            } catch (Exception e) {
                e.printStackTrace();
            }
            current = Calendar.getInstance();


            TimeZone tzCurrent = current.getTimeZone();
            int offset = tzCurrent.getRawOffset();
            if (tzCurrent.inDaylightTime(new Date())) {
                offset = offset + tzCurrent.getDSTSavings();
            }

            Calendar current1 = Calendar.getInstance();

            current1.setTime(myDate);
            miliSeconds = current1.getTimeInMillis();
            miliSeconds = miliSeconds + offset;
            resultdate = new Date(miliSeconds);
            SimpleDateFormat destFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            TimeZone london = TimeZone.getTimeZone("Europe/London");
            long now = resultdate.getTime();
            Date resultdate1 = new Date(miliSeconds - london.getOffset(now));
            result = destFormat.format(resultdate1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    private class GetCurrentDateTime extends android.os.AsyncTask<String, String, Bitmap> {





       /* @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }*/

        protected Bitmap doInBackground(String... args) {
            try {


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {


        }
    }


    private String getLocalTimewithsecond(String datetime) {
        String result = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "HH:mm");
            Date myDate = null;
            try {
                myDate = dateFormat.parse(datetime);

            } catch (Exception e) {
                e.printStackTrace();
            }
            current = Calendar.getInstance();


            TimeZone tzCurrent = current.getTimeZone();
            int offset = tzCurrent.getRawOffset();
            if (tzCurrent.inDaylightTime(new Date())) {
                offset = offset + tzCurrent.getDSTSavings();
            }

            Calendar current1 = Calendar.getInstance();

            current1.setTime(myDate);
            miliSeconds = current1.getTimeInMillis();
            miliSeconds = miliSeconds + offset;
            resultdate = new Date(miliSeconds);
            SimpleDateFormat destFormat = new SimpleDateFormat("HH:mm");
            TimeZone london = TimeZone.getTimeZone("Europe/London");
            long now = resultdate.getTime();
            Date resultdate1 = new Date(miliSeconds - london.getOffset(now));
            result = destFormat.format(resultdate1);


            ;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    String block_day = "";

    private int getOffsetTime() {
        String result = "";
        int hoursoofest = 0;
        try {

            long totalhours = 0, offsetlong = 0;
            current = Calendar.getInstance();
            miliSeconds = current.getTimeInMillis();
            TimeZone tzCurrent = current.getTimeZone();
            int offset = tzCurrent.getRawOffset();
            if (tzCurrent.inDaylightTime(new Date())) {
                offset = offset + tzCurrent.getDSTSavings();
            }


            if (offset >= 0) {
                if (offset != 0) {
                    hoursoofest = (int) offset / 3600000;
                    hoursoofest++;
                    block_day = "Monday";
                } else {
                    hoursoofest = 0;
                    block_day = "";
                }


            } else {
                hoursoofest = (int) offset / 3600000;
                hoursoofest = -(hoursoofest);
                hoursoofest++;
                block_day = "Saturday";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hoursoofest;
    }

    protected void startTracking(String path) {
        Tracker t = ((SilentSecretApplication) getApplication()).getTracker(
                SilentSecretApplication.TrackerName.APP_TRACKER);
        t.setScreenName(path);
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    public void initWebView(String url) {

        webview.getSettings().setJavaScriptEnabled(true);


        webview.loadUrl(url);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                webview.loadUrl(url);
                return false;

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, final String url) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }
        });
    }

    JSONObject UserInfoobj = null;

    private class GetAppointment extends android.os.AsyncTask<String, String, Bitmap> {

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
                IfriendRequest http = new IfriendRequest(ct);
                FindbyNameDTO findbyNameDTO = new FindbyNameDTO(MainActivity.enc_username);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(findbyNameDTO, "getLastAppointment");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                String status = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true")) {
                        JSONArray json_array = null;
                        if (jsonObject.has("data"))
                            json_array = jsonObject.getJSONArray("data");

                        if (json_array != null && json_array.length() > 0) {

                            MainActivity.is_booking = true;
                            UserInfoobj = new JSONObject(json_array.getString(0));

                            if (UserInfoobj.has("mode")) {
                                suggestedby_counseller_chat_mode = UserInfoobj.getString("mode");
                                Mode = UserInfoobj.getString("mode");
                            }
                            LiveCounselling.chat_mode = suggestedby_counseller_chat_mode;

                            if (suggestedby_counseller_chat_mode != null && suggestedby_counseller_chat_mode.equalsIgnoreCase("Video")) {
                                IncomingCallScreenActivity.is_video_call = true;
                            } else {
                                IncomingCallScreenActivity.is_video_call = false;
                            }
                            String req_status = "";
                            if (UserInfoobj.has("status"))
                                req_status = UserInfoobj.getString("status");

                            if (UserInfoobj.has("impact_question"))
                                LiveCounselling.submited_impact = UserInfoobj.getBoolean("impact_question");

                            String appointmentdate = "";
                            if (UserInfoobj.has("apntmnt_date"))
                                appointmentdate = getLocalTime(UserInfoobj.getString("apntmnt_date"));

                            String suggested_by = "";
                            if (UserInfoobj.has("suggested_by"))
                                suggested_by = UserInfoobj.getString("suggested_by");

                            if (UserInfoobj.has("apntmnt_id"))
                                LiveCounselling.appointmentid = UserInfoobj.getString("apntmnt_id");

                            if (UserInfoobj.has("clcnslrun01"))
                                LiveCounselling.counsellerid = UserInfoobj.getString("clcnslrun01");
                            //  Suggested_Object = mTRUE;


                            try {
                                String outputPattern = "";
                                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                                Date date2 = null;
                                date2 = format.parse(appointmentdate);
                                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                                SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                                Dayname = outFormat.format(date2);
                                MonthName = month_date.format(date2);

                                Dayname = Dayname.substring(0, 3);
                                MonthName = MonthName.substring(0, 3);

                            } catch (java.text.ParseException e2) {
                                // TODO Auto-generated catch block
                                e2.printStackTrace();
                            }

                            try {
                                String appint_array[] = appointmentdate.split(" ");
                                if (appint_array != null && appint_array.length > 0) {
                                    date_suggestby_counsel = appint_array[0];

                                    String datesplitarray[] = date_suggestby_counsel.split("/");

                                    if (datesplitarray != null && datesplitarray.length > 2) {
                                        day_suugested_bycouselor = datesplitarray[1];
                                        Month_suggested_bycounsellor = datesplitarray[0];
                                        year_suggested_bycounselor = datesplitarray[2];
                                    }

                                    time_suggestby_counseller = appint_array[1];


                                    String sephourmi[] = time_suggestby_counseller.split(":");
                                    if (sephourmi != null && sephourmi.length > 0) {
                                        hour_suggestby_counseller = sephourmi[0];
                                        minut_suugestby_counseller = sephourmi[1];

                                        int hourint = Integer.parseInt(hour_suggestby_counseller);

                                        if (hourint > 11) {
                                            if (hourint == 12) {
                                                hour_suggestby_counseller = "" + hourint;
                                            } else {
                                                hourint = hourint - 12;
                                                hour_suggestby_counseller = "" + hourint;

                                                if (hourint > 9)
                                                    hour_suggestby_counseller = "" + hourint;
                                                else
                                                    hour_suggestby_counseller = "0" + hourint;

                                            }
                                            ampmtext_suggestby_counseller = "PM";

                                        } else {
                                            if (hourint >= 10)
                                                hour_suggestby_counseller = "" + hourint;
                                            else
                                                hour_suggestby_counseller = "0" + hourint;

                                            ampmtext_suggestby_counseller = "AM";
                                        }

                                    }

                                }

                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }


                            if (req_status != null && req_status.equalsIgnoreCase("Pending") && suggested_by != null && suggested_by.equalsIgnoreCase("Counsellor")) {
                                AlertDialog.Builder updateAlert = new AlertDialog.Builder(ct);
                                updateAlert.setIcon(R.drawable.ic_launcher);
                                updateAlert.setTitle("Cypher");


                                String counsellor_f_name = "";
                                try {
                                    counsellor_f_name = UserInfoobj.getString("counsellor_firstname");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (counsellor_f_name == null || counsellor_f_name.equalsIgnoreCase("") || counsellor_f_name.equalsIgnoreCase("null"))
                                    updateAlert.setMessage("Your Counsellor has suggested a new time for appointment." + Dayname + " " + day_suugested_bycouselor + " " + MonthName + ", " + year_suggested_bycounselor + " " + hour_suggestby_counseller + ":" + minut_suugestby_counseller + " " + ampmtext_suggestby_counseller + "\n" + "Please accept or suggest new time .");
                                else {
                                    String counse_nme = CryptLib.decrypt(counsellor_f_name);
                                    counse_nme = counse_nme.substring(0, 1).toUpperCase() + counse_nme.substring(1);
                                    updateAlert.setMessage(counse_nme + " ( Counsellor ) " + "has suggested a new time for appointment." + Dayname + " " + day_suugested_bycouselor + " " + MonthName + ", " + year_suggested_bycounselor + " " + hour_suggestby_counseller + ":" + minut_suugestby_counseller + " " + ampmtext_suggestby_counseller + "\n" + "Please accept or suggest new time .");
                                }
                                updateAlert.setPositiveButton(
                                        "Accept",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                try {

                                                    progress_bar.setVisibility(View.VISIBLE);
                                                    Suggested_Object = UserInfoobj;
                                                    appointmentCancelDTO = new AppointmentCancelDTO(Suggested_Object.getString("clcnslrun01"), "Accepted", Suggested_Object.getString("apntmnt_id"), "User", MainActivity.enc_username);
                                                    new DeniedAppinment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                                } catch (Exception e) {
                                                    progress_bar.setVisibility(View.GONE);
                                                    e.printStackTrace();
                                                }


                                            }
                                        });

                                updateAlert.setNegativeButton(
                                        "Suggest new time",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();


                                                Suggested_Object = UserInfoobj;

                                                book_appointment.setText("Suggest new time");
                                                booking_appoint_layout.setVisibility(View.GONE);
                                                booking_lyout.setVisibility(View.VISIBLE);
                                                start_booking_layout.setVisibility(View.GONE);
                                                progress_bar.setVisibility(View.GONE);
                                                try {


                                                    // int day_ago = show(date_suggestby_counsel);

                                                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                                                    Date date2 = null;
                                                    date2 = format.parse(date_suggestby_counsel);


                                                    Calendar nextday = Calendar.getInstance();
                                                    nextday.setTime(date2);
                                                    // if (am_pm == 0) {
                                                    nextday.add(Calendar.DAY_OF_MONTH, 0);
                                                    //} else {
                                                    //   nextday.add(Calendar.DAY_OF_MONTH, 2);
                                                    // }
                                                    Calendar nextYear = Calendar.getInstance();
                                                    nextYear.setTime(date2);
                                                    nextYear.add(Calendar.YEAR, 1);

                                                    calendar_view.init(nextday.getTime(), nextYear.getTime())
                                                            .inMode(CalendarPickerView.SelectionMode.SINGLE);


                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                try {
                                                    if (moodgraphtext != null)
                                                        moodgraphtext.setVisibility(View.GONE);

                                                    /*if (Suggested_Object.has("mode"))
                                                    {
                                                        Mode = Suggested_Object.getString("mode");

                                                    }*/


                                                    if (suggestedby_counseller_chat_mode != null && suggestedby_counseller_chat_mode.equalsIgnoreCase("Chat")) {

                                                        chat_text.setBackgroundResource(R.drawable.text_chat);
                                                        text_chat_text.setTextColor(getResources().getColor(R.color.chat_color));

                                                        audio_calling.setBackgroundResource(R.drawable.audio_calling_buller);
                                                        audio_calling_text.setTextColor(getResources().getColor(R.color.buller_color));

                                                        video_calling.setBackgroundResource(R.drawable.video_calling_buller);
                                                        video_calling_text.setTextColor(getResources().getColor(R.color.buller_color));
                                                    } else if (suggestedby_counseller_chat_mode != null && suggestedby_counseller_chat_mode.equalsIgnoreCase("Audio")) {

                                                        chat_text.setBackgroundResource(R.drawable.text_chat_buller);
                                                        text_chat_text.setTextColor(getResources().getColor(R.color.buller_color));

                                                        audio_calling.setBackgroundResource(R.drawable.audio_calling);
                                                        audio_calling_text.setTextColor(getResources().getColor(R.color.audio_color));

                                                        video_calling.setBackgroundResource(R.drawable.video_calling_buller);
                                                        video_calling_text.setTextColor(getResources().getColor(R.color.buller_color));
                                                    } else if (suggestedby_counseller_chat_mode != null && suggestedby_counseller_chat_mode.equalsIgnoreCase("Video")) {

                                                        chat_text.setBackgroundResource(R.drawable.text_chat_buller);
                                                        text_chat_text.setTextColor(getResources().getColor(R.color.buller_color));

                                                        audio_calling.setBackgroundResource(R.drawable.audio_calling_buller);
                                                        audio_calling_text.setTextColor(getResources().getColor(R.color.buller_color));

                                                        video_calling.setBackgroundResource(R.drawable.video_calling);
                                                        video_calling_text.setTextColor(getResources().getColor(R.color.video_color));
                                                    }

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    unselect.setVisibility(View.GONE);
                                                    select_checkbox.setVisibility(View.GONE);
                                                } catch (Exception e) {

                                                }

                                            }
                                        });


                                AlertDialog alertUp = updateAlert.create();
                                alertUp.setCanceledOnTouchOutside(false);
                                alertUp.setCancelable(false);
                                alertUp.show();

                            } else if (req_status != null && req_status.equalsIgnoreCase("Accepted")) {

                                int day_ago = 100;

                                if (CompareDate != null && !CompareDate.equalsIgnoreCase(""))
                                    day_ago = Getdatediff(date_suggestby_counsel);
                                else
                                    day_ago = show(date_suggestby_counsel);


                                if (day_ago <= 0 && req_status != null && req_status.equalsIgnoreCase("Accepted")) {

                                    if (mytime == null || mytime.equalsIgnoreCase("")) {
                                        new GetAppointment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        return;
                                    }

                                    int deff = Gettimediff();

                                    if (deff < 6) {
                                        if (deff > 0) {
                                            LiveCounselling.is_show_start = false;
                                        } else {
                                            LiveCounselling.is_show_start = true;

                                        }
                                        progress_bar.setVisibility(View.GONE);

                                        if (LiveCounselling.livecounsel != null) {

                                            LiveCounselling.livecounsel.finish();
                                        }

                                        MainActivity.is_booking = true;
                                        Intent intent = new Intent(CounsellerBooking.this, LiveCounselling.class);
                                        startActivity(intent);
                                        finish();


                                    } else {
                                        booking_appoint_layout.setVisibility(View.VISIBLE);
                                        booking_lyout.setVisibility(View.GONE);
                                        start_booking_layout.setVisibility(View.GONE);
                                        progress_bar.setVisibility(View.GONE);


                                        String counsellor_f_name = "";
                                        try {
                                            counsellor_f_name = UserInfoobj.getString("counsellor_firstname");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        if (counsellor_f_name != null && !counsellor_f_name.equalsIgnoreCase("") && !counsellor_f_name.equalsIgnoreCase("null")) {
                                            String counse_nme = CryptLib.decrypt(counsellor_f_name);
                                            counse_nme = counse_nme.substring(0, 1).toUpperCase() + counse_nme.substring(1);
                                            app_text.setText("You have an appointment schedule with " + counse_nme + ". You can book another appointment after scheduled one.");

                                        }

                                        counsellingmode.setText(getResources().getString(R.string.appcounselling) + " Mode : " + suggestedby_counseller_chat_mode);
                                        counseldate_time.setText(getResources().getString(R.string.appcounselling) + " Time : " + Dayname + " " + day_suugested_bycouselor + " " + MonthName + ", " + year_suggested_bycounselor + " " + hour_suggestby_counseller + ":" + minut_suugestby_counseller + " " + ampmtext_suggestby_counseller);
                                        ///  counseldate_time.setText("Counselling Time : " + Dayname + " " +   daymont + " "   + MonthName + " " + YearName + " "+ hour_suggestby_counseller + ":" + minut_suugestby_counseller + " " + ampmtext_suggestby_counseller);
                                        request_status.setText(getResources().getString(R.string.appcounselling) + " Request Status : " + req_status);
                                    }
                                } else {

                                    booking_appoint_layout.setVisibility(View.VISIBLE);
                                    booking_lyout.setVisibility(View.GONE);
                                    start_booking_layout.setVisibility(View.GONE);
                                    progress_bar.setVisibility(View.GONE);


                                    counsellingmode.setText(getResources().getString(R.string.appcounselling) + " Mode : " + suggestedby_counseller_chat_mode);
                                    counseldate_time.setText(getResources().getString(R.string.appcounselling) + " Time : " + Dayname + " " + day_suugested_bycouselor + " " + MonthName + ", " + year_suggested_bycounselor + " " + hour_suggestby_counseller + ":" + minut_suugestby_counseller + " " + ampmtext_suggestby_counseller);
                                    request_status.setText(getResources().getString(R.string.appcounselling) + " Request Status : " + req_status);
                                }
                            } else if (req_status != null && (req_status.equalsIgnoreCase("Pending") || req_status.equalsIgnoreCase("Denied"))) {
                                booking_appoint_layout.setVisibility(View.VISIBLE);
                                booking_lyout.setVisibility(View.GONE);
                                start_booking_layout.setVisibility(View.GONE);
                                progress_bar.setVisibility(View.GONE);

                                String counsellor_f_name = "";
                                try {
                                    counsellor_f_name = UserInfoobj.getString("counsellor_firstname");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (counsellor_f_name != null && !counsellor_f_name.equalsIgnoreCase("") && !counsellor_f_name.equalsIgnoreCase("null")) {
                                    String counse_nme = CryptLib.decrypt(counsellor_f_name);
                                    counse_nme = counse_nme.substring(0, 1).toUpperCase() + counse_nme.substring(1);

                                    app_text.setText("You have an appointment schedule with " + counse_nme + ". You can book another appointment after scheduled one.");

                                }

                                counsellingmode.setText(getResources().getString(R.string.appcounselling) + " Mode : " + suggestedby_counseller_chat_mode);
                                counseldate_time.setText(getResources().getString(R.string.appcounselling) + " Time : " + Dayname + " " + day_suugested_bycouselor + " " + MonthName + ", " + year_suggested_bycounselor + " " + hour_suggestby_counseller + ":" + minut_suugestby_counseller + " " + ampmtext_suggestby_counseller);
                                request_status.setText(getResources().getString(R.string.appcounselling) + " Request Status : " + "Pending");


                            } else
                                new CheckPendingRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                        } else {

                            MainActivity.is_booking = false;
                            progress_bar.setVisibility(View.GONE);
                            start_booking_layout.setVisibility(View.VISIBLE);
                            booking_appoint_layout.setVisibility(View.GONE);
                            booking_lyout.setVisibility(View.GONE);
                            if (MainActivity.session_left <= 0 && MainActivity.is_booking == false) {
                                Intent intent = new Intent(ct, CameraTestActivity.class);
                                startActivity(intent);
                                finish();
                            }


                        }

                    } else {

                    }
                }

            } catch (Exception e) {
                booking_appoint_layout.setVisibility(View.VISIBLE);
                booking_lyout.setVisibility(View.GONE);
                start_booking_layout.setVisibility(View.GONE);
                progress_bar.setVisibility(View.GONE);
                e.printStackTrace();
            }

        }
    }


   /* private class GetUnavailable extends android.os.AsyncTask<String, String, Bitmap> {

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
                IfriendRequest http = new IfriendRequest(ct);

                GetUnavailableDataDTO findbyNameDTO = new GetUnavailableDataDTO(Agency_id);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(findbyNameDTO, "getUnavailableTimeAgency");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

        }
    }*/


    private class CheckPendingRating extends android.os.AsyncTask<String, String, Bitmap> {

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
                IfriendRequest http = new IfriendRequest(ct);
                FindbyNameDTO findbyNameDTO = new FindbyNameDTO(MainActivity.enc_username);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(findbyNameDTO, "checkUserRatting");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                progress_bar.setVisibility(View.GONE);

                String status = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true")) {
                        JSONArray json_array = null;
                        if (jsonObject.has("data"))
                            json_array = jsonObject.getJSONArray("data");

                        if (json_array != null && json_array.length() > 0) {
                            LiveCounselling.is_show_rating = true;
                            if (LiveCounselling.livecounsel != null) {

                                LiveCounselling.livecounsel.finish();
                            }

                            Intent booking = new Intent(ct, LiveCounselling.class);
                            startActivity(booking);
                            finish();

                        } else {
                            String req_status = "";
                            if (UserInfoobj.has("status"))
                                req_status = UserInfoobj.getString("status");

                            if (req_status != null && req_status.equalsIgnoreCase("Completed")) {
                                if (UserInfoobj.has("impact_questions"))
                                    LiveCounselling.submited_impact = UserInfoobj.getBoolean("impact_questions");

                                String appointmentdate = "";
                                if (UserInfoobj.has("apntmnt_date"))
                                    appointmentdate = getLocalTime(UserInfoobj.getString("apntmnt_date"));

                                String agencyId = "";

                                if (UserInfoobj.has("agnc_unq_id"))
                                    agencyId = UserInfoobj.getString("agnc_unq_id");

                                //  MainActivity.AgencyId = AppSession.getValue(ct, Constants.AGENCY_ID);
                                if (agencyId != null && agencyId.equalsIgnoreCase(Agency_id)) {
                                    counseller_id = UserInfoobj.getString("counc_unq_id");
                                    counsellor_name = UserInfoobj.getString("clcnslrun01");
                                } else
                                    counseller_id = "";


                                Calendar nextYear = Calendar.getInstance();
                                nextYear.add(Calendar.YEAR, 1);

                                Date completed_date = CommonFunction.StringtoDate(appointmentdate);
                                Calendar nextday = Calendar.getInstance();

                                if (completed_date != null)
                                    nextday.setTime(completed_date);

                                nextday.add(Calendar.DAY_OF_MONTH, 7);
                                calendar_view.init(nextday.getTime(), nextYear.getTime())
                                        .inMode(CalendarPickerView.SelectionMode.SINGLE);

                                start_booking_layout.setVisibility(View.VISIBLE);
                                booking_appoint_layout.setVisibility(View.GONE);
                                booking_lyout.setVisibility(View.GONE);

                                MainActivity.is_booking = false;
                                if (MainActivity.session_left <= 0 && MainActivity.is_booking == false) {
                                    Intent intent = new Intent(ct, CameraTestActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    session_left.setText("You have " + MainActivity.session_left + " session left");
                                }

                            } else {
                                MainActivity.is_booking = false;

                                start_booking_layout.setVisibility(View.VISIBLE);
                                booking_appoint_layout.setVisibility(View.GONE);
                                booking_lyout.setVisibility(View.GONE);
                                if (MainActivity.session_left <= 0 && MainActivity.is_booking == false) {
                                    Intent intent = new Intent(ct, CameraTestActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }


                    }
                }
            } catch (
                    Exception e
                    )

            {
                e.printStackTrace();
            }

        }

    }

    private class BookFreshAppointment extends android.os.AsyncTask<String, String, Bitmap> {

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
                IfriendRequest http = new IfriendRequest(ct);
                //   BookAppointmentDataDTO findbyNameDTO = new BookAppointmentDataDTO(bookAppointmentDataDTO);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(bookAppointmentDataDTO, "bookAnApntmnt");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {


                if (response != null && response.contains("true")) {

                    MainActivity.is_booking = true;
                    MainActivity.session_left = MainActivity.session_left - 1;
                    AppSession.save(ct, Constants.USER_SESSION_LEFT, "" + MainActivity.session_left);
                    try {
                        progress_bar.setVisibility(View.GONE);
                        booking_appoint_layout.setVisibility(View.VISIBLE);
                        booking_lyout.setVisibility(View.GONE);
                        start_booking_layout.setVisibility(View.GONE);

                        String datesplitarray[] = Final_date.split("/");

                        String day = "", Month = "", yyear = "";
                        if (datesplitarray != null && datesplitarray.length > 2) {
                            day = datesplitarray[1];
                            Month = datesplitarray[0];
                            yyear = datesplitarray[2];
                        }


                        String counsellor_f_name = "";
                        try {
                            counsellor_f_name = final_list.get(select_counce_index).getCounce_firstname();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (counsellor_f_name != null && !counsellor_f_name.equalsIgnoreCase("") && !counsellor_f_name.equalsIgnoreCase("null")) {
                            String counse_nme = CryptLib.decrypt(counsellor_f_name);
                            counse_nme = counse_nme.substring(0, 1).toUpperCase() + counse_nme.substring(1);
                            app_text.setText("You have an appointment schedule with " + counse_nme + ". You can book another appointment after scheduled one.");

                        }


                        counsellingmode.setText(getResources().getString(R.string.appcounselling) + " Mode : " + Mode);
                        request_status.setText(getResources().getString(R.string.appcounselling) + " Request Status : Pending");
                        counseldate_time.setText(getResources().getString(R.string.appcounselling) + " Time : " + Dayname + " " + day + " " + MonthName + ", " + yyear + " " + Hour + ":" + Minute + " " + Ampmtext);
                        // counseldate_time.setText("Counselling Time : " + day + "/" + Month + "/" + yyear + " " + Hour + ":" + Minute + " " + Ampmtext);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    progress_bar.setVisibility(View.GONE);
                    Toast.makeText(ct, "Booking Failed", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class BookNextAppointment extends android.os.AsyncTask<String, String, Bitmap> {

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
                IfriendRequest http = new IfriendRequest(ct);
                //   BookAppointmentDataDTO findbyNameDTO = new BookAppointmentDataDTO(bookAppointmentDataDTO);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(bookAppointmentDataDTO, "bookAnApntmnt");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                AppSession.save(ct, Constants.NEXT_APPOINTMENT_BOOKED, "true");


                Intent intent = new Intent(CounsellerBooking.this, LiveCounselling.class);
                startActivity(intent);

                finish();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class AcceptTermsCondition extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  progress_bar.setVisibility(View.VISIBLE);
        }


        protected Bitmap doInBackground(String... args) {
            try {
                IfriendRequest http = new IfriendRequest(ct);

                AcceptTermsConditionDataDTO findbyNameDTO = new AcceptTermsConditionDataDTO(MainActivity.enc_username);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(findbyNameDTO, "AcceptTC");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response = http.Getappointment(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                is_tandC_submit = true;

                String status = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true")) {
                        AppSession.save(ct, Constants.USER_TERMSCONDITION, "true");
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            // new GetAppointment().execute();
        }
    }


    private class SuggestNewTime extends android.os.AsyncTask<String, String, Bitmap> {

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
                IfriendRequest http = new IfriendRequest(ct);

                CommonRequestTypeDTO commonRequestDTO = new CommonRequestTypeDTO(suggestNewTimeDTO, "suggestionAction");
                FinalObjectDTO suggestNewTimeObjectDTO = new FinalObjectDTO(commonRequestDTO);

                response = http.SuggestTime(suggestNewTimeObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                String status = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true")) {
                        try {
                            MainActivity.is_booking = true;
                            booking_appoint_layout.setVisibility(View.VISIBLE);
                            booking_lyout.setVisibility(View.GONE);
                            start_booking_layout.setVisibility(View.GONE);
                            progress_bar.setVisibility(View.GONE);


                            String datesplitarray[] = Final_date.split("/");

                            String day = "", Month = "", yyear = "";
                            if (datesplitarray != null && datesplitarray.length > 2) {
                                day = datesplitarray[1];
                                Month = datesplitarray[0];
                                yyear = datesplitarray[2];
                            }


                            String counsellor_f_name = "";
                            try {
                                counsellor_f_name = Suggested_Object.getString("counsellor_firstname");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (counsellor_f_name != null && !counsellor_f_name.equalsIgnoreCase("") && !counsellor_f_name.equalsIgnoreCase("null")) {
                                String counse_nme = CryptLib.decrypt(counsellor_f_name);
                                counse_nme = counse_nme.substring(0, 1).toUpperCase() + counse_nme.substring(1);
                                app_text.setText("You have an appointment schedule with " + counse_nme + ". You can book another appointment after scheduled one.");

                            }


                            counsellingmode.setText(getResources().getString(R.string.appcounselling) + " Mode : " + suggestedby_counseller_chat_mode);
                            counseldate_time.setText(getResources().getString(R.string.appcounselling) + " Time : " + day + "/" + Month + "/" + yyear + " " + Hour + ":" + Minute + " " + Ampmtext);
                            request_status.setText(getResources().getString(R.string.appcounselling) + " Request Status : " + "Pending");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class DeniedAppinment extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(ct);
                CommonRequestTypeDTO appointmentCancelObjectDTO = new CommonRequestTypeDTO(appointmentCancelDTO, "appointmentAction");
                FinalObjectDTO finalObjectDTO = new FinalObjectDTO(appointmentCancelObjectDTO);
                response = http.AcceptAppointment(finalObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                progress_bar.setVisibility(View.GONE);

                String status = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true")) {
                        try {
                            booking_appoint_layout.setVisibility(View.VISIBLE);
                            booking_lyout.setVisibility(View.GONE);
                            start_booking_layout.setVisibility(View.GONE);
                            progress_bar.setVisibility(View.GONE);


                            counsellingmode.setText(getResources().getString(R.string.appcounselling) + " Mode : " + suggestedby_counseller_chat_mode);
                            counseldate_time.setText(getResources().getString(R.string.appcounselling) + " Time : " + Dayname + " " + day_suugested_bycouselor + " " + MonthName + ", " + year_suggested_bycounselor + " " + hour_suggestby_counseller + ":" + minut_suugestby_counseller + " " + ampmtext_suggestby_counseller);
                            request_status.setText(getResources().getString(R.string.appcounselling) + " Request Status : " + "Accepted");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class ChecUserSessionfirsttime extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;
        String response = "";


        protected Bitmap doInBackground(String... args) {
            try {
                IfriendRequest http = new IfriendRequest(ct);
                CheckUserSessionDataDTO findbyNameDTO = new CheckUserSessionDataDTO(MainActivity.enc_username);
                CheckUserSessionDTO checkUserSessionDTO = new CheckUserSessionDTO(findbyNameDTO);
                CheckUserSessionObjectDTO findbynameObjectDTO = new CheckUserSessionObjectDTO(checkUserSessionDTO);
                response = http.CheckUserSession(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                String status = "", qid = "", agency_unq_id = "", agent_unq_id = "", Session_left = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true")) {
                        JSONArray json_array = null;
                        if (jsonObject.has("data"))
                            json_array = jsonObject.getJSONArray("data");

                        for (int i = 0; i < json_array.length(); i++) {

                            JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                            if (UserInfoobj.has("qid"))
                                qid = UserInfoobj.getString("qid");

                            if (UserInfoobj.has("agency_unq_id"))
                                agency_unq_id = UserInfoobj.getString("agency_unq_id");

                            if (UserInfoobj.has("agent_unq_id"))
                                agent_unq_id = UserInfoobj.getString("agent_unq_id");

                            if (UserInfoobj.has("session_left"))
                                Session_left = UserInfoobj.getString("session_left");


                            AppSession.save(ct, Constants.USED_QR_CODE, qid);
                            AppSession.save(ct, Constants.AGENCY_ID, agency_unq_id);
                            AppSession.save(ct, Constants.AGENT_UNIQUE_ID, agent_unq_id);
                            AppSession.save(ct, Constants.USER_SESSION_LEFT, Session_left);

                            MainActivity.session_left = Integer.parseInt(Session_left);

                            session_left.setText("You have " + MainActivity.session_left + " session left");

                        }

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class GetCounsellorAppointment extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(ct);
                GetCounsellorAppointmentDTO getCounsellorAppointmentDTO = new GetCounsellorAppointmentDTO(counsellor_name);
                CommonRequestTypeDTO appointmentCancelObjectDTO = new CommonRequestTypeDTO(getCounsellorAppointmentDTO, "getAppointmentForCounsellor");
                FinalObjectDTO finalObjectDTO = new FinalObjectDTO(appointmentCancelObjectDTO);
                response = http.AcceptAppointment(finalObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                progress_bar.setVisibility(View.GONE);

                String status = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true")) {
                        JSONArray json_array = null;
                        if (jsonObject.has("data"))
                            json_array = jsonObject.getJSONArray("data");

                        if (json_array != null && json_array.length() > 0) {
                            for (int i = 0; i < json_array.length(); i++) {
                                JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                                if (UserInfoobj.has("mode")) {
                                    suggestedby_counseller_chat_mode = UserInfoobj.getString("mode");
                                    Mode = UserInfoobj.getString("mode");
                                }

                                if (counselleracceptlist != null)
                                    counselleracceptlist = null;

                                counselleracceptlist = new ArrayList<String>();

                                if (UserInfoobj.has("apntmnt_date"))
                                    counselleracceptlist.add(getLocalTime(UserInfoobj.getString("apntmnt_date")));

                            }


                        }
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onResume() {
        //  new GetCurrentDateTime().execute();
        progress_bar.setVisibility(View.VISIBLE);
        super.onResume();
        if (ConnectionDetector.isNetworkAvailable(ct))
            executeAsyncTask();
        else
            new ToastUtil(ct, "Please check your internet connection.");
    }


    public void executeAsyncTask() {


        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            String response = "";
            String Unavailableresoponse = "";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress_bar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(Void... params) {

                IfriendRequest http = new IfriendRequest(ct);
                GetCurrentDateTimeDTO allIfriendDTO = new GetCurrentDateTimeDTO("currentTime");
                GetCurrentDateObjectDTO getCurrentDateObjectDTO = new GetCurrentDateObjectDTO(allIfriendDTO);
                response = http.GetDateTime(getCurrentDateObjectDTO);

                GetUnavailableDataDTO findbyNameDTO = new GetUnavailableDataDTO(Agency_id);
                CommonRequestTypeDTO checkUserSessionDTO = new CommonRequestTypeDTO(findbyNameDTO, "getCounsellorForAgency");
                FinalObjectDTO findbynameObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                Unavailableresoponse = http.Getappointment(findbynameObjectDTO);

                return "";
            }

            @Override
            protected void onPostExecute(String msg) {

                progress_bar.setVisibility(View.GONE);
                if (response != null && !response.equalsIgnoreCase("")) {
                    try {
                        response = getLocalTime(response);
                        String[] datetime = response.split(" ");

                        CompareDate = datetime[0];
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "MM/dd/yyyy HH:mm");
                        try {
                            currentdatetime = dateFormat.parse(response);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        setdate_time();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


                try {


                    mondaylist.clear();
                    tuesdaylist.clear();
                    wednesdaylist.clear();
                    thursdaylist.clear();
                    fridaylist.clear();
                    saturdaylist.clear();
                    Sundaylist.clear();


                    String status = "";
                    Object object = new JSONTokener(Unavailableresoponse).nextValue();
                    if (object instanceof JSONObject) {
                        JSONObject jsonObject = new JSONObject(Unavailableresoponse);

                        if (jsonObject.has("status"))
                            status = jsonObject.getString("status");
                        if (status != null && status.equalsIgnoreCase("true")) {
                            JSONArray json_array = null;
                            if (jsonObject.has("data"))
                                json_array = jsonObject.getJSONArray("data");


                            if (json_array != null && json_array.length() > 0) {


                                String clcnslrun01 = "";
                                String to = "";
                                String day = "";
                                String counc_unq_id = "";
                                String agency_unq_id = "";
                                String counce_firstname = "";
                                String available_type = "";
                                String from = "";


                                for (int i = 0; i < json_array.length(); i++) {
                                    JSONObject UserInfoobj = new JSONObject(json_array.getString(i));


                                    if (UserInfoobj.has("clcnslrun01")) {
                                        clcnslrun01 = UserInfoobj.getString("clcnslrun01");
                                    }

                                    if (UserInfoobj.has("to")) {
                                        to = UserInfoobj.getString("to");
                                    }

                                    if (UserInfoobj.has("day")) {
                                        day = UserInfoobj.getString("day");
                                    }

                                    if (UserInfoobj.has("counc_unq_id")) {
                                        counc_unq_id = UserInfoobj.getString("counc_unq_id");
                                    }
                                    if (UserInfoobj.has("agency_unq_id")) {
                                        agency_unq_id = UserInfoobj.getString("agency_unq_id");
                                    }
                                    if (UserInfoobj.has("counce_firstname")) {
                                        counce_firstname = UserInfoobj.getString("counce_firstname");
                                    }
                                    if (UserInfoobj.has("available_type")) {
                                        available_type = UserInfoobj.getString("available_type");
                                    }

                                    if (UserInfoobj.has("from")) {
                                        from = UserInfoobj.getString("from");
                                    }

                                    if(counce_firstname == null || counce_firstname.equalsIgnoreCase(""))
                                        counce_firstname = clcnslrun01;


                                    if (day.contains("Mon"))
                                        mondaylist.add(new SetAvailabilityDataDTO(clcnslrun01, from, to, day, counc_unq_id,
                                                agency_unq_id, counce_firstname, available_type));
                                    else if (day.contains("Tue")) {
                                        tuesdaylist.add(new SetAvailabilityDataDTO(clcnslrun01, from, to, day, counc_unq_id,
                                                agency_unq_id, counce_firstname, available_type));
                                    } else if (day.contains("Wed")) {
                                        wednesdaylist.add(new SetAvailabilityDataDTO(clcnslrun01, from, to, day, counc_unq_id,
                                                agency_unq_id, counce_firstname, available_type));
                                    } else if (day.contains("Thu")) {
                                        thursdaylist.add(new SetAvailabilityDataDTO(clcnslrun01, from, to, day, counc_unq_id,
                                                agency_unq_id, counce_firstname, available_type));
                                    } else if (day.contains("Fri")) {
                                        fridaylist.add(new SetAvailabilityDataDTO(clcnslrun01, from, to, day, counc_unq_id,
                                                agency_unq_id, counce_firstname, available_type));
                                    } else if (day.contains("Sat")) {
                                        saturdaylist.add(new SetAvailabilityDataDTO(clcnslrun01, from, to, day, counc_unq_id,
                                                agency_unq_id, counce_firstname, available_type));
                                    } else if (day.contains("Sun")) {
                                        Sundaylist.add(new SetAvailabilityDataDTO(clcnslrun01, from, to, day, counc_unq_id,
                                                agency_unq_id, counce_firstname, available_type));
                                    }


                                }

                                setDaywiseAvailabilityDataDTO = new SetDaywiseAvailabilityDataDTO(mondaylist, tuesdaylist, wednesdaylist, thursdaylist, fridaylist, saturdaylist, Sundaylist);
                            }
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (!LiveCounselling.is_from_fivr_min_booking)
                    new GetAppointment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else {
                    Calendar nextYear = Calendar.getInstance();
                    nextYear.add(Calendar.YEAR, 1);

                    Date completed_date = CommonFunction.StringtoDate(LiveCounselling.Counsellertime);
                    Calendar nextday = Calendar.getInstance();

                    if (completed_date != null)
                        nextday.setTime(completed_date);

                    nextday.add(Calendar.DAY_OF_MONTH, 7);
                    calendar_view.init(nextday.getTime(), nextYear.getTime())
                            .inMode(CalendarPickerView.SelectionMode.SINGLE);


                    progress_bar.setVisibility(View.GONE);
                    start_booking_layout.setVisibility(View.VISIBLE);
                    booking_appoint_layout.setVisibility(View.GONE);
                    booking_lyout.setVisibility(View.GONE);
                }
            }
        };

        if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            task.execute();
        }
    }


    List<LinearLayout> counse_list_linea_layout = new ArrayList<>();

    private void showCounsellorList(String day_name) {
        try {
            if (setDaywiseAvailabilityDataDTO != null) {
                counse_list_linea_layout.clear();

                select_counce_index = -1;

                counsellor_main.removeAllViews();

                if (day_name.contains("Mon"))
                    final_list_with_same_councel = setDaywiseAvailabilityDataDTO.getMonday();
                else if (day_name.contains("Tue")) {
                    final_list_with_same_councel = setDaywiseAvailabilityDataDTO.getTuesday();
                } else if (day_name.contains("Wed")) {
                    final_list_with_same_councel = setDaywiseAvailabilityDataDTO.getWednesday();
                } else if (day_name.contains("Thu")) {
                    final_list_with_same_councel = setDaywiseAvailabilityDataDTO.getThursday();
                } else if (day_name.contains("Fri")) {
                    final_list_with_same_councel = setDaywiseAvailabilityDataDTO.getFriday();
                } else if (day_name.contains("Sat")) {
                    final_list_with_same_councel = setDaywiseAvailabilityDataDTO.getSaturday();
                } else if (day_name.contains("Sun")) {
                    final_list_with_same_councel = setDaywiseAvailabilityDataDTO.getSunday();
                }

                if (is_samecounseller != null && is_samecounseller.equalsIgnoreCase("true"))
                {
                    // final_list.clear();

                    int final_size = final_list_with_same_councel.size();

                    if(final_list.size() <= 0) {
                        for (int jj = 0; jj < final_size; jj++) {
                            if (final_list_with_same_councel.get(jj).getCounc_unq_id().equalsIgnoreCase(counseller_id))
                                final_list.add(final_list_with_same_councel.get(jj));
                        }
                    }
                    else
                    {
                        final_list.remove(0);
                        for (int jj = 0; jj < final_size; jj++)
                        {
                            if (final_list_with_same_councel.get(jj).getCounc_unq_id().equalsIgnoreCase(counseller_id))
                                final_list.add(final_list_with_same_councel.get(jj));
                        }
                    }

                }
                else  if(Suggested_Object!= null)
                {
                    String counsellor_id = Suggested_Object.getString("counc_unq_id");
                    int final_size = final_list_with_same_councel.size();

                    if(final_list.size() <= 0)
                    {
                        for (int jj = 0; jj < final_size; jj++) {
                            if (final_list_with_same_councel.get(jj).getCounc_unq_id().equalsIgnoreCase(counsellor_id))
                                final_list.add(final_list_with_same_councel.get(jj));
                        }
                    }
                    else
                    {
                        final_list.remove(0);
                        for (int jj = 0; jj < final_size; jj++)
                        {
                            if (final_list_with_same_councel.get(jj).getCounc_unq_id().equalsIgnoreCase(counsellor_id))
                                final_list.add(final_list_with_same_councel.get(jj));
                        }
                    }

                }
                else
                {
                    // final_list.clear();

                    final_list = final_list_with_same_councel;
                }


                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout Rowlayout = new LinearLayout(ct);
                Rowlayout.setOrientation(LinearLayout.HORIZONTAL);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 8);
                Rowlayout.setLayoutParams(lp);


                TextView dayname = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(width / 3 + width / 9, width / 8);

                dayname.setBackgroundColor(Color.parseColor("#32B1A0"));
                dayname.setTextColor(Color.parseColor("#ffffff"));
                dayname.setTextSize(18);
                dayname.setText("Counsellor name");

                dayname.setLayoutParams(lp);
                dayname.setGravity(Gravity.CENTER);


                TextView seperator = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(width / 200, width / 8);


                //  seperator.setBackgroundColor(Color.parseColor("#eeeeee"));


                seperator.setLayoutParams(lp);

                TextView timeslot = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(width / 3 + width / 9, width / 8);


                timeslot.setBackgroundColor(Color.parseColor("#32B1A0"));
                timeslot.setTextColor(Color.parseColor("#ffffff"));
                timeslot.setTextSize(18);
                timeslot.setText("Time slot");

                timeslot.setLayoutParams(lp);

                timeslot.setGravity(Gravity.CENTER);


                Rowlayout.addView(dayname);
                Rowlayout.addView(seperator);
                Rowlayout.addView(timeslot);


                TextView margin = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 200);


                margin.setBackgroundColor(Color.parseColor("#aaaaaa"));

                margin.setLayoutParams(lp);

                counsellor_main.addView(Rowlayout);
                counsellor_main.addView(margin);


                for (int i = 0; i < final_list.size(); i++) {

                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    LinearLayout Rowlayout1 = new LinearLayout(ct);
                    Rowlayout1.setOrientation(LinearLayout.HORIZONTAL);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 8);
                    Rowlayout1.setLayoutParams(lp);
                    Rowlayout1.setId(i + 1);
                    Rowlayout1.setBackgroundColor(Color.parseColor("#ffffff"));
                    Rowlayout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            select_counce_index = view.getId() - 1;
                            for (int k = 0; k < counse_list_linea_layout.size(); k++) {
                                counse_list_linea_layout.get(k).setBackgroundColor(Color.parseColor("#ffffff"));
                            }

                            counse_list_linea_layout.get(select_counce_index).setBackgroundColor(Color.parseColor("#f3b547"));

                        }
                    });

                    counse_list_linea_layout.add(Rowlayout1);
                    TextView dayname1 = new TextView(ct);
                    lp = new RelativeLayout.LayoutParams(width / 3 + width / 9, width / 8);


                    //  dayname1.setBackgroundColor(Color.parseColor("#ffffff"));
                    dayname1.setTextColor(Color.parseColor("#222222"));
                    dayname1.setTextSize(15);
                    dayname1.setText(CryptLib.decrypt(final_list.get(i).getCounce_firstname()));


                    // dayname.setTextColor(Color.parseColor("#222222"));
                    dayname1.setLayoutParams(lp);
                    dayname1.setGravity(Gravity.CENTER);


                    TextView seperator1 = new TextView(ct);
                    lp = new RelativeLayout.LayoutParams(width / 200, width / 8);


                    // seperator1.setBackgroundColor(Color.parseColor("#aaaaaa"));


                    seperator1.setLayoutParams(lp);

                    TextView timeslot1 = new TextView(ct);
                    lp = new RelativeLayout.LayoutParams(width / 3 + width / 9, width / 8);


                    //timeslot1.setBackgroundColor(Color.parseColor("#ffffff"));
                    timeslot1.setTextColor(Color.parseColor("#222222"));
                    timeslot1.setTextSize(15);

                    String from = final_list.get(i).getFrom();
                    if (from.contains("always"))
                        timeslot1.setText("24 hours available");
                    else if (from.contains("not"))
                        timeslot1.setText("Not available");
                    else {
                        String counselor_unaval_time_form = getLocalTimewithsecond(final_list.get(i).getFrom());
                        String counselunaval_timeto = getLocalTimewithsecond(final_list.get(i).getTo());

                        timeslot1.setText(counselor_unaval_time_form + " to " + counselunaval_timeto);
                    }


                    timeslot1.setLayoutParams(lp);

                    timeslot1.setGravity(Gravity.CENTER);


                    Rowlayout1.addView(dayname1);
                    Rowlayout1.addView(seperator1);
                    Rowlayout1.addView(timeslot1);


                    TextView margin1 = new TextView(ct);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 200);


                    margin1.setBackgroundColor(Color.parseColor("#aaaaaa"));

                    margin1.setLayoutParams(lp);

                    counsellor_main.addView(Rowlayout1);
                    counsellor_main.addView(margin1);
                }


                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout sec_canlce_layout = new LinearLayout(ct);
                sec_canlce_layout.setOrientation(LinearLayout.HORIZONTAL);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 10);
                sec_canlce_layout.setLayoutParams(lp);


                TextView cancel_counce_list = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(width / 3 + width / 9, width / 10);

                cancel_counce_list.setBackgroundColor(Color.parseColor("#ED5F39"));
                cancel_counce_list.setTextColor(Color.parseColor("#ffffff"));
                cancel_counce_list.setTextSize(18);
                cancel_counce_list.setText("Cancel");

                cancel_counce_list.setLayoutParams(lp);
                cancel_counce_list.setGravity(Gravity.CENTER);


                TextView seperator2 = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(width / 200, width / 10);


                //  seperator.setBackgroundColor(Color.parseColor("#eeeeee"));


                seperator2.setLayoutParams(lp);

                TextView select_counsellor = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(width / 3 + width / 9, width / 10);


                select_counsellor.setBackgroundColor(Color.parseColor("#32B1A0"));
                select_counsellor.setTextColor(Color.parseColor("#ffffff"));
                select_counsellor.setTextSize(18);
                select_counsellor.setText("Ok");

                select_counsellor.setLayoutParams(lp);

                select_counsellor.setGravity(Gravity.CENTER);


                sec_canlce_layout.addView(cancel_counce_list);
                sec_canlce_layout.addView(seperator2);
                sec_canlce_layout.addView(select_counsellor);


                TextView margin2 = new TextView(ct);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 200);


                margin2.setBackgroundColor(Color.parseColor("#aaaaaa"));

                margin2.setLayoutParams(lp);


                cancel_counce_list.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        show_counsellor_list.setVisibility(View.GONE);


                    }
                });

                select_counsellor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            if (select_counce_index >= 0) {


                                counselor_unaval_time_form = final_list.get(select_counce_index).getTo();
                                counselunaval_timeto = final_list.get(select_counce_index).getFrom();
                                  if (counselor_unaval_time_form.equalsIgnoreCase("always")) {
                                    show_counsellor_list.setVisibility(View.GONE);
                                    is_counsellor_unavailable = false;
                                    time_lyout_bg.setVisibility(View.VISIBLE);
                                    if(counselor_unavail_list!= null)
                                    counselor_unavail_list.clear();

                                      selected_counsellor_name = final_list.get(select_counce_index).getClcnslrun01();
                                      new GetSelectedCounsellorAppointment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                                  } else if (counselor_unaval_time_form.equalsIgnoreCase("not")) {
                                    new ToastUtil(ct, CryptLib.decrypt(final_list.get(select_counce_index).getCounce_firstname()) + " is not available for today.");
                                } else {


                                    counselunaval_timeto = getLocalTimewithsecond(final_list.get(select_counce_index).getFrom());
                                    counselor_unaval_time_form = getLocalTimewithsecond(final_list.get(select_counce_index).getTo());

                                    if(counselor_unavail_list!= null)
                                        counselor_unavail_list.clear();

                                    if(counselunaval_timeto.equalsIgnoreCase(counselor_unaval_time_form))
                                    is_counsellor_unavailable = false;
                                    else
                                    is_counsellor_unavailable = true;

                                    show_counsellor_list.setVisibility(View.GONE);

                                    time_lyout_bg.setVisibility(View.VISIBLE);


                                      selected_counsellor_name = final_list.get(select_counce_index).getClcnslrun01();
                                      new GetSelectedCounsellorAppointment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                  }
                            } else {
                                new ToastUtil(ct, "Please select Counsellor.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


                counsellor_main.addView(sec_canlce_layout);
                counsellor_main.addView(margin2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    private class GetSelectedCounsellorAppointment extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(ct);
                GetCounsellorAppointmentDTO getCounsellorAppointmentDTO = new GetCounsellorAppointmentDTO(selected_counsellor_name);
                CommonRequestTypeDTO appointmentCancelObjectDTO = new CommonRequestTypeDTO(getCounsellorAppointmentDTO, "getAppointmentForCounsellor");
                FinalObjectDTO finalObjectDTO = new FinalObjectDTO(appointmentCancelObjectDTO);
                response = http.AcceptAppointment(finalObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                progress_bar.setVisibility(View.GONE);

                if (counselleracceptlist != null)
                    counselleracceptlist = null;

                counselleracceptlist = new ArrayList<String>();

                String status = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if (status != null && status.equalsIgnoreCase("true")) {
                        JSONArray json_array = null;
                        if (jsonObject.has("data"))
                            json_array = jsonObject.getJSONArray("data");

                        if (json_array != null && json_array.length() > 0) {
                            for (int i = 0; i < json_array.length(); i++) {
                                JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                                String app_status = "";
                                if (UserInfoobj.has("status")) {
                                  //  suggestedby_counseller_chat_mode = UserInfoobj.getString("status");
                                    app_status = UserInfoobj.getString("status");
                                }



                                if (UserInfoobj.has("apntmnt_date") && app_status.equalsIgnoreCase("Accepted"))
                                    counselleracceptlist.add(getLocalTime(UserInfoobj.getString("apntmnt_date")));

                            }


                        }
                    }


                }




            } catch (Exception e) {
                e.printStackTrace();
            }



            try
            {
                sethourslist();
                blockminuteto = 55;
                setminutelist();
            }
            catch (Exception e)
            {

            }
        }
    }




}


