package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import creo.com.myapplication.utils.Global;
import creo.com.myapplication.utils.SessionManager;

public class savedplacelist extends AppCompatActivity {
    private SavedAdapter savedAdapter;
    private RecyclerView recyclerView;
    Context context=this;
    private String URLline = Global.BASE_URL+"user/get_favorite/";
    ArrayList<SavedPojo> save;
    SessionManager sessionManager;
    private ProgressDialog dialog ;
    TextView add;

    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedplacelist);
        sessionManager = new SessionManager(this);
        recyclerView = findViewById(R.id.re);
        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(savedplacelist.this,searchplace.class));
            }
        });
        dialog=new ProgressDialog(savedplacelist.this,R.style.MyAlertDialogStyle);
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
                        Toast.makeText(savedplacelist.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("places");

                            Log.d("otp","mm"+ot);


                            Toast.makeText(savedplacelist.this, ot, Toast.LENGTH_LONG).show();
                            //  JSONObject obj = new JSONObject(response);





                            save = new ArrayList<>();
                         //   JSONObject jsonObject1=jsonObject.getJSONObject("places");
                            Log.d("data","mm"+status);
                            JSONArray dataArray  = new JSONArray(status);
                            JSONObject jsonObject4 = dataArray.optJSONObject(0);

                           // Log.d("fieldcab","mm"+fieldcab);

                            for (int i = 0; i < dataArray.length(); i++) {

                                SavedPojo playerModel = new SavedPojo();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                JSONObject fieldcab = dataobj.optJSONObject("fields");
                                playerModel.setName(fieldcab.getString("place_name"));
                            //    playerModel.setLatitude(fieldcab.getString("latitude"));
                            //    playerModel.setLongitude(fieldcab.getString("longitude"));
                                //  playerModel.setRat(dataobj.getString(""));


                                save.add(playerModel);



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
                        Toast.makeText(savedplacelist.this,error.toString(),Toast.LENGTH_LONG).show();
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

        savedAdapter = new SavedAdapter(this,save);
        recyclerView.setAdapter(savedAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }
}
