package com.firebaseloginapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebaseloginapp.AccountActivity.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spark.submitbutton.SubmitButton;

import static android.R.style.Theme_Holo_Light_Dialog_MinWidth;

/**
 * Created by Eesha on 18-10-2018.
 */

public class IncomeFragment extends Fragment {
    @Nullable
    private TextView mDisplayDate;
    private DatePickerDialog mDateSetListener;
    private DatePickerDialog.OnDateSetListener listen;
    EditText income;
    EditText amt;
    SubmitButton iButton;
    DatabaseReference databaseincome;
    FirebaseUser user;
    ProgressDialog pd;
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_income,container,false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        databaseincome = FirebaseDatabase.getInstance().getReference(uid);
        mDisplayDate = (TextView) view.findViewById(R.id.date_calender1);
        income = (EditText) view.findViewById(R.id.income_type);
        amt = (EditText) view.findViewById(R.id.amount1_type);
        iButton = (SubmitButton) view.findViewById(R.id.submit_income);
        iButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(getActivity());
                pd.setTitle("Please Wait");
                pd.setMessage("Submitting...");
                pd.show();
                addIncome();
            }
        });
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

        return view;
    }

    private void addIncome() {
        String incometype = income.getText().toString().trim();
        String amttype = amt.getText().toString().trim();
        String datetype = mDisplayDate.getText().toString();

        if (!TextUtils.isEmpty(incometype) && !TextUtils.isEmpty(amttype) && !TextUtils.isEmpty(datetype)) {
            //String id = databaseexpense.push().getKey();

            //Income income= new Income(incometype,amttype,datetype);
            databaseincome.child(datetype).child("incomes").child(incometype).setValue(amttype).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    pd.cancel();
                    Toast.makeText(getActivity(),"Income added",Toast.LENGTH_LONG).show();
                }
            });

        } else {
            pd.cancel();
            Toast.makeText(getActivity(), "you should enter the field content", Toast.LENGTH_SHORT).show();
        }
    }

}
