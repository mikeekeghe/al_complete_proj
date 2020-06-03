package techline.carsapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import adapter.Home_deal_adapter;
import io.paperdb.Paper;
import model.City_model;
import model.Post_model;
import util.CommonAsyTask;
import util.ConnectivityReceiver;
import util.LocaleHelper;
import util.NameValuePair;

public class FilterActivity extends CommonAppCompatActivity {

    private static String TAG = FilterActivity.class.getSimpleName();

    private EditText et_search, et_search_by_brand, et_search_by_model, et_search_by_year;
    private Spinner sp_search;
    private Button btn_search, btn_search_by_brand, btn_search_by_model, btn_search_by_year;
    private RecyclerView rv_search;

    private List<Post_model> post_modelList = new ArrayList<>();
    private List<String> city_id = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        et_search = (EditText) findViewById(R.id.et_search);
        sp_search = (Spinner) findViewById(R.id.sp_search);
        btn_search = (Button) findViewById(R.id.btn_search);
        rv_search = (RecyclerView) findViewById(R.id.rv_search);

        et_search_by_brand = (EditText) findViewById(R.id.et_search_by_brand);
        et_search_by_model = (EditText) findViewById(R.id.et_search_by_model);
        et_search_by_year = (EditText) findViewById(R.id.et_search_by_year);

        btn_search_by_brand = (Button) findViewById(R.id.btn_search_by_brand);
        btn_search_by_model = (Button) findViewById(R.id.btn_search_by_model);
        btn_search_by_year = (Button) findViewById(R.id.btn_search_by_year);


        rv_search.setLayoutManager(new LinearLayoutManager(this));

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getSearch = et_search.getText().toString();

                String getcity_id = city_id.get(sp_search.getSelectedItemPosition());
                makeGetPostSearch(getSearch, getcity_id);

            }
        });
        btn_search_by_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getSearch = et_search_by_brand.getText().toString();

                String getcity_id = city_id.get(sp_search.getSelectedItemPosition());
                makeGetPostSearchBrand(getSearch, getcity_id);

            }
        });
        btn_search_by_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getSearch = et_search_by_year.getText().toString();

                String getcity_id = city_id.get(sp_search.getSelectedItemPosition());
                makeGetPostSearchYear(getSearch, getcity_id);

            }
        });
        btn_search_by_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getSearch = et_search_by_model.getText().toString();

                String getcity_id = city_id.get(sp_search.getSelectedItemPosition());
                makeGetPostSearch(getSearch, getcity_id);

            }
        });

        if (ConnectivityReceiver.isConnected()) {
            makeGetCity();
        } else {
            ConnectivityReceiver.showSnackbar(this);
        }
/*

        rv_search.addOnItemTouchListener(new RecyclerTouchListener(this, rv_search, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent post_detail = new Intent(FilterActivity.this, Post_detailActivity.class);
                post_detail.putExtra("post_id", post_modelList.get(position).getPost_id());
                post_detail.putExtra("title", post_modelList.get(position).getPost_title());
                post_detail.putExtra("date", post_modelList.get(position).getPost_date());
                post_detail.putExtra("year", post_modelList.get(position).getPost_year());
                post_detail.putExtra("km", post_modelList.get(position).getPost_km());
                post_detail.putExtra("price", post_modelList.get(position).getPost_price());
                post_detail.putExtra("city_name", post_modelList.get(position).getCity_name());
                post_detail.putExtra("desc", post_modelList.get(position).getPost_description());
                post_detail.putExtra("user_email", post_modelList.get(position).getUser_email());
                post_detail.putExtra("user_name", post_modelList.get(position).getUser_fullname());
                post_detail.putExtra("user_phone", post_modelList.get(position).getUser_phone());
                post_detail.putExtra("user_id", post_modelList.get(position).getUser_id());
                startActivity(post_detail);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
*/

    }

    private void makeGetCity() {

        CommonAsyTask task_post = new CommonAsyTask(new ArrayList<NameValuePair>(),
                BaseURL.GET_CITY_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                List<City_model> city_modelList = new ArrayList<>();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<City_model>>() {
                }.getType();

                city_modelList = gson.fromJson(response, listType);

                List<String> city_name = new ArrayList<>();

                for (int i = 0; i < city_modelList.size(); i++) {
                    city_name.add(city_modelList.get(i).getCity_name());
                    city_id.add(city_modelList.get(i).getCity_id());
                }

                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(FilterActivity.this,
                        R.layout.row_spinner_text, R.id.tv_sp, city_name);
                sp_search.setAdapter(adapter_city);

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task_post.execute();

    }

    private void makeGetPostSearch(String searchtext, String city_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (searchtext != null && searchtext != "") {
            params.add(new NameValuePair("search", searchtext));
        }
        params.add(new NameValuePair("city_id", city_id));

        CommonAsyTask task_post = new CommonAsyTask(params,
                BaseURL.GET_POST_SEARCH_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Post_model>>() {
                }.getType();

                post_modelList = gson.fromJson(response, listType);

                Home_deal_adapter adapter_deal = new Home_deal_adapter(post_modelList);
                rv_search.setAdapter(adapter_deal);
                adapter_deal.notifyDataSetChanged();

                if (post_modelList.isEmpty()) {
                    Toast.makeText(FilterActivity.this, getResources().getString(R.string.record_not_found), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task_post.execute();
    }

    private void makeGetPostSearchBrand(String searchtext, String city_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (searchtext != null && searchtext != "") {
            params.add(new NameValuePair("search", searchtext));
        }
        params.add(new NameValuePair("city_id", city_id));

        CommonAsyTask task_post = new CommonAsyTask(params,
                BaseURL.GET_POST_SEARCH_BRAND_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Post_model>>() {
                }.getType();

                post_modelList = gson.fromJson(response, listType);

                Home_deal_adapter adapter_deal = new Home_deal_adapter(post_modelList);
                rv_search.setAdapter(adapter_deal);
                adapter_deal.notifyDataSetChanged();

                if (post_modelList.isEmpty()) {
                    Toast.makeText(FilterActivity.this, getResources().getString(R.string.record_not_found), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task_post.execute();
    }

    private void makeGetPostSearchYear(String searchtext, String city_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (searchtext != null && searchtext != "") {
            params.add(new NameValuePair("search", searchtext));
        }
        params.add(new NameValuePair("city_id", city_id));

        CommonAsyTask task_post = new CommonAsyTask(params,
                BaseURL.GET_POST_SEARCH_YEAR_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Post_model>>() {
                }.getType();

                post_modelList = gson.fromJson(response, listType);

                Home_deal_adapter adapter_deal = new Home_deal_adapter(post_modelList);
                rv_search.setAdapter(adapter_deal);
                adapter_deal.notifyDataSetChanged();

                if (post_modelList.isEmpty()) {
                    Toast.makeText(FilterActivity.this, getResources().getString(R.string.record_not_found), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task_post.execute();
    }

}
