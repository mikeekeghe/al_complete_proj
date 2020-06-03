package techline.carsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import adapter.My_post_adapter;
import io.paperdb.Paper;
import model.Post_model;
import util.CommonAsyTask;
import util.ConnectivityReceiver;
import util.LocaleHelper;
import util.NameValuePair;
import util.Session_management;

public class My_postActivity extends CommonAppCompatActivity implements View.OnClickListener {

    private static String TAG = My_postActivity.class.getSimpleName();

    private FloatingActionButton fab_add_post;
    private FloatingActionMenu fab_menu;
    private RecyclerView rv_my_post;

    private Session_management sessionManagement;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        sessionManagement = new Session_management(this);

        rv_my_post = (RecyclerView) findViewById(R.id.rv_my_post);
        fab_menu = (FloatingActionMenu) findViewById(R.id.menu);
        fab_add_post = (FloatingActionButton) findViewById(R.id.fab_posts_addnew);

        rv_my_post.setLayoutManager(new LinearLayoutManager(this));

        fab_add_post.setOnClickListener(this);

        if (ConnectivityReceiver.isConnected()) {
            makeGetPostByUser();
        } else {
            ConnectivityReceiver.showSnackbar(My_postActivity.this);
        }

        // hide floating action menu when user scroll up
        rv_my_post.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scroll Down
                    if (fab_menu.isShown()) {
                        fab_menu.hideMenu(true);
                    }
                } else if (dy < 0) {
                    // Scroll Up
                    if (!fab_menu.isShown()) {
                        fab_menu.showMenu(true);
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view) {

        if (fab_menu.isOpened()) {
            fab_menu.close(true);
        }

        if (sessionManagement.isLoggedIn()) {
            Intent i = new Intent(My_postActivity.this, Add_postActivity.class);
            startActivity(i);
        } else {
            Intent i = new Intent(My_postActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    private void makeGetPostByUser() {

        String user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("user_id", user_id));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task_post = new CommonAsyTask(params,
                BaseURL.GET_POST_BY_USER_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                List<Post_model> post_modelList = new ArrayList<>();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Post_model>>() {
                }.getType();

                post_modelList = gson.fromJson(response, listType);

                My_post_adapter myPostAdapter = new My_post_adapter(post_modelList);
                rv_my_post.setAdapter(myPostAdapter);
                myPostAdapter.notifyDataSetChanged();

                if (post_modelList.isEmpty()) {
                    Toast.makeText(My_postActivity.this, getResources().getString(R.string.record_not_found), Toast.LENGTH_SHORT).show();
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
        makeGetPostByUser();
    }

}
