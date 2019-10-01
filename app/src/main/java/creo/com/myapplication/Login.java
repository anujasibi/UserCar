package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import creo.com.myapplication.utils.SessionManager;

public class Login extends AppCompatActivity {
    TextView login;
    TextView signup;
    TextInputEditText phoneno,password;
    TextView forget;
    SessionManager sessionManager;
    Context context=this;
    String phone_no = null;
    private String URLline = Global.BASE_URL+"user/user_login/";
    private ProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.tb);
        phoneno=findViewById(R.id.name);
        signup=findViewById(R.id.si);
        password=findViewById(R.id.names);
        forget=findViewById(R.id.tbc);
        Bundle bundle = getIntent().getExtras();
        phone_no = bundle.getString("phone_no");
        Log.d("phone","mm"+phone_no);
        phoneno.setText(phone_no);
        phoneno.setEnabled(false);
        dialog=new ProgressDialog(Login.this,R.style.MyAlertDialogStyle);
        sessionManager = new SessionManager(this);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.getText().toString().equals("")){
                    Toast.makeText(Login.this,"Password Field Required",Toast.LENGTH_LONG).show();

                }
                else {
                    dialog.setMessage("Loading..");
                    dialog.show();
                    loginuser();

                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, signup.class);
                intent.putExtra("phone_no",phone_no);
                startActivity(intent);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,forgotpassword.class));
            }
        });
    }
    private void loginuser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("status");
                            String token=jsonObject.optString("token");
                            sessionManager.setTokens(token);
                            Log.d("otp","mm"+ot);
                            if(status.equals("200")){
                                Toast.makeText(Login.this, ot, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Login.this, "Invalid Password."+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("phone_no",phoneno.getText().toString());
                params.put("password",password.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

}

