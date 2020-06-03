package adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Config.BaseURL;
import techline.carsapp.R;
import model.Notification_model;

/**
 * Created by Rajesh on 2017-09-02.
 */

public class Notification_adapter extends RecyclerView.Adapter<Notification_adapter.MyViewHolder> {

    private List<Notification_model> modelList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, comment, date;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.tv_notific_title);
            comment = (TextView) view.findViewById(R.id.tv_notific_msg);
            date = (TextView) view.findViewById(R.id.tv_notific_date);
            image = (ImageView) view.findViewById(R.id.iv_notific_img);
        }
    }

    public Notification_adapter(List<Notification_model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public Notification_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notification, parent, false);

        context = parent.getContext();

        return new Notification_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Notification_adapter.MyViewHolder holder, final int position) {
        final Notification_model mList = modelList.get(position);

        Picasso.with(context)
                .load(BaseURL.IMG_POST_URL + mList.getImage_path())
                .placeholder(R.drawable.ic_loading_02)
                .into(holder.image);

        holder.title.setText(mList.getTitle());

        String message = mList.getMessage();

        if (message.length() > 20){
            message = message.substring(0,20)+"...";
        }

        holder.comment.setText(message);
        holder.date.setText(mList.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}