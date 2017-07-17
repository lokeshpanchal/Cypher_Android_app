package com.helio.silentsecret.tutorial;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.utils.Preference;
import com.nineoldandroids.animation.Animator;

import java.util.HashMap;

public class MainViewTutorial {

    private Context mContext;
    private View mainLayout;

    private static final int PLUS = 0;
    private static final int MENU = 1;
    private static final int TRENDING = 2;
    private static final int GLIMPSE = 3;
    private static final int HAPPY = 4;
    private static final int FILTER = 5;
    private static final int EMOTIONS = 6;


    private static final int INVISIBLE = 66;
    private static final int LEFT = 45;
    private static final int CENTER = 35;
    private static final int RIGHT = 38;

    private int currentState = 0;
    private int curTab = HAPPY;
    private int curPage = 0;

    private View viewToAttach;
    private View clickOk;
    private TextView mText;

    private int[] happyRules = new int[4];
    private int[] glimpseRules = new int[4];
    private int[] trendingRules = new int[4];
    private int[] filterRules = new int[4];

    private HashMap<Integer, String> textMap = new HashMap<>(4);

    public MainViewTutorial(Context context, View showView) {
        mContext = context;
        mainLayout = showView;
        fillRules();
    }

    private void fillRules() {
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    happyRules[i] = INVISIBLE;
                    glimpseRules[i] = RIGHT;
                    filterRules[i] = INVISIBLE;
                    trendingRules[i] = CENTER;
                    break;
                case 1:
                    happyRules[i] = RIGHT;
                    glimpseRules[i] = CENTER;
                    filterRules[i] = INVISIBLE;
                    trendingRules[i] = LEFT;
                    break;
                case 2:
                    happyRules[i] = CENTER;
                    glimpseRules[i] = LEFT;
                    filterRules[i] = RIGHT;
                    trendingRules[i] = INVISIBLE;
                    break;
                case 3:
                    happyRules[i] = LEFT;
                    glimpseRules[i] = INVISIBLE;
                    filterRules[i] = CENTER;
                    trendingRules[i] = INVISIBLE;
                    break;
            }
        }

        textMap.put(HAPPY, mContext.getString(R.string.happy_tutorial_text));
        textMap.put(GLIMPSE, mContext.getString(R.string.be_curious));
        textMap.put(FILTER, mContext.getString(R.string.the_latest_secrets));
        textMap.put(TRENDING, mContext.getString(R.string.the_most_popular_secrets));
    }

    public void checkForThePerformance() {
        if (!Preference.getPlusTutorial()) {
            showView(PLUS);
            return;
        }

        /*if (!Preference.getEmotionsTutorial()) {
            showView(EMOTIONS);
            return;
        }
*/
        if (!Preference.getMenuTutorial()) {
            showView(MENU);
            return;
        }

        updateTopTutorials(curPage);
    }

    public void updateTopTutorials(int curPage) {
        this.curPage = curPage;

        if (!Preference.getMenuTutorial())
            return;

        if (!Preference.getHappyTutorial()) {
            curTab = HAPPY;
            showView(happyRules[curPage]);
            return;
        }

        if (!Preference.getGlimpseTutorial()) {
            curTab = GLIMPSE;
            showView(glimpseRules[curPage]);
            return;
        }

        if (!Preference.getFilterTutorial()) {
            curTab = FILTER;
            showView(filterRules[curPage]);
            return;
        }

        if (!Preference.getTrendingTutorial()) {
            curTab = TRENDING;
            showView(trendingRules[curPage]);
            return;
        }

    }

    private void showView(int state) {
        currentState = state;
        mainLayout.setVisibility(View.VISIBLE);

        String text = textMap.get(curTab);

        if (viewToAttach != null && (Integer) viewToAttach.getTag() != 0) {
            YoYo.with(Techniques.ZoomOut).duration(300).playOn(viewToAttach);
        }

        switch (state) {
            case PLUS:
                viewToAttach = mainLayout.findViewById(R.id.plus_view);
                clickOk = mainLayout.findViewById(R.id.plus_ok);
                break;
            case MENU:
                viewToAttach = mainLayout.findViewById(R.id.menu_view);
                clickOk = mainLayout.findViewById(R.id.menu_ok);
                break;
            case LEFT:
                viewToAttach = mainLayout.findViewById(R.id.tutorial_left_view);
                clickOk = mainLayout.findViewById(R.id.tutorial_left_ok);
                mText = (TextView) mainLayout.findViewById(R.id.tutorial_left_text);
                break;
            case CENTER:
                viewToAttach = mainLayout.findViewById(R.id.tutorial_center_view);
                clickOk = mainLayout.findViewById(R.id.tutorial_center_ok);
                mText = (TextView) mainLayout.findViewById(R.id.tutorial_center_text);
                break;
            case RIGHT:
                viewToAttach = mainLayout.findViewById(R.id.tutorial_right_view);
                clickOk = mainLayout.findViewById(R.id.tutorial_right_ok);
                mText = (TextView) mainLayout.findViewById(R.id.tutorial_right_text);
                break;
            case EMOTIONS:
               /* viewToAttach = mainLayout.findViewById(R.id.emotions_view);
                clickOk = mainLayout.findViewById(R.id.emotions_ok);*/

            default:
                break;

        }

        if (clickOk != null && viewToAttach != null) {
            clickOk.setOnClickListener(OnClick);
            viewToAttach.setTag(0);
        }

        if (viewToAttach != null && state != INVISIBLE) {
            viewToAttach.setTag(1);

            if (mText != null)
                mText.setText(text);

            viewToAttach.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.BounceIn).duration(500).playOn(viewToAttach);
        }
    }

    private View.OnClickListener OnClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            switch (currentState) {
                case PLUS:
                    Preference.setPlusTutorial(true);
                    showView(MENU);
                    break;
                case EMOTIONS:
                    Preference.setEmotionsTutorial(true);
                    showView(MENU);
                    break;
                case MENU:
                    Preference.setMenuTutorial(true);
                    showView(happyRules[curPage]);
                    break;

                default:
                    moveOn();
                    break;
            }
        }
    };

    private void moveOn() {
        switch (curTab) {
            case TRENDING:
                Preference.setTrendingTutorial(true);
                finishMenuTutorial();
                break;
            case HAPPY:
                Preference.setHappyTutorial(true);
                curTab = GLIMPSE;
                showView(glimpseRules[curPage]);
                break;
            case FILTER:
                Preference.setFilterTutorial(true);
                curTab = TRENDING;
                showView(trendingRules[curPage]);
                break;
            case GLIMPSE:
                Preference.setGlempseTutorial(true);
                curTab = FILTER;
                showView(filterRules[curPage]);
                break;
        }
    }

    private void finishMenuTutorial() {
        YoYo.with(Techniques.FadeOut).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mainLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).duration(500).playOn(viewToAttach);

    }

    public void showMineTutorial(final View view) {

        if (Preference.getMineTutorial())
            return;

        view.findViewById(R.id.secret_tutorial_view).setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(view.findViewById(R.id.secret_tutorial_view));
        view.findViewById(R.id.secret_tutorial_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preference.setMineTutorial(true);

                YoYo.with(Techniques.ZoomOut).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.findViewById(R.id.secret_tutorial_view).setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).duration(500).playOn(view.findViewById(R.id.secret_tutorial_view));

            }
        });

    }

   /* public void showInviteTutorial(final View view) {

        if (Preference.getInviteTutorial())
            return;

        view.findViewById(R.id.invite_tutorial_view).setVisibility(View.VISIBLE);
        view.findViewById(R.id.invite_tutorial_triangle).setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(view.findViewById(R.id.invite_tutorial_triangle));
        YoYo.with(Techniques.BounceIn).duration(500).playOn(view.findViewById(R.id.invite_tutorial_view));
        view.findViewById(R.id.invite_tutorial_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preference.setInviteTutorial(true);

                YoYo.with(Techniques.ZoomOut).duration(300).playOn(view.findViewById(R.id.invite_tutorial_triangle));
                YoYo.with(Techniques.ZoomOut).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.findViewById(R.id.invite_tutorial_view).setVisibility(View.GONE);
                        view.findViewById(R.id.invite_tutorial_triangle).setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).duration(500).playOn(view.findViewById(R.id.invite_tutorial_view));
            }
        });
    }*/

    public void showAppTutorial(final View view) {

        if (Preference.getAppcounsellingTutorial())
            return;

        view.findViewById(R.id.appcounselling_tutorial_view).setVisibility(View.VISIBLE);
        view.findViewById(R.id.appcounselling_tutorial_triangle).setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(view.findViewById(R.id.appcounselling_tutorial_triangle));
        YoYo.with(Techniques.BounceIn).duration(500).playOn(view.findViewById(R.id.appcounselling_tutorial_view));
        view.findViewById(R.id.appcounselling_tutorial_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preference.setAppcounsellingTutorial(true);

                YoYo.with(Techniques.ZoomOut).duration(300).playOn(view.findViewById(R.id.appcounselling_tutorial_triangle));
                YoYo.with(Techniques.ZoomOut).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.findViewById(R.id.appcounselling_tutorial_view).setVisibility(View.GONE);
                        view.findViewById(R.id.appcounselling_tutorial_triangle).setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).duration(500).playOn(view.findViewById(R.id.appcounselling_tutorial_view));
            }
        });
    }


    public void showIFriendsTutorial(final View view) {


        if (Preference.getIFriendsTutorial())
            return;

        view.findViewById(R.id.iFriends_tutorial_view).setVisibility(View.VISIBLE);
        view.findViewById(R.id.iFriends_tutorial_triangle).setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(view.findViewById(R.id.iFriends_tutorial_triangle));
        YoYo.with(Techniques.BounceIn).duration(500).playOn(view.findViewById(R.id.iFriends_tutorial_view));
        view.findViewById(R.id.iFriends_tutorial_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preference.setIFriendsTutorial(true);

                YoYo.with(Techniques.ZoomOut).duration(300).playOn(view.findViewById(R.id.iFriends_tutorial_triangle));
                YoYo.with(Techniques.ZoomOut).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.findViewById(R.id.iFriends_tutorial_view).setVisibility(View.GONE);
                        view.findViewById(R.id.iFriends_tutorial_triangle).setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).duration(500).playOn(view.findViewById(R.id.iFriends_tutorial_view));
            }
        });
    }





    public void showPetTutorial(final View view) {



        if (Preference.getPetsTutorial() || MainActivity.pet_name == null || MainActivity.pet_name.equalsIgnoreCase(""))
            return;

        view.findViewById(R.id.Pet_tutorial_view).setVisibility(View.VISIBLE);
        view.findViewById(R.id.pet_tutorial_triangle).setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(view.findViewById(R.id.pet_tutorial_triangle));
        YoYo.with(Techniques.BounceIn).duration(500).playOn(view.findViewById(R.id.Pet_tutorial_view));
        view.findViewById(R.id.Pet_tutorial_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preference.setPetssTutorial(true);

                YoYo.with(Techniques.ZoomOut).duration(300).playOn(view.findViewById(R.id.pet_tutorial_triangle));
                YoYo.with(Techniques.ZoomOut).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.findViewById(R.id.Pet_tutorial_view).setVisibility(View.GONE);
                        view.findViewById(R.id.pet_tutorial_triangle).setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).duration(500).playOn(view.findViewById(R.id.Pet_tutorial_view));
            }
        });
    }


}
