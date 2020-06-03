package adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Config.BaseURL;
import techline.carsapp.R;
import model.Review_model;
import util.CommonAsyTask;
import util.ConnectivityReceiver;
import util.NameValuePair;

/**
 * Created by Rajesh on 2017-08-24.
 */

public class Review_adapter extends RecyclerSwipeAdapter<Review_adapter.MyViewHolder> {

    private List<Review_model> modelList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public SwipeLayout swipeLayout;
        public Button buttonDelete;
        public TextView title, comment, date;
        public ImageView image;
        public RatingBar ratingBar;

        public MyViewHolder(View view) {
            super(view);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            buttonDelete = (Button) itemView.findViewById(R.id.btn_review_delete);
            title = (TextView) view.findViewById(R.id.tv_review_name);
            comment = (TextView) view.findViewById(R.id.tv_review_comment);
            date = (TextView) view.findViewById(R.id.tv_review_date);
            image = (ImageView) view.findViewById(R.id.iv_review_img);
            ratingBar = (RatingBar) view.findViewById(R.id.rb_review);

            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public Review_adapter(List<Review_model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public Review_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_review, parent, false);

        context = parent.getContext();

        return new Review_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Review_adapter.MyViewHolder holder, final int position) {
        final Review_model mList = modelList.get(position);

        // swipe layout for delete row
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                //YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        holder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(holder.swipeLayout);

                if (ConnectivityReceiver.isConnected()) {
                    makeDeleteReview(mList.getReview_id(), position);
                } else {
                    ConnectivityReceiver.showSnackbar(context);
                }
            }
        });

        Picasso.with(context)
                .load(BaseURL.IMG_PROFILE_URL + mList.getUser_image())
                .placeholder(R.drawable.ic_loading_02)
                .into(holder.image);

        holder.title.setText(mList.getUser_fullname());
        holder.comment.setText(mList.getComment());

        try {
            // it comes out like this 2013-08-31 10:55:22 so adjust the date format
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = df.parse(mList.getOn_date());
            long epoch = date.getTime();

            String timePassedString = DateUtils.getRelativeTimeSpanString(epoch, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

            holder.date.setText(timePassedString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Float rating = Float.valueOf(mList.getRating());
        holder.ratingBar.setRating(rating);

        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    private void makeDeleteReview(String review_id, final int position) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("review_id", review_id));

        CommonAsyTask task = new CommonAsyTask(params,
                BaseURL.DELETE_REVIEW_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(context.toString(), response);

                modelList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, modelList.size());
                mItemManger.closeAllItems();

                Toast.makeText(context, "" + response, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void VError(String responce) {
                Log.e(context.toString(), responce);
                Toast.makeText(context, "" + responce, Toast.LENGTH_SHORT).show();
            }
        }, true, context);
        task.execute();

    }

}