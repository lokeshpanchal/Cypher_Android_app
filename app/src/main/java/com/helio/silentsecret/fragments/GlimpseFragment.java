package com.helio.silentsecret.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.activities.MoodActivity;
import com.helio.silentsecret.application.SilentSecretApplication;
import com.helio.silentsecret.controller.Controller;
import com.helio.silentsecret.utils.BounceAnimation;
import com.helio.silentsecret.utils.Preference;
import com.helio.silentsecret.utils.ToastUtil;
import com.helio.silentsecret.utils.Utils;
import com.nineoldandroids.animation.Animator;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GlimpseFragment extends Fragment implements MainActivity.OnReplace, View.OnClickListener {

    private View mView;

    private View moodView;
/*    private View enterView;
    private View newsView;
    private View lifestyleView;
    private View pagesView;*/
    private View bookview;

    private ImageView mToolImageView;
    private ImageView mBackground;
    private View mToolLayout;

    private View changeBack;

    private View animOn;
    private View animOff;
    private View circlesTool;
    private View shapesTool;

    private int[] durations = new int[6];

    private int step = 0x1f4;

    private List<View> moods = new ArrayList<>();

    private int GALLERY_REQUEST_CODE = 2;

    public static int count;
    public static boolean FOR_SHARE_LINK = false;

    private static Handler handler;

    public static int maxAllowedGlimpse;

    public static int sesstion_time = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.glimpse_mood_clickable:
                Controller.runGlimpse(Controller.MOOD, getActivity(), true);
                break;

            case R.id.glimpse_counsell_clickable:
                    Controller.runGlimpse(Controller.COUNSELL, getActivity(), true);
                break;
        }
    }

    private enum StyleType {
        BUBBLES,
        SQUARES
    }

    private StyleType styleType = StyleType.BUBBLES;

    @Override
    public void runMood() {
        startActivity(new Intent(getActivity(), MoodActivity.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_glimpse, null);

        ((MainActivity) getActivity()).mMoodRunner = this;

        moodView = mView.findViewById(R.id.glimpse_mood_layout);
       /* enterView = mView.findViewById(R.id.glimpse_enter_layout);
        newsView = mView.findViewById(R.id.glimpse_news_layout);
        lifestyleView = mView.findViewById(R.id.glimpse_lifestyle_layout);
        pagesView = mView.findViewById(R.id.glimpse_pages_layout);*/
        bookview = mView.findViewById(R.id.glimpse_counselling_layout);
        moods.add(moodView);
       /* moods.add(enterView);
        moods.add(newsView);
        moods.add(lifestyleView);
        moods.add(pagesView);*/
        moods.add(bookview);


       /* bookview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((MainActivity) getActivity(),CameraTestActivity.class);
                ((MainActivity) getActivity()).startActivity(intent);
            }
        });*/


        changeBack = mView.findViewById(R.id.glimple_change_background);

        changeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();

                startTracking(MainActivity.ct.getString(R.string.analytics_ChangeBackground));
            }
        });

        mBackground = (ImageView) mView.findViewById(R.id.glimpse_background);
        setBackImage(Preference.getLastImage(), Preference.getLastImageURI());

        mToolImageView = (ImageView) mView.findViewById(R.id.glimpse_tool_navigator);
        mToolLayout = mView.findViewById(R.id.glimpse_tools);
        mToolLayout.setVisibility(View.GONE);

        mToolImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToolLayout.getVisibility() == View.GONE) {
                    mToolLayout.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.SlideInUp).duration(100).playOn(mToolLayout);
                    mToolImageView.setImageResource(R.drawable.ic_arrow_down);
                } else {
                    mToolImageView.setImageResource(R.drawable.ic_arrow_up);
                    YoYo.with(Techniques.SlideOutDown).duration(100).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mToolLayout.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).playOn(mToolLayout);
                }
            }
        });

        animOn = mView.findViewById(R.id.glimpse_animation_on);
        animOff = mView.findViewById(R.id.glimpse_animation_off);

        animOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animOn.setAlpha(1f);
                animOff.setAlpha(0.4f);

                for (View view : moods) {
                    if (view.getAnimation() != null && (Integer) view.getTag() == 1)
//                        BounceAnimation.makeRepeat(view);
                        BounceAnimation.makeMove(view, getView());
                    else
                        runAnimations();
                }
            }
        });

        animOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animOn.setAlpha(0.4f);
                animOff.setAlpha(1f);


                for (View view : moods) {
                    if (view.getAnimation() != null)
                        BounceAnimation.makeMoveStart(view, getView());
                }
            }
        });

        circlesTool = mView.findViewById(R.id.glimpse_cirles);
        shapesTool = mView.findViewById(R.id.glimpse_sqaures);

        circlesTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circlesTool.setAlpha(1f);
                shapesTool.setAlpha(0.4f);
                makeCircles();
            }
        });

        shapesTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circlesTool.setAlpha(0.4f);
                shapesTool.setAlpha(1f);
                makeSquares();
            }
        });

        runAnimations();
        attachListeners();
        makeCircles();

        /*moodView.postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                   if (ConnectionDetector.isNetworkAvailable(MainActivity.ct))
                    {




                       // maxAllowedFriends();
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }, 5000);*/


        handler = new Handler();


        return mView;
    }

    private void maxAllowedFriends() {

        /*ParseQuery<ParseObject> maxAllowedquery = ParseQuery.getQuery("IfriendSetting");
        maxAllowedquery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> userList, ParseException e) {


                if (e == null) {
                    if (userList != null && userList.size() > 0) {


                        ParseObject maxAllowedObject = userList.get(0);
                        maxAllowedGlimpse = maxAllowedObject.getInt("maxfriend");
                        sesstion_time = maxAllowedObject.getInt("session_duration");

                    }

                } else {
                    System.out.println("Something wrong ----> " + e.getMessage());

                }
            }
        });*/
    }

    /*public static int fetchMyFriends() {

        int myCount = 0;
        ParseQuery myQuery1 = new ParseQuery(MainActivity.friend_table_name);//DONE
        myQuery1.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        myQuery1.whereEqualTo("status", "approved");


        try {
            myCount = myQuery1.count();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return myCount;
    }*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                String file = null;
                try {
                    file = Utils.saveImage(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setBackImage(file, data.getData());
            }
        }
    }

    private void setBackImage(String data, Uri uri) {
        if (data == null
                || data.isEmpty()
                || uri == null
                || uri.toString().isEmpty()) {
            try {
                //loadParseFileChecker();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return;
        }

        Preference.saveLastImage(data);
        Preference.saveLastImageURI(uri.toString());
        Bitmap bitmap = null;
        try {
            bitmap = Utils.getCorrectlyOrientedImage(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            mBackground.setImageBitmap(bitmap);
            saveFileToParse(bitmap);
        }
    }

    private void saveFileToParse(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] data = stream.toByteArray();

        if (data == null)
            return;


    }

  /*  private void loadParseFileChecker() throws Exception {
        ParseFile picture = ParseUser.getCurrentUser().getParseFile(Constants.BACK_IMAGE);
        if (picture == null)
            return;
        new ImageLoaderUtil(getActivity()).loadImageAlphaCache(picture.getUrl(), mBackground);
    }*/

    private void choosePhoto() {
        try {
            MainActivity.is_From_glimpse = true;
            startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            new ToastUtil(getActivity(), getActivity().getString(R.string._error));
        }
    }

    private void makeCircles() {
        for (final View view : moods) {

            if (styleType == StyleType.BUBBLES)
                return;

            YoYo.with(Techniques.ZoomOut).duration(500).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                   /* if (view.getId() == R.id.glimpse_pages_layout) {
                        view.setBackgroundResource(R.drawable.blue_circle);
                    } else {*/
                        view.setBackgroundResource(R.drawable.white_circle);
                    //}
                    YoYo.with(Techniques.ZoomIn).duration(500).playOn(view);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).playOn(view);
        }

        styleType = StyleType.BUBBLES;
    }

    private void makeSquares() {
        for (final View view : moods) {

            if (styleType == StyleType.SQUARES)
                return;

            YoYo.with(Techniques.ZoomOut).duration(500).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                   /* if (view.getId() == R.id.glimpse_pages_layout) {
                        view.setBackgroundResource(R.drawable.blue_square);
                    } else {*/
                        view.setBackgroundResource(R.drawable.white_square);
                   // }
                    YoYo.with(Techniques.ZoomIn).duration(500).playOn(view);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).playOn(view);
        }

        styleType = StyleType.SQUARES;
    }

    private void attachListeners() {
        mView.findViewById(R.id.glimpse_mood_clickable).setOnClickListener(this);
        /*mView.findViewById(R.id.glimpse_news_clickable).setOnClickListener(this);
        mView.findViewById(R.id.glimpse_enter_clickable).setOnClickListener(this);
        mView.findViewById(R.id.glimpse_lifestyle_clickable).setOnClickListener(this);
        mView.findViewById(R.id.glimpse_pages_clickable).setOnClickListener(this);*/
        mView.findViewById(R.id.glimpse_counsell_clickable).setOnClickListener(this);

    }

    private void runAnimations() {

        try {


            for (int i = 0; i < durations.length; i++) {
                durations[i] = step + (step * i);
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    BounceAnimation.makeMove(moodView, getView());
                }
            }, durations[0]);

            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    BounceAnimation.makeMove(enterView, getView());
                }
            }, durations[1]);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    BounceAnimation.makeMove(newsView, getView());
                }
            }, durations[2]);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    BounceAnimation.makeMove(lifestyleView, getView());
                }
            }, durations[3]);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    BounceAnimation.makeMove(pagesView, getView());
                }
            }, durations[4]);*/

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    BounceAnimation.makeMove(bookview, getView());
                }
            }, durations[3]);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void startTracking(String path) {
        Tracker t = ((SilentSecretApplication) MainActivity.ct.getApplicationContext()).getTracker(
                SilentSecretApplication.TrackerName.APP_TRACKER);
        t.setScreenName(path);
        t.send(new HitBuilders.AppViewBuilder().build());
    }
}
