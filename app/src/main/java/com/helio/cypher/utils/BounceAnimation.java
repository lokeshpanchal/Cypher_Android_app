package com.helio.cypher.utils;

import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.helio.cypher.R;

import java.util.Random;

public class BounceAnimation {

    private static final int DURATION = 4000;

    public static void makeMove(View view, View root) {
        if (root == null || view == null)
            return;
        view.setTag(1);
        TranslateAnimation animation = null;
        try {
            animation = getAnimationView(view, root);
        } catch (NullPointerException e) {

        }

        if (animation == null)
            return;

        animation.setDuration(new Random().nextInt(8000) + DURATION);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(2);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    private static TranslateAnimation getAnimationView(View view, View root) {

        switch (view.getId()) {
            case R.id.glimpse_mood_layout:
                return new TranslateAnimation(0, -root.getWidth() /30 /*(new Random().nextInt(20) + 6)*/, 0, -view.getHeight());
           /* case R.id.glimpse_enter_layout:
                return new TranslateAnimation(0, root.getWidth() / 30, 0, -view.getHeight() / 1.5f);
            case R.id.glimpse_news_layout:
                return new TranslateAnimation(0, -view.getWidth() / 2, 0, -view.getHeight() / 5);
            case R.id.glimpse_lifestyle_layout:
                return new TranslateAnimation(0, root.getWidth() / 8, 0, view.getHeight());
            case R.id.glimpse_pages_layout:
                return new TranslateAnimation(0, -view.getWidth(), 0, 0);*/
            case R.id.glimpse_counselling_layout:
                return new TranslateAnimation(0,0, 0,  view.getHeight());

        }

        return null;
    }


    public static void makeMoveStart(final View view, final View root) {

//        view.clearAnimation();
/*
        view.getAnimation().setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                onAnimationEnd(animation);

                view.getAnimation().setRepeatCount(0);
//                view.getAnimation().setRepeatMode(Animation.REVERSE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                wave.startAnimation(animation1);
//                YoYo.with(Techniques.ZoomIn).duration(300).playOn(view);
                TranslateAnimation animation2 = null;
//                animation.setRepeatCount(0);
                animation2 = cancer(view, root);
                animation2.setDuration(2000);
                animation2.setFillAfter(true);
                view.startAnimation(animation2);

            }
        });
*/

//        if(view.getId() == R.id.glimpse_lifestyle_layout) {

            int currentPositionX, currentPositionY;
            Animation currentAnimation = view.getAnimation();
            float[] matrix = new float[9];
            Transformation transformation = new Transformation();

            if (currentAnimation != null) {
                currentAnimation.getTransformation(android.view.animation.AnimationUtils.currentAnimationTimeMillis(), transformation);
                transformation.getMatrix().getValues(matrix);
                float y = matrix[Matrix.MTRANS_Y];
                float x = matrix[Matrix.MTRANS_X];

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                params.topMargin += y;
//                params.leftMargin += x;

                currentPositionX = (int) Double.parseDouble(String.valueOf(x));
                currentPositionY = (int) Double.parseDouble(String.valueOf(y));
                view.requestLayout();

                currentAnimation.setAnimationListener(null);
                currentAnimation.cancel();
            } else {
                currentPositionX = view.getLeft();
                currentPositionY = view.getTop();
            }

//            final int accPositionx = view.getWidth() - view.getLeft();
//            final int accPositionY = view.getHeight() - view.getTop();
//            TranslateAnimation animation = new TranslateAnimation(0, 0, 0, accPosition - currentPosition);

            Log.d("JSd", "Current position: " + currentPositionX + ", " + currentPositionY);
            Log.d("JSd", "X, Y: " + view.getLeft() + ", " + view.getTop());
            Log.d("JSd", "W, H: " + view.getHeight() + ", " + view.getWidth());
            Log.d("JSd", "Xr, Yr: " + root.getLeft() + ", " + root.getTop());
            Log.d("JSd", "Wr, Hr: " + root.getHeight() + ", " + root.getWidth());

            TranslateAnimation animation2 = null;
//            animation2 = new TranslateAnimation(currentPositionX, -(root.getWidth() / 8) + view.getWidth(), currentPositionY, -view.getHeight());
            animation2 = new TranslateAnimation(currentPositionX, 0, currentPositionY, 0);
            animation2.setDuration(1000);
            animation2.setFillAfter(true);
            view.startAnimation(animation2);
//        }


    /*
        view.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("JSd", "+2");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("JSd","+3");
                if (view.getX() != 0 && view.getY() != 0) {
                    view.setTag(0);
                    YoYo.with(Techniques.ZoomOut).withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            moveStart(view);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).duration(300).playOn(view);
                }
            }

            private void moveStart(View view) {
                Log.d("JSd","+4");

                YoYo.with(Techniques.ZoomIn).duration(300).playOn(view);
                TranslateAnimation animation = null;
                if (view.getId() == R.id.glimpse_mood_layout) {
                    animation = new TranslateAnimation(-view.getX(), 0, -view.getY(), 0);
                } else {
                    animation = new TranslateAnimation(view.getX(), 0, view.getY(), 0);
                }
                animation.setDuration(2000);
                animation.setFillAfter(true);
                view.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d("JSd","+5");
                animation.setRepeatCount(1);
                view.setTag(1);
            }
        });
*/
    }

    public static void makeRepeat(View view) {
        view.getAnimation().setAnimationListener(null);
        view.getAnimation().setRepeatCount(Animation.INFINITE);
    }
}
