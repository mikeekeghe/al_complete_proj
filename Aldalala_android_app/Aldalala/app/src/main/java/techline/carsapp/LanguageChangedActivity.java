package techline.carsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;
import util.LocaleHelper;

public class LanguageChangedActivity extends AppCompatActivity {
    Button buttonOk;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_changed);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        buttonOk = findViewById(R.id.buttonOk);

        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(2 * 1000);

                    // After 5 seconds redirect to another intent
                    Intent i = new Intent(LanguageChangedActivity.this, MainActivity.class);
                    startActivity(i);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // start thread
        background.start();


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LanguageChangedActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

}
