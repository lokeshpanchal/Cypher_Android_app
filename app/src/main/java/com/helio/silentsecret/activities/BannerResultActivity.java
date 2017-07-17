package com.helio.silentsecret.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.adapters.CommentsAdapter;
import com.helio.silentsecret.adapters.MineAdapter;
import com.helio.silentsecret.models.Comment;
import com.helio.silentsecret.models.Secret;
;

import java.util.Date;
import java.util.List;

public class BannerResultActivity extends BaseActivity implements View.OnClickListener {

    public static final int COMMENTS_MODE = 0;
    public static final int SECRETS_MODE = 1;

    public static final String COMMENTS_MODE_KEY = "comments_mode";
    public static final String SECRETS_MODE_KEY = "secrets_mode";

    public static final String START_OVER_DATE = "date";
    public static final String END_OVER_DATE = "end_date";
    public static final String TITLE = "title";

    private int curMode;

    private Date mDate;
    private Date mCurrentDate;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        if (getIntent().getExtras() == null)
            finish();

        if (getIntent().getExtras().containsKey(COMMENTS_MODE_KEY)) {
            curMode = COMMENTS_MODE;
        } else if (getIntent().getExtras().containsKey(SECRETS_MODE_KEY)) {
            curMode = SECRETS_MODE;
        } else {
            finish();
        }

        init();
        mDate = (Date) getIntent().getExtras().getSerializable(START_OVER_DATE);

        if (getIntent().getExtras().containsKey(END_OVER_DATE)) {
            mCurrentDate = (Date) getIntent().getExtras().getSerializable(END_OVER_DATE);
        }

       /* if (curMode == COMMENTS_MODE)
        {
            loadComments();
        } else {
            loadSecrets();
        }*/
    }

    private void init() {
        mListView = (ListView) findViewById(R.id.result_list);
        style();
    }

    private void style() {
        ImageView close = (ImageView) findViewById(R.id.comment_result_close);
        close.setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.comment_result_message);
        title.setText(getIntent().getExtras().getString(TITLE));
        TextView noResults = (TextView) findViewById(R.id.results_no);

        if (curMode == SECRETS_MODE) {
            noResults.setTextColor(getResources().getColor(R.color.white));
            title.setTextColor(getResources().getColor(R.color.white));
            close.setImageResource(R.drawable.ic_close_white);
            findViewById(R.id.result_root).setBackgroundColor(getResources().getColor(R.color.main));
            mListView.setDividerHeight(0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_result_close:
                finish();
                break;
        }
    }

/*
    private void loadComments() {
        MineLoader.getSecretsCount().findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects == null || objects.isEmpty()) {
                        showNoResults(true);
                        return;
                    }

                    List<ParseObject> list = new ArrayList<ParseObject>();

                    for (int i = 0; i < objects.size(); i++) {
                        list.add(ObjectMaker.form(objects.get(i)).getObject());
                    }

                    loadCommentsFromIds(list);
                } else {
                    showNoResults(true);
                }
            }
        });
    }

    private void loadCommentsFromIds(List<ParseObject> secrets) {
        FeedLoader.getCommentsOfYear(mDate, secrets).findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    if (objects == null) {
                        showNoResults(true);
                        return;
                    }
                    if (objects.size() > 0) {
                        new AsyncTask<Void, Void, List<Comment>>() {

                            private List<Comment> data = new ArrayList<Comment>();

                            @Override
                            protected List<Comment> doInBackground(Void... voids) {
                               */
/* for (ParseObject item : objects)
                                    data.add(ObjectMaker.formComment(item));*//*


                                List<Comment> reply = new ArrayList<>();
                                List<Comment> temp = new ArrayList<>();
                                for (Comment item : data) {
                                    if (item.isReply())
                                        reply.add(item);
                                }

                                for (Comment item : data) {
                                    if (!item.isReply()) {
                                        temp.add(item);
                                        for (Comment replyItem : reply) {
                                            if (replyItem.getReplyTo().equals(item.getUser()))
                                                temp.add(replyItem);
                                        }
                                    }
                                }
                                data.clear();
                                reply.clear();
                                return temp;
                            }

                            @Override
                            protected void onPostExecute(List<Comment> list) {
                                super.onPostExecute(list);
                                updateList(list);
                            }
                        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        showNoResults(true);
                    }
                } else {
                    showNoResults(true);
                }
            }
        });
    }

    private void loadSecrets() {
        MineLoader.getHappyLoveYear(mDate, mCurrentDate).findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    if (objects == null) {
                        showNoResults(true);
                        return;
                    }
                    List<Secret> list = new ArrayList<>();
                    if (objects.size() > 0) {
                        showNoResults(false);
                        for (ParseObject item : objects)
                            list.add(ObjectMaker.form(item));

                        updateListAgainst(list);
                    } else {
                        showNoResults(true);
                    }
                } else {
                    showNoResults(true);
                }
            }
        });
    }
*/

    private void updateList(List<Comment> list) {
        mListView.setAdapter(new CommentsAdapter(LayoutInflater.from(this),
                list, this, true));
    }

    private void updateListAgainst(List<Secret> list) {
        MineAdapter adapter = new MineAdapter(LayoutInflater.from(this),
                list, this, false);
        adapter.setDeleteMode(false);
        adapter.setShowEnvelope(false);
        adapter.setVerifyMode(false);
        mListView.setAdapter(adapter);
    }

    private void showNoResults(boolean flag) {
        if (flag)
            findViewById(R.id.results_no).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.results_no).setVisibility(View.GONE);

    }
}
