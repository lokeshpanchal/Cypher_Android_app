package com.helio.silentsecret.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.MainActivity;

public class AnimationUtils {


    static ImageView center;
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
                    center = (ImageView) activity.findViewById(R.id.heart_center);
                    activity_anim.findViewById(R.id.hug_anim_root).setVisibility(View.VISIBLE);
                    center.setImageResource(R.drawable.ic_hug);
                    center.postDelayed(show_heart_anim_towar_pet, 100);
                    counter = 0;
                    break;
                case Constants.ANIM_STATE_LOVE:
                    center = (ImageView) activity.findViewById(R.id.heart_center);
                    center.setImageResource(R.drawable.ic_hearted);
                    activity_anim.findViewById(R.id.hug_anim_root).setVisibility(View.VISIBLE);

                    center.postDelayed(show_heart_anim_towar_pet, 100);
                    counter = 0;
                    break;
                case Constants.ANIM_STATE_ME2:
                    center = (ImageView) activity.findViewById(R.id.heart_center);
                    center.setImageResource(R.drawable.ic_me);
                    activity_anim.findViewById(R.id.hug_anim_root).setVisibility(View.VISIBLE);
                    center.postDelayed(show_heart_anim_towar_pet, 100);
                    counter = 0;

                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        /*YoYo.with(Techniques.FadeIn).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                activity.findViewById(R.id.hug_anim_root).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                if (left == null && right == null) {
                    String pet_name = AppSession.getValue(activity_anim, Constants.USER_PET_NAME);
                    if (pet_name != null && !pet_name.equalsIgnoreCase("") && !pet_name.equalsIgnoreCase("null") && anim_state < 2)
                    {
                        //  center.setVisibility(View.VISIBLE);

                        activity_anim.findViewById(R.id.hug_anim_root).setVisibility(View.VISIBLE);
                        center.postDelayed(show_heart_anim_towar_pet, 100);
                        counter = 0;
                    } else
                    {

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
        }).duration(300).playOn(activity.findViewById(R.id.hug_anim_root));*/
    }


    static int counter = 0, alpha_counter = 0;
    static float alpha = 1;
    static Runnable show_heart_anim_towar_pet = new Runnable() {
        @Override
        public void run() {
            try {
                center.removeCallbacks(show_heart_anim_towar_pet);
                counter++;
                if (counter < 21)
                {
                    if(counter%2==0)
                    {
                        alpha = (float) (20 - alpha_counter) / 20;
                        alpha_counter++;
                    }
                    center.setAlpha(alpha);
                    activity_anim.findViewById(R.id.hug_anim_root).setVisibility(View.VISIBLE);
                    final RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(width / 10, width / 10);
                    lpt.setMargins(width / 2 + (width / 55) * (counter), height / 2 - (height / 42) * (counter), 0, 0);
                    center.setLayoutParams(lpt);
                    center.postDelayed(show_heart_anim_towar_pet, 25);

                    center.setVisibility(View.VISIBLE);
                } else {
                    counter = 0;
                    alpha_counter = 0;
                    alpha = 1;
                    activity_anim.findViewById(R.id.hug_anim_root).setVisibility(View.GONE);
                    center.setVisibility(View.INVISIBLE);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
}
