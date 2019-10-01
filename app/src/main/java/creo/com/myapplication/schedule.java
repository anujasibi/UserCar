package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import creo.com.myapplication.utils.Global;
import creo.com.myapplication.utils.SessionManager;

public class schedule extends AppCompatActivity {
    private long date;
    private DateFormat format;
    String currentDateandTime;
    private SavedAdapter savedAdapter;
    private SlidingUpPanelLayout mLayout;
    EditText editText;
    CardView cardView,card;
    TextView textView,text,book;
    private RecyclerView recyclerView;
    SessionManager sessionManager;
    Context mContext=this;
    Context context=this;
    String phone_no = null;
    TextView set_date,set_time;
    ArrayList<SavedPojo> save;
    private String URLline = Global.BASE_URL+"user/schedule_trip/";
    private String URLlinen = Global.BASE_URL+"user/get_favorite/";
    private ProgressDialog dialog ;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        sessionManager = new SessionManager(this);
        dialog=new ProgressDialog(schedule.this,R.style.MyAlertDialogStyle);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        passlatlong();
      //  final ArrayList<SlidingPojo>pojo = new ArrayList<>();
        /*SlidingPojo slidingPojo = new SlidingPojo("kottayam");
        SlidingPojo slidingPojo1 = new SlidingPojo("kochi");
        pojo.add(slidingPojo);
        pojo.add(slidingPojo1);*/
//        final SlidingPojo[] slidingPojo = new SlidingPojo[]{
//                new SlidingPojo("Kaloor"),
//                new SlidingPojo("Kottayam"),
//                new SlidingPojo("Thrissur"),
//                new SlidingPojo("Kollam"),
//                new SlidingPojo("Thrissur"),
//
//
//
//        };
        set_date = (TextView) findViewById(R.id.textj);
        set_time = (TextView) findViewById(R.id.textjj);

        book=findViewById(R.id.book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Loading..");
                dialog.show();
                String address = editText.getText().toString();

                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address,
                        getApplicationContext(), new schedule.GeocoderHandler());
                scheduletrip();


            }
        });
        recyclerView = findViewById(R.id.re);
        /*SlidingAdapter slidingAdapter = new SlidingAdapter(pojo,mContext);

        recyclerView.setAdapter(slidingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(schedule.this,"You clicked"+pojo.get(position).getName(),Toast.LENGTH_SHORT).show();
                editText.setText(pojo.get(position).getName());
            }
        });*/


        editText=findViewById(R.id.whereto);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });
        cardView=findViewById(R.id.carder);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(schedule.this,PLACE.class));
            }
        });

       textView=findViewById(R.id.teb);

       Log.d("cllll","api"+Apiclient.place_name+editText.getText().toString());
       // String s=getIntent().getStringExtra("name");
        if(!(Apiclient.place_name.equals("null"))) {
            if(Apiclient.work_name.equals("null")) {
                textView.setText(Apiclient.place_name);
                editText.setText(Apiclient.place_name);
            }
        }

        if(!(Apiclient.work_name.equals("null"))) {
            if(Apiclient.place_name.equals("null")) {
                text.setText(Apiclient.work_name);
                editText.setText(Apiclient.work_name);
            }
        }
//
//        if(!(Apiclient.work_name.equals("null"))) {
//            text.setText(Apiclient.work_name);
//            editText.setText(Apiclient.work_name);
//        }
//        if(Apiclient.work_name.equals("null")){
//            text.setText("");
//            editText.setText("");
//        }
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i("kkll", "onPanelSlide, offset " + slideOffset);


            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i("lll", "onPanelStateChanged " + newState);
               /* InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);*/


            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            }
        });







        final long date = System.currentTimeMillis();
        final Calendar ca = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("EEE , dd  MMM");

        try {
            set_date.setText(format.format(ca.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat forma = new SimpleDateFormat("h:mm a");
        try {
            set_time.setText(forma.format(ca.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*String dateString = sdf.format(date);
        set_date.setText(dateString);

        long datev = System.currentTimeMillis();

        SimpleDateFormat sdfv = new SimpleDateFormat("h:mm a");
        String dateStringv = sdfv.format(datev);
        set_time.setText(dateStringv);*/

        final DatePickerDialog.OnDateSetListener dated = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                ca.set(Calendar.YEAR, year);
                ca.set(Calendar.MONTH, monthOfYear);
                ca.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "EEE , dd  MMM"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                set_date.setText(sdf.format(ca.getTime()));
            }

        };

        set_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(schedule.this, dated, ca
                        .get(Calendar.YEAR), ca.get(Calendar.MONTH),
                        ca.get(Calendar.DAY_OF_MONTH)).show();
            }
                
            
        });

        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(schedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String AM_PM ;
                        if(hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        set_time.setText(hourOfDay + " : " + minutes + " " + AM_PM );


                    }
                }, 0, 0, false);
                timePickerDialog.show();
            }

        });


    }
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            String lat,lonh;

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    lat= bundle.getString("lat");
                    lonh=bundle.getString("long");
                    Log.d("latitude","mm"+lat);
                    Log.d("longitude","mm"+lonh);
                    sessionManager.setScheduledlat(lat);
                    sessionManager.setScheduledlong(lonh);
                    break;
                default:
                    locationAddress = null;
            }
            //  latLongTV.setText(locationAddress);
            Log.d("locationaddress","mm"+locationAddress);
        }
    }
    private void scheduletrip(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      dialog.dismiss();
                        Toast.makeText(schedule.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            String rate=jsonObject.optString("Estimated rate");
                            Log.d("otp","mm"+ot);
                            if(status.equals("200")){
                                Toast.makeText(schedule.this, ot, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(schedule.this,scheduledetails.class));
                                Intent i=new Intent(schedule.this,scheduledetails.class);
                                i.putExtra("name",editText.getText().toString());
                                Log.d("mmvv","mm"+editText.getText());
                                i.putExtra("date",set_date.getText());
                                i.putExtra("time",set_time.getText());
                                i.putExtra("rate",rate);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(schedule.this, "Invalid Password."+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(schedule.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("date",set_date.getText().toString());
                params.put("time",set_time.getText().toString());
                params.put("dest_lat",sessionManager.getScheduledlat());
                Log.d("destlatt","mm"+sessionManager.getScheduledlat());
                params.put("dest_long",sessionManager.getScheduledlong());
                Log.d("destlatt","mm"+sessionManager.getScheduledlong());
                params.put("src_lat",sessionManager.getSourcLat());
                params.put("src_lng",sessionManager.getSourcLong());
                params.put("source",sessionManager.getSourceadd());
                params.put("destination",editText.getText().toString());
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
    private void passlatlong(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLlinen,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(schedule.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("places");

                            Log.d("otp","mm"+ot);


                            Toast.makeText(schedule.this, ot, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(schedule.this,error.toString(),Toast.LENGTH_LONG).show();
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
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(schedule.this,"You clicked"+save.get(position).getName(),Toast.LENGTH_SHORT).show();
                editText.setText(save.get(position).getName());
            }
        });

    }
}




   

