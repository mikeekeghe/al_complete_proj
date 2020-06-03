package fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import techline.carsapp.R;

/**
 * Created by Rajesh on 2017-08-25.
 */

public class Owner_detailFragment extends Fragment {

    private TextView tv_name,tv_email,tv_phone;

    public Owner_detailFragment() {
        // Required empty public constructor
    }

    public static Owner_detailFragment newInstance(String name,String email,String phone) {

        // show fragment and add data like name,email,phone
        Owner_detailFragment f = new Owner_detailFragment();
        Bundle b = new Bundle();
        b.putString("name", name);
        b.putString("email", email);
        b.putString("phone", phone);

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
        View view = inflater.inflate(R.layout.fragment_owner_detail, container, false);

        tv_name = (TextView) view.findViewById(R.id.tv_owner_name);
        tv_email = (TextView) view.findViewById(R.id.tv_owner_email);
        tv_phone = (TextView) view.findViewById(R.id.tv_owner_phone);

        String getname = getArguments().getString("name");
        String getemail = getArguments().getString("email");
        String getphone = getArguments().getString("phone");

        tv_name.setText(getname);
        tv_email.setText(getemail);
        tv_phone.setText(getphone);

        return view;
    }

}
