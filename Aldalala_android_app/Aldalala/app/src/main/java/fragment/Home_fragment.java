package fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import adapter.Home_ads_adapter;
import adapter.Home_deal_adapter;
import adapter.Home_slider_adapter;
import model.Post_model;
import model.Slider_model;
import techline.carsapp.Add_postActivity;
import techline.carsapp.All_postActivity;
import techline.carsapp.All_postRentActivity;
import techline.carsapp.LoginActivity;
import techline.carsapp.MainActivity;
import techline.carsapp.Post_detailActivity;
import techline.carsapp.R;
import util.CommonAsyTask;
import util.ConnectivityReceiver;
import util.NameValuePair;
import util.RecyclerTouchListener;
import util.Session_management;

/**
 * Created by Rajesh Dabhi on 19/7/2017.
 */

public class Home_fragment extends Fragment implements View.OnClickListener {

    private static String TAG = Home_fragment.class.getSimpleName();

    private List<Slider_model> slider_modelList = new ArrayList<>();
    private List<Post_model> post_modelList = new ArrayList<>();
    private List<Post_model> ad_modelList = new ArrayList<>();

    private RecyclerView rv_slider, rv_ads, rv_deal;
    private Button btn_addpost, btn_rent;
    private TextView tv_viewall, tv_location;

    private Session_management sessionManagement;

    private Boolean exit = false;

    public Home_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_home, container, false);

        if (getActivity() != null) {
            sessionManagement = new Session_management(getActivity());
        }

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (exit) {
                        ((MainActivity) getActivity()).setFinish();
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.press_back),
                                Toast.LENGTH_SHORT).show();
                        exit = true;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                exit = false;
                            }
                        }, 3 * 1000);
                    }
                    return true;
                }
                return false;
            }
        });

        tv_location = (TextView) view.findViewById(R.id.tv_home_location);
        tv_viewall = (TextView) view.findViewById(R.id.tv_home_viewall);
        btn_addpost = (Button) view.findViewById(R.id.btn_home_addpost);
        btn_rent = (Button) view.findViewById(R.id.btn_rent);
        rv_slider = (RecyclerView) view.findViewById(R.id.rv_home_slider);
        rv_ads = (RecyclerView) view.findViewById(R.id.rv_home_ads);
        rv_deal = (RecyclerView) view.findViewById(R.id.rv_home_deals);
        NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.nested);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        final LinearLayoutManager linearLayoutManager_deal = new LinearLayoutManager(getActivity());

        rv_slider.setLayoutManager(linearLayoutManager);
        rv_ads.setLayoutManager(linearLayoutManager1);

        rv_deal.setLayoutManager(linearLayoutManager_deal);

        rv_deal.setNestedScrollingEnabled(false);

        if (getActivity() != null) {
            if (ConnectivityReceiver.isConnected()) {
                makeGetSlider();
            } else {
                ConnectivityReceiver.showSnackbar(getActivity());
            }
        }

        btn_addpost.setOnClickListener(this);
        btn_rent.setOnClickListener(this);
        tv_viewall.setOnClickListener(this);
        tv_location.setOnClickListener(this);
/*

        rv_deal.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv_deal, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent post_detail = new Intent(getActivity(), Post_detailActivity.class);

                post_detail.putExtra("post_id", post_modelList.get(position).getPost_id());
                post_detail.putExtra("title", post_modelList.get(position).getPost_title());
                post_detail.putExtra("date", post_modelList.get(position).getPost_date());
                post_detail.putExtra("year", post_modelList.get(position).getPost_year());
                post_detail.putExtra("km", post_modelList.get(position).getPost_km());
                post_detail.putExtra("price", post_modelList.get(position).getPost_price());
                post_detail.putExtra("city_name", post_modelList.get(position).getCity_name());
                post_detail.putExtra("desc", post_modelList.get(position).getPost_description());
                post_detail.putExtra("user_email", post_modelList.get(position).getUser_email());
                post_detail.putExtra("user_name", post_modelList.get(position).getUser_fullname());
                post_detail.putExtra("user_phone", post_modelList.get(position).getUser_phone());
                post_detail.putExtra("user_id", post_modelList.get(position).getUser_id());
                post_detail.putExtra("currency", post_modelList.get(position).getCurrency());

                startActivity(post_detail);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
*/

        rv_ads.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv_ads, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent post_detail = new Intent(getActivity(), Post_detailActivity.class);
                post_detail.putExtra("post_id", ad_modelList.get(position).getPost_id());
                post_detail.putExtra("title", ad_modelList.get(position).getPost_title());
                post_detail.putExtra("date", ad_modelList.get(position).getPost_date());
                post_detail.putExtra("year", ad_modelList.get(position).getPost_year());
                post_detail.putExtra("km", ad_modelList.get(position).getPost_km());
                post_detail.putExtra("price", ad_modelList.get(position).getPost_price());
                post_detail.putExtra("city_name", ad_modelList.get(position).getCity_name());
                post_detail.putExtra("desc", ad_modelList.get(position).getPost_description());
                post_detail.putExtra("user_email", ad_modelList.get(position).getUser_email());
                post_detail.putExtra("user_name", ad_modelList.get(position).getUser_fullname());
                post_detail.putExtra("user_phone", ad_modelList.get(position).getUser_phone());
                post_detail.putExtra("user_id", ad_modelList.get(position).getUser_id());
                post_detail.putExtra("currency", ad_modelList.get(position).getCurrency());
                startActivity(post_detail);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_home_addpost) {
            if (sessionManagement.isLoggedIn()) {
                Intent addpostIntent = new Intent(getActivity(), Add_postActivity.class);
                startActivity(addpostIntent);
            } else {
                Intent addpostIntent = new Intent(getActivity(), LoginActivity.class);
                addpostIntent.putExtra("setfinish", "true");
                startActivity(addpostIntent);
            }
        } else if (id == R.id.btn_rent) {
            Intent allpostIntent = new Intent(getActivity(), All_postRentActivity.class);
            startActivity(allpostIntent);
        } else if (id == R.id.tv_home_viewall) {

            Intent allpostIntent = new Intent(getActivity(), All_postActivity.class);
            startActivity(allpostIntent);

        } else if (id == R.id.tv_home_location) {

            if (ConnectivityReceiver.isConnected()) {
                makeGetPostByCity();
            } else {
                ConnectivityReceiver.showSnackbar(getActivity());
            }
        }
    }

    private void makeGetSlider() {

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task = new CommonAsyTask(new ArrayList<NameValuePair>(),
                BaseURL.GET_SLIDER, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Slider_model>>() {
                }.getType();

                slider_modelList = gson.fromJson(response, listType);

                Home_slider_adapter adapter = new Home_slider_adapter(slider_modelList);
                rv_slider.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                makeGetPost();
            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, getActivity());
        task.execute();

    }

    private void makeGetPost() {

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task_post = new CommonAsyTask(new ArrayList<NameValuePair>(),
                BaseURL.GET_POST, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Post_model>>() {
                }.getType();

                ad_modelList = gson.fromJson(response, listType);

                Home_ads_adapter adapter_ad = new Home_ads_adapter(ad_modelList);
                rv_ads.setAdapter(adapter_ad);
                adapter_ad.notifyDataSetChanged();

                makeGetPostByCity();
            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, getActivity());
        task_post.execute();

    }

    private void makeGetPostByCity() {

        String city_id = sessionManagement.getUserDetails().get(BaseURL.KEY_CITY);

        Log.e(TAG, city_id);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("city_id", city_id));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task_post = new CommonAsyTask(params,
                BaseURL.GET_POST_BY_CITY_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, "post city: " + response);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Post_model>>() {
                }.getType();

                post_modelList = (ArrayList<Post_model>) gson.fromJson(response, listType);

                Home_deal_adapter adapter_deal = new Home_deal_adapter(post_modelList);
                rv_deal.setAdapter(adapter_deal);
                adapter_deal.notifyDataSetChanged();

                if (post_modelList.isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.record_not_found_location), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, getActivity());
        task_post.execute();
    }

}
