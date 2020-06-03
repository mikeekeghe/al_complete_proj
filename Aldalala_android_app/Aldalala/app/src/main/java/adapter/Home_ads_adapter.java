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
import model.Post_model;

/**
 * Created by Rajesh Dabhi on 20/7/2017.
 */

public class Home_ads_adapter extends RecyclerView.Adapter<Home_ads_adapter.MyViewHolder> {

    private List<Post_model> modelList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, model, price, currency;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_post_ad_title);
            model = (TextView) view.findViewById(R.id.tv_post_ad_model);
            price = (TextView) view.findViewById(R.id.tv_post_ad_price);
            currency = (TextView) view.findViewById(R.id.tv_post_ad_currency);
            image = (ImageView) view.findViewById(R.id.iv_post_ad_img);
        }
    }

    public Home_ads_adapter(List<Post_model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public Home_ads_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_home_rv_ads, parent, false);

        context = parent.getContext();

        return new Home_ads_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Home_ads_adapter.MyViewHolder holder, int position) {
        Post_model mList = modelList.get(position);

        Picasso.with(context)
                .load(BaseURL.IMG_POST_URL + mList.getImage_path())
                .placeholder(R.drawable.ic_loading_02)
                .into(holder.image);

        holder.title.setText(mList.getPost_title());
        holder.model.setText(mList.getCity_name());
        holder.currency.setText(mList.getCurrency());
        holder.price.setText(mList.getPost_km());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}