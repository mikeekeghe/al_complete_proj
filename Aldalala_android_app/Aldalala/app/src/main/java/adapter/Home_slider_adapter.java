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
import model.Slider_model;

/**
 * Created by Rajesh Dabhi on 20/7/2017.
 */

public class Home_slider_adapter extends RecyclerView.Adapter<Home_slider_adapter.MyViewHolder> {

    private List<Slider_model> modelList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            //title = (TextView) view.findViewById(R.id.tv_app_bar_title);
            image = (ImageView) view.findViewById(R.id.iv_home_slider);
        }
    }

    public Home_slider_adapter(List<Slider_model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public Home_slider_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_home_rv_slider, parent, false);

        context = parent.getContext();

        return new Home_slider_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Home_slider_adapter.MyViewHolder holder, int position) {
        Slider_model mList = modelList.get(position);

        Picasso.with(context)
                .load(BaseURL.IMG_SLIDER_URL+mList.getSlider_image())
                .placeholder(R.drawable.ic_loading_02)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}
