package creo.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import cdflynn.android.library.checkview.CheckView;

public class scheduleverify extends AppCompatActivity {
    CheckView checkView;
    String phone_no = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduleverify);

        checkView = findViewById(R.id.check);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                checkView.check();
                gotoNext();
            }
        }, 600);

    }

    public void gotoNext() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(scheduleverify.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },500);
    }

}

