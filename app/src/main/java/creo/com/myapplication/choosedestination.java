package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;

import creo.com.myapplication.utils.SessionManager;

public class choosedestination extends AppCompatActivity {
    private String TAG;
    String cname,dname,imag=null;
    TextView textView,latLongTV;
    TextInputEditText editText;
    SessionManager sessionManager;
ImageView imagen;
    Button addressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosedestination);
        sessionManager = new SessionManager(this);

     //  latLongTV = (TextView) findViewById(R.id.latLongTV);
        editText =  findViewById(R.id.name);

        addressButton = (Button) findViewById(R.id.addressButton);

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
        Places.initialize(getApplicationContext(), "AIzaSyDFVdjJdMr7UdE2SH8-W3GT53YcolB5l5E");
       textView=findViewById(R.id.txt);
        PlacesClient placesClient = Places.createClient(this);
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(placeFields);

        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                String address = editText.getText().toString();

                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address,
                        getApplicationContext(), new choosedestination.GeocoderHandler());
                Intent intent=new Intent(choosedestination.this,CarDetailsnew.class);
                intent.putExtra("Car",cname);
                intent.putExtra("Name",dname);
                intent.putExtra("image",imag);
                intent.putExtra("dest",address);
                startActivity(intent);
            }
        });


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                textView.setText(place.getName());
                grtl();
                Intent intent=new Intent(choosedestination.this,CarDetailsnew.class);
                intent.putExtra("Car",cname);
                intent.putExtra("Name",dname);
                intent.putExtra("image",imag);
                intent.putExtra("dest",place.getName());
                startActivity(intent);
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
                    sessionManager.setDestLat(lat);
                    sessionManager.setDestLong(lonh);
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
                getApplicationContext(), new choosedestination.GeocoderHandler());
    }

}
