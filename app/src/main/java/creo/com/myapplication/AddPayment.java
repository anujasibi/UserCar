package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import creo.com.myapplication.utils.Global;

public class AddPayment extends AppCompatActivity {

    CardView cardView,cardv;
    TextView cash,online;
    String cname,dname,imag,dest,amount=null;

    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        cardView=findViewById(R.id.carde);
        cardv=findViewById(R.id.cardee);

        cash=findViewById(R.id.textn);
        online=findViewById(R.id.textn1);

        Bundle bundle=getIntent().getExtras();
        cname=bundle.getString("Car");
        dname=bundle.getString("Name");
        imag=bundle.getString("image");
        dest=bundle.getString("dest");
        amount=bundle.getString("rate");
        imagen=findViewById(R.id.imk);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddPayment.this,CarDetailsnew.class);
//                i.putExtra("cash",cash.getText().toString());
                Global.mode="Cash";
                intent.putExtra("Car",cname);
                intent.putExtra("Name",dname);
                intent.putExtra("image",imag);
                intent.putExtra("dest",dest);
                intent.putExtra("rate",amount);
                startActivity(intent);
            }
        });
        cardv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddPayment.this,CarDetailsnew.class);
//                i.putExtra("online",online.getText().toString());
                Global.mode="Pay Online";
                intent.putExtra("Car",cname);
                intent.putExtra("Name",dname);
                intent.putExtra("image",imag);
                intent.putExtra("dest",dest);
                intent.putExtra("rate",amount);
                startActivity(intent);
            }
        });

    }
}
