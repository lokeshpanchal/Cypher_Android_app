package com.helio.silentsecret.application;

import android.content.Context;
import android.os.AsyncTask;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.helio.silentsecret.models.SupportOrganisation;
import com.helio.silentsecret.utils.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class SilentSecretApplication extends MultiDexApplication {

    private static SilentSecretApplication mApp;
    private static final String PROPERTY_ID = "UA-62601348-1";

    private List<SupportOrganisation> mOrganisations;

    public List<SupportOrganisation> getmOrganisations() {
        return mOrganisations;
    }

    public void setmOrganisations(List<SupportOrganisation> mOrganisations) {
        this.mOrganisations = mOrganisations;
    }

    public enum TrackerName {
        APP_TRACKER
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public synchronized Tracker getTracker(TrackerName trackerId)
    {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = trackerId == TrackerName.APP_TRACKER ? analytics.newTracker(PROPERTY_ID) : analytics.newTracker(PROPERTY_ID);
            mTrackers.put(trackerId, t);
        }
        loadLocal();
        Fabric.with(this, new Crashlytics());
        initImageLoader(this);
        return mTrackers.get(trackerId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try
        {
            mApp = this;

            //initParse();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

/*
    private void initParse()
    {


        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                        .applicationId("bvqclrrq5Y215z9CdcP0CVkQAObUWNx7KxQcC81w")
                        .clientKey("r0LjcaCn0OmeJxev25H90NyWRl0A61LYkiZhOUgS")

                .server("http://parseserver-has22-env.us-west-2.elasticbeanstalk.com/parse/")
                .build()
        );

        ParsePush.subscribeInBackground("all", new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.e("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);

        try
        {
            loadSupport();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
*/

/*
    private void loadSupport() {
        try
        {
            new SupportLoader(new SupportCallback() {
                @Override
                public void onUpdate(List<SupportOrganisation> data) {
                    try
                    {
                        if (data == null)
                            return;
                        setmOrganisations(data);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }).execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
*/

    public static SilentSecretApplication instance() {
        return mApp;
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY).denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    protected void loadLocal() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                Constants.LOCAL_KEYS = readLocalKeys().split(" ");
                return null;
            }

            private String readLocalKeys() {
                String text = "local.txt";
                byte[] buffer = null;
                InputStream is;
                try {
                    is = getAssets().open(text);
                    int size = is.available();
                    buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new String(buffer);
            }
        }.execute();
    }

}
