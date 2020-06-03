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
import model.Chat_client_model;
import util.Session_management;

/**
 * Created by Rajesh on 2017-08-26.
 */

public class Chat_client_adapter extends RecyclerView.Adapter<Chat_client_adapter.MyViewHolder> {

    private List<Chat_client_model> modelList;
    private Context context;
    private String getuser_id;
    private Session_management sessionManagement;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_chat_client_name);
            image = (ImageView) view.findViewById(R.id.iv_chat_client_img);
        }
    }

    public Chat_client_adapter(List<Chat_client_model> modelList, Context context) {
        this.modelList = modelList;
        sessionManagement = new Session_management(context);
        getuser_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);
    }

    @Override
    public Chat_client_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat_client, parent, false);

        context = parent.getContext();

        return new Chat_client_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Chat_client_adapter.MyViewHolder holder, int position) {
        Chat_client_model mList = modelList.get(position);

        String image_url = "";

        if (getuser_id.equals(mList.getUser_1()) && !getuser_id.equals(mList.getUser_2())) {
            holder.title.setText(mList.getUser_2_fullname());
            image_url = mList.getUser_2_image();
        } else if (!getuser_id.equals(mList.getUser_1()) && getuser_id.equals(mList.getUser_2())) {
            holder.title.setText(mList.getUser_1_fullname());
            image_url = mList.getUser_1_image();
        }

        Picasso.with(context)
                .load(BaseURL.IMG_PROFILE_URL + image_url)
                .placeholder(R.drawable.ic_loading_02)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}