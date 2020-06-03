package techline.carsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import Config.BaseURL;
import io.paperdb.Paper;
import util.CommonAsyTask;
import util.JSONParser;
import util.LocaleHelper;
import util.NameValuePair;
import util.Session_management;

import static Config.BaseURL.KEY_CITY;
import static Config.BaseURL.KEY_CITY_NAME;
import static Config.BaseURL.KEY_CURRENCY;

public class Edit_profileActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = Edit_profileActivity.class.getSimpleName();

    private EditText et_phone, et_name, et_email;
    private Button btn_update;
    private TextView tv_phone, tv_name, tv_email, tv_socity, btn_socity;
    private ImageView iv_profile;
    //private Spinner sp_socity;

    private String getsocity = "";
    private String filePath = "";
    private static final int GALLERY_REQUEST_CODE1 = 201;
    private Bitmap bitmap;
    private Uri imageuri;

    private Session_management sessionManagement;

    private SharedPreferences prefs_profile;
    private SharedPreferences.Editor editor_proffile;
    private boolean is_city_edit = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.edit_profile));

        prefs_profile = getSharedPreferences("Profile_city", 0);
        editor_proffile = prefs_profile.edit();

        sessionManagement = new Session_management(this);

        et_phone = (EditText) findViewById(R.id.et_pro_phone);
        et_name = (EditText) findViewById(R.id.et_pro_name);
        tv_phone = (TextView) findViewById(R.id.tv_pro_phone);
        tv_name = (TextView) findViewById(R.id.tv_pro_name);
        tv_email = (TextView) findViewById(R.id.tv_pro_email);
        et_email = (EditText) findViewById(R.id.et_pro_email);
        iv_profile = (ImageView) findViewById(R.id.iv_pro_img);
        tv_socity = (TextView) findViewById(R.id.tv_pro_city);
        btn_update = (Button) findViewById(R.id.btn_pro_edit);
        btn_socity = (TextView) findViewById(R.id.et_pro_city);

        String getemail = sessionManagement.getUserDetails().get(BaseURL.KEY_EMAIL);
        String getimage = sessionManagement.getUserDetails().get(BaseURL.KEY_IMAGE);
        String getname = sessionManagement.getUserDetails().get(BaseURL.KEY_NAME);
        String getphone = sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE);
        getsocity = sessionManagement.getUserDetails().get(BaseURL.KEY_CITY);
        String getsocity_name = sessionManagement.getUserDetails().get(BaseURL.KEY_CITY_NAME);

        et_name.setText(getname);
        et_phone.setText(getphone);
        btn_socity.setText(getsocity_name);

        if (!TextUtils.isEmpty(getimage)) {

            Picasso.with(this)
                    .load(BaseURL.IMG_PROFILE_URL + getimage)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(iv_profile);
        }

        if (!TextUtils.isEmpty(getemail)) {
            et_email.setText(getemail);
        }

        btn_update.setOnClickListener(this);
        btn_socity.setOnClickListener(this);
        iv_profile.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_pro_edit) {
            attemptEditProfile();
        } else if (id == R.id.et_pro_city) {

            Intent cityIntent = new Intent(Edit_profileActivity.this, CityActivity.class);
            startActivity(cityIntent);

        } else if (id == R.id.iv_pro_img) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start the Intent
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE1);
        }
    }

    private void attemptEditProfile() {

        tv_phone.setText(getResources().getString(R.string.mobile_required));
        tv_email.setText(getResources().getString(R.string.email_required));
        tv_name.setText(getResources().getString(R.string.name_required));
        tv_socity.setText(getResources().getString(R.string.choose_location));

        String getphone = et_phone.getText().toString();
        String getname = et_name.getText().toString();
        String getemail = et_email.getText().toString();
        String getcity = "";
        String getcity_name = "";
        String getcurrency = "";
        if(is_city_edit){
            getcity = prefs_profile.getString(KEY_CITY, null);
            getcity_name = prefs_profile.getString(KEY_CITY_NAME, null);
            getcurrency = prefs_profile.getString(KEY_CURRENCY, null);
        }else{
            getcity = sessionManagement.getUserDetails().get(BaseURL.KEY_CITY);
            getcity_name = sessionManagement.getUserDetails().get(BaseURL.KEY_CITY_NAME);
            getcurrency = sessionManagement.getUserDetails().get(BaseURL.KEY_CURRENCY);
        }

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(getphone)) {
            focusView = et_phone;
            cancel = true;
        } else if (!isPhoneValid(getphone)) {
            tv_phone.setText(getResources().getString(R.string.phone_to_short));
            focusView = et_phone;
            cancel = true;
        }

        if (TextUtils.isEmpty(getname)) {
            focusView = et_name;
            cancel = true;
        }

        if (TextUtils.isEmpty(getemail)) {
            focusView = et_email;
            cancel = true;
        }

        if (TextUtils.isEmpty(getcity) && getcity == null) {
            focusView = btn_socity;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null)
                focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            String user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

            makeUpdateProfile(user_id, getname, getcity, getphone,getcity_name,getcurrency);

        }
    }

    private boolean isPhoneValid(String phoneno) {
        //TODO: Replace this with your own logic
        return phoneno.length() > 9;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        // if the result is capturing Image
        if ((requestCode == GALLERY_REQUEST_CODE1)) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();

                    //filePath = imgDecodableString;

                    Bitmap b = BitmapFactory.decodeFile(imgDecodableString);
                    Bitmap out = Bitmap.createScaledBitmap(b, 1200, 1024, false);

                    //getting image from gallery
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);


                    File file = new File(imgDecodableString);
                    filePath = file.getAbsolutePath();
                    FileOutputStream fOut;
                    try {
                        fOut = new FileOutputStream(file);
                        out.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                        fOut.flush();
                        fOut.close();
                        //b.recycle();
                        //out.recycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (requestCode == GALLERY_REQUEST_CODE1) {

                        // Set the Image in ImageView after decoding the String
                        iv_profile.setImageBitmap(bitmap);

                        String user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);
                        new updateProfile(user_id, file.getAbsolutePath()).execute();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void makeUpdateProfile(String user_id, String user_fullname, String user_city, String user_phone, final String city_name,final String currency) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("user_id", user_id));
        params.add(new NameValuePair("user_fullname", user_fullname));
        params.add(new NameValuePair("user_city", user_city));
        params.add(new NameValuePair("user_phone", user_phone));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task = new CommonAsyTask(params,
                BaseURL.UPDATE_PROFILE_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Toast.makeText(Edit_profileActivity.this, getResources().getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String user_id = jsonObject.getString("user_id");
                    String user_email = jsonObject.getString("user_email");
                    String getname = jsonObject.getString("user_fullname");
                    String getphone = jsonObject.getString("user_phone");
                    String getimage = jsonObject.getString("user_image");
                    String getcity = jsonObject.getString("user_city");

                    sessionManagement.updateData(getname, getphone, getcity, getimage);
                    sessionManagement.updateCity(getcity,city_name,currency);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
                Toast.makeText(Edit_profileActivity.this, "" + responce, Toast.LENGTH_SHORT).show();
            }
        }, true, this);
        task.execute();

    }

    // asynctask for upload data with image or not image using HttpOk
    private class updateProfile extends AsyncTask<String, Integer, Void> {

        JSONParser jsonParser;
        ArrayList<NameValuePair> nameValuePairs;
        boolean response;
        String error_string, success_msg;
        String filePath = "";

        private updateProfile(String user_id, String filepath) {

            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new NameValuePair("user_id", user_id));

            this.filePath = filepath;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            jsonParser = new JSONParser(Edit_profileActivity.this);
        }

        protected Void doInBackground(String... urls) {

            String json_responce = null;
            try {
                json_responce = jsonParser.execMultiPartPostScriptJSON(BaseURL.UPDATE_PROFILE_PIC_URL,
                        nameValuePairs, "image/png", filePath, "image");
                Log.e(TAG, json_responce + "," + filePath);

                JSONObject jObj = new JSONObject(json_responce);
                if (jObj.getBoolean("responce")) {
                    response = true;
                    success_msg = jObj.getString("data");
                } else {
                    response = false;
                    error_string = jObj.getString("error");
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            if (response) {
                sessionManagement.updateImage(success_msg);
                Toast.makeText(Edit_profileActivity.this, getResources().getString(R.string.profile_pic_updated), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Edit_profileActivity.this, "" + error_string, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem shareItem = menu.findItem(R.id.action_change_password);
        shareItem.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                this.finish();
                return true;
            case R.id.action_change_password:
                Intent changeIntent = new Intent(this, Change_passwordActivity.class);
                startActivity(changeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String prof_city_id = prefs_profile.getString(KEY_CITY, null);
        String prof_city_name = prefs_profile.getString(KEY_CITY_NAME, null);

        if (!TextUtils.isEmpty(prof_city_name)) {
            is_city_edit = true;
            btn_socity.setText(prof_city_name);
        }else{
            is_city_edit = false;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        editor_proffile.clear();
        editor_proffile.commit();
    }
}
