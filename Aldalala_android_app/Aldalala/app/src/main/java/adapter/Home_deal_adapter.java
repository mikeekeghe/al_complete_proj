package adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Config.BaseURL;
import techline.carsapp.Post_detailActivity;
import techline.carsapp.R;
import model.Post_model;

/**
 * Created by Rajesh Dabhi on 20/7/2017.
 */

public class Home_deal_adapter extends RecyclerView.Adapter<Home_deal_adapter.MyViewHolder> {

    private List<Post_model> modelList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

            image.setOnClickListener(this);
            title.setOnClickListener(this);
            model.setOnClickListener(this);
            price.setOnClickListener(this);
            currency.setOnClickListener(this);
            location.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Post_model model = modelList.get(getAdapterPosition());

            if (id == R.id.iv_home_deal_img) {
                showImages(model.getImage_path());
            } else {
                Intent post_detail = new Intent(context, Post_detailActivity.class);

                post_detail.putExtra("post_id", model.getPost_id());
                post_detail.putExtra("title", model.getPost_title());
                post_detail.putExtra("date", model.getPost_date());
                post_detail.putExtra("year", model.getPost_year());
                post_detail.putExtra("km", model.getPost_km());
                post_detail.putExtra("price", model.getPost_price());
                post_detail.putExtra("city_name", model.getCity_name());
                post_detail.putExtra("desc", model.getPost_description());
                post_detail.putExtra("user_email", model.getUser_email());
                post_detail.putExtra("user_name", model.getUser_fullname());
                post_detail.putExtra("user_phone", model.getUser_phone());
                post_detail.putExtra("user_id", model.getUser_id());
                post_detail.putExtra("currency", model.getCurrency());

                context.startActivity(post_detail);
            }
        }
    }

    public Home_deal_adapter(List<Post_model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public Home_deal_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_home_rv_location_deal, parent, false);

        context = parent.getContext();

        return new Home_deal_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Home_deal_adapter.MyViewHolder holder, int position) {
        Post_model mList = modelList.get(position);

        Picasso.with(context)
                .load(BaseURL.IMG_POST_URL + mList.getImage_path())
                .placeholder(R.drawable.ic_loading_02)
                .into(holder.image);

        holder.title.setText(mList.getPost_title());
        holder.model.setText(mList.getPost_year() + ", " + mList.getPost_km() + " " + context.getResources().getString(R.string.kms));
        holder.location.setText(mList.getCity_name());
        holder.currency.setText(mList.getCurrency());
        holder.price.setText(mList.getPost_price());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    // showing custom alertdialog with viewpager images
    private void showImages(String imagepath) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_image_detail_zoom);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView iv_dialoge_cancle = (ImageView) dialog.findViewById(R.id.iv_dialog_cancle);
        ImageView iv_dialoge_img = (ImageView) dialog.findViewById(R.id.iv_dialog_img);

        Picasso.with(context)
                .load(BaseURL.IMG_POST_URL + imagepath)
                .placeholder(R.drawable.ic_loading_02)
                .skipMemoryCache()
                .resize(1024, 1024)
                .into(iv_dialoge_img);

        iv_dialoge_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}