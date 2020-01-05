package com.firebaseloginapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;

import static android.R.style.Theme_Holo_Light_Dialog_MinWidth;

/**
 * Created by Eesha on 18-10-2018.
 */

public class StatisticsFragment extends Fragment{
    @Nullable
    private TextView mDisplayDate;
    String check;
    private Button gen;
    private DatePickerDialog mDateSetListener;
    private DatePickerDialog.OnDateSetListener listen;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_statistics,container,false);
        mDisplayDate = (TextView) view.findViewById(R.id.from_date);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal= Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                mDateSetListener = new DatePickerDialog(getActivity(),listen,day,month,year);
                mDateSetListener.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                mDateSetListener.show();
            }
        });
        listen = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date = month+"-"+dayOfMonth+"-"+year;
                mDisplayDate.setText(date);
            }
        };


        Spinner spin = (Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(getActivity(), R.array.report_list,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                switch(text){
                    case "Expense Bar Chart":
                        if(mDisplayDate.getText().toString().isEmpty()) {
                            mDisplayDate.setError("please enter a date");
                            mDisplayDate.requestFocus();
                        }else{
                            Intent k = new Intent(getActivity(), ExpenseBarchart.class);
                            k.putExtra("Fromdate", mDisplayDate.getText());
                            startActivity(k);
                        }
                        break;
                    case "Expense Pie Chart":
                        if(mDisplayDate.getText().toString().isEmpty()) {
                            mDisplayDate.setError("please enter a date");
                            mDisplayDate.requestFocus();
                        }else {
                            Intent a = new Intent(getActivity(), ExpensePieChart.class);
                            a.putExtra("Fromdate", mDisplayDate.getText());
                            startActivity(a);
                        }
                        break;
                    case "Income Bar Chart":
                        if(mDisplayDate.getText().toString().isEmpty()) {
                            mDisplayDate.setError("please enter a date");
                            mDisplayDate.requestFocus();
                        }else {
                            Intent b = new Intent(getActivity(), IncomeBarChart.class);
                            b.putExtra("Fromdate", mDisplayDate.getText());
                            startActivity(b);
                        }
                        break;
                    case "Income Pie Chart":
                        if(mDisplayDate.getText().toString().isEmpty()) {
                            mDisplayDate.setError("please enter a date");
                            mDisplayDate.requestFocus();
                        }else {
                            Intent c = new Intent(getActivity(), IncomePieChart.class);
                            c.putExtra("Fromdate", mDisplayDate.getText());
                            startActivity(c);
                        }
                        break;
                }
                Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

}
