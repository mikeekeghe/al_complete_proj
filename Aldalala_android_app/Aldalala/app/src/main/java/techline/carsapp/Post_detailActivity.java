package techline.carsapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import adapter.Home_ads_adapter;
import adapter.Image_pager_adapter;
import adapter.Post_detail_fragment_adapter;
import io.paperdb.Paper;
import model.Post_image_model;
import model.Post_model;
import util.CommonAsyTask;
import util.DatabaseHandler;
import util.LocaleHelper;
import util.NameValuePair;
import util.RecyclerTouchListener;
import util.Session_management;

public class Post_detailActivity extends CommonAppCompatActivity implements View.OnClickListener {

    private static String TAG = Post_detailActivity.class.getSimpleName();

    private List<Post_model> post_modelList = new ArrayList<>();

    private FloatingActionButton fab_chat, fab_call;
    private FloatingActionMenu fab_menu;
    private TextView tv_title, tv_location, tv_price, tv_model, tv_date, tv_currency;
    private RecyclerView rv_similar_product;
    private ViewPager vp_image, vp_fragment, vp_dialoge_image;
    private ImageView iv_left, iv_right, iv_zoom, iv_fav,iv_noimage;
    private TabLayout tab_detail;

    private int total_image = 0;
    private int current_position = 0;
    private int current_position_dialog = 0;

    private String user_phone, user_id, login_user_id, post_id;

    private Image_pager_adapter image_pager_adapter;
    private Image_pager_adapter image_pager_adapter_zoom;

    private Session_management sessionManagement;
    private DatabaseHandler db;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManagement = new Session_management(Post_detailActivity.this);
        login_user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

        db = new DatabaseHandler(Post_detailActivity.this);

        vp_image = (ViewPager) findViewById(R.id.viewPager);
        iv_noimage = (ImageView) findViewById(R.id.iv_post_detail_no_image);
        iv_left = (ImageView) findViewById(R.id.iv_post_detail_left);
        iv_right = (ImageView) findViewById(R.id.iv_post_detail_right);
        iv_zoom = (ImageView) findViewById(R.id.iv_post_detail_zoom);
        iv_fav = (ImageView) findViewById(R.id.iv_post_detail_fav);
        fab_chat = (FloatingActionButton) findViewById(R.id.menu_detail_chat);
        fab_call = (FloatingActionButton) findViewById(R.id.menu_detail_call);
        fab_menu = (FloatingActionMenu) findViewById(R.id.menu);
        tv_title = (TextView) findViewById(R.id.tv_post_detail_title);
        tv_location = (TextView) findViewById(R.id.tv_post_detail_location);
        tv_price = (TextView) findViewById(R.id.tv_post_detail_price);
        tv_currency = (TextView) findViewById(R.id.tv_post_detail_currency);
        tv_model = (TextView) findViewById(R.id.tv_post_detail_model);
        tv_date = (TextView) findViewById(R.id.tv_post_detail_date);
        rv_similar_product = (RecyclerView) findViewById(R.id.rv_detail_similar_product);
        tab_detail = (TabLayout) findViewById(R.id.tab_post_detail);
        vp_fragment = (ViewPager) findViewById(R.id.vp_post_detail_fm);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_similar_product.setLayoutManager(linearLayoutManager);

        Bundle args = getIntent().getExtras();

        post_id = args.getString("post_id");
        String title = args.getString("title");
        String date = args.getString("date");
        String km = args.getString("km");
        String year = args.getString("year");
        String price = args.getString("price");
        String city_name = args.getString("city_name");
        String descreption = args.getString("desc");
        String user_email = args.getString("user_email");
        String user_name = args.getString("user_name");
        user_id = args.getString("user_id");
        user_phone = args.getString("user_phone");
        String get_currency = args.getString("currency");

        getSupportActionBar().setTitle(title);

        tv_title.setText(title);
        tv_date.setText(date);
        tv_model.setText(year + ", " + km + " Kms, ");
        tv_price.setText(price);
        tv_currency.setText(get_currency);
        tv_location.setText(city_name);

        setfav(false);

        makeGetGalleryByPost(post_id);
        makeGetPost();

        if (login_user_id.equals(user_id)) {
            fab_chat.setVisibility(View.GONE);
        } else {
            fab_chat.setVisibility(View.VISIBLE);
        }

        fab_chat.setOnClickListener(this);
        fab_call.setOnClickListener(this);
        iv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        iv_zoom.setOnClickListener(this);
        iv_fav.setOnClickListener(this);
        fab_menu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (!sessionManagement.isLoggedIn()) {
                    startLogin();
                }
            }
        });

        rv_similar_product.addOnItemTouchListener(new RecyclerTouchListener(this, rv_similar_product, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent post_detail = new Intent(Post_detailActivity.this, Post_detailActivity.class);

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

        vp_image.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                current_position = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vp_fragment.setAdapter(new Post_detail_fragment_adapter(getSupportFragmentManager(),
                post_id, descreption, user_name, user_email, user_phone, user_id));

        tab_detail.setupWithViewPager(vp_fragment);
        setupTab();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.menu_detail_chat) {
            if (sessionManagement.isLoggedIn()) {
                makeGetJoinUser(user_id, login_user_id);
            } else {
                startLogin();
            }
        } else if (id == R.id.menu_detail_call) {
            if (sessionManagement.isLoggedIn()) {
                if (login_user_id.equals(user_id)) {
                    Toast.makeText(Post_detailActivity.this, getResources().getString(R.string.able_to_call), Toast.LENGTH_SHORT).show();
                } else {
                    showCallDialog(user_phone);
                }
            } else {
                startLogin();
            }
        } else if (id == R.id.iv_post_detail_right) {
            if (current_position < total_image) {
                current_position++;

                vp_image.setCurrentItem(current_position);
            }
        } else if (id == R.id.iv_post_detail_left) {
            if (current_position > 0) {
                current_position--;

                vp_image.setCurrentItem(current_position);
            }

        } else if (id == R.id.iv_post_detail_zoom) {
            showImages();
        } else if (id == R.id.iv_post_detail_fav) {
            if (sessionManagement.isLoggedIn()) {
                db.setFav(post_id);
                setfav(true);
            } else {
                startLogin();
            }
        }

    }

    private void startLogin() {
        Intent loginIntent = new Intent(Post_detailActivity.this, LoginActivity.class);
        loginIntent.putExtra("setfinish", "true");
        startActivity(loginIntent);
    }

    // set favorite icon in imageview and show message
    private void setfav(boolean ismsg) {
        if (db.isFav(post_id)) {
            iv_fav.setBackgroundResource(R.drawable.ic_favorite_orenge);
            if (ismsg)
                Toast.makeText(this, getResources().getString(R.string.added_favorite), Toast.LENGTH_SHORT).show();
        } else {
            iv_fav.setBackgroundResource(R.drawable.ic_favorite_white);
            if (ismsg)
                Toast.makeText(this, getResources().getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show();
        }
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

                post_modelList = gson.fromJson(response, listType);

                Home_ads_adapter adapter_ad = new Home_ads_adapter(post_modelList);
                rv_similar_product.setAdapter(adapter_ad);
                adapter_ad.notifyDataSetChanged();

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task_post.execute();

    }

    // get post gallery from server
    private void makeGetGalleryByPost(String post_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("post_id", post_id));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task_post = new CommonAsyTask(params,
                BaseURL.GET_POST_IMAGE_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                List<Post_image_model> post_image_modelList = new ArrayList<>();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Post_image_model>>() {
                }.getType();

                post_image_modelList = gson.fromJson(response, listType);

                total_image = post_image_modelList.size();

                image_pager_adapter = new Image_pager_adapter(Post_detailActivity.this, post_image_modelList, false);
                image_pager_adapter_zoom = new Image_pager_adapter(Post_detailActivity.this, post_image_modelList, true);
                vp_image.setAdapter(image_pager_adapter_zoom);

                if (post_image_modelList.isEmpty()){
                    iv_noimage.setVisibility(View.VISIBLE);
                    vp_image.setVisibility(View.GONE);
                }else{
                    iv_noimage.setVisibility(View.GONE);
                    vp_image.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task_post.execute();
    }

    // this api for join user for chating with onther client how post his product
    private void makeGetJoinUser(String post_user_id, String login_user_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("post_user_id", post_user_id));
        params.add(new NameValuePair("login_user_id", login_user_id));

        CommonAsyTask task_post = new CommonAsyTask(params,
                BaseURL.GET_JOIN_USER_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Intent chatIntent = new Intent(Post_detailActivity.this, ChatActivity.class);
                chatIntent.putExtra("chat_id", response);
                chatIntent.putExtra("user_id", user_id);
                startActivity(chatIntent);
            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task_post.execute();
    }

    private void setupTab() {
        // set tab title or text in tablayout
        tab_detail.getTabAt(0).setText(getResources().getString(R.string.specifications));
        tab_detail.getTabAt(1).setText(getResources().getString(R.string.details));
        tab_detail.getTabAt(2).setText(getResources().getString(R.string.owner_contact));
        tab_detail.getTabAt(3).setText(getResources().getString(R.string.reviews));
    }

    // showing custom alertdialog with viewpager images
    private void showImages() {

        final Dialog dialog = new Dialog(Post_detailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_post_image_detail);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        vp_dialoge_image = (ViewPager) dialog.findViewById(R.id.vp_dialog_img);
        ImageView iv_dialoge_cancle = (ImageView) dialog.findViewById(R.id.iv_dialog_cancle);
        ImageView iv_dialoge_left = (ImageView) dialog.findViewById(R.id.iv_dialog_left);
        ImageView iv_dialoge_right = (ImageView) dialog.findViewById(R.id.iv_dialog_right);

        vp_dialoge_image.setAdapter(image_pager_adapter);

        vp_dialoge_image.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                current_position_dialog = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        iv_dialoge_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        iv_dialoge_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (current_position_dialog > 0) {

                    current_position_dialog--;

                    vp_dialoge_image.setCurrentItem(current_position_dialog);

                }
            }
        });

        iv_dialoge_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (current_position_dialog < total_image) {
                    current_position_dialog++;
                    vp_dialoge_image.setCurrentItem(current_position_dialog);

                }
            }
        });

    }

    // calling cilent phone number
    private void showCallDialog(final String user_phone) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Post_detailActivity.this);
        builder.setTitle(getResources().getString(R.string.note_call))
                .setMessage(getResources().getString(R.string.owner_phone) + user_phone)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // call intent for call given phone number
                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + user_phone));
                        startActivity(callIntent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
        //.show();

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                this.finish();
                return true;
            case R.id.action_search:
                Intent searchIntent = new Intent(this, FilterActivity.class);
                startActivity(searchIntent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}
