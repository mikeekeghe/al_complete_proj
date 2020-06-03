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

import java.util.ArrayList;

import Config.BaseURL;
import io.paperdb.Paper;
import util.CommonAsyTask;
import util.LocaleHelper;
import util.NameValuePair;
import util.Session_management;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = RegisterActivity.class.getSimpleName();

    private TextView tv_email, tv_password, tv_name, tv_phone, tv_repassword;
    private EditText et_email, et_password, et_repassword, et_name, et_phone;
    private Button btn_register;

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

        setContentView(R.layout.activity_register);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        tv_name = (TextView) findViewById(R.id.tv_reg_username);
        tv_email = (TextView) findViewById(R.id.tv_reg_email);
        tv_phone = (TextView) findViewById(R.id.tv_reg_phone);
        tv_password = (TextView) findViewById(R.id.tv_reg_password);
        tv_repassword = (TextView) findViewById(R.id.tv_reg_repassword);
        et_name = (EditText) findViewById(R.id.et_register_username);
        et_email = (EditText) findViewById(R.id.et_register_email);
        et_phone = (EditText) findViewById(R.id.et_register_phone);
        et_password = (EditText) findViewById(R.id.et_register_password);
        et_repassword = (EditText) findViewById(R.id.et_register_password_re);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        attemptRegister();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {

        // Reset errors.
        /*tl_name.setError(null);
        tl_email.setError(null);
        tl_phone.setError(null);
        tl_password.setError(null);
        tl_repassword.setError(null);*/
        tv_name.setText(getResources().getString(R.string.name_required));
        tv_email.setText(getResources().getString(R.string.email_required));
        tv_phone.setText(getResources().getString(R.string.mobile_required));
        tv_password.setText(getResources().getString(R.string.password_required));
        tv_repassword.setText(getResources().getString(R.string.repassword_required));


        // Store values at the time of the login attempt.
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String phone = et_phone.getText().toString();
        String password = et_password.getText().toString();
        String repassword = et_repassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            //tl_name.setError(getResources().getString(R.string.error_field_required));
            focusView = tv_name;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            //tl_email.setError(getResources().getString(R.string.error_field_required));
            focusView = tv_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            tv_email.setText(getResources().getString(R.string.error_invalid_email));
            //tl_email.setError(getResources().getString(R.string.error_invalid_email));
            focusView = tv_email;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)) {
            //tl_phone.setError(getResources().getString(R.string.error_field_required));
            focusView = tv_phone;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            tv_phone.setText(getResources().getString(R.string.phone_to_short));
            //tl_phone.setError(getResources().getString(R.string.phone_to_short));
            focusView = tv_phone;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            //tl_password.setError(getResources().getString(R.string.error_field_required));
            focusView = tv_password;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            tv_password.setText(getResources().getString(R.string.error_invalid_password));
            //tl_password.setError(getResources().getString(R.string.error_invalid_password));
            focusView = tv_password;
            cancel = true;
        }

        if (TextUtils.isEmpty(repassword)) {
            //tl_repassword.setError(getResources().getString(R.string.error_field_required));
            focusView = tv_repassword;
            cancel = true;
        } else if (!repassword.equals(password)) {
            tv_repassword.setText(getResources().getString(R.string.password_not_match));
            //tl_repassword.setError(getResources().getString(R.string.password_not_match));
            focusView = tv_repassword;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            Session_management sessionManagement = new Session_management(RegisterActivity.this);

            String getcity_id = sessionManagement.getUserDetails().get(BaseURL.KEY_CITY);

            makeRegister(name, email, phone, password, repassword, getcity_id);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPhoneValid(String phoneno) {
        //TODO: Replace this with your own logic
        return phoneno.length() > 9;
    }

    private void makeRegister(String name, String email, String phone, String password, String repassword, String city) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("full_name", name));
        params.add(new NameValuePair("email", email));
        params.add(new NameValuePair("phone", phone));
        params.add(new NameValuePair("password", password));
        params.add(new NameValuePair("cpassword", repassword));
        params.add(new NameValuePair("city", city));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task_login = new CommonAsyTask(params,
                BaseURL.REGISTER_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Toast.makeText(RegisterActivity.this, "" + response, Toast.LENGTH_SHORT).show();

                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
                Toast.makeText(RegisterActivity.this, "" + responce, Toast.LENGTH_SHORT).show();
            }
        }, true, this);
        task_login.execute();

    }

}
