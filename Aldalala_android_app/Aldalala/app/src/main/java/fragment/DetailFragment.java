package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import io.paperdb.Paper;
import techline.carsapp.R;

/**
 * Created by Rajesh on 2017-08-24.
 */

public class DetailFragment extends Fragment {

    private TextView tv_desc;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(String post_id,String desc) {

        // new fragment with put post data and description
        DetailFragment f = new DetailFragment();
        Bundle b = new Bundle();
        b.putString("post_id", post_id);
        b.putString("desc", desc);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Paper.init(getContext());

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        tv_desc = (TextView) view.findViewById(R.id.tv_detail_desc);

        String getdesc = getArguments().getString("desc");

        tv_desc.setText(getdesc);

        return view;
    }

}
