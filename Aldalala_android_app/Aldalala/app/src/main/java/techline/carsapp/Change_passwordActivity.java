package techline.carsapp;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Config.BaseURL;
import io.paperdb.Paper;
import util.CommonAsyTask;
import util.LocaleHelper;
import util.NameValuePair;
import util.Session_management;

public class Change_passwordActivity extends CommonAppCompatActivity {

    private static String TAG = Change_passwordActivity.class.getSimpleName();

    private TextView tv_new_pass, tv_old_pass, tv_con_pass;
    private EditText et_new_pass, et_old_pass, et_con_pass;
    private Button btn_change_pass;

    private Session_management sessionManagement;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }

        getSupportActionBar().setTitle(getResources().getString(R.string.change_password));

        sessionManagement = new Session_management(this);

        tv_new_pass = (TextView) findViewById(R.id.tv_change_new_password);
        tv_old_pass = (TextView) findViewById(R.id.tv_change_old_password);
        tv_con_pass = (TextView) findViewById(R.id.tv_change_con_password);
        et_new_pass = (EditText) findViewById(R.id.et_change_new_password);
        et_old_pass = (EditText) findViewById(R.id.et_change_old_password);
        et_con_pass = (EditText) findViewById(R.id.et_change_con_password);
        btn_change_pass = (Button) findViewById(R.id.btn_change_password);

        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptChangePassword();
            }
        });

    }

    private void attemptChangePassword() {

        tv_new_pass.setText(getResources().getString(R.string.new_password_req));
        tv_old_pass.setText(getResources().getString(R.string.old_password_req));
        tv_con_pass.setText(getResources().getString(R.string.confirm_password_req));

        String get_new_pass = et_new_pass.getText().toString();
        String get_old_pass = et_old_pass.getText().toString();
        String get_con_pass = et_con_pass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(get_old_pass)) {
            focusView = tv_old_pass;
            cancel = true;
        } else if (!isPasswordValid(get_old_pass)) {
            tv_old_pass.setText(getString(R.string.error_invalid_password));
            focusView = tv_old_pass;
            cancel = true;
        }

        if (TextUtils.isEmpty(get_new_pass)) {
            focusView = tv_new_pass;
            cancel = true;
        } else if (!isPasswordValid(get_new_pass)) {
            tv_new_pass.setText(getString(R.string.error_invalid_password));
            focusView = tv_new_pass;
            cancel = true;
        }

        if (TextUtils.isEmpty(get_con_pass)) {
            focusView = tv_con_pass;
            cancel = true;
        } else if (!get_con_pass.equals(get_new_pass)) {
            tv_con_pass.setText(getResources().getString(R.string.password_not_match));
            focusView = tv_con_pass;
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

            makeChangePassword(user_id,get_old_pass,get_new_pass);

        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void makeChangePassword(String user_id, String current_password, String new_password) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("user_id", user_id));
        params.add(new NameValuePair("current_password", current_password));
        params.add(new NameValuePair("new_password", new_password));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task = new CommonAsyTask(params,
                BaseURL.CHANGE_PASSWORD_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Toast.makeText(Change_passwordActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                sessionManagement.logoutSession();
                finish();
            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
                Toast.makeText(Change_passwordActivity.this, "" + responce, Toast.LENGTH_SHORT).show();
            }
        }, true, this);
        task.execute();

    }

}
