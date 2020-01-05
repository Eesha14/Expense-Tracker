package com.firebaseloginapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnboardFragment extends AppCompatActivity {

    private ViewPager mslide;
    private LinearLayout dotlayout;
    private TextView[] mdots;
    private Button mprev;
    private Button mnext;
    int mcurrent;
    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_fragment);
        mnext=(Button)findViewById(R.id.next);
        mprev=(Button)findViewById(R.id.prev);
        mslide=(ViewPager) findViewById(R.id.viewPager);
        dotlayout=(LinearLayout) findViewById(R.id.dotslayout);

        sliderAdapter = new SliderAdapter(this);

        mslide.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        mslide.addOnPageChangeListener(viewlistenser);

        mnext.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (mnext.getText().toString().equals("Finish")) {
                    Intent i = new Intent(OnboardFragment.this, ListActivity.class);
                    startActivity(i);
                } else {
                    mslide.setCurrentItem(mcurrent + 1);
                }
            }
        });

        mprev.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mslide.setCurrentItem(mcurrent -1);
            }
        });
    }

    public void addDotsIndicator(int position){
        mdots= new TextView[3];
        dotlayout.removeAllViews();

        for(int i=0;i<mdots.length;i++){
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(getResources().getColor(R.color.white));

            dotlayout.addView(mdots[i]);
        }
        if(mdots.length >0){
            mdots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    ViewPager.OnPageChangeListener viewlistenser = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mcurrent = position;
            if (position == 0) {
                mnext.setEnabled(true);
                mprev.setEnabled(false);
                mprev.setVisibility(View.INVISIBLE);
                mnext.setText("Next");
                mprev.setText("");
            } else if (position == mdots.length-1) {
                mnext.setEnabled(true);
                mprev.setEnabled(true);
                mprev.setVisibility(View.VISIBLE);

                mnext.setText("Finish");
                mprev.setText("Back");
            } else {
                mnext.setEnabled(true);
                mprev.setEnabled(true);
                mprev.setVisibility(View.VISIBLE);

                mnext.setText("Next");
                mprev.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
