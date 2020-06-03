package techline.carsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import adapter.Chat_client_adapter;
import io.paperdb.Paper;
import model.Chat_client_model;
import util.CommonAsyTask;
import util.ConnectivityReceiver;
import util.LocaleHelper;
import util.NameValuePair;
import util.RecyclerTouchListener;
import util.Session_management;

public class Chat_client_listActivity extends CommonAppCompatActivity {

    private static String TAG = Chat_client_listActivity.class.getSimpleName();

    private RecyclerView rv_client;

    private List<Chat_client_model> chat_client_modelList = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_client_list);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        rv_client = (RecyclerView) findViewById(R.id.rv_chat_client);
        rv_client.setLayoutManager(new LinearLayoutManager(this));

        Session_management sessionManagement = new Session_management(this);
        String getuser_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

        if (ConnectivityReceiver.isConnected()) {
            makeGetClient(getuser_id);
        }else{
            ConnectivityReceiver.showSnackbar(this);
        }

        rv_client.addOnItemTouchListener(new RecyclerTouchListener(this, rv_client, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent chatIntent = new Intent(Chat_client_listActivity.this, ChatActivity.class);
                chatIntent.putExtra("chat_id", chat_client_modelList.get(position).getJoin_id());
                chatIntent.putExtra("title", chat_client_modelList.get(position).getUser_2_fullname());
                startActivity(chatIntent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }

    private void makeGetClient(String user_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("user_id", user_id));

        CommonAsyTask task = new CommonAsyTask(params,
                BaseURL.GET_CHAT_DATA_BY_USER_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Chat_client_model>>() {
                }.getType();

                chat_client_modelList = gson.fromJson(response, listType);

                Chat_client_adapter adapter_deal = new Chat_client_adapter(chat_client_modelList,Chat_client_listActivity.this);
                rv_client.setAdapter(adapter_deal);
                adapter_deal.notifyDataSetChanged();

                if (chat_client_modelList.isEmpty()) {
                    Toast.makeText(Chat_client_listActivity.this, getResources().getString(R.string.record_not_found), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
                Toast.makeText(Chat_client_listActivity.this, "" + responce, Toast.LENGTH_SHORT).show();
            }
        }, true, this);
        task.execute();

    }

}
