package com.helio.cypher.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helio.cypher.R;
import com.helio.cypher.activities.ChatDetailsScreen;
import com.helio.cypher.models.ChatDetailBean;

import java.util.ArrayList;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class ChatDetailsAdapter extends BaseAdapter {

    private ArrayList<ChatDetailBean> mChatList = new ArrayList<ChatDetailBean>();
    private Activity mActivity;
     ChatDetailBean bean = null;
    public ChatDetailsAdapter(Activity mActivity, ArrayList<ChatDetailBean> mChatList) {
        this.mActivity = mActivity;
        this.mChatList = mChatList;
    }

    @Override
    public int getCount() {
        return mChatList.size();
    }

    @Override
    public ChatDetailBean getItem(int position) {
        return mChatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /*********
     * Create a holder Class to contain inflated xml file elements
     *********/
    public static class ViewHolder {
        public TextView receive_message_text;
        public TextView receive_message_date;
        public TextView sent_message_date;
        public TextView sent_message_text;


        public LinearLayout chat_receive_layout;
        public LinearLayout sent_msg_ll;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        try {


        if (convertView == null)
        {
            /***********  Layout inflator to call external xml layout () ***********/
            LayoutInflater inflater = (LayoutInflater) mActivity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            convertView = inflater.inflate(R.layout.chat_details_row_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.receive_message_text = (TextView) convertView.findViewById(R.id.receive_message_text);
            holder.receive_message_date = (TextView) convertView.findViewById(R.id.receive_message_date);
            holder.sent_message_date = (TextView) convertView.findViewById(R.id.sent_message_date);
            holder.sent_message_text = (TextView) convertView.findViewById(R.id.sent_message_text);
            holder.chat_receive_layout = (LinearLayout) convertView.findViewById(R.id.chat_receive_layout);
            holder.sent_msg_ll = (LinearLayout) convertView.findViewById(R.id.sent_msg_ll);

            /************  Set holder with LayoutInflater ************/



            holder.sent_message_text.setId(position+1);
            holder.receive_message_text.setId(position+2);

            convertView.setTag(holder);




        } else {
            holder = (ViewHolder) convertView.getTag();
        }


    bean = mChatList.get(position);
        if (bean.isMine())
        {

            try
            {
                holder.sent_msg_ll.setVisibility(View.VISIBLE);
                holder.chat_receive_layout.setVisibility(View.GONE);



                String  doowurl = bean.getDoowappmUrl();

                if(doowurl!= null && !doowurl.equalsIgnoreCase(""))
                {



                    String lyrics = bean.getYourMsg();

                    String splitarra[] = lyrics.split("\"");

                    if(splitarra!= null && splitarra.length>1)
                    {
                        String underlinetext = splitarra[1];

                        SpannableString content = new SpannableString(underlinetext);
                        content.setSpan(new UnderlineSpan(), 0, underlinetext.length(), 0);


                        holder.sent_message_text.setText(splitarra[0]);
                        holder.sent_message_text.append(content);
                        if(splitarra.length>2)
                        holder.sent_message_text.append(splitarra[2]);

                    }
                    else
                    {
                        SpannableString content = new SpannableString(lyrics);
                        content.setSpan(new UnderlineSpan(), 0, lyrics.length(), 0);
                        holder.sent_message_text.setText(content);

                    }




                }
                else
                {


                   if (bean.getYourMsg() != null && bean.getYourMsg().contains("www.") || bean.getYourMsg().contains("http://"))
                    {
                        holder.sent_message_text.setText(bean.getYourMsg());
                        holder.sent_message_text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                try
                                {
                                    TextView tev = (TextView) v;
                                    ChatDetailsScreen.webview_layout.setVisibility(View.VISIBLE);
                                    ChatDetailsScreen.initWebView(tev.getText().toString());
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();

                                }

                            }
                        });
                    } else {

                        holder.sent_message_text.setText(bean.getYourMsg());

                    }


                }



                holder.sent_message_date.setText(bean.getSentTime());
            }
            catch (Exception e)
            {

            }

        } else
        {
            try
            {
                holder.sent_msg_ll.setVisibility(View.GONE);
                holder.chat_receive_layout.setVisibility(View.VISIBLE);

                String  doowurl = bean.getDoowappmUrl();


                if(doowurl!= null && !doowurl.equalsIgnoreCase(""))
                {


      /*              SpannableString content = new SpannableString(bean.getYourMsg());
                    content.setSpan(new UnderlineSpan(), 0, bean.getYourMsg().length(), 0);
                    holder.receive_message_text.setText(content);*/

                    String lyrics = bean.getYourMsg();

                    String splitarra[] = lyrics.split("\"");

                    if(splitarra!= null && splitarra.length>1)
                    {
                        String underlinetext = splitarra[1];

                        SpannableString content = new SpannableString(underlinetext);
                        content.setSpan(new UnderlineSpan(), 0, underlinetext.length(), 0);


                        holder.receive_message_text.setText(splitarra[0]);
                        holder.receive_message_text.append(content);
                        if(splitarra.length>2)
                        holder.receive_message_text.append(splitarra[2]);

                    }
                    else
                    {
                        SpannableString content = new SpannableString(lyrics);
                        content.setSpan(new UnderlineSpan(), 0, lyrics.length(), 0);
                        holder.receive_message_text.setText(content);

                    }


                }
                else
                {

                    //if (!bean.getYourMsg().contains("www."))
                    if (bean.getYourMsg() != null && bean.getYourMsg().contains("www.") || bean.getYourMsg().contains("http://"))
                    {
                        holder.receive_message_text.setText(bean.getYourMsg());
                        holder.receive_message_text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try
                                {
                                    TextView tev = (TextView) v;
                                    ChatDetailsScreen.webview_layout.setVisibility(View.VISIBLE);
                                    ChatDetailsScreen.initWebView(tev.getText().toString());
                                }
                                catch (Exception e)
                                {

                                }

                            }
                        });

                    } else {
                        holder.receive_message_text.setText(bean.getYourMsg());


                    }


                }

                holder.receive_message_date.setText(bean.getSentTime());
            }
            catch (Exception e)
                {
                    e.printStackTrace();
                }

        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return convertView;
    }

    public void add(ChatDetailBean object) {
        mChatList.add(object);
        notifyDataSetChanged();
    }
}
