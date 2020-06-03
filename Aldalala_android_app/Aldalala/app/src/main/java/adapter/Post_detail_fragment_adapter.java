package adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fragment.DetailFragment;
import fragment.Owner_detailFragment;
import fragment.ReviewFragment;
import fragment.SpecificationFragment;

/**
 * Created by Rajesh on 2017-08-24.
 */

public class Post_detail_fragment_adapter extends FragmentPagerAdapter {

    String post_id, desc, user_name, user_email, user_phone,user_id;

    public Post_detail_fragment_adapter(FragmentManager fm, String post_id,
                                        String desc,String user_name,String user_email,
                                        String user_phone,String user_id) {
        super(fm);

        this.post_id = post_id;
        this.desc = desc;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_id = user_id;
    }

    @Override
    public Fragment getItem(int pos) {
        // return fragment and pass data
        switch (pos) {
            case 0:
                return SpecificationFragment.newInstance(post_id);
            case 1:
                return DetailFragment.newInstance(post_id, desc);
            case 2:
                return Owner_detailFragment.newInstance(user_name, user_email, user_phone);
            case 3:
                return ReviewFragment.newInstance(post_id,user_id);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}