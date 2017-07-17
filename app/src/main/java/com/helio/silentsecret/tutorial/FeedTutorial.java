package com.helio.silentsecret.tutorial;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.silentsecret.R;
import com.helio.silentsecret.adapters.FeedAdapter;
import com.helio.silentsecret.utils.Preference;
import com.nineoldandroids.animation.Animator;

public class FeedTutorial {

    public static void makeFlagTutorial(final FeedAdapter.ViewHolder holder) {

        holder.flagView.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(holder.flagView);
        View.OnClickListener finishFlag = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.ZoomOut).duration(500).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        holder.flagView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(holder.flagView);
                Preference.setFlagTutorial(true);
            }
        };
        holder.flagClick.setOnClickListener(finishFlag);
    }

    public static void makeSupportTutorial(final FeedAdapter.ViewHolder holder) {
        holder.supportView.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(holder.supportView);
        View.OnClickListener finishSupport = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.ZoomOut).duration(500).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        holder.supportView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(holder.supportView);
                Preference.setSupportTutorial(true);
            }
        };
        holder.supportClick.setOnClickListener(finishSupport);
    }

    public static void clearFlagSupportFromView(final FeedAdapter.ViewHolder holder) {
        if (!Preference.getSupportTutorial())
            YoYo.with(Techniques.ZoomOut).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    holder.supportView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).duration(500).playOn(holder.supportView);
        if (!Preference.getFlagTutorial())
            YoYo.with(Techniques.ZoomOut).duration(500).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    holder.flagView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).playOn(holder.flagView);
    }

    public static void makeActionHugView(final FeedAdapter.ViewHolder holder, Context context) {
        holder.actionsView.setVisibility(View.INVISIBLE);
        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mainParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        //mainParams.addRule(RelativeLayout.BELOW, R.id.feed_text);
        LinearLayout.LayoutParams triangleParams = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.tutorial_third_triangle_w), (int) context.getResources().getDimension(R.dimen.tutorial_plus_triangel_h));
        triangleParams.gravity = Gravity.CENTER;
        triangleParams.setMargins((int) context.getResources().getDimension(R.dimen.tutorial_plus_margin_side), 0, 0, 30);
        holder.actionsText.setText(context.getString(R.string.give_a_hug));
        holder.actionsView.setLayoutParams(mainParams);
        holder.actionsTriangle.setLayoutParams(triangleParams);
        holder.actionsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preference.setHugTutorial(true);
                YoYo.with(Techniques.ZoomOut).duration(500).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        holder.actionsView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(holder.actionsView);
            }
        });

        holder.actionsView.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(holder.actionsView);
    }

    public static void makeActionHeartView(final FeedAdapter.ViewHolder holder, Context context)
    {
        holder.actionsView.setVisibility(View.INVISIBLE);

        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        //mainParams.addRule(RelativeLayout.BELOW, R.id.feed_text);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        mainParams.setMargins(0, 0, 2 * (int) context.getResources().getDimension(R.dimen.tutorial_plus_margin_side), 0);

        LinearLayout.LayoutParams triangleParams = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.tutorial_third_triangle_w), (int) context.getResources().getDimension(R.dimen.tutorial_plus_triangel_h));
        triangleParams.gravity = Gravity.LEFT;
        triangleParams.setMargins((int) context.getResources().getDimension(R.dimen.tutorial_plus_margin_side), 0, 0, 30);

        holder.actionsText.setText(context.getString(R.string.express_love));
        holder.actionsView.setLayoutParams(mainParams);
        holder.actionsTriangle.setLayoutParams(triangleParams);
        holder.actionsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preference.setHeartTutorial(true);
                YoYo.with(Techniques.ZoomOut).duration(500).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        holder.actionsView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(holder.actionsView);
            }
        });

        holder.actionsView.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(holder.actionsView);
    }

    public static void makeActionMe2View(final FeedAdapter.ViewHolder holder, Context context) {
        holder.actionsView.setVisibility(View.INVISIBLE);
        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        //mainParams.addRule(RelativeLayout.BELOW, R.id.feed_text);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        mainParams.setMargins(0, 0, (int) context.getResources().getDimension(R.dimen.tutorial_plus_margin_side), 0);
        LinearLayout.LayoutParams triangleParams = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.tutorial_third_triangle_w), (int) context.getResources().getDimension(R.dimen.tutorial_plus_triangel_h));
        triangleParams.gravity = Gravity.RIGHT;
        triangleParams.setMargins(0, 0, 4 * (int) context.getResources().getDimension(R.dimen.tutorial_plus_margin_side), 30);
        holder.actionsText.setText(context.getString(R.string.express_mutual_feeling));
        holder.actionsView.setLayoutParams(mainParams);
        holder.actionsTriangle.setLayoutParams(triangleParams);
        holder.actionsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preference.setMe2Tutorial(true);
                YoYo.with(Techniques.ZoomOut).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        holder.actionsView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).duration(500).playOn(holder.actionsView);
            }
        });

        holder.actionsView.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(holder.actionsView);
    }

    public static void makeVerifyTutorial(final FeedAdapter.ViewHolder holder) {
        holder.verifyView.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(holder.verifyView);
        View.OnClickListener finishVerify = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.ZoomOut).duration(500).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        holder.verifyView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(holder.verifyView);
                Preference.setVerifyTutorial(true);
            }
        };
        holder.verifyClick.setOnClickListener(finishVerify);
    }

}
