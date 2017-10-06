package com.helio.silentsecret.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.helio.silentsecret.R;
import com.helio.silentsecret.models.DisplaySizes;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SecretPagerAdapter extends PagerAdapter {
    private Context mContext;
    LayoutInflater mLayoutInflater;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private DisplayImageOptions options;


    List<DisplaySizes> PhotoModels = null;

String secret_text = "";

    public SecretPagerAdapter(String secret_text, Context mContext, List<DisplaySizes> PhotoModels) {
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.PhotoModels = PhotoModels;
        this.secret_text = secret_text;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.adapter_secretpager, container, false);
        //ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_foster);
         final ImageView iv_foster = (ImageView) itemView.findViewById(R.id.iv_foster);
          TextView sec_text = (TextView) itemView.findViewById(R.id.sec_text);

        iv_foster.setScaleType(ImageView.ScaleType.FIT_XY);
       // sec_text.setVisibility(View.VISIBLE);
      //  iv_foster.setBackgroundResource(PhotoModels.get(position).getImageUrl(ImageSize.MEDIUM));
        String url = PhotoModels.get(position).getImageUrl();

        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.loading_gif)
                .into(iv_foster);
        //ImageLoader.getInstance().displayImage(PhotoModels.get(position).getImageUrl(ImageSize.SMALL), iv_foster, options, animateFirstListener);



       /* Glide.with(mContext)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.loading_gif)
                .into(new SimpleTarget<Bitmap>(100, 100)
        {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    iv_foster.setBackground(drawable);
                }
            }
        });*/

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);

    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return PhotoModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void finishUpdate(View container) {

    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener
    {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }


}