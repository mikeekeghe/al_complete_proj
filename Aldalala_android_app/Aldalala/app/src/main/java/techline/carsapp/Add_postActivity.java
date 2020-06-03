package techline.carsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Config.BaseURL;
import model.Category_model;
import model.City_model;
import util.CommonAsyTask;
import util.LocaleHelper;
import util.NameValuePair;
import util.Session_management;

public class Add_postActivity extends CommonAppCompatActivity implements View.OnClickListener {

    private static String TAG = "add_post";

    private List<String> cat_list = new ArrayList<>();
    private List<String> cat_id_list = new ArrayList<>();
    private List<String> owner_list = new ArrayList<>();
    private List<String> city_id_list = new ArrayList<>();
    private ArrayList<String> years = new ArrayList<String>();

    private TextView tv_currency, tv_title, rent_details;
    private TextInputLayout ti_title, ti_price, ti_about, ti_km, ti_price2;
    private EditText et_price, et_about, et_km;
    private Button btn_add_post;
    private Spinner sp_mfg, sp_owner, sp_category, sp_city, et_title;

    private Session_management sessionManagement;

    private boolean is_edit = false;
    private String post_id = "";
    private String city_name = "";
    private ArrayAdapter<String> adapter2;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        androidx.appcompat.app.ActionBar mActionBar = getSupportActionBar();

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.header_image, null);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        setContentView(R.layout.activity_add_post);

        sessionManagement = new Session_management(this);

        tv_title = (TextView) findViewById(R.id.tv_add_post_title);
        tv_currency = (TextView) findViewById(R.id.tv_add_post_currency);
        rent_details = (TextView) findViewById(R.id.rent_details);
        //ti_title = (TextInputLayout) findViewById(R.id.et_add_post_title);
        ti_price = (TextInputLayout) findViewById(R.id.ti_add_post_price);
        ti_price2 = (TextInputLayout) findViewById(R.id.ti_add_post_price_2);
        ti_about = (TextInputLayout) findViewById(R.id.ti_add_post_about);
        ti_km = (TextInputLayout) findViewById(R.id.ti_add_post_km);
        et_price = (EditText) findViewById(R.id.et_add_post_price);
        et_about = (EditText) findViewById(R.id.et_add_post_about);
        et_km = (EditText) findViewById(R.id.et_add_post_km);
        //sp_category = (Spinner) findViewById(R.id.sp_add_post_category);
        sp_mfg = (Spinner) findViewById(R.id.sp_add_post_year);
        sp_owner = (Spinner) findViewById(R.id.sp_add_post_ownertype);
        sp_city = (Spinner) findViewById(R.id.sp_add_post_city);
        btn_add_post = (Button) findViewById(R.id.btn_add_post_submit);

/*        owner_list.add("1st Hand");
        owner_list.add("2nd Hand");
        owner_list.add("3rd Hand");
        owner_list.add("4th Hand");
        owner_list.add("5th Hand");
        owner_list.add("6th Hand");*/

        owner_list.add("مباشرة");
        owner_list.add("اليد الثانية");
        owner_list.add("اليد الثالثة");
        owner_list.add("اليد الرابعة\n");
        owner_list.add("اليد الخامسة");
        owner_list.add("اليد السادسة\n");

        // bind static owner list
        ArrayAdapter<String> adapter_owner = new ArrayAdapter<String>(this, R.layout.row_spinner_text, R.id.tv_sp, owner_list);
        sp_owner.setAdapter(adapter_owner);

        // get current year and bind list with years from 1995 to current
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1995; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        // add year data in adapter
        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, R.layout.row_spinner_text, R.id.tv_sp, years);
        sp_mfg.setAdapter(adapter_year);

        btn_add_post.setOnClickListener(this);

        //makeGetCategory();
        makeGetCity();

        Bundle args = getIntent().getExtras();

        tv_title.setText(getResources().getString(R.string.add_sale));

        if (args != null) {

            tv_title.setText(getResources().getString(R.string.edit_sale));

            is_edit = true;
            post_id = args.getString("post_id");
            String post_title = args.getString("post_title");
            String post_desc = args.getString("post_desc");
            String post_year = args.getString("post_year");
            String post_km = args.getString("post_km");
            String post_owner = args.getString("post_owner");
            String post_price = args.getString("post_price");
            String city_id = args.getString("city_id");
            city_name = args.getString("city_name");
            String get_currency = args.getString("currency");

//            et_title.setText(post_title);


            et_price.setText(post_price);
            et_about.setText(post_desc);
            et_km.setText(post_km);
            sp_mfg.setSelection(years.indexOf(post_year));
            sp_owner.setSelection(owner_list.indexOf(post_owner));
            tv_currency.setText(get_currency);

        } else {
            String get_currency = sessionManagement.getUserDetails().get(BaseURL.KEY_CURRENCY);
            tv_currency.setText(get_currency);
        }
        loadSellRentMenu();

        et_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sp1 = String.valueOf(et_title.getSelectedItem());
                if ((sp1.contentEquals("Rent")) || (sp1.contains("تأجير"))) {
                    rent_details.setVisibility(View.VISIBLE);
                } else {
                    rent_details.setVisibility(View.GONE);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void loadSellRentMenu() {
        et_title = (Spinner) findViewById(R.id.et_add_post_title);
        List<String> sellrentList = new ArrayList<>();
        sellrentList.add(getString(R.string.select_one));
        sellrentList.add(getString(R.string.sell));
        sellrentList.add(getString(R.string.rent));

        adapter2 = new ArrayAdapter<String>(this, R.layout.row_spinner_text, R.id.tv_sp, sellrentList);
        et_title.setAdapter(adapter2);

    }

    @Override
    public void onClick(View view) {
        /*Intent loginIntent = new Intent(Add_postActivity.this, Add_post_attributeActivity.class);
        startActivity(loginIntent);*/

        attemptAddPost();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptAddPost() {
        Log.d(TAG, "attemptAddPost: inside");
        // Reset errors.
//        ti_title.setError(null);
        ti_price.setError(null);
        ti_price2.setError(null);
        ti_about.setError(null);
        ti_km.setError(null);

        // Store values at the time of the login attempt.
//        String title = et_title.getText().toString();
        String title = String.valueOf(et_title.getSelectedItem());
        Log.d(TAG, "attemptAddPost: title " + title);
        if (title.trim().equalsIgnoreCase("--SELECT ONE--")) {
            Toast.makeText(getApplicationContext(), "Select A Category.", Toast.LENGTH_SHORT).show();
            return;
        }

        String price = et_price.getText().toString();
        String about = et_about.getText().toString();
        String km = et_km.getText().toString();
        //String cat_id = cat_id_list.get(sp_category.getSelectedItemPosition()).toString();
        String mfg_year = years.get(sp_mfg.getSelectedItemPosition()).toString();
        String owner = owner_list.get(sp_owner.getSelectedItemPosition());
        String getcity_id = city_id_list.get(sp_city.getSelectedItemPosition());

        boolean cancel = false;
        View focusView = null;

       /* if (TextUtils.isEmpty(title)) {
            ti_title.setError(getResources().getString(R.string.error_field_required));

            focusView = ti_title;
            cancel = true;
        }*/

        if (TextUtils.isEmpty(price)) {
            ti_price.setError(getResources().getString(R.string.error_field_required));
            ti_price2.setError("");
            focusView = ti_price;
            cancel = true;
        }

        if (TextUtils.isEmpty(about)) {
            ti_about.setError(getResources().getString(R.string.error_field_required));
            focusView = ti_about;
            cancel = true;
        }

        if (TextUtils.isEmpty(km)) {
            ti_km.setError(getResources().getString(R.string.error_field_required));
            focusView = ti_km;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            Session_management sessionManagement = new Session_management(Add_postActivity.this);

            String getuser_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

            if (sessionManagement.isLoggedIn()) {
                if (is_edit) {
                    makeAddPost(getuser_id, title, price, about, "", mfg_year, km, owner, post_id, getcity_id, true);
                } else {
                    makeAddPost(getuser_id, title, price, about, "", mfg_year, km, owner, "", getcity_id, false);
                }
            } else {
                Intent loginIntent = new Intent(Add_postActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }

        }
    }

    private void makeAddPost(String user_id, String post_title, String post_price,
                             String post_description, String category_id, String post_year,
                             String post_km, String post_hand, String post_id, String post_city, final boolean is_edit) {

        String url = "";

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        if (is_edit) {
            url = BaseURL.EDIT_POST_URL;
            params.add(new NameValuePair("post_id", post_id));
        } else {
            url = BaseURL.ADD_POST_URL;
            params.add(new NameValuePair("user_id", user_id));
        }

        params.add(new NameValuePair("post_title", post_title));
        params.add(new NameValuePair("post_price", post_price));
        params.add(new NameValuePair("post_description", post_description));
        //params.add(new NameValuePair("category_id", category_id));
        params.add(new NameValuePair("post_year", post_year));
        params.add(new NameValuePair("post_km", post_km));
        params.add(new NameValuePair("post_hand", post_hand));
        params.add(new NameValuePair("city_id", post_city));
        Log.e(TAG, post_city);
        CommonAsyTask task_login = new CommonAsyTask(params,
                url, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                if (is_edit) {
                    Toast.makeText(Add_postActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Intent galleryIntent = new Intent(Add_postActivity.this, Add_post_galleryActivity.class);
                    galleryIntent.putExtra("post_id", response);
                    startActivity(galleryIntent);
                    finish();
                }
            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
                Toast.makeText(Add_postActivity.this, "" + responce, Toast.LENGTH_SHORT).show();
            }
        }, true, this);
        task_login.execute();

    }

    private void makeGetCategory() {

        CommonAsyTask task = new CommonAsyTask(new ArrayList<NameValuePair>(),
                BaseURL.GET_CATEGORIES, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                List<Category_model> category_modelList = new ArrayList<>();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Category_model>>() {
                }.getType();

                category_modelList = gson.fromJson(response, listType);

                for (int i = 0; i < category_modelList.size(); i++) {
                    cat_list.add(category_modelList.get(i).getTitle());
                    cat_id_list.add(category_modelList.get(i).getId());
                }

                ArrayAdapter<String> adapter_category = new ArrayAdapter<String>(Add_postActivity.this, R.layout.row_spinner_text, R.id.tv_sp, cat_list);

                sp_category.setAdapter(adapter_category);

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task.execute();

    }

    private void makeGetCity() {

        // CommonAsyTask class for load data from api and manage response and api
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

                List<String> city_name_list = new ArrayList<>();

                for (int i = 0; i < city_modelList.size(); i++) {
                    city_name_list.add(city_modelList.get(i).getCity_name());
                    city_id_list.add(city_modelList.get(i).getCity_id());
                }

                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(Add_postActivity.this, R.layout.row_spinner_text, R.id.tv_sp, city_name_list);
                sp_city.setAdapter(adapter_city);

                if (!city_name.isEmpty()) {
                    sp_city.setSelection(city_name_list.indexOf(city_name));
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
