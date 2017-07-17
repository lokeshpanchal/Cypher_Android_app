package com.helio.silentsecret.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.helio.silentsecret.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoaderUtil {

    ImageLoader mImageLoader;
    ExecutorService executorService;
    Context mContext;
    Builder mOptionsBuilderCacheAlpha;
    Builder mOptionsBuilderFromAssets;

    private static final String ASSETS_BACKS = "assets://backs/";
    private static final String ASSETS_FRUITS = "assets://";
    private static final String ASSETS_DRAWABLE = "drawable://";

    public ImageLoaderUtil(Context context) {
        mContext = context;
        executorService = Executors.newFixedThreadPool(5);

        mOptionsBuilderCacheAlpha = new Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher).resetViewBeforeLoading(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT).showImageOnFail(R.drawable.ic_launcher).
                        considerExifParams(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true);

        mOptionsBuilderFromAssets = new Builder()
                .showImageForEmptyUri(R.drawable.ic_default_bg).resetViewBeforeLoading(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT).showImageOnFail(R.drawable.ic_default_bg).
                        considerExifParams(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisk(true);

        mImageLoader = ImageLoader.getInstance();
    }

    public void loadImageAlphaCache(String url, ImageView imageView) {
        mImageLoader.displayImage(url, imageView, mOptionsBuilderCacheAlpha.build());
    }

    public void loadImageAlphaCache(String url, ImageView imageView, ImageLoadingListener listener) {
        mImageLoader.displayImage(url, imageView, mOptionsBuilderCacheAlpha.build(), listener);
    }

    public void loadDrawable(int id, ImageView imageView) {
        mImageLoader.displayImage(ASSETS_DRAWABLE + id, imageView, mOptionsBuilderCacheAlpha.build());
    }

    public void loadSimpleDraw(String id, ImageView imageView) {
        mImageLoader.displayImage(ASSETS_BACKS + id, imageView, mOptionsBuilderFromAssets.build());
    }

    public void loadFruit(String id, ImageView imageView) {
        mImageLoader.displayImage(ASSETS_FRUITS + id, imageView, mOptionsBuilderCacheAlpha.build());
    }
}
