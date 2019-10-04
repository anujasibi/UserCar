package creo.com.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
    TextView carname,drivername,destn,payment,book;
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
    CardView cardView;
    private ProgressDialog dialogs ;
    private String URLlin = "http://creocabs.herokuapp.com/user/make_payment/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbysource);
        fair();

        carname=findViewById(R.id.text);
        cardView=findViewById(R.id.cardz);
        drivername=findViewById(R.id.names);
        imageView=findViewById(R.id.indicator);
        destn=findViewById(R.id.textn1);
        amount=findViewById(R.id.amount);
        payment=findViewById(R.id.payment);
        book=findViewById(R.id.book);
        sessionManager = new SessionManager(this);
        dialogs=new ProgressDialog(carbysource.this,R.style.MyAlertDialogStyle);
        imagen=findViewById(R.id.imk);
        dialogs.setMessage("Loading..");
        dialogs.show();
        payment.setText(Global.mode);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(carbysource.this,AddPayment.class);
                intent.putExtra("Car",cname);
                intent.putExtra("Name",dname);
                intent.putExtra("image",imag);
                intent.putExtra("dest",dest);
                intent.putExtra("rate",amount.getText().toString());
                startActivity(intent);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogs.setMessage("Loading..");
                dialogs.show();
                if(Global.mode.equals("Cash")){
                    boouser();
                }
                if(Global.mode.equals("Pay Online")){
                    Intent intent=new Intent(carbysource.this,PaymentActivity.class);
                    intent.putExtra("destination",destn.getText().toString());
                    intent.putExtra("mode_of_payment",payment.getText().toString());
                    intent.putExtra("imagamounte",rate);
                    startActivity(intent);
                }



            }
        });


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
        destn.setText(sessionManager.getBookinglong());



    }
    private void fair(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialogs.dismiss();
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

    private void boouser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLlin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialogs.dismiss();
                        //  dialog.dismiss();
                        Toast.makeText(carbysource.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("status");
                            Log.d("otp","mm"+ot);
                            Toast.makeText(carbysource.this, ot, Toast.LENGTH_LONG).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);

                            String msg="Thank you for Choosing Us";
                            String msg1="Booking Confirmed.";
                            String msg2="Click to Track your car.";



                            builder.setTitle(msg).setMessage(msg1+"\n"+msg2)
                                    .setCancelable(false)
                                    .setPositiveButton("Track", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            Intent intent = new Intent(carbysource.this, CarTracking.class);

                                            //  Log.d("pppppp","mm"+token);
                                            startActivity(intent);

                                            //  MyActivity.this.finish();
                                        }
                                    });


                            AlertDialog alert = builder.create();
                            alert.show();


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
                params.put("driver", sessionManager.getID());
                Log.d("driver","mm"+sessionManager.getID());
                params.put("source",sessionManager.getSourceadd());
                Log.d("source","mm"+sessionManager.getSourceadd());
                params.put("destination",destn.getText().toString());
                Log.d("des","mm"+destn.getText().toString());
                sessionManager.setBookinglat(destn.getText().toString());
                params.put("mode_of_payment",payment.getText().toString());
                Log.d("lat","mm"+payment.getText().toString());
                params.put("amount",rate);
                Log.d("lat","mm"+rate);
                params.put("payment_id","");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                return params;
            }



        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);



    }


}
