package com.firebaseloginapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebaseloginapp.R;

/**
 * Created by Eesha on 30-10-2018.
 */

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(){

    }

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images= {
            R.drawable.wallet,
            R.drawable.imgback,
            R.drawable.pieimg
    };

    public String[] slide_headings ={
            "EXPENSES",
            "INCOMES",
            "STATISTICS"
    };

    public String[] slide_descs ={
            "Add your expenses on daily basis by entering various categories of expenses spent on that day.",
            "Add your incomes on daily basis by entering various categories of income sources gained on that day.",
            "Get a interactable and colourful report of your daily expenses and incomes in terms of graphs",
    };
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout)  object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slidelayout,container,false);
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.head1);
        TextView slideDescription = (TextView) view.findViewById(R.id.desp1);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
