package techline.carsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import adapter.Home_deal_all_adapter;
import io.paperdb.Paper;
import model.Post_model;
import util.CommonAsyTask;
import util.LocaleHelper;
import util.NameValuePair;
import util.RecyclerTouchListener;

public class All_postActivity extends CommonAppCompatActivity {

    private static String TAG = All_postActivity.class.getSimpleName();

    private List<Post_model> post_modelAllList = new ArrayList<>();

    private RecyclerView rv_post_all;

    private int current_page = 0;
    private boolean loadingMore;
    private boolean stopLoadingData;
    private boolean is_first_time = true;

    private Home_deal_all_adapter adapter_all;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        rv_post_all = (RecyclerView) findViewById(R.id.rv_all);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_post_all.setLayoutManager(linearLayoutManager);

        rv_post_all.addOnItemTouchListener(new RecyclerTouchListener(this, rv_post_all, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent post_detail = new Intent(All_postActivity.this, Post_detailActivity.class);

                post_detail.putExtra("post_id", post_modelAllList.get(position).getPost_id());
                post_detail.putExtra("title", post_modelAllList.get(position).getPost_title());
                post_detail.putExtra("date", post_modelAllList.get(position).getPost_date());
                post_detail.putExtra("year", post_modelAllList.get(position).getPost_year());
                post_detail.putExtra("km", post_modelAllList.get(position).getPost_km());
                post_detail.putExtra("price", post_modelAllList.get(position).getPost_price());
                post_detail.putExtra("city_name", post_modelAllList.get(position).getCity_name());
                post_detail.putExtra("desc", post_modelAllList.get(position).getPost_description());
                post_detail.putExtra("user_email", post_modelAllList.get(position).getUser_email());
                post_detail.putExtra("user_name", post_modelAllList.get(position).getUser_fullname());
                post_detail.putExtra("user_phone", post_modelAllList.get(position).getUser_phone());
                post_detail.putExtra("user_id", post_modelAllList.get(position).getUser_id());
                post_detail.putExtra("currency", post_modelAllList.get(position).getCurrency());

                startActivity(post_detail);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        adapter_all = new Home_deal_all_adapter(post_modelAllList);
        rv_post_all.setAdapter(adapter_all);

        makeGetAllPost(current_page);

        rv_post_all.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) //check for scroll down
                {
                    int pastVisiblesItems, visibleItemCount, totalItemCount;
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!(loadingMore)) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            if (stopLoadingData == false) {
                                // FETCH THE NEXT BATCH OF FEEDS
                                is_first_time = false;
                                makeGetAllPost(current_page);
                            }
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    private void makeGetAllPost(int count) {

        loadingMore = true;
        stopLoadingData = true;

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("page", "" + count));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task = new CommonAsyTask(params,
                BaseURL.GET_POST, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Post_model>>() {
                }.getType();

                ArrayList<Post_model> arraylist = (ArrayList<Post_model>) gson.fromJson(response, listType);

                if (is_first_time) {
                    post_modelAllList.clear();
                } else {
                    //remove progress item
                    post_modelAllList.remove(post_modelAllList.size() - 1);
                    adapter_all.notifyItemRemoved(post_modelAllList.size());
                }

                post_modelAllList.addAll(arraylist);

                adapter_all.notifyDataSetChanged();

                //add progress item
                post_modelAllList.add(null);
                adapter_all.notifyItemInserted(post_modelAllList.size());

                if (arraylist.isEmpty()) {
                    //remove progress item
                    post_modelAllList.remove(post_modelAllList.size() - 1);
                    adapter_all.notifyItemRemoved(post_modelAllList.size());

                    stopLoadingData = true;
                    loadingMore = true;
                } else {
                    stopLoadingData = false;
                    loadingMore = false;
                    current_page++;
                }

                if (post_modelAllList.isEmpty()) {
                    Toast.makeText(All_postActivity.this, getResources().getString(R.string.record_not_found), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, false, this);
        task.execute();
    }

}
