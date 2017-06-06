package com.helio.cypher.checkers;

import android.os.AsyncTask;

import com.helio.cypher.StaticObjectDTO.RiskWordDTO;
import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.callbacks.RiskCheckerCallback;

import java.util.regex.Pattern;

public class RiskChecker {

    private RiskCheckerCallback mCallback;

    public RiskChecker(RiskCheckerCallback callback) {
        mCallback = callback;
    }

    public void execute( final String text) {
        new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... params) {

                try {
                    if(MainActivity.stataicObjectDTO!= null)
                    {
                        if (MainActivity.stataicObjectDTO.getRiskWordDTOs() == null || text == null)
                        {
                            mCallback.onDone(true, null, null);
                            return null;
                        }
                    }
                    else
                    {
                        mCallback.onDone(true, null, null);
                        return null;
                    }

                    for (RiskWordDTO item : MainActivity.stataicObjectDTO.getRiskWordDTOs()) {
                        for (String part : item.getKeys()) {
                            if (Pattern.compile(Pattern.quote(part), Pattern.CASE_INSENSITIVE).matcher(text).find()) {
                                mCallback.onDone(false, part, item.getState());
                                return null;
                            }
                        }
                    }

                    mCallback.onDone(true, null, null);
                    return null;
                } catch (NullPointerException nil) {
                    mCallback.onDone(true, null, null);
                    return null;
                }
            }
        }.execute();
    }


}
