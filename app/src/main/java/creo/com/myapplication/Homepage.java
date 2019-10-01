package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import creo.com.myapplication.utils.Global;

public class Homepage extends AppCompatActivity {
    EditText num;
    TextView textView;
    ImageView im;
    Context context=this;
    private String URLline = Global.BASE_URL+"user/is_user_exist/";
    public static String otp,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        num=findViewById(R.id.name);

        textView=findViewById(R.id.cn);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(Homepage.this,R.style.MyAlertDialogStyle);
                dialog.setMessage("Loading..");
                dialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                  //  Toast.makeText(Homepage.this,response,Toast.LENGTH_LONG).show();
                    parseData(response);
                    dialog.dismiss();
                  //  Log.d("response","hhh"+response);
                    if(otp.equals("verify")){
                        Intent intent = new Intent(Homepage.this, Login.class);
                        intent.putExtra("phone_no", num.getText().toString());
                        startActivity(intent);

                    }
                    else if(status.equals("400")){
                        Toast.makeText(Homepage.this,"Invalid PhoneNumber",Toast.LENGTH_LONG).show();

                    }
                    else if(status.equals("422")){
                        Toast.makeText(Homepage.this,"PhoneNumber Field should not be null",Toast.LENGTH_LONG).show();

                    }
                    else {
                        Intent intent = new Intent(Homepage.this, otpverify.class);
                        intent.putExtra("phone_no", num.getText().toString());
                        intent.putExtra("otp", otp);
                      //  Log.d("otp", "mmm" + otp);
                      //  Log.d("phone", "mmm" + num.getText().toString());
                        startActivity(intent);
                    }

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Homepage.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
        @Override
        protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<String, String>();
            params.put("phone_no",num.getText().toString());


            return params;
        }

    };

    RequestQueue requestQueue = Volley.newRequestQueue(context);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);

}
    public void parseData(String response) {
       // Log.d("response","hhh"+response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            otp = jsonObject.optString("message");
            status=jsonObject.optString("status");

          //  Log.d("otp","mm"+otp);
          //  Log.d("status","mm"+status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


});




        }
}
