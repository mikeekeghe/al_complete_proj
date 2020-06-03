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
import model.Category_model;

/**
 * Created by Rajesh Dabhi on 19/7/2017.
 */

public class Home_category_adapter extends RecyclerView.Adapter<Home_category_adapter.MyViewHolder> {

    private List<Category_model> modelList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_app_bar_title);
            image = (ImageView) view.findViewById(R.id.iv_app_bar_img);
        }
    }

    public Home_category_adapter(List<Category_model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public Home_category_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_app_bar_rv_category, parent, false);

        context = parent.getContext();

        return new Home_category_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Home_category_adapter.MyViewHolder holder, int position) {
        Category_model mList = modelList.get(position);

        Picasso.with(context)
                .load(BaseURL.IMG_CATEGORY_URL+mList.getImage())
                .placeholder(R.mipmap.ic_launcher)
                /*.centerCrop()*/
                .into(holder.image);

        holder.title.setText(mList.getTitle());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}
