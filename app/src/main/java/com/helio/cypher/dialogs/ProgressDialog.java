package com.helio.cypher.dialogs;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

public class ProgressDialog {

    public static void showDialog(View back, View progress) {
        back.setVisibility(View.VISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        progress.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn).duration(500).playOn(back);
        YoYo.with(Techniques.FadeIn).duration(500).playOn(progress);
    }

    public static void hideDialog(final View back, final View progress) {
        YoYo.with(Techniques.FadeOut).duration(500).playOn(back);
        YoYo.with(Techniques.FadeOut).duration(500).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                back.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).playOn(progress);
    }
}
