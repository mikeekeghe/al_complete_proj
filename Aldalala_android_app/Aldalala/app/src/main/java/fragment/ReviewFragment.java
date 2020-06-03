package fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import adapter.Review_adapter;
import techline.carsapp.LoginActivity;
import techline.carsapp.R;
import model.Review_model;
import util.CommonAsyTask;
import util.NameValuePair;
import util.Session_management;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Rajesh on 2017-08-24.
 */

public class ReviewFragment extends Fragment implements View.OnClickListener {

    private static String TAG = ReviewFragment.class.getSimpleName();

    private TextView tv_addreview;
    private RecyclerView rv_review;

    private Session_management sessionManagement;

    private String post_id;
    private String post_user_id;

    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance(String post_id, String user_id) {

        ReviewFragment f = new ReviewFragment();
        Bundle b = new Bundle();
        b.putString("post_id", post_id);
        b.putString("user_id", user_id);

        f.setArguments(b);

        return f;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // animate here
            Log.e(TAG, "animate here");
            makeGetReview(post_id);
        }else{
            Log.e(TAG, "fragment is no longer visible");
            // fragment is no longer visible
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        sessionManagement = new Session_management(getActivity());

        post_id = getArguments().getString("post_id");
        post_user_id = getArguments().getString("user_id");

        tv_addreview = (TextView) view.findViewById(R.id.tv_review_add);
        rv_review = (RecyclerView) view.findViewById(R.id.rv_reviews);
        rv_review.setLayoutManager(new LinearLayoutManager(getActivity()));

        tv_addreview.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        if (sessionManagement.isLoggedIn()) {
            String getuser_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

            if (getuser_id.equals(post_user_id)) {
                Toast.makeText(getActivity(), getResources().getString(R.string.note_review), Toast.LENGTH_SHORT).show();
            } else {
                showDialog();
            }
        }else{
            Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
            loginIntent.putExtra("setfinish", "true");
            startActivity(loginIntent);
        }
    }

    private void showDialog() {
        final RatingBar ratingBar;
        final EditText et_comment;
        final TextInputLayout ti_comment;

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.dialog_add_review,
                (ViewGroup) ((Activity) getActivity()).findViewById(R.id.ll_review_dialog));
        et_comment = (EditText) layout.findViewById(R.id.et_review_dialog_comment);
        ratingBar = (RatingBar) layout.findViewById(R.id.rb_review_dialog);
        ti_comment = (TextInputLayout) layout.findViewById(R.id.ti_review_dialog_comment);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        //stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        //stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        final AlertDialog dialog;

        alertDialog.setView(layout);

        alertDialog.setPositiveButton(getResources().getString(R.string.add_review), null);

        alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = alertDialog.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);

                Button ok = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                ok.setTextColor(Color.BLACK);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String getcomment = et_comment.getText().toString();

                        ti_comment.setError(null);

                        if (!getcomment.isEmpty()) {
                            String getuser_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);
                            String getrating = String.valueOf(Math.round(ratingBar.getRating()));

                            dialog.dismiss();

                            makeAddReview(getcomment, getrating, getuser_id, post_id);
                        } else {
                            ti_comment.setError(getResources().getString(R.string.field_requierd));
                        }
                    }
                });
            }
        });

        dialog.show();

    }

    private void makeAddReview(String comment, String rating,
                               String user_id, final String post_id) {

        Log.e(TAG, comment + "," + rating + "," + user_id + "," + post_id);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("comment", comment));
        params.add(new NameValuePair("rating", rating));
        params.add(new NameValuePair("user_id", user_id));
        params.add(new NameValuePair("post_id", post_id));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task = new CommonAsyTask(params,
                BaseURL.ADD_REVIEW_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                Toast.makeText(getActivity(), "" + response, Toast.LENGTH_SHORT).show();
                makeGetReview(post_id);

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
                Toast.makeText(getActivity(), "" + responce, Toast.LENGTH_SHORT).show();
            }
        }, true, getActivity());
        task.execute();

    }

    private void makeGetReview(String post_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("post_id", post_id));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task = new CommonAsyTask(params,
                BaseURL.GET_REVIEW_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                List<Review_model> review_modelList = new ArrayList<>();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Review_model>>() {
                }.getType();

                review_modelList = gson.fromJson(response, listType);

                Review_adapter adapter = new Review_adapter(review_modelList);
                ((Review_adapter) adapter).setMode(Attributes.Mode.Single);
                rv_review.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if (review_modelList.isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.record_not_found), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
                Toast.makeText(getActivity(), "" + responce, Toast.LENGTH_SHORT).show();
            }
        }, true, getActivity());
        task.execute();

    }

}
