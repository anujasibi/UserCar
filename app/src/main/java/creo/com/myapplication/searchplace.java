package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
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
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import creo.com.myapplication.utils.Global;
import creo.com.myapplication.utils.SessionManager;

public class searchplace extends AppCompatActivity {
    private String TAG;

    TextView textView,save;
    SessionManager sessionManager;
    Context context=this;
    EditText editText;
    private String URLline = Global.BASE_URL+"user/add_favorite/";
    private ProgressDialog dialog ;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchplace);
        sessionManager = new SessionManager(this);
        Places.initialize(getApplicationContext(), "AIzaSyDFVdjJdMr7UdE2SH8-W3GT53YcolB5l5E");
        textView=findViewById(R.id.txt);
        save=findViewById(R.id.save);
        editText=findViewById(R.id.txt11);
        dialog=new ProgressDialog(searchplace.this,R.style.MyAlertDialogStyle);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Loading..");
                dialog.show();
                saveplace();
            }
        });
        imagen=findViewById(R.id.imk);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        PlacesClient placesClient = Places.createClient(this);
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(placeFields);


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                textView.setText(place.getName());
                grtl();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
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
                    sessionManager.setSavelat(lat);
                    sessionManager.setSavelong(lonh);
                    break;
                default:
                    locationAddress = null;
            }
            //  latLongTV.setText(locationAddress);
            Log.d("locationaddress","mm"+locationAddress);
        }
    }
    private void grtl(){
        String address = textView.getText().toString();
        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(address,
                getApplicationContext(), new searchplace.GeocoderHandler());
    }

    private void saveplace(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(searchplace.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            String rate=jsonObject.optString("Estimated rate");
                            Log.d("otp","mm"+ot);
                            if(status.equals("200")){
                                Toast.makeText(searchplace.this, ot, Toast.LENGTH_LONG).show();
                               /* startActivity(new Intent(searchplace.this,scheduledetails.class));
                                startActivity(i);*/
                            }
                            else{
                                Toast.makeText(searchplace.this, "Invalid Password."+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(searchplace.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name",editText.getText().toString());
               // Log.d("lat","mm"+sessionManager.getScheduledlat());
                params.put("lat",sessionManager.getSavelat());
              //  Log.d("destlatt","mm"+sessionManager.getScheduledlong());
                params.put("long",sessionManager.getSavelong());
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
