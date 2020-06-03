package adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import Config.BaseURL;
import techline.carsapp.R;
import model.Post_image_model;

/**
 * Created by Rajesh on 2017-08-23.
 */

public class Image_pager_adapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    private List<Post_image_model> modelList;
    private boolean is_zoom;

    public Image_pager_adapter(Context context, List<Post_image_model> modelList, boolean is_zoom) {
        this.is_zoom = is_zoom;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = layoutInflater.inflate(R.layout.row_image_pager, container, false);

        final Post_image_model mList = modelList.get(position);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_post_image);

        Picasso.with(context)
                .load(BaseURL.IMG_POST_URL + mList.getImage_path())
                .placeholder(R.drawable.ic_loading_02)
                .into(imageView);

        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if zoom equel true then show image dialog
                if (is_zoom) {
                    showImages(mList.getImage_path());
                }
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
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
