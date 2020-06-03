package adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Config.BaseURL;
import techline.carsapp.R;
import model.Post_model;

/**
 * Created by Rajesh on 2017-09-02.
 */

public class Home_deal_all_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post_model> modelList;
    private Context context;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, model, price, location, currency;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_home_deal_title);
            model = (TextView) view.findViewById(R.id.tv_home_deal_model);
            price = (TextView) view.findViewById(R.id.tv_home_deal_price);
            currency = (TextView) view.findViewById(R.id.tv_home_deal_currency);
            location = (TextView) view.findViewById(R.id.tv_home_deal_location);
            image = (ImageView) view.findViewById(R.id.iv_home_deal_img);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar6);


        }
    }

    public Home_deal_all_adapter(List<Post_model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public int getItemViewType(int position) {
        return modelList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_rv_location_deal, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_progress, parent, false);
            return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            Post_model mList = modelList.get(position);

            MyViewHolder userViewHolder = (MyViewHolder) holder;

            Picasso.with(context)
                    .load(BaseURL.IMG_POST_URL + mList.getImage_path())
                    .placeholder(R.drawable.ic_loading_02)
                    .into(userViewHolder.image);

            userViewHolder.title.setText(mList.getPost_title());
            userViewHolder.model.setText(mList.getPost_year() + ", " + mList.getPost_km() + " " + context.getResources().getString(R.string.kms));
            userViewHolder.location.setText(mList.getCity_name());
            userViewHolder.currency.setText(mList.getCurrency());
            userViewHolder.price.setText(mList.getPost_price());

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return modelList == null ? 0 : modelList.size();
    }

}