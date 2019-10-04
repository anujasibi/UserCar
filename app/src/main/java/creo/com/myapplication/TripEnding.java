package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import creo.com.myapplication.utils.SessionManager;

public class TripEnding extends AppCompatActivity {

    TextView sources,destination,amount;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_ending);

        sessionManager = new SessionManager(this);

        sources=findViewById(R.id.td);
        destination=findViewById(R.id.tn);
        amount=findViewById(R.id.amount);


        sources.setText(sessionManager.getSourceadd());
        destination.setText(sessionManager.getBookinglat());
        amount.setText(sessionManager.getBookingamount());
    }
}
