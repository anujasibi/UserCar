package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import creo.com.myapplication.utils.Global;

public class signup extends AppCompatActivity {
    TextView signup;
    TextView login;
    Context context=this;
    String phone_no = null;
    TextInputEditText name,email,phoneno,password,uname;
    private String URLline = Global.BASE_URL+"user/new_cabs/sign_up/";
    private ProgressDialog dialog ;
    boolean doubleBackToExitPressedOnce = false;
    private String URLl = Global.BASE_URL+"user/check_username/";
    private String URLli = Global.BASE_URL+"user/check_email/";
    private String URLlin = Global.BASE_URL+"user/check_phoneno/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=findViewById(R.id.name);
        email=findViewById(R.id.ema);
        phoneno=findViewById(R.id.phnn);
        password=findViewById(R.id.names);
        uname=findViewById(R.id.unames);
        signup=findViewById(R.id.tb);
        login=findViewById(R.id.si);
        Bundle bundle = getIntent().getExtras();
        phone_no = bundle.getString("phone_no");
        Log.d("phone","mm"+phone_no);
        phoneno.setText(phone_no);
        phoneno.setEnabled(false);
        dialog=new ProgressDialog(signup.this,R.style.MyAlertDialogStyle);
        uname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkuname();
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkemail();
            }
        });
        phoneno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkphone();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Loading..");
                dialog.show();
                signupuser();
             //   startActivity(new Intent(signup.this,Login.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, Login.class);
                intent.putExtra("phone_no",phone_no);
                startActivity(intent);
            }
        });


    }

    private void checkphone(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLlin,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(registernew.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            Log.d("otp","mm"+ot);
                            if(status.equals("2OO")){
                                //Toast.makeText(signup.this, ot, Toast.LENGTH_LONG).show();
                            }
                            else if(status.equals("201")){
                                //   Toast.makeText(registernew.this,"PhoneNumber Field should not be null",Toast.LENGTH_LONG).show();
                                phoneno.setError("PhoneNumber already exist");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(signup.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("phone_no",phoneno.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    private void checkemail(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLli,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(registernew.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            Log.d("otp","mm"+ot);
                            if(status.equals("2OO")){
                                //Toast.makeText(signup.this, ot, Toast.LENGTH_LONG).show();
                            }
                            else if(status.equals("201")){
                                //   Toast.makeText(registernew.this,"PhoneNumber Field should not be null",Toast.LENGTH_LONG).show();
                                email.setError("Email already exist");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(signup.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
    private void checkuname(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(registernew.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            Log.d("otp","mm"+ot);
                            if(status.equals("2OO")){
                                //Toast.makeText(signup.this, ot, Toast.LENGTH_LONG).show();
                            }
                            else if(status.equals("201")){
                                //   Toast.makeText(registernew.this,"PhoneNumber Field should not be null",Toast.LENGTH_LONG).show();
                                uname.setError("Username already exist");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(signup.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",uname.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }


    private void signupuser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(signup.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("status");
                            Log.d("otp","mm"+ot);
                            if(status.equals("400")){
                                Toast.makeText(signup.this, ot, Toast.LENGTH_LONG).show();
                            }
                            else if(status.equals("422")){
                                Toast.makeText(signup.this,"PhoneNumber Field should not be null",Toast.LENGTH_LONG).show();

                            }
                            else{
                                Toast.makeText(signup.this, ot, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(signup.this, MainActivity.class);
                                startActivity(intent);

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
                        Toast.makeText(signup.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name",name.getText().toString());
                params.put("email",email.getText().toString());
                params.put("password",password.getText().toString());
                params.put("phone_no",phoneno.getText().toString());
                params.put("username",uname.getText().toString());
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
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



}

