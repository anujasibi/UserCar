package creo.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class cardetails extends AppCompatActivity  {
    private static ViewPager mPager,npager;
    TextView textView,text;
    Context context = this;
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    private static final Integer[] IMAGES = {R.drawable.back,R.drawable.back,R.drawable.back,R.drawable.back,R.drawable.back,R.drawable.back,R.drawable.back,R.drawable.back};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private ArrayList<String>images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardetails);
        textView=findViewById(R.id.textn);
        text=findViewById(R.id.textn1);
        mPager = findViewById(R.id.pager);
        //   npager=findViewById(R.id.pagernew);
        CirclePageIndicator indicator =
                findViewById(R.id.indicator);
        // CirclePageIndicator indicatornew =
        //     findViewById(R.id.indicatornew);
        init();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cardetails.this,AddPayment.class));
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cardetails.this,searchplace.class));

            }
        });

    }

    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager =  findViewById(R.id.pager);
        images.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSFkJUlsdyTEJox7ZnWUG2oW-36-v-GtTzVUf0yvJc25Ho5G0zl");
        images.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRegRj4esGKya39pkh1IC6X0BYh63TIvrDFnXgZ63o2mu6BmeIu");
        images.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdzN1nJqVwOq9me1sK5xXcqD9zwLFwh13gnHJ-ttIG6RNjUhuI");
        images.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3DNDkmbBiGR7OmzhXl0FNNq19e8mARkwrn7z8miAwHGmsR0Kx");

        mPager.setAdapter(new slideadp(context,images));

        CirclePageIndicator indicator =
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 1000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }

        });

    }
}

