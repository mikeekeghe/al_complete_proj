package techline.carsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import adapter.City_adapter;
import io.paperdb.Paper;
import model.City_model;
import util.CommonAsyTask;
import util.ConnectivityReceiver;
import util.LocaleHelper;
import util.NameValuePair;
import util.Session_management;

import static Config.BaseURL.KEY_CITY;
import static Config.BaseURL.KEY_CITY_NAME;
import static Config.BaseURL.KEY_CURRENCY;

public class CityActivity extends AppCompatActivity {

    private static String TAG = CityActivity.class.getSimpleName();

    private City_adapter adapter;

    private EditText et_city;
    private RecyclerView rv_city;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lang_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.language_en) {
            Paper.book().write("language", "en");
            updateView((String) Paper.book().read("language"));
        } else if (id == R.id.language_ar) {
            Paper.book().write("language", "ar");
            updateView((String) Paper.book().read("language"));
        }

        return true;
    }

    private Button btn_add_city;

    private String city_id, city_name, currency;

    private List<City_model> city_modelList = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // title remove
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_city);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        final Session_management sessionManagement = new Session_management(CityActivity.this);

        et_city = (EditText) findViewById(R.id.et_city_select);
        btn_add_city = (Button) findViewById(R.id.btn_city_continue);
        rv_city = (RecyclerView) findViewById(R.id.rv_city);
        rv_city.setLayoutManager(new LinearLayoutManager(this));

        et_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0 && charSequence != "") {
                    adapter.filter(city_modelList, charSequence.toString());
                } else {
                    adapter = new City_adapter(city_modelList);
                    rv_city.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(city_id)) {
                    Toast.makeText(CityActivity.this, getResources().getString(R.string.please_select_city), Toast.LENGTH_SHORT).show();
                } else {

                    if (sessionManagement.isLoggedIn()) {
                        SharedPreferences prefs_profile;
                        SharedPreferences.Editor editor_proffile;
                        prefs_profile = getSharedPreferences("Profile_city", 0);
                        editor_proffile = prefs_profile.edit();
                        editor_proffile.putString(KEY_CITY, city_id);
                        editor_proffile.putString(KEY_CITY_NAME, city_name);
                        editor_proffile.putString(KEY_CURRENCY, currency);
                        editor_proffile.commit();

                        finish();
                    } else {
                        sessionManagement.createLoginSession("", "", "", "", "", "", "", city_id, city_name, "", "", "", currency);

                        Intent loginIntent = new Intent(CityActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }
                }

            }
        });

        if (ConnectivityReceiver.isConnected()) {
            makeGetCity();
        } else {
            ConnectivityReceiver.showSnackbar(this);
        }

    }

    private void makeGetCity() {

        CommonAsyTask task_post = new CommonAsyTask(new ArrayList<NameValuePair>(),
                BaseURL.GET_CITY_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<City_model>>() {
                }.getType();

                city_modelList = gson.fromJson(response, listType);

                adapter = new City_adapter(city_modelList);
                rv_city.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task_post.execute();

    }

    @Override
    public void onPause() {
        super.onPause();
        // unregister reciver
        this.unregisterReceiver(mCity);
    }

    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);
        Resources resources = context.getResources();

        Toast.makeText(this, "Language changed", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume() {
        super.onResume();
        // register reciver
        this.registerReceiver(mCity, new IntentFilter("Carondeal_city"));
    }

    // broadcast reciver for receive data
    private BroadcastReceiver mCity = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String type = intent.getStringExtra("type");

            if (type.contentEquals("update_city")) {
                String get_city = intent.getStringExtra("city_id");
                String get_city_name = intent.getStringExtra("city_name");
                String get_currency = intent.getStringExtra("currency");

                city_id = get_city;
                city_name = get_city_name;
                currency = get_currency;
            }
        }
    };

}
