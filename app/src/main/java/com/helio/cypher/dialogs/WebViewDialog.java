package com.helio.cypher.dialogs;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.cypher.R;
import com.helio.cypher.activities.MySecretsActivity;
import com.helio.cypher.activities.SupportActivity;
import com.helio.cypher.utils.Constants;

public class WebViewDialog extends Fragment implements View.OnClickListener {

    private View mView;
    private View mOutView;

    private WebView mWeb;
    private String url;

    boolean loadingFinished = true;
    boolean redirect = false;

    public static WebViewDialog instance(String url, String type) {
        WebViewDialog dialog = new WebViewDialog();
        Bundle data = new Bundle();
        data.putString(Constants.DIALOG_KEY, type);
        data.putString(Constants.WEB_URL, url);
        dialog.setArguments(data);
        return dialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.web_dialog, null);

        mOutView = mView.findViewById(R.id.web_root);
        mOutView.setOnClickListener(this);

        YoYo.with(Techniques.FadeIn).duration(500).playOn(mOutView);
        mView.findViewById(R.id.web_dialog_close).setOnClickListener(this);

        mWeb = (WebView) mView.findViewById(R.id.support_web_view);

        try {
            mWeb.getSettings().setJavaScriptEnabled(true);
            mWeb.getSettings().setSupportZoom(true);
            mWeb.getSettings().setBuiltInZoomControls(true);
            mWeb.getSettings().setDisplayZoomControls(true);
            mWeb.getSettings().setLoadWithOverviewMode(true);
        } catch (Exception e) {
        }

        try {
            url = getArguments().getString(Constants.WEB_URL);
            if (!url.startsWith("http://"))
                url = "http://" + url;
        } catch (NullPointerException e) {
            url = "http://silentsecret.uk";
        }

       /* if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.SUPPORT_WEB_VIEW)) {
            ((SupportActivity) getActivity()).showProgress();
        } else if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.BACKEND_WEB_VIEW)) {
            ((MySecretsActivity) getActivity()).showProgress();
        }
*/

        mWeb.loadUrl(url);

        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                mWeb.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadingFinished = false;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                if (!redirect) {
                    loadingFinished = true;
                }

                if (loadingFinished && !redirect) {
                    try {
                       /* if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.SUPPORT_WEB_VIEW)) {
                            ((SupportActivity) getActivity()).hideProgress();
                        } else if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.BACKEND_WEB_VIEW)) {
                            ((MySecretsActivity) getActivity()).stopProgress();
                        }*/
                    } catch (NullPointerException nil) {
                    }
                } else {
                    redirect = false;
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                /*if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.SUPPORT_WEB_VIEW)) {
                    if (isAdded()) ((SupportActivity) getActivity()).hideProgress();
                } else if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.BACKEND_WEB_VIEW)) {
                    if (isAdded()) ((MySecretsActivity) getActivity()).stopProgress();
                }*/
            }
        });

        YoYo.with(Techniques.BounceIn).duration(500).playOn(mWeb);
        YoYo.with(Techniques.BounceIn).duration(500).playOn(mView.findViewById(R.id.web_dialog_close));
        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.web_dialog_close:

                try {
                    if (Build.VERSION.SDK_INT < 18) {
                        mWeb.clearView();
                    } else {
                        mWeb.loadUrl("about:blank");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.SUPPORT_WEB_VIEW)) {
                    ((SupportActivity) getActivity()).webViewClosed();
                } else if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.BACKEND_WEB_VIEW)) {
                    ((MySecretsActivity) getActivity()).webViewClosed();
                }
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.SUPPORT_WEB_VIEW)) {
            ((SupportActivity) getActivity()).hideProgress();
        } else if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.BACKEND_WEB_VIEW)) {
            ((MySecretsActivity) getActivity()).stopProgress();
        }
    }
}
