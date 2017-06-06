package com.helio.cypher.utils;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.cypher.R;
import com.helio.cypher.activities.MainActivity;
import com.nineoldandroids.animation.Animator;

public class AnimationUtils {

    static View left;
    static View right;
    static View center;

    public static void playHugAnim(int state, final MainActivity activity) {
        try
        {
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
        }
        catch (Exception e)
        {
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
                                    try
                                    {
                                        activity.findViewById(R.id.hug_anim_root).setVisibility(View.GONE);
                                        center.setVisibility(View.INVISIBLE);
                                    }
                                    catch (Exception e)
                                    {
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
                    return;
                }


                YoYo.with(Techniques.FadeInLeft).duration(600).playOn(left);
                YoYo.with(Techniques.FadeInRight).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        try
                        {
                            left.setVisibility(View.VISIBLE);
                            right.setVisibility(View.VISIBLE);
                        }
                        catch (Exception e)
                        {
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
                            public void onAnimationEnd(Animator animation)
                            {
                                try
                                {
                                    if(activity!= null)
                                    {
                                        activity.findViewById(R.id.hug_anim_root).setVisibility(View.GONE);
                                        left.setVisibility(View.INVISIBLE);
                                        right.setVisibility(View.INVISIBLE);
                                    }
                                }
                                catch (Exception e)
                                {
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
                }).duration(600).playOn(right);


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).duration(300).playOn(activity.findViewById(R.id.hug_anim_root));
    }
}
