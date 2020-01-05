package com.firebaseloginapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class ExpenseFragment extends Fragment {
    @Nullable
    private static TextView mDisplayDate;
    private DatePickerDialog mDateSetListener;
    private DatePickerDialog.OnDateSetListener listen;
   private static  EditText expense;
    EditText amount;
    SubmitButton bButton;
    DatabaseReference databaseexpense;
    FirebaseUser user;
    ProgressDialog pd;
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_expenses,container,false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        databaseexpense = FirebaseDatabase.getInstance().getReference(uid);
        mDisplayDate = (TextView) view.findViewById(R.id.date_calender);
        expense = (EditText) view.findViewById(R.id.expense_type);
        amount = (EditText) view.findViewById(R.id.amount_type);
        bButton = (SubmitButton) view.findViewById(R.id.submit_expense);
        bButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(getActivity());
                pd.setTitle("Please Wait");
                pd.setMessage("Submitting...");
                pd.show();
                addExpense();
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
                   //databaseexpense.setValue(d);
                   //databaseexpense.child(date).setValue("expenses");
                       mDisplayDate.setText(date);
               }
           };


        return view;
    }


    private void addExpense() {
        String expensetype = expense.getText().toString().trim();
        String amounttype = amount.getText().toString().trim();
        String datetype = mDisplayDate.getText().toString();

        if (!TextUtils.isEmpty(expensetype) && !TextUtils.isEmpty(amounttype) && !TextUtils.isEmpty(datetype)) {
            //String id = databaseexpense.push().getKey();
            Expense expense= new Expense(expensetype,amounttype);
            databaseexpense.child(datetype).child("expenses").child(expensetype).setValue(amounttype).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    pd.cancel();
                    Toast.makeText(getActivity(),"Expense added",Toast.LENGTH_LONG).show();
                }
            });

        } else {
            pd.cancel();
            Toast.makeText(getActivity(), "you should enter the field content", Toast.LENGTH_SHORT).show();
        }
    }
}
