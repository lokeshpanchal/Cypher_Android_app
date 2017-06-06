package com.helio.cypher.dialogs;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.helio.cypher.R;
import com.helio.cypher.activities.MySecretsActivity;
import com.helio.cypher.activities.SupportActivity;
import com.helio.cypher.utils.Constants;

public class RateDialog extends Fragment implements View.OnClickListener {

    private View mView;
    private RatingBar ratingBar;
    public int rate = 5;

    public static RateDialog instance(String t) {
        RateDialog rateFrag = new RateDialog();
        Bundle data = new Bundle();
        data.putString(Constants.DIALOG_KEY, t);
        rateFrag.setArguments(data);
        return rateFrag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.rate_dialog, null);
        mView.findViewById(R.id.rate_submit).setOnClickListener(this);
        mView.findViewById(R.id.rate_root).setOnClickListener(this);
        ratingBar = (RatingBar) mView.findViewById(R.id.rate_bar);

        TextView rateTitle = (TextView) mView.findViewById(R.id.rate_dialog_title);

        if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.SUPPORT_WEB_VIEW)) {
            rateTitle.setText(getString(R.string.rate_question));
        } else if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.BACKEND_WEB_VIEW)) {
            rateTitle.setText(getString(R.string.how_helpful_backend));
        }

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                rate = (int) rating;
                ratingBar.setRating(rating);
            }
        });

        mView.findViewById(R.id.rate_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(RateDialog.this).commit();
            }
        });

        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rate_submit:

                if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.SUPPORT_WEB_VIEW)) {
                    ((SupportActivity) getActivity()).makeRate(rate);
                } else if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.BACKEND_WEB_VIEW)) {
                    ((MySecretsActivity) getActivity()).makeRate(rate);
                }
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }
}
