package fcm;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import Config.BaseURL;
import util.CommonAsyTask;
import util.NameValuePair;

/**
 * Created by Rajesh on 2017-08-07.
 */

public class MyFirebaseRegister {

    Activity _context;

    public MyFirebaseRegister(Activity context) {
        this._context = context;

    }

    public void RegisterUser(String user_id) {
        // [START subscribe_topics]
        String token = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("carsapp");
        // [END subscribe_topics]

        // register token to servier
        makeRegisterToken(user_id,token,"android");

    }

    private void makeRegisterToken(String user_id, final String token, String device) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new NameValuePair("user_id", user_id));
        nameValuePairs.add(new NameValuePair("token", token));
        nameValuePairs.add(new NameValuePair("device", device));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task_login = new CommonAsyTask(nameValuePairs,
                BaseURL.REGISTER_FCM_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(_context.toString(), response+","+token);
            }

            @Override
            public void VError(String responce) {
                Log.e(_context.toString(), responce);
            }
        }, true, _context);
        task_login.execute();

    }

}
