package techline.carsapp;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import util.LocaleHelper;

/**
 * Created by Rajesh Dabhi on 20/7/2017.
 */

public class CommonAppCompatActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "ar"));
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                this.finish();
                return true;
            case R.id.action_change_password:
                Intent changeIntent = new Intent(this, Edit_profileActivity.class);
                startActivity(changeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}
