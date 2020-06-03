package fragment;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import techline.carsapp.R;
import model.Attribute_model;
import model.Attribute_sub_model;
import util.CommonAsyTask;
import util.NameValuePair;

/**
 * Created by Rajesh on 2017-08-24.
 */

public class SpecificationFragment extends Fragment {

    private static String TAG = SpecificationFragment.class.getSimpleName();

    List<Attribute_sub_model> edit_attribute_modelList = new ArrayList<>();

    private boolean is_compare = false;

    public SpecificationFragment() {
        // Required empty public constructor
    }

    public static SpecificationFragment newInstance(String post_id) {

        SpecificationFragment f = new SpecificationFragment();
        Bundle b = new Bundle();
        b.putString("post_id", post_id);

        f.setArguments(b);

        return f;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // animate here
            Log.e(TAG, "animate here");
        } else {
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
        View view = inflater.inflate(R.layout.fragment_specification, container, false);

        String post_id = getArguments().getString("post_id");
        makeGetAttributeByPost(post_id);
        makeGetAttribute();

        return view;
    }

    private void makeGetAttribute() {

        CommonAsyTask task = new CommonAsyTask(new ArrayList<NameValuePair>(),
                BaseURL.GET_ATTRIBUTE_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                List<Attribute_model> attribute_modelList = new ArrayList<>();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Attribute_model>>() {
                }.getType();

                attribute_modelList = gson.fromJson(response, listType);

                final LinearLayout lm = (LinearLayout) ((Activity) getActivity()).findViewById(R.id.ll_specification);

                // create the layout params that will be used to define how your
                // button will be displayed
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for (int i = 0; i < attribute_modelList.size(); i++) {

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 5, 0, 0);
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

// fill in any details dynamically here
                    LinearLayout linearLayout1 = new LinearLayout(getActivity());
                    linearLayout1.setLayoutParams(layoutParams);
                    linearLayout1.setGravity(Gravity.CENTER);
                    linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

                    TextView textView = new TextView(getActivity());
                    textView.setLayoutParams(layoutParams1);
                    textView.setTextColor(getResources().getColor(R.color.black));
                    textView.setText(attribute_modelList.get(i).getAttr_group_name());

                    linearLayout1.addView(textView);

                    final List<String> list_name = new ArrayList<>();
                    final List<String> list_id = new ArrayList<>();
                    final List<String> list_group_id = new ArrayList<>();

                    for (int color = 0; color < attribute_modelList.get(i).getAttribute_sub_models().size(); color++) {
                        list_name.add(attribute_modelList.get(i).getAttribute_sub_models().get(color).getAttr_rolle());
                        list_id.add(attribute_modelList.get(i).getAttribute_sub_models().get(color).getAttr_id());
                        list_group_id.add(attribute_modelList.get(i).getAttribute_sub_models().get(color).getAttr_group_id());
                    }

                    if (attribute_modelList.get(i).getAttr_group_choosen().equals("multiple")) {

                        TextView textView2 = new TextView(getActivity());
                        textView2.setLayoutParams(layoutParams1);
                        textView2.setTextColor(getResources().getColor(R.color.black));
                        textView2.setTypeface(null, Typeface.BOLD);

                        if (is_compare) {

                            List<String> selected_list = new ArrayList<>();

                            StringBuilder sb = new StringBuilder();

                            String group_id = attribute_modelList.get(i).getAttr_group_id();

                            for (int e = 0; e < edit_attribute_modelList.size(); e++) {

                                if (group_id.equals(edit_attribute_modelList.get(e).getAttr_group_id())) {

                                    selected_list.add(", " + edit_attribute_modelList.get(e).getAttr_id());

                                    int id = list_id.indexOf(edit_attribute_modelList.get(e).getAttr_id());

                                    sb.append("," + list_name.get(id));

                                    Log.e("Multi", "" + edit_attribute_modelList.get(e).getAttr_id());
                                }
                            }

                            if (sb.length() > 0) {
                                textView2.setText(sb.substring(1));
                            }
                        } else {
                            textView2.setText(getResources().getString(R.string.record_not_found));
                        }

                        linearLayout1.addView(textView2);
                    } else {

                        TextView textView2 = new TextView(getActivity());
                        textView2.setLayoutParams(layoutParams1);
                        textView2.setTextColor(getResources().getColor(R.color.black));
                        textView2.setTypeface(null, Typeface.BOLD);

                        String group_id = attribute_modelList.get(i).getAttr_group_id();

                        if (is_compare) {
                            for (int e = 0; e < edit_attribute_modelList.size(); e++) {

                                if (group_id.equals(edit_attribute_modelList.get(e).getAttr_group_id())) {

                                    int id = list_id.indexOf(edit_attribute_modelList.get(e).getAttr_id().toString());

                                    Log.e("Select", "" + id);

                                    textView2.setText(list_name.get(id));
                                }

                            }
                        } else {
                            textView2.setText(getResources().getString(R.string.record_not_found));
                        }

                        linearLayout1.addView(textView2);

                    }

                    lm.addView(linearLayout1);

                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, getActivity());
        task.execute();

    }

    private void makeGetAttributeByPost(String post_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("post_id", post_id));

        CommonAsyTask task2 = new CommonAsyTask(params,
                BaseURL.GET_POST_ATTRIBUTE_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, "post " + response);

                //List<Attribute_sub_model> edit_attribute_modelList = new ArrayList<>();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Attribute_sub_model>>() {
                }.getType();

                edit_attribute_modelList = gson.fromJson(response, listType);

                if (!edit_attribute_modelList.isEmpty()) {
                    is_compare = true;
                }
            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, getActivity());
        task2.execute();

    }

}