package com.firebaseloginapp.AccountActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebaseloginapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword;
    ProgressDialog pd;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        findViewById(R.id.buttonSignUP).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }
     private void registerUser(){
         String email = editTextEmail.getText().toString().trim();
         String password = editTextPassword.getText().toString().trim();

         if(email.isEmpty()){
             pd.cancel();
             editTextEmail.setError("Email is required");
             editTextEmail.requestFocus();
             return;
         }
         if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
             pd.cancel();
             editTextEmail.setError("please enter valid email");
             editTextEmail.requestFocus();
             return;
         }
         if(password.isEmpty()){
             pd.cancel();
             editTextPassword.setError("Password is required");
             editTextPassword.requestFocus();
             return;
         }
         if(password.length()<6){
             pd.cancel();
             editTextPassword.setError("Minimum length of password should be 6");
             editTextPassword.requestFocus();
             return;
         }

         mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 pd.cancel();
                 if(task.isSuccessful()){
                     Toast.makeText(getApplicationContext(),"User Registered Successful",Toast.LENGTH_SHORT).show();
                 }
             }
         });
     }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignUP:
                pd = new ProgressDialog(SignupActivity.this);
                pd.setTitle("Sign In");
                pd.setMessage("Signing In....");
                pd.show();
                registerUser();
                break;

            case R.id.textViewLogin:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }
}
