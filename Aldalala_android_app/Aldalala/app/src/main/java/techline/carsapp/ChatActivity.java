package techline.carsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import adapter.Chat_adapter;
import io.paperdb.Paper;
import model.Chat_model;
import util.CommonAsyTask;
import util.ConnectivityReceiver;
import util.LocaleHelper;
import util.NameValuePair;
import util.Session_management;

public class ChatActivity extends CommonAppCompatActivity implements View.OnClickListener {

    private static String TAG = ChatActivity.class.getSimpleName();

    private EditText et_msg;
    private Button btn_send;
    private RecyclerView rv_chat;

    private String getjoin_id, user_id;

    private Session_management sessionManagement;

    private List<Chat_model> chat_modelList = new ArrayList<>();

    private Chat_adapter adapter_deal;

    private boolean is_chat = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        sessionManagement = new Session_management(ChatActivity.this);
        user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

        getjoin_id = getIntent().getStringExtra("chat_id");
        String getpost_user_id = getIntent().getStringExtra("user_id");
        String gettitle = getIntent().getStringExtra("title");

        if (gettitle != null && !gettitle.isEmpty()) {
            is_chat = true;
            getSupportActionBar().setTitle(gettitle);
        } else {
            is_chat = false;
            getSupportActionBar().setTitle(getResources().getString(R.string.chat_title));
        }

        if (getIntent().getStringExtra("isopen") != null) {
            Log.e(TAG, getIntent().getStringExtra("isopen"));
        }

        et_msg = (EditText) findViewById(R.id.editChat);
        btn_send = (Button) findViewById(R.id.btnSend);
        rv_chat = (RecyclerView) findViewById(R.id.rv_chat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        rv_chat.setLayoutManager(linearLayoutManager);

        btn_send.setVisibility(View.VISIBLE);

        if (getpost_user_id != null && user_id.equals(getpost_user_id)) {
            btn_send.setEnabled(false);
            btn_send.setVisibility(View.GONE);
        }

        btn_send.setOnClickListener(this);

        if (ConnectivityReceiver.isConnected()) {
            makeGetJoinData(getjoin_id);
        } else {
            ConnectivityReceiver.showSnackbar(this);
        }

    }

    @Override
    public void onClick(View view) {
        String getmsg = et_msg.getText().toString();

        if (!getmsg.isEmpty()) {
            makeAddChat(getjoin_id, user_id, getmsg);
            et_msg.setText("");
        }
    }

    private void makeGetJoinData(String join_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("join_id", join_id));

        CommonAsyTask task = new CommonAsyTask(params,
                BaseURL.GET_JOIN_DATA_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Chat_model>>() {
                }.getType();

                chat_modelList = gson.fromJson(response, listType);

                adapter_deal = new Chat_adapter(ChatActivity.this, chat_modelList, user_id);
                rv_chat.setAdapter(adapter_deal);
                adapter_deal.notifyDataSetChanged();

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task.execute();
    }

    private void makeAddChat(String join_id, String sender_id, String message) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("join_id", join_id));
        params.add(new NameValuePair("sender_id", sender_id));
        params.add(new NameValuePair("message", message));

        CommonAsyTask task = new CommonAsyTask(params,
                BaseURL.ADD_CHAT_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                try {

                    JSONObject object = new JSONObject(response);

                    Chat_model chat_model = new Chat_model();
                    chat_model.setChat_id(object.getString("chat_id"));
                    chat_model.setJoin_id(object.getString("join_id"));
                    chat_model.setSender_id(object.getString("sender_id"));
                    chat_model.setMessage(object.getString("message"));
                    chat_model.setCreated_date(object.getString("created_date"));

                    chat_modelList.add(chat_model);
                    adapter_deal.notifyDataSetChanged();
                    rv_chat.scrollToPosition(chat_modelList.size() - 1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task.execute();
    }

    public static boolean isActivityopen;
    public static String getJoinId = "";

    @Override
    public void onPause() {
        super.onPause();
        isActivityopen = false;
        getJoinId = getjoin_id;
        // unregister reciver
        this.unregisterReceiver(mChatdata);
    }

    @Override
    public void onResume() {
        super.onResume();
        isActivityopen = true;
        getJoinId = getjoin_id;
        // register reciver
        this.registerReceiver(mChatdata, new IntentFilter("Chat_Data"));
    }

    // broadcast reciver for receive data
    private BroadcastReceiver mChatdata = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String jsonobject = intent.getStringExtra("jsonobject");

            Log.e(TAG, jsonobject);

            try {
                JSONObject jsonObject = new JSONObject(jsonobject);

                Chat_model chat_model = new Chat_model();
                chat_model.setChat_id(jsonObject.getString("chat_id"));
                chat_model.setJoin_id(jsonObject.getString("join_id"));
                chat_model.setSender_id(jsonObject.getString("sender_id"));
                chat_model.setMessage(jsonObject.getString("message"));
                chat_model.setCreated_date(jsonObject.getString("created_date"));

                chat_modelList.add(chat_model);
                adapter_deal.notifyDataSetChanged();
                rv_chat.scrollToPosition(chat_modelList.size() - 1);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

}
