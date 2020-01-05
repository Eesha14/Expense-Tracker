package com.firebaseloginapp.AccountActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebaseloginapp.ExpenseFragment;
import com.firebaseloginapp.ListActivity;
import com.firebaseloginapp.MainActivity;
import com.firebaseloginapp.OnboardFragment;
import com.firebaseloginapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private TextView loginclick;
    private Button signclick;
    private EditText musername;
    private EditText mpassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog pd;
    DatabaseReference databaselogin;
    //public static final String EXTRA_TEXT= "com.firebaseloginapp.EXTRA_TEXT";
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);

         mAuth = FirebaseAuth.getInstance();
         databaselogin = FirebaseDatabase.getInstance().getReference().child("users");
         signclick = (Button) findViewById(R.id.button4);
         loginclick = (TextView) findViewById(R.id.login_but);
         musername = (EditText) findViewById(R.id.username);
         mpassword = (EditText) findViewById(R.id.password);
         mAuthListener = new FirebaseAuth.AuthStateListener() {

             @Override
             public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                         if(firebaseAuth.getCurrentUser() != null){
                             startActivity(new Intent(LoginActivity.this,OnboardFragment.class));
                         }
             }
         };

         loginclick.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 pd = new ProgressDialog(LoginActivity.this);
                 pd.setTitle("Log In");
                 pd.setMessage("Logging In....");
                 pd.show();
                 startSignIn();
             }
         });
         signclick.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View v) {
                 Intent j = new Intent(LoginActivity.this,SignupActivity.class);
                 startActivity(j);
             }
         });
     }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
   private void startSignIn(){
       String user = musername.getText().toString();
       String pass = mpassword.getText().toString();
       if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))
       {
           pd.cancel();
           Toast.makeText(LoginActivity.this,"Fields are Empty",Toast.LENGTH_LONG).show();
       } else {

           mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   pd.cancel();

                   if (!task.isSuccessful()) {
                       Toast.makeText(LoginActivity.this, "Sign in Problem", Toast.LENGTH_LONG).show();
                   }
               }
           });
       }
   }

}


