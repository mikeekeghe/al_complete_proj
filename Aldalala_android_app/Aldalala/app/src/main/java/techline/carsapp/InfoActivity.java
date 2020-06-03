package techline.carsapp;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import io.paperdb.Paper;
import model.Info_model;
import util.CommonAsyTask;
import util.ConnectivityReceiver;
import util.LocaleHelper;
import util.NameValuePair;

public class InfoActivity extends CommonAppCompatActivity {

    private static String TAG = InfoActivity.class.getSimpleName();

    private TextView tv_info;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        tv_info = (TextView) findViewById(R.id.tv_info);

        String gettitle = getIntent().getStringExtra("title");

        getSupportActionBar().setTitle(gettitle);

        if (ConnectivityReceiver.isConnected()) {
            if (gettitle.equals("About Us")) {
                makeGetInfo(BaseURL.ABOUT_US_URL);
            } else {
                makeGetInfo(BaseURL.TERMS_URL);
            }
        }else{
            ConnectivityReceiver.showSnackbar(this);
        }

    }

    private void makeGetInfo(String url) {

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task = new CommonAsyTask(new ArrayList<NameValuePair>(),
                url, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                List<Info_model> info_modelList = new ArrayList<>();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Info_model>>() {
                }.getType();

                info_modelList = gson.fromJson(response, listType);

                tv_info.setText(Html.fromHtml(info_modelList.get(0).getPg_descri()));

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task.execute();

    }

}
