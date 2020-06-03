package adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import techline.carsapp.Add_postActivity;
import techline.carsapp.Add_post_attributeActivity;
import techline.carsapp.Add_post_galleryActivity;
import techline.carsapp.R;
import techline.carsapp.ReviewActivity;
import model.Post_model;
import util.CommonAsyTask;
import util.NameValuePair;

/**
 * Created by Rajesh on 2017-08-12.
 */

public class My_post_adapter extends RecyclerView.Adapter<My_post_adapter.MyViewHolder> {

    private List<Post_model> modelList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, model, price, location, currency, tv_status;
        public ImageView image, iv_menu;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_home_deal_title);
            model = (TextView) view.findViewById(R.id.tv_home_deal_model);
            price = (TextView) view.findViewById(R.id.tv_home_deal_price);
            currency = (TextView) view.findViewById(R.id.tv_home_deal_currency);
            location = (TextView) view.findViewById(R.id.tv_home_deal_location);
            image = (ImageView) view.findViewById(R.id.iv_home_deal_img);
            iv_menu = (ImageView) view.findViewById(R.id.iv_my_post_menu);
            tv_status = (TextView) view.findViewById(R.id.tv_my_post_status);

            title.setOnClickListener(this);
            image.setOnClickListener(this);
            model.setOnClickListener(this);
            price.setOnClickListener(this);
            currency.setOnClickListener(this);
            location.setOnClickListener(this);
            iv_menu.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            int position = getAdapterPosition();

            if (id == R.id.iv_my_post_menu) {
                showPopup(position);
            } else if (id == R.id.iv_home_deal_img) {
                showImages(modelList.get(position).getImage_path());
            } else {
                String get_status = modelList.get(position).getPost_status();

                if (get_status.equals("1")) {
                    Intent editIntent = new Intent(context, Add_post_galleryActivity.class);
                    editIntent.putExtra("post_id", modelList.get(position).getPost_id());
                    editIntent.putExtra("isedit", "true");
                    context.startActivity(editIntent);
                } else if (get_status.equals("2")) {
                    Intent editIntent = new Intent(context, Add_post_attributeActivity.class);
                    editIntent.putExtra("post_id", modelList.get(position).getPost_id());
                    editIntent.putExtra("is_edit", "edit");
                    context.startActivity(editIntent);
                }
            }
        }

        public void showPopup(final int position) {
            // intialize popup menu with view
            final PopupMenu popup = new PopupMenu(context, iv_menu, Gravity.CENTER);
            // set xml menu in popup view
            popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());

            Menu get_menu = popup.getMenu();

            // popup menu click event
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    int i = item.getItemId();
                    int position = getAdapterPosition();

                    if (i == R.id.edit) {
                        Intent editIntent = new Intent(context, Add_postActivity.class);
                        editIntent.putExtra("post_id", modelList.get(position).getPost_id());
                        editIntent.putExtra("post_title", modelList.get(position).getPost_title());
                        editIntent.putExtra("post_price", modelList.get(position).getPost_price());
                        editIntent.putExtra("post_desc", modelList.get(position).getPost_description());
                        editIntent.putExtra("post_year", modelList.get(position).getPost_year());
                        editIntent.putExtra("post_km", modelList.get(position).getPost_km());
                        editIntent.putExtra("post_owner", modelList.get(position).getPost_hand());
                        editIntent.putExtra("city_id", modelList.get(position).getCity_id());
                        editIntent.putExtra("city_name", modelList.get(position).getCity_name());
                        editIntent.putExtra("currency", modelList.get(position).getCurrency());
                        context.startActivity(editIntent);

                        return true;
                    } else if (i == R.id.delete) {

                        //show alertdialog for delete user post
                        showDeleteDialog(position);

                        return true;
                    } else if (i == R.id.add_gallery) {
                        Intent editIntent = new Intent(context, Add_post_galleryActivity.class);
                        editIntent.putExtra("post_id", modelList.get(position).getPost_id());
                        editIntent.putExtra("isedit", "true");
                        context.startActivity(editIntent);
                        return true;
                    } else if (i == R.id.add_feature) {
                        Intent editIntent = new Intent(context, Add_post_attributeActivity.class);
                        editIntent.putExtra("post_id", modelList.get(position).getPost_id());
                        editIntent.putExtra("is_edit", "edit");
                        context.startActivity(editIntent);
                        return true;
                    } else if (i == R.id.review) {
                        Intent editIntent = new Intent(context, ReviewActivity.class);
                        editIntent.putExtra("post_id", modelList.get(position).getPost_id());
                        context.startActivity(editIntent);
                        return true;
                    } else {
                        return onMenuItemClick(item);
                    }

                }
            });

            String get_status = modelList.get(position).getPost_status();

            if (get_status.equals("1")) {
                get_menu.findItem(R.id.add_gallery).setVisible(false);
                get_menu.findItem(R.id.add_feature).setVisible(false);
                get_menu.findItem(R.id.review).setVisible(false);
            } else if (get_status.equals("2")) {
                get_menu.findItem(R.id.add_feature).setVisible(false);
                get_menu.findItem(R.id.review).setVisible(false);
            }

            popup.show();
        }
    }

    public My_post_adapter(List<Post_model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public My_post_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_my_post, parent, false);

        context = parent.getContext();

        return new My_post_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(My_post_adapter.MyViewHolder holder, int position) {
        Post_model mList = modelList.get(position);

        Picasso.with(context)
                .load(BaseURL.IMG_POST_URL + mList.getImage_path())
                .placeholder(R.drawable.ic_loading_02)
                .into(holder.image);

        holder.title.setText(mList.getPost_title());
        holder.model.setText(mList.getPost_year() + ", " + mList.getPost_km() + " " + context.getResources().getString(R.string.kms));
        holder.location.setText(mList.getCity_name());
        holder.currency.setText(mList.getCurrency());
        holder.price.setText(mList.getPost_km());

        if (mList.getPost_status().equals("1")) {
            holder.tv_status.setVisibility(View.VISIBLE);
        } else if (mList.getPost_status().equals("2")) {
            holder.tv_status.setVisibility(View.VISIBLE);
        } else if (mList.getPost_status().equals("3")) {
            holder.tv_status.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    private void showDeleteDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.delete_post_note))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        String post_id = modelList.get(position).getPost_id();
                        makeDeletePost(post_id, position);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
        //.show();

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });

        dialog.show();
    }

    private void makeDeletePost(String post_id, final int position) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("post_id", post_id));

        CommonAsyTask task_post = new CommonAsyTask(params,
                BaseURL.DELETE_POST_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(context.toString(), response);

                modelList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, modelList.size());

                Toast.makeText(context, "" + response, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void VError(String responce) {
                Log.e(context.toString(), responce);
            }
        }, true, context);
        task_post.execute();
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