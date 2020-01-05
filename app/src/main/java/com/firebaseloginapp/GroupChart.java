package com.firebaseloginapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupChart extends AppCompatActivity {

    BarChart groupchart;
    DatabaseReference ref,ref2;
    FirebaseUser user;
    String uid,fromdate;
    BarEntry yentries;
    BarDataSet barDataSet1,barDataSet2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chart);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        groupchart = (BarChart) findViewById(R.id.groupgraph);
        Intent i = getIntent();
        fromdate = i.getStringExtra("Fromdate");
        ref = FirebaseDatabase.getInstance().getReference(uid).child(fromdate).child("expenses");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int x,sum=0;
                String amt;
                for (DataSnapshot mydatasnap : dataSnapshot.getChildren()) {
                    //Amount expense = mydatasnap.getValue(Amount.class);
                    String type = mydatasnap.getKey();
                    amt = mydatasnap.getValue(String.class);
                    x = Integer.parseInt(amt);
                    sum = sum + x;
                    yentries= new BarEntry(0,sum);
                    barDataSet1.setColor(Color.GREEN);
                    //ArrayList<BarEntry> barentries = new ArrayList<>()
                }
                ref= FirebaseDatabase.getInstance().getReference(uid).child(fromdate).child("incomes");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int sum=0,x;
                        String amt;
                        for (DataSnapshot mydatasnap : dataSnapshot.getChildren()) {
                            //Amount expense = mydatasnap.getValue(Amount.class);
                            String type = mydatasnap.getKey();
                            amt = mydatasnap.getValue(String.class);
                            x = Integer.parseInt(amt);
                            sum = sum + x;
                            yentries= new BarEntry(1f,sum);
                            barDataSet1.addEntry(yentries);
                            barDataSet2.setColor(Color.GREEN);
                            //ArrayList<BarEntry> barentries = new ArrayList<>()
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                        XAxis xAxis = groupchart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return fromdate; // here you can map your values or pass it as empty string
                            }
                        });
                        BarData data = new BarData(barDataSet1,barDataSet2);
                        groupchart.setData(data);

                        groupchart.setTouchEnabled(true);
                        groupchart.setDragEnabled(true);
                        groupchart.setScaleEnabled(true);
                        groupchart.invalidate();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
}
