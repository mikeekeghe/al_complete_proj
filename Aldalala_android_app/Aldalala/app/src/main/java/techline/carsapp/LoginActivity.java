package techline.carsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Config.BaseURL;
import io.paperdb.Paper;
import util.CommonAsyTask;
import util.LocaleHelper;
import util.NameValuePair;
import util.Session_management;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = LoginActivity.class.getSimpleName();

    //private TextInputLayout tl_email, tl_password;
    private EditText et_email, et_password;
    private TextView tv_register, tv_forgot, tv_email, tv_password;
    private Button btn_register_later, btn_login;

    private boolean isFinish = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // title remove
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        if (getIntent().getStringExtra("setfinish") != null){
            isFinish = true;
        }

        et_email = (EditText) findViewById(R.id.et_login_username);
        et_password = (EditText) findViewById(R.id.et_login_password);
        //tl_email = (TextInputLayout) findViewById(R.id.ti_login_username);
        //tl_password = (TextInputLayout) findViewById(R.id.ti_login_password);
        tv_email = (TextView) findViewById(R.id.tv_login_email);
        tv_password = (TextView) findViewById(R.id.tv_login_password);
        tv_register = (TextView) findViewById(R.id.tv_login_reg);
        tv_forgot = (TextView) findViewById(R.id.tv_login_forgot);
        btn_register_later = (Button) findViewById(R.id.btn_login_later);
        btn_login = (Button) findViewById(R.id.btn_login);

        tv_forgot.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_register_later.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        Intent i = null;

        switch (id) {
            case R.id.btn_login:
                attemptLogin();
                break;
            case R.id.btn_login_later:
                i = new Intent(LoginActivity.this, MainActivity.class);
                finish();
                break;
            case R.id.tv_login_reg:
                i = new Intent(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.tv_login_forgot:
                i = new Intent(LoginActivity.this, ForgotActivity.class);
                break;
        }

        if (i != null) {
            startActivity(i);
        }

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        //tl_email.setError(null);
        //tl_password.setError(null);
        tv_email.setText(getResources().getString(R.string.email_required));
        tv_password.setText(getResources().getString(R.string.password_required));

        // Store values at the time of the login attempt.
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            //tl_email.setError(getString(R.string.error_field_required));
            focusView = tv_email;
            cancel = true;
        }

/*        else if (!isEmailValid(email)) {
            tv_email.setText(getResources().getString(R.string.error_invalid_email));
            //tl_email.setError(getString(R.string.error_invalid_email));
            focusView = tv_email;
            cancel = true;
        }*/

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            //tl_password.setError(getString(R.string.error_field_required));
            focusView = tv_password;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            tv_password.setText(getResources().getString(R.string.error_invalid_password));
            //tl_password.setError(getString(R.string.error_invalid_password));
            focusView = tv_password;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            makeLogin(email, password);
        }
    }

/*    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }*/

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void makeLogin(String email, String password) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("email", email));
        params.add(new NameValuePair("password", password));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task_login = new CommonAsyTask(params,
                BaseURL.LOGIN_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String user_id = jsonObject.getString("user_id");
                    String user_email = jsonObject.getString("user_email");
                    String user_fullname = jsonObject.getString("user_fullname");
                    String user_password = jsonObject.getString("user_password");
                    String user_type_id = jsonObject.getString("user_type_id");
                    String user_bdate = jsonObject.getString("user_bdate");
                    String user_phone = jsonObject.getString("user_phone");
                    String is_email_varified = jsonObject.getString("is_email_varified");
                    String varified_token = jsonObject.getString("varified_token");
                    String user_gcm_code = jsonObject.getString("user_gcm_code");
                    String user_ios_token = jsonObject.getString("user_ios_token");
                    String user_status = jsonObject.getString("user_status");
                    String user_image = jsonObject.getString("user_image");
                    String user_city = jsonObject.getString("user_city");
                    String user_country = jsonObject.getString("user_country");
                    String user_state = jsonObject.getString("user_state");


                    Session_management sessionManagement = new Session_management(LoginActivity.this);
                    String getcity_id = sessionManagement.getUserDetails().get(BaseURL.KEY_CITY);
                    String getcity_name = sessionManagement.getUserDetails().get(BaseURL.KEY_CITY_NAME);
                    String getcurrency = sessionManagement.getUserDetails().get(BaseURL.KEY_CURRENCY);

                    sessionManagement.createLoginSession(user_id, user_email, user_fullname, user_type_id, user_bdate, user_phone,
                            user_status, getcity_id, getcity_name, user_country, user_state, user_image, getcurrency);

                    if (!isFinish) {
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                    }
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
                Toast.makeText(LoginActivity.this, "" + responce, Toast.LENGTH_SHORT).show();
            }
        }, true, this);
        task_login.execute();

    }

    /*@Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }*/

}
