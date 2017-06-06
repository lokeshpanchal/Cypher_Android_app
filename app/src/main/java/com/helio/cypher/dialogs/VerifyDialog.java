package com.helio.cypher.dialogs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helio.cypher.R;

public class VerifyDialog extends Fragment implements View.OnClickListener {

    private View mView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.verify_dialog, null);
        mView.findViewById(R.id.verify_ok).setOnClickListener(this);
        mView.findViewById(R.id.verify_root).setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verify_ok:
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }
}
