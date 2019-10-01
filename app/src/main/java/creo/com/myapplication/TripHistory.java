package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

public class TripHistory extends AppCompatActivity {
    private TripAdapter tripAdapter;
    private RecyclerView recyclerView;
    Context mContext=this;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);

        TripPojo[] tripPojo = new TripPojo[]{
                new TripPojo("₹ 300", "12 SEP 2018", "Kaloor", "Vytila"),
                new TripPojo("₹ 345", "12 NOV 2018", "Kaloor", "Vytila"),
                new TripPojo("₹ 400", "26 AUG 2018", "Kaloor", "Vytila"),


        };


        recyclerView = findViewById(R.id.re);
        TripAdapter tripAdapter = new TripAdapter(tripPojo,mContext);

        recyclerView.setAdapter(tripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


    }
}
