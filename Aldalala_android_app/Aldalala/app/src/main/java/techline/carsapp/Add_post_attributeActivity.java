package techline.carsapp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Config.BaseURL;
import io.paperdb.Paper;
import model.Attribute_model;
import model.Attribute_sub_model;
import util.CommonAsyTask;
import util.DatabaseHandler;
import util.LocaleHelper;
import util.MultiSpinner;
import util.NameValuePair;

/**
 * Created by Rajesh on 2017-07-29.
 */

public class Add_post_attributeActivity extends AppCompatActivity {

    private static String TAG = Add_post_attributeActivity.class.getSimpleName();

    List<Attribute_sub_model> edit_attribute_modelList = new ArrayList<>();

    private List<String> commen_list = new ArrayList<>();

    private Spinner sp_color, sp_fuel, sp_gear;
    private Button btn_finish;

    private Boolean exit = false;
    private String get_post_id;
    private boolean is_edit = false;

    JSONArray passArray;

    private DatabaseHandler db;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // title remove
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        passArray = new JSONArray();

        setContentView(R.layout.activity_add_post_attribute);

        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }

        db = new DatabaseHandler(Add_post_attributeActivity.this);
        db.clearAttr();

        get_post_id = getIntent().getStringExtra("post_id");
        String edit = getIntent().getStringExtra("is_edit");

        /*if (edit != null && edit.equals("edit")) {
            is_edit = true;

            makeGetAttributeByPost(get_post_id);
        }*/

        btn_finish = (Button) findViewById(R.id.btn_finish);

        makeGetAttributeByPost(get_post_id);
        makeGetAttribute();

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get data from local database
                ArrayList<HashMap<String, String>> items = db.getAllAttr();

                if (items.size() > 0) {
                    final JSONArray passArray = new JSONArray();
                    for (int i = 0; i < items.size(); i++) {
                        HashMap<String, String> map = items.get(i);

                        JSONObject jObjP = new JSONObject();

                        try {

                            jObjP.put("attr_group_id", map.get("attr_group_id"));
                            jObjP.put("attr_id", map.get("attr_id"));

                            passArray.put(jObjP);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    Log.e(TAG, db.getAllAttr().toString());

                    makeAddAttribute(get_post_id, passArray);
                }

            }
        });

    }

    private void makeGetAttribute() {

        CommonAsyTask task = new CommonAsyTask(new ArrayList<NameValuePair>(),
                BaseURL.GET_ATTRIBUTE_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                List<Attribute_model> attribute_modelList = new ArrayList<>();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Attribute_model>>() {
                }.getType();

                attribute_modelList = gson.fromJson(response, listType);

                final LinearLayout lm = (LinearLayout) findViewById(R.id.ll_feature);

                // create the layout params that will be used to define how your
                // button will be displayed
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for (int i = 0; i < attribute_modelList.size(); i++) {

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 5, 0, 0);
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

                    // fill in any details dynamically here
                    LinearLayout linearLayout1 = new LinearLayout(Add_post_attributeActivity.this);
                    linearLayout1.setLayoutParams(layoutParams);
                    linearLayout1.setGravity(Gravity.CENTER);
                    linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

                    TextView textView = new TextView(Add_post_attributeActivity.this);
                    textView.setLayoutParams(layoutParams1);
                    textView.setTextColor(getResources().getColor(R.color.white));
                    textView.setText(attribute_modelList.get(i).getAttr_group_name());

                    linearLayout1.addView(textView);

                    final List<String> list_name = new ArrayList<>();
                    final List<String> list_id = new ArrayList<>();
                    final List<String> list_group_id = new ArrayList<>();

                    for (int color = 0; color < attribute_modelList.get(i).getAttribute_sub_models().size(); color++) {
                        list_name.add(attribute_modelList.get(i).getAttribute_sub_models().get(color).getAttr_rolle());
                        list_id.add(attribute_modelList.get(i).getAttribute_sub_models().get(color).getAttr_id());
                        list_group_id.add(attribute_modelList.get(i).getAttribute_sub_models().get(color).getAttr_group_id());
                    }

                    if (attribute_modelList.get(i).getAttr_group_choosen().equals("multiple")) {

                        // multispinner for showing multiple value in spinner and select multiple value
                        MultiSpinner spinner = new MultiSpinner(Add_post_attributeActivity.this);
                        spinner.setLayoutParams(layoutParams1);
                        // check current install app device version greater then 21 API lavel
                        if (Build.VERSION.SDK_INT > 21) {
                            spinner.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                        }

                        spinner.setItems(list_name, "Select " + attribute_modelList.get(i).getAttr_group_name(), new MultiSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(boolean[] selected) {

                                for (int i = 0; i < selected.length; i++) {

                                    if (selected[i]) {
                                        Log.e(TAG, list_id.get(i));

                                        HashMap<String, String> map = new HashMap<String, String>();
                                        map.put("attr_group_id", list_group_id.get(i));
                                        map.put("attr_id", list_id.get(i));
                                        map.put("attr_group_choosen", "multiple");

                                        if (!db.isInAttr_id(list_id.get(i))) {
                                            db.setAttrributeMulti(map);
                                        }
                                    } else {

                                        db.removeItemFromAttr(list_id.get(i));
                                        Log.e(TAG, "not" + list_id.get(i));
                                    }

                                }
                            }
                        });

                        List<String> selected_list = new ArrayList<>();

                        String group_id = attribute_modelList.get(i).getAttr_group_id();

                        for (int e = 0; e < edit_attribute_modelList.size(); e++) {

                            if (group_id.equals(edit_attribute_modelList.get(e).getAttr_group_id())) {

                                selected_list.add(edit_attribute_modelList.get(e).getAttr_id());

                                Log.e("Multi", "" + edit_attribute_modelList.get(e).getAttr_id());
                            }
                        }

                        spinner.setMultiSelection(list_id,selected_list);

                        linearLayout1.addView(spinner);
                    } else {

                        // create new spinner
                        Spinner spinner = new Spinner(Add_post_attributeActivity.this);
                        spinner.setLayoutParams(layoutParams1);
                        // check current install app device version greater then 21 API lavel
                        if (Build.VERSION.SDK_INT > 21) {
                            spinner.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add_post_attributeActivity.this, R.layout.row_spinner_text, R.id.tv_sp, list_name);

                        spinner.setAdapter(adapter);

                        String group_id = attribute_modelList.get(i).getAttr_group_id();

                        for (int e = 0; e < edit_attribute_modelList.size(); e++) {

                            if (group_id.equals(edit_attribute_modelList.get(e).getAttr_group_id())) {

                                int id = list_id.indexOf(edit_attribute_modelList.get(e).getAttr_id().toString());

                                Log.e("Select", "" + id);

                                spinner.setSelection(id);
                            }

                        }

                        linearLayout1.addView(spinner);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                commen_list.remove(list_group_id.get(i));

                                commen_list.add(list_group_id.get(i));

                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("attr_group_id", list_group_id.get(i));
                                map.put("attr_id", list_id.get(i));
                                map.put("attr_group_choosen", "single");

                                db.setAttrribute(map);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }

                    // add view in layout dynamically
                    lm.addView(linearLayout1);

                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task.execute();

    }

    private void makeAddAttribute(String post_id, JSONArray passArray) {

        String url = "";

        if (is_edit){
            url = BaseURL.EDIT_POST_ATTRIBUTE_URL;
        }else{
            url = BaseURL.ADD_POST_ATTRIBUTE_URL;
        }

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("post_id", post_id));
        params.add(new NameValuePair("data", passArray.toString()));

        Log.e(TAG, passArray.toString());

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task = new CommonAsyTask(params,
                url, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Toast.makeText(Add_post_attributeActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task.execute();

    }

    private void makeGetAttributeByPost(String post_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("post_id", post_id));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task = new CommonAsyTask(params,
                BaseURL.GET_POST_ATTRIBUTE_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Attribute_sub_model>>() {
                }.getType();

                edit_attribute_modelList = gson.fromJson(response, listType);

                if (!edit_attribute_modelList.isEmpty()){
                    is_edit = true;
                }
            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task.execute();

    }

    @Override
    public void onBackPressed() {
        if (exit) {
            this.finish();
        } else {
            Toast.makeText(this, getResources().getString(R.string.press_back_feature),
                    Toast.LENGTH_SHORT).show();
            exit = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }

}
