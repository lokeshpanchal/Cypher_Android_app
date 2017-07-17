package com.helio.silentsecret.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.LiveCounselling;
import com.helio.silentsecret.activities.MoodActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    private static final int MAX_IMAGE_DIMENSION = 500;

    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public static  Date StringTodate( String dtStart )
    {
        Date date = null;

        String parsedate = dtStart.substring(0,19);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
             date = format.parse(parsedate);
            System.out.println(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }



    public static  long DiffBetTwoDate(Calendar todays_date, Calendar birth_date )
    {
        long diff = todays_date.getTimeInMillis() - birth_date.getTimeInMillis();
        return diff;
    }


    public static  Date StringTodateyyyymmdd( String dtStart )
    {
        Date date = null;


        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }


    public static  Date StringTodateyyyymmddwithdiffformate( String dtStart )
    {
        Date date = null;


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public static Bitmap getCorrectlyOrientedImage(Context context) throws IOException {

        String photoPath = Preference.getLastImage();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);

        options.inJustDecodeBounds = false;

        int rotatedWidth, rotatedHeight;
        int orientation = getOrientation(context, Preference.getLastImageURI());

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = options.outHeight;
            rotatedHeight = options.outWidth;
        } else {
            rotatedWidth = options.outWidth;
            rotatedHeight = options.outHeight;
        }

        Bitmap srcBitmap;
        if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
            float widthRatio = ((float) rotatedWidth) / ((float) MAX_IMAGE_DIMENSION);
            float heightRatio = ((float) rotatedHeight) / ((float) MAX_IMAGE_DIMENSION);
            float maxRatio = Math.max(widthRatio, heightRatio);

            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeFile(photoPath, options);
        } else {
            options.inSampleSize = calculateInSampleSize(options, MAX_IMAGE_DIMENSION, MAX_IMAGE_DIMENSION);
            srcBitmap = BitmapFactory.decodeFile(photoPath, options);
        }

        if (orientation > 0 && srcBitmap != null) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                    srcBitmap.getHeight(), matrix, true);
        }

        return srcBitmap;
    }

    public static String saveImage(Bitmap src) {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + Constants.SECRET_CLASS);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File file = new File(folder.getAbsolutePath(), System.currentTimeMillis() + ".jpg");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            src.compress(Bitmap.CompressFormat.JPEG, 95, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    public static void AutoshareDataOfSecret(final View view, final Context context, final float topMargin)
    {
        new AsyncTask<Void, Void, Void>() {
            Uri bmpUri = null;
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Bitmap bitmap = getBitmapFromView(view);
                    Bitmap newBitmap;
                    Bitmap.Config config = bitmap.getConfig();
                    if (config == null) {
                        config = Bitmap.Config.ARGB_8888;
                    }
                    newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
                  //  LiveCounselling.bitmapmoodgraph = newBitmap;
                    Canvas canvas = new Canvas(newBitmap);
                    String captionString = context.getString(R.string.app_name);
                    canvas.drawBitmap(bitmap, 0, 0, null);

                    Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paintText.setColor(Color.WHITE);
                    paintText.setTextSize(context.getResources().getDimension(R.dimen.watermark_size));
                    paintText.setStyle(Paint.Style.FILL);

                    Rect rectText = new Rect();
                    paintText.getTextBounds(captionString, 0, captionString.length(), rectText);

                    canvas.drawText(captionString,
                            (newBitmap.getWidth() / 3) - (rectText.width() / 3), rectText.height() * topMargin, paintText);
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
                    bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() /3, bitmap.getHeight() / 3, false);

                    canvas.drawBitmap(bitmap, 10, 10, null);

                    bitmap.recycle();

                    String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), newBitmap, "default", null);

                    Uri bmpUri = Uri.parse(path);

                    try
                    {
                        LiveCounselling.bitmapmoodgraph  =  MediaStore.Images.Media.getBitmap(context.getContentResolver(),bmpUri);


                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                   /* final Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    share.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    share.setType("image/png");
                    context.startActivity(share);*/

                    bitmap.recycle();
                    newBitmap.recycle();
                } catch (OutOfMemoryError e) {
                } catch (NullPointerException e) {
                } catch (IllegalStateException e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {

                       /* if(bmpUri!= null)
                        {



                        }
                    else
                        {

                        }*/

             /*   Intent moodact = new Intent(context, LiveCounselling.class);
                context.startActivity(moodact);*/
                ((Activity)context).finish();
                super.onPostExecute(aVoid);
            }
        }.execute();
    }



    public static void shareDataOfSecret(final View view, final Context context, final float topMargin, final MoodActivity.ShareCallback callback) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Bitmap bitmap = getBitmapFromView(view);
                    Bitmap newBitmap;
                    Bitmap.Config config = bitmap.getConfig();
                    if (config == null) {
                        config = Bitmap.Config.ARGB_8888;
                    }
                    newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);

                    Canvas canvas = new Canvas(newBitmap);
                    String captionString = context.getString(R.string.app_name);
                    canvas.drawBitmap(bitmap, 0, 0, null);

                    Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paintText.setColor(Color.WHITE);
                    paintText.setTextSize(context.getResources().getDimension(R.dimen.support_close_ts));
                    paintText.setStyle(Paint.Style.FILL);

                    Rect rectText = new Rect();
                    paintText.getTextBounds(captionString, 0, captionString.length(), rectText);

                 //   canvas.drawText(captionString, (newBitmap.getWidth() / 3) - (rectText.width() / 3), rectText.height() * topMargin, paintText);
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
                    bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 3, bitmap.getHeight() / 3, false);
                    canvas.drawBitmap(bitmap, 10, 10, null);

                    canvas.drawText(captionString, 20 +  bitmap.getWidth(), bitmap.getHeight()/2 + bitmap.getHeight()/3  , paintText);


                    bitmap.recycle();

                    String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), newBitmap, "default", null);
                    Uri bmpUri = Uri.parse(path);


                    final Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    share.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    share.setType("image/png");
                    context.startActivity(share);

                    bitmap.recycle();
                    newBitmap.recycle();
                } catch (OutOfMemoryError e) {
                } catch (NullPointerException e) {
                } catch (IllegalStateException e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                callback.onShare();


                super.onPostExecute(aVoid);
            }
        }.execute();
    }



    public static void shareDataOfSecretMonth(final View view,final View view1, final Context context, final float topMargin, final MoodActivity.ShareCallback callback) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Bitmap bitmap = getBitmapFromView(view);
                    Bitmap bitmap1 = getBitmapFromView(view1);

                    Bitmap combinebitmap = ProcessingBitmap(bitmap,bitmap1);

                    Bitmap newBitmap;
                    Bitmap.Config config = combinebitmap.getConfig();
                    if (config == null) {
                        config = Bitmap.Config.ARGB_8888;
                    }
                    newBitmap = Bitmap.createBitmap(combinebitmap.getWidth(), combinebitmap.getHeight(), config);

                    Canvas canvas = new Canvas(newBitmap);
                    String captionString = context.getString(R.string.app_name);
                    canvas.drawBitmap(combinebitmap, 0, 0, null);

                    Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paintText.setColor(Color.WHITE);
                    paintText.setTextSize(context.getResources().getDimension(R.dimen.support_close_ts));
                    paintText.setStyle(Paint.Style.FILL);

                    Rect rectText = new Rect();
                    paintText.getTextBounds(captionString, 0, captionString.length(), rectText);

                  /*  canvas.drawText(captionString,
                            (newBitmap.getWidth() / 2) - (rectText.width() / 2), rectText.height() * topMargin, paintText);
                  */  bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
                    bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 3, bitmap.getHeight() / 3, false);
                    canvas.drawBitmap(bitmap, 10, 10, null);

                    canvas.drawText(captionString, 20 +  bitmap.getWidth(), bitmap.getHeight()/2 + bitmap.getHeight()/3  , paintText);


                    bitmap.recycle();

                    String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), newBitmap, "default", null);
                    Uri bmpUri = Uri.parse(path);


                    final Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    share.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    share.setType("image/png");
                    context.startActivity(share);

                    bitmap.recycle();
                    newBitmap.recycle();
                } catch (OutOfMemoryError e) {
                } catch (NullPointerException e) {
                } catch (IllegalStateException e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                callback.onShare();


                super.onPostExecute(aVoid);
            }
        }.execute();
    }



    public static Bitmap decodeSampledBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 98, stream);
        byte[] byteArray = stream.toByteArray();
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
    }


    public static Bitmap getBitmapFromView(View view)
    {
        int viewwidth = view.getWidth();

        Bitmap returnedBitmap = null;


          returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);



        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return decodeSampledBitmap(returnedBitmap, view.getWidth(), view.getWidth());
    }

/*

    private static  Bitmap CombineBitmap(Bitmap v1, Bitmap v2){
        Bitmap c = v1;
        Bitmap s = v2;
        Bitmap newBitmap = null;

        try {

            int w;
            if(c.getWidth() >= s.getWidth()){
                w = c.getWidth();
            }else{
                w = s.getWidth();
            }

            int h;
            if(c.getHeight() >= s.getHeight()){
                h = c.getHeight();
            }else{
                h = s.getHeight();
            }

            Bitmap.Config config = c.getConfig();
            if(config == null){
                config = Bitmap.Config.ARGB_8888;
            }

            newBitmap = Bitmap.createBitmap(w, h, config);
            Canvas newCanvas = new Canvas(newBitmap);

            newCanvas.drawBitmap(c, 0, 0, null);

            Paint paint = new Paint();
            paint.setAlpha(125);
            newCanvas.drawBitmap(s, 0, 0, paint);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return newBitmap;
    }
*/


    private static  Bitmap ProcessingBitmap(Bitmap v1, Bitmap v2){
        Bitmap bm1 = v1;
        Bitmap bm2 =  v2;
        Bitmap newBitmap = null;

        try {


            int w = bm1.getWidth() + bm2.getWidth();
            int h;
            if(bm1.getHeight() >= bm2.getHeight()){
                h = bm1.getHeight();
            }else{
                h = bm2.getHeight();
            }

            Bitmap.Config config = bm2.getConfig();
            if(config == null){
                config = Bitmap.Config.ARGB_8888;
            }

            newBitmap = Bitmap.createBitmap(w, h, config);
            Canvas newCanvas = new Canvas(newBitmap);

            newCanvas.drawBitmap(bm2, 0, 0, null);
            newCanvas.drawBitmap(bm1, bm2.getWidth(), 0, null);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newBitmap;
    }


    public static void shareData(Context activity, String string) {
        final Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String text = activity.getString(R.string.checkou_app) + string;
        share.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
        share.putExtra(Intent.EXTRA_TEXT, text);
        share.setType("text/plain");
        activity.startActivity(share);
    }

    public static boolean checkBackgroundImage(String bgImageName) {
        if (bgImageName == null)
            return false;
        try
        {
            bgImageName = bgImageName.replaceAll("[^\\d]", "");

            if (Integer.parseInt(bgImageName) > 13)
                return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return false;
    }
}
