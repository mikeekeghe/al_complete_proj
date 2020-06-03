package techline.carsapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.util.Attributes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import adapter.Review_adapter;
import io.paperdb.Paper;
import model.Review_model;
import util.CommonAsyTask;
import util.LocaleHelper;
import util.NameValuePair;

/**
 * Created by Rajesh on 2017-08-25.
 */

public class ReviewActivity extends CommonAppCompatActivity {

    private static String TAG = ReviewActivity.class.getSimpleName();

    private RecyclerView rv_review;
    private RelativeLayout rl_review;

    private String post_id;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_review);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        post_id = getIntent().getStringExtra("post_id");

        rl_review = (RelativeLayout) findViewById(R.id.rl_review);
        rv_review = (RecyclerView) findViewById(R.id.rv_reviews);
        rv_review.setLayoutManager(new LinearLayoutManager(this));

        rl_review.setVisibility(View.GONE);

        makeGetReview(post_id);
    }

    private void makeGetReview(String post_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("post_id", post_id));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task = new CommonAsyTask(params,
                BaseURL.GET_REVIEW_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                List<Review_model> review_modelList = new ArrayList<>();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Review_model>>() {
                }.getType();

                review_modelList = gson.fromJson(response, listType);

                Review_adapter adapter = new Review_adapter(review_modelList);
                ((Review_adapter) adapter).setMode(Attributes.Mode.Single);
                rv_review.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if (review_modelList.isEmpty()) {
                    Toast.makeText(ReviewActivity.this, getResources().getString(R.string.record_not_found), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
                Toast.makeText(ReviewActivity.this, "" + responce, Toast.LENGTH_SHORT).show();
            }
        }, true, this);
        task.execute();

    }

}
