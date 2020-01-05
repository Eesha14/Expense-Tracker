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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExpenseBarchart extends AppCompatActivity {

    BarChart expensechart;
    DatabaseReference ref;
    FirebaseUser user;
    String uid,todate,fromdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_barchart);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        expensechart = (BarChart) findViewById(R.id.bargraph);
        Intent i = getIntent();
        fromdate = i.getStringExtra("Fromdate");
        if (fromdate == null || fromdate.isEmpty())
            Log.i("checknulls", "got null");
        else
            Log.i("checknulls", "not null");
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
                    //ArrayList<BarEntry> barentries = new ArrayList<>()
                }
                ArrayList<BarEntry> yentries = new ArrayList<>();
                yentries.add(new BarEntry(1,sum));
                BarDataSet barDataSet = new BarDataSet(yentries, "Expense");
                barDataSet.setColor(Color.RED);
                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(barDataSet);
                XAxis xAxis = expensechart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return fromdate; // here you can map your values or pass it as empty string
                    }
                });


                BarData data = new BarData(dataSets);
                expensechart.setData(data);

                expensechart.setTouchEnabled(true);
                expensechart.setDragEnabled(true);
                expensechart.setScaleEnabled(true);
                expensechart.invalidate();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
