package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import creo.com.myapplication.utils.Global;
import creo.com.myapplication.utils.SessionManager;

public class BookingHistory extends AppCompatActivity {
    private bookingAdapter bookingAdapter;
    private RecyclerView recyclerView;
    Context context=this;
    private String URLline = Global.BASE_URL+"user/get_bookings/";
    ArrayList<bookingPojo> bookingPojos;
    SessionManager sessionManager;
    private ProgressDialog dialog ;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        recyclerView = findViewById(R.id.re);
        sessionManager = new SessionManager(this);
        dialog=new ProgressDialog(BookingHistory.this,R.style.MyAlertDialogStyle);
        dialog.setMessage("Loading..");
        dialog.show();
        passlatlong();
        imagen=findViewById(R.id.imk);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void passlatlong(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(BookingHistory.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("data");

                            Log.d("otp","mm"+ot);


                            Toast.makeText(BookingHistory.this, ot, Toast.LENGTH_LONG).show();
                            //  JSONObject obj = new JSONObject(response);





                            bookingPojos = new ArrayList<>();
                            //   JSONObject jsonObject1=jsonObject.getJSONObject("places");
                            Log.d("data","mm"+status);
                            JSONArray dataArray  = new JSONArray(status);
                            JSONObject jsonObject4 = dataArray.optJSONObject(0);

                            // Log.d("fieldcab","mm"+fieldcab);

                            for (int i = 0; i < dataArray.length(); i++) {

                                bookingPojo playerModel = new bookingPojo();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                playerModel.setDate(dataobj.getString("date"));
                                playerModel.setTimes(dataobj.getString("time"));
                                playerModel.setSources(dataobj.getString("source"));
                                playerModel.setDestination(dataobj.getString("destination"));


                                bookingPojos.add(playerModel);



                                setupRecycler();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookingHistory.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
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
    @SuppressLint("WrongConstant")
    private void setupRecycler(){

        bookingAdapter = new bookingAdapter(this,bookingPojos);
        recyclerView.setAdapter(bookingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }
}



