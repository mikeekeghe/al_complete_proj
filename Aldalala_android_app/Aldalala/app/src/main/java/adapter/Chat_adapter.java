package adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import techline.carsapp.R;
import model.Chat_model;

/**
 * Created by Rajesh on 2017-08-07.
 */

public class Chat_adapter extends RecyclerView.Adapter<Chat_adapter.ViewHolder> {

    //user id
    private String userId;
    private Context context;

    //Tag for tracking self message
    private int SELF = 786;

    //ArrayList of messages object containing all the messages in the thread
    private List<Chat_model> messages;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    //Constructor
    public Chat_adapter(Context context, List<Chat_model> messages, String userId) {
        this.userId = userId;
        this.messages = messages;
        this.context = context;
    }

    //IN this method we are tracking the self message
    @Override
    public int getItemViewType(int position) {
        //getting message object of current position
        Chat_model message = messages.get(position);

        //If its owner  id is  equals to the logged in user id
        if (message.getSender_id().equals(userId)) {
            //Returning self
            return SELF;
        }

        //else returning position
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Creating view
        View itemView;
        //if view type is self
        if (viewType == SELF) {
            //Inflating the layout self
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_chat_self, parent, false);
        } else {
            //else inflating the layout others
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_chat_other, parent, false);
        }
        //returing the view
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Adding messages to the views
        Chat_model message = messages.get(position);

        holder.textViewMessage.setText(message.getMessage());

        try {
            // it comes out like this 2013-08-31 10:55:22 so adjust the date format
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = df.parse(message.getCreated_date());
            long epoch = date.getTime();

            String timePassedString = DateUtils.getRelativeTimeSpanString(epoch, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

            holder.textViewTime.setText(timePassedString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    //Initializing views
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMessage;
        public TextView textViewTime;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewMessage = (TextView) itemView.findViewById(R.id.textViewMessage);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
        }
    }

}