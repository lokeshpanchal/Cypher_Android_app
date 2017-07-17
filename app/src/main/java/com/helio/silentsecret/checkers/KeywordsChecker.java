package com.helio.silentsecret.checkers;

import android.os.AsyncTask;

import com.helio.silentsecret.callbacks.KeywordCheckerCallback;

import java.util.List;
import java.util.regex.Pattern;

public class KeywordsChecker {

    private KeywordCheckerCallback mCallback;

    public KeywordsChecker(KeywordCheckerCallback callback) {
        mCallback = callback;
    }

    public void execute(final List<String> modelInput, final String text) {
        new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... params) {
                try {
                    if (modelInput == null || text == null) {
                        mCallback.onDone(true,"");
                        return null;
                    }

                    if(modelInput.size() == 1)
                    {
                        String keys = modelInput.get(0);
                        if (!keys.equalsIgnoreCase(""))
                        {
                            String[] separated = keys.split(";\\s*");
                            for (int i = 0; i < separated.length; i++)
                            {
                                String valueAfterSplit = separated[i];
                                if(valueAfterSplit!= null && !valueAfterSplit.equalsIgnoreCase(""))
                                modelInput.add(valueAfterSplit);
                            }
                        }
                    }

                    for (String item : modelInput)
                    {
                        if (Pattern.compile(Pattern.quote(item), Pattern.CASE_INSENSITIVE).matcher(text).find())
                        {
                            mCallback.onDone(false,item);
                            return null;
                        }
                    }

                    mCallback.onDone(true,"");
                    return null;
                } catch (NullPointerException nil) {
                    mCallback.onDone(true,"");
                    return null;
                }
            }
        }.execute();
    }


}
