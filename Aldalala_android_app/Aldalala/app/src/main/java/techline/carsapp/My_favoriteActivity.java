package techline.carsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import model.Post_model;
import util.CommonAsyTask;
import util.ConnectivityReceiver;
import util.DatabaseHandler;
import util.LocaleHelper;
import util.NameValuePair;

public class My_favoriteActivity extends CommonAppCompatActivity {

    private static String TAG = My_favoriteActivity.class.getSimpleName();

    private List<Post_model> post_modelList = new ArrayList<>();

    private RecyclerView rv_favorite;
    private TextView tv_clear;

    private Home_deal_adapter adapter_deal;

    private DatabaseHandler db;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        db = new DatabaseHandler(this);

        tv_clear = (TextView) findViewById(R.id.tv_favorite_clear);
        rv_favorite = (RecyclerView) findViewById(R.id.rv_favorite);
        final LinearLayoutManager linearLayoutManager_deal = new LinearLayoutManager(this);
        rv_favorite.setLayoutManager(linearLayoutManager_deal);

        if (ConnectivityReceiver.isConnected()) {
            makeGetFavorite();
        }else{
            ConnectivityReceiver.showSnackbar(My_favoriteActivity.this);
        }

        /*rv_favorite.addOnItemTouchListener(new RecyclerTouchListener(this, rv_favorite, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent post_detail = new Intent(My_favoriteActivity.this, Post_detailActivity.class);
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
                post_detail.putExtra("currency", post_modelList.get(position).getCurrency());
                startActivity(post_detail);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));*/

        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (post_modelList.size() > 0) {
                    showDeleteDialog();
                }
            }
        });

    }

    private void makeGetFavorite() {

        String post_id = db.getFavConcatString();

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("post_id", post_id));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task_post = new CommonAsyTask(params,
                BaseURL.GET_BOOKMARK_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Post_model>>() {
                }.getType();

                post_modelList = gson.fromJson(response, listType);

                adapter_deal = new Home_deal_adapter(post_modelList);
                rv_favorite.setAdapter(adapter_deal);
                adapter_deal.notifyDataSetChanged();

                if (post_modelList.isEmpty()) {
                    Toast.makeText(My_favoriteActivity.this, getResources().getString(R.string.record_not_found_location), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task_post.execute();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        makeGetFavorite();
    }

    private void showDeleteDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(My_favoriteActivity.this);
        builder.setTitle(getResources().getString(R.string.clear_favorite_note))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        // clear all data in database
                        db.clearFavorite();
                        post_modelList.clear();
                        adapter_deal.notifyDataSetChanged();
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

}
