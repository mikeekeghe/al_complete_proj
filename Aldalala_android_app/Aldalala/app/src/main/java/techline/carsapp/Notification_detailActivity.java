package techline.carsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import Config.BaseURL;
import io.paperdb.Paper;
import util.LocaleHelper;

public class Notification_detailActivity extends CommonAppCompatActivity {

    private TextView tv_date, tv_detail, tv_title;
    private ImageView iv_img;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        Intent args = getIntent();

        String title = args.getStringExtra("title");
        String date = args.getStringExtra("date");
        String message = args.getStringExtra("desc");
        String image = args.getStringExtra("image_path");

        tv_title = (TextView) findViewById(R.id.tv_notific_title);
        tv_detail = (TextView) findViewById(R.id.tv_notific_detail);
        tv_date = (TextView) findViewById(R.id.tv_notific_date);
        iv_img = (ImageView) findViewById(R.id.iv_notific_img);

        tv_title.setText(title);
        tv_date.setText(date);
        tv_detail.setText(message);

        if (image == null) {
            iv_img.setVisibility(View.GONE);
        } else {
            iv_img.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(BaseURL.IMG_POST_URL + image)
                    .placeholder(R.drawable.ic_loading_02)
                    .into(iv_img);
        }

    }
}
