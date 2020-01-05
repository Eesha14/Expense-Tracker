package com.firebaseloginapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IncomePieChart extends AppCompatActivity {

    PieChart piegraph;
    DatabaseReference ref;
    FirebaseUser user;
    String uid,fromdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_pie_chart);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        piegraph = (PieChart) findViewById(R.id.piechart1);
        Intent i = getIntent();
        fromdate = i.getStringExtra("Fromdate");
        ref = FirebaseDatabase.getInstance().getReference(uid).child(fromdate).child("incomes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<PieEntry> entries = new ArrayList<>();
                int x;
                String amt,type;
                for (DataSnapshot mydatasnap : dataSnapshot.getChildren()) {
                     type = mydatasnap.getKey();
                    amt = mydatasnap.getValue(String.class);
                    entries.add(new PieEntry(Float.parseFloat(amt),type));
                }
                PieDataSet set = new PieDataSet(entries, "Incomes");
                Description des = new Description();
                des.setText("The incomes on "+fromdate+" are shown above");
                des.setTextSize(15);
                piegraph.setDescription(des);
                piegraph.setTouchEnabled(true);
                piegraph.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        Float val = e.getY();
                        PieEntry pe = (PieEntry) e;
                        String s = pe.getLabel();
                        Toast.makeText(IncomePieChart.this,s +":"+ String.valueOf(val), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });
                set.setColors(ColorTemplate.COLORFUL_COLORS);
                piegraph.animateY(1000, Easing.EasingOption.EaseInOutBounce);
                PieData data = new PieData(set);
                data.setValueTextColor(Color.BLACK);
                data.setValueTextSize(14f);
                piegraph.setData(data);
                piegraph.setUsePercentValues(true);
                piegraph.getDescription().setEnabled(true);
                piegraph.invalidate();
                piegraph.setExtraOffsets(5,10,5,5);
                piegraph.setDragDecelerationFrictionCoef(0.95f);
                piegraph.setDrawHoleEnabled(false);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
