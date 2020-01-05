package com.firebaseloginapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.JOYFUL_COLORS;

public class ExpensePieChart extends AppCompatActivity {

    PieChart piegraph;
    DatabaseReference ref;
    FirebaseUser user;
    String uid,fromdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_pie_chart);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        piegraph = (PieChart) findViewById(R.id.piechart);
        Intent i = getIntent();
        fromdate = i.getStringExtra("Fromdate");
        ref = FirebaseDatabase.getInstance().getReference(uid).child(fromdate).child("expenses");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<PieEntry> entries = new ArrayList<>();
                float x;
                String amt;
                x= dataSnapshot.getChildrenCount();
                for (DataSnapshot mydatasnap : dataSnapshot.getChildren()) {
                    String type = mydatasnap.getKey();
                    amt = mydatasnap.getValue(String.class);
                    entries.add(new PieEntry(Float.parseFloat(amt),type));
                }
                PieDataSet set = new PieDataSet(entries, "ExpensesType");
                Description des = new Description();
                des.setText("The expenses on "+fromdate+" are shown above");
                des.setTextSize(15);
                piegraph.setDescription(des);
                piegraph.setTouchEnabled(true);
                piegraph.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        Float val = e.getY();
                        PieEntry pe = (PieEntry) e;
                        String s = pe.getLabel();
                        Toast.makeText(ExpensePieChart.this,s+" : "+ String.valueOf(val), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });
                set.setColors(ColorTemplate.JOYFUL_COLORS);
                piegraph.animateY(1000, Easing.EasingOption.EaseInOutCubic);
                PieData data = new PieData(set);
                data.setValueTextSize(14f);
                data.setValueTextColor(Color.BLACK);
                piegraph.setData(data);
                piegraph.invalidate();
                piegraph.setTouchEnabled(true);
                piegraph.setUsePercentValues(true);
                piegraph.getDescription().setEnabled(true);
                piegraph.setExtraOffsets(5,10,5,5);
                piegraph.setDragDecelerationFrictionCoef(0.95f);
                piegraph.setDrawHoleEnabled(true);
                piegraph.setTransparentCircleRadius(61f);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
