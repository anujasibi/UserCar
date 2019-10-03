package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import creo.com.myapplication.utils.Global;
import creo.com.myapplication.utils.SessionManager;

public class carbysource extends AppCompatActivity {
    TextView carname,drivername,destn;
    String cname,dname,imag,dest=null;
    ImageView imageView;
    TextView amount;
    Context context=this;
    String phone_no = null;
    String rate=null;
    boolean doubleBackToExitPressedOnce = false;
    SessionManager sessionManager;
    private String URLline = Global.BASE_URL+"driver/get_trip_fare/";
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbysource);
        fair();

        carname=findViewById(R.id.text);
        drivername=findViewById(R.id.names);
        imageView=findViewById(R.id.indicator);
        destn=findViewById(R.id.textn1);
        amount=findViewById(R.id.amount);
        sessionManager = new SessionManager(this);

        imagen=findViewById(R.id.imk);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle=getIntent().getExtras();
        cname=bundle.getString("Car");
        dname=bundle.getString("Name");
        imag=bundle.getString("image");
        dest=bundle.getString("dest");

        Picasso.with(carbysource.this).load(imag ).into(imageView);
        carname.setText(cname);
        drivername.setText(dname);
        destn.setText(dest);
        amount.setText(rate);



    }
    private void fair(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  dialog.dismiss();
                        Toast.makeText(carbysource.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("status");
                            rate=jsonObject.optString("rate");
                            Log.d("ratemmmmmm","mm"+rate);
                            amount.setText(rate);
                            Log.d("otp","mm"+ot);
                            Toast.makeText(carbysource.this, ot, Toast.LENGTH_LONG).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(carbysource.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("dest_latitude", sessionManager.getDesttripLat());
                Log.d("lat","mm"+sessionManager.getDesttripLat());
                params.put("dest_longitude",sessionManager.getDesttripLong());
                Log.d("DESTLONG","mm"+sessionManager.getDesttripLong());
                params.put("source_latitude",sessionManager.getSourcetriplat());
                Log.d("lat","mm"+sessionManager.getSourcetriplat());
                params.put("source_longitude",sessionManager.getSourcetriplong());
                Log.d("lat","mm"+sessionManager.getSourcetriplong());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to homepage", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(carbysource.this,MainActivity.class));

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
