package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import cdflynn.android.library.checkview.CheckView;

public class BookingVerified extends AppCompatActivity {
    CheckView checkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_verified);

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

                Intent intent = new Intent(BookingVerified.this, BookingHistory.class);
                startActivity(intent);
                finish();
            }
        },500);
    }
    }

