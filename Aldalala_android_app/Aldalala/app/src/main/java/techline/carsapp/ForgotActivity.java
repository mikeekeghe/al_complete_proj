package techline.carsapp;

import android.content.Context;
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

public class ForgotActivity extends AppCompatActivity {

    private static String TAG = "ForgotPasswordActivity";

    private EditText et_email;
    private TextView tv_email;
    private Button btn_forgot;

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

        setContentView(R.layout.activity_forgot);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }

        et_email = (EditText) findViewById(R.id.et_forgot_username);
        tv_email = (TextView) findViewById(R.id.tv_forgot_email);
        btn_forgot = (Button) findViewById(R.id.btn_forgot);

        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptForgot();
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptForgot() {

        // Reset errors.
        tv_email.setText(getResources().getString(R.string.email_required));

        // Store values at the time of the login attempt.
        String email = et_email.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            //tl_email.setError(getString(R.string.error_field_required));
            focusView = tv_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            tv_email.setText(getResources().getString(R.string.error_invalid_email));
            //tl_email.setError(getString(R.string.error_invalid_email));
            focusView = tv_email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            makeForgot(email);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private void makeForgot(String email) {
        Log.d(TAG, "makeForgot: Email: "+ email);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("email", email));

        CommonAsyTask task = new CommonAsyTask(params,
                BaseURL.FORGOT_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Toast.makeText(ForgotActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
                Toast.makeText(ForgotActivity.this, "" + responce, Toast.LENGTH_SHORT).show();
            }
        }, true, this);
        task.execute();

    }

}
