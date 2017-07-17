package com.helio.silentsecret.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.MainActivity;
import com.nineoldandroids.animation.Animator;

public class AnimationUtils {

    static View left;
    static View right;
    static View center;
    static Activity activity_anim = null;
    static int width = 0, height = 0;
    static int anim_state = 0;

    public static void playHugAnim(int state, final MainActivity activity) {
        try {

            DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
            width = displayMetrics.widthPixels;
            height = displayMetrics.heightPixels;
            activity_anim = activity;
            anim_state = state;
            switch (state) {
                case Constants.ANIM_STATE_HUG:
                    left = activity.findViewById(R.id.hug_left);
                    right = activity.findViewById(R.id.hug_right);
                    break;
                case Constants.ANIM_STATE_LOVE:
                    left = null;
                    right = null;
                    center = activity.findViewById(R.id.heart_center);
                    break;
                case Constants.ANIM_STATE_ME2:
                    left = activity.findViewById(R.id.me2_left);
                    right = activity.findViewById(R.id.me2_right);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        YoYo.with(Techniques.FadeIn).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                activity.findViewById(R.id.hug_anim_root).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (left == null && right == null) {
                    String pet_name = AppSession.getValue(activity_anim, Constants.USER_PET_NAME);
                    if (pet_name != null && !pet_name.equalsIgnoreCase("") && !pet_name.equalsIgnoreCase("null") && anim_state < 2) {
                        //  center.setVisibility(View.VISIBLE);

                        activity_anim.findViewById(R.id.hug_anim_root).setVisibility(View.VISIBLE);
                        center.postDelayed(show_heart_anim_towar_pet, 100);
                        counter = 0;
                    } else {

                        YoYo.with(Techniques.Pulse).withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                center.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {


                                YoYo.with(Techniques.FadeOut).withListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        try {
                                            activity.findViewById(R.id.hug_anim_root).setVisibility(View.GONE);
                                            center.setVisibility(View.INVISIBLE);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                }).duration(300).playOn(activity.findViewById(R.id.hug_anim_root));
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).duration(600).playOn(center);
                    }
                    return;
                }


                String pet_name = AppSession.getValue(activity_anim, Constants.USER_PET_NAME);
                if (pet_name != null && !pet_name.equalsIgnoreCase("") && !pet_name.equalsIgnoreCase("null") && anim_state < 2) {

                    left.postDelayed(show_anim_towar_pet, 600);
                    counter = 0;
                } else {


                    YoYo.with(Techniques.FadeInLeft).duration(600).playOn(left);
                    YoYo.with(Techniques.FadeInRight).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            try {

                                left.setVisibility(View.VISIBLE);
                                right.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {


                            YoYo.with(Techniques.FadeOut).withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    try {
                                        if (activity != null) {

                                            activity.findViewById(R.id.hug_anim_root).setVisibility(View.GONE);
                                            left.setVisibility(View.INVISIBLE);
                                            right.setVisibility(View.INVISIBLE);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            }).duration(600).playOn(activity.findViewById(R.id.hug_anim_root));
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).duration(600).playOn(right);
                }


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).duration(300).playOn(activity.findViewById(R.id.hug_anim_root));
    }

    static int counter = 0;
    static int last_valueof_redicon = 0;
    static Runnable show_anim_towar_pet = new Runnable() {
        @Override
        public void run() {
            try {
                left.removeCallbacks(show_anim_towar_pet);
                counter++;
                if (counter < 16) {

                    left.setVisibility(View.VISIBLE);
                    right.setVisibility(View.VISIBLE);
                    activity_anim.findViewById(R.id.hug_anim_root).setVisibility(View.VISIBLE);

                    if (counter < 8) {
                        last_valueof_redicon = (width / 25) * counter;
                        final RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(width / 2, width / 2);
                        lpt.setMargins((width / 25) * counter, width / 2, 0, 0);
                        left.setLayoutParams(lpt);
                        final RelativeLayout.LayoutParams lpt1 = new RelativeLayout.LayoutParams(width / 2, width / 2);
                        lpt1.setMargins(width / 2 - (width / 25) * counter, width / 2, 0, 0);
                        right.setLayoutParams(lpt1);
                    } else {
                        final RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(width / 2 - (width / 25) * (counter - 7), width / 2 - (width / 25) * (counter - 7));
                        lpt.setMargins(last_valueof_redicon - (width / 25) * (counter - 6), width / 2 - (width / 17) * (counter - 6), 0, 0);
                        // lpt.setMargins(width / 4  - (width /25)* counter, width / 2 - (width /17)* counter, 0, 0);
                        left.setLayoutParams(lpt);
                        final RelativeLayout.LayoutParams lpt1 = new RelativeLayout.LayoutParams(width / 2 - (width / 25) * (counter - 7), width / 2 - (width / 25) * (counter - 7));
                        lpt1.setMargins(width / 2 - (width / 25) * counter, width / 2 - (width / 17) * (counter - 6), 0, 0);
                        //  lpt1.setMargins((width / 2 +  width / 4) - (width /11)* counter, width / 2 - (width /17)* counter, 0, 0);
                        right.setLayoutParams(lpt1);
                    }


                  /* final RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(width/2 + width/8  - (width /25)* counter ,width/2 + width/8  - (width /25)* counter);
                   lpt.setMargins(width / 4  - (width /25)* counter, width / 2 - (width /17)* counter, 0, 0);
                   left.setLayoutParams(lpt);
                   final RelativeLayout.LayoutParams lpt1 = new RelativeLayout.LayoutParams(width/2 + width/8  - (width /25)* counter ,width/2 + width/8  - (width /25)* counter);
                   lpt1.setMargins((width / 2 +  width / 4) - (width /11)* counter, width / 2 - (width /17)* counter, 0, 0);
                   right.setLayoutParams(lpt1);*/
                    left.postDelayed(show_anim_towar_pet, 100);
                } else {
                    counter = 0;
                    activity_anim.findViewById(R.id.hug_anim_root).setVisibility(View.GONE);
                    left.setVisibility(View.INVISIBLE);
                    right.setVisibility(View.INVISIBLE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
    static int last_heart_size = 0;

    static Runnable show_heart_anim_towar_pet = new Runnable() {
        @Override
        public void run() {
            try {
                center.removeCallbacks(show_anim_towar_pet);
                counter++;
                if (counter < 16) {


                    if (counter < 8) {
                        last_heart_size = width / 3 + (width / 25) * counter;
                        last_valueof_redicon = (width / 25) * counter;

                        activity_anim.findViewById(R.id.hug_anim_root).setVisibility(View.VISIBLE);
                        final RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(width / 3 + (width / 25) * counter, width / 3 + (width / 25) * counter);
                        lpt.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
                        center.setLayoutParams(lpt);
                        center.postDelayed(show_heart_anim_towar_pet, 100);
                    } else {
                        activity_anim.findViewById(R.id.hug_anim_root).setVisibility(View.VISIBLE);
                        final RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(last_heart_size - (width / 15) * (counter-6),last_heart_size - (width / 15) * (counter-6));
                        lpt.setMargins(width/3 - (width / 25) * (counter-6), width / 2 - (width / 17) * (counter -6), 0, 0);
                        center.setLayoutParams(lpt);
                        center.postDelayed(show_heart_anim_towar_pet, 100);
                    }

                    center.setVisibility(View.VISIBLE);
                } else {
                    counter = 0;
                    activity_anim.findViewById(R.id.hug_anim_root).setVisibility(View.GONE);
                    center.setVisibility(View.INVISIBLE);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
}
