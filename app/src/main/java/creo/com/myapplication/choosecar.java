package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import creo.com.myapplication.utils.Global;
import creo.com.myapplication.utils.SessionManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class choosecar extends AppCompatActivity {
   // private RecyclerAdapter recyclerAdapter;
    private recylernewadapter recylernewadapter;
    private RecyclerView recyclerView;
    Context mContext=this;
    EditText editText,editt,time;
    Context context = this;
    SessionManager sessionManager;
    ArrayList<RecyclerPojo> dataModelArrayList;
    private String URLline = Global.BASE_URL+"driver/get_cabs_on_source/";
    private ProgressDialog dialogs ;
    ImageView imagen;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        dialogs=new ProgressDialog(choosecar.this,R.style.MyAlertDialogStyle);
        dialogs.setMessage("Loading");
        dialogs.show();

      /*  imagen=findViewById(R.id.imk);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
*/
        setContentView(R.layout.activity_choosecar);
       /* RecyclerPojo[] recyclerPojo = new RecyclerPojo[]{
                new RecyclerPojo("ALTO", R.drawable.alto, "    45 KM", "4.2*"),
                new RecyclerPojo("DZIRE", R.drawable.dzire, "50 KM", "3.5*"),
                new RecyclerPojo("SWIFT", R.drawable.swift, "60 KM", "4.0*"),
                new RecyclerPojo("SWIFT", R.drawable.swift, "60 KM", "4.0*"),
                new RecyclerPojo("SWIFT", R.drawable.swift, "60 KM", "4.0*"),

        };*/


        recyclerView = findViewById(R.id.rev);
        sessionManager = new SessionManager(this);
        passlatlong();
      //  RecyclerAdapter recyclerAdapter = new RecyclerAdapter(recyclerPojo,mContext);

       /* recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));*/


    }
    private void passlatlong(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialogs.dismiss();
                        recyclerView.setVisibility(View.VISIBLE);
                        Toast.makeText(choosecar.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("status");
                            Log.d("otp","mm"+ot);

                            Toast.makeText(choosecar.this, ot, Toast.LENGTH_LONG).show();
                            //  JSONObject obj = new JSONObject(response);


                            dataModelArrayList = new ArrayList<>();
                            JSONArray dataArray  = jsonObject.getJSONArray("available_cabs");

                            for (int i = 0; i < dataArray.length(); i++) {

                                RecyclerPojo playerModel = new RecyclerPojo();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                playerModel.setName(dataobj.getString("Car"));
                                playerModel.setDistance(dataobj.getString("Name"));
                                playerModel.setImage(Global.BASE_URL+"media/"+dataobj.getString("image"));
                                //  playerModel.setRat(dataobj.getString(""));


                                dataModelArrayList.add(playerModel);



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
                        Toast.makeText(choosecar.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("latitude",sessionManager.getSourcLat() );
                params.put("longitude", sessionManager.getSourcLong());
                params.put("dest_lat",sessionManager.getUpdatelat());
                params.put("dest_long",sessionManager.getUpdateLong());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    @SuppressLint("WrongConstant")
    private void setupRecycler(){

        recylernewadapter = new recylernewadapter(this,dataModelArrayList);
        recyclerView.setAdapter(recylernewadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }
}
