package com.example.etbc;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.etbc.Admin.Admin__menu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class login_class extends AppCompatActivity {
    private static final int REQUEST_LOCATION=1;
    private static final int REQUEST_CAMERA=2;
    private static final int REQUEST_INTERNET=3;
    //database
    public  static String uid="userId";
    TextView password,signup,userlog;
    EditText edtPassword,edtEmail;
    Button signIn;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference users=db.getReference("Users");
   // DatabaseReference admin=db.getReference("Admin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        ActivityCompat .requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CAMERA);
        ActivityCompat .requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_INTERNET);
        edtPassword= findViewById(R.id.etPassword);
        edtEmail= findViewById(R.id.etEmail);
        signIn= findViewById(R.id.btn_signIn);
        signup= findViewById(R.id.reg);
        password= findViewById(R.id.forgotpass);
    password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_class.this, password.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_class.this, register2.class));
            }
        });
        signIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                loginuser();
            }
        });
    }
    private void loginuser(){
        if (TextUtils.isEmpty(edtEmail.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Please Enter Email address",Toast.LENGTH_LONG).show();
            //Snackbar.make(,"Please Enter Email address",Snackbar.LENGTH_LONG)
            //       .show();
            return;
        }
        if (TextUtils.isEmpty(edtPassword.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Please Enter currect password",Toast.LENGTH_LONG).show();
           /* Snackbar.make(v,"Please Enter Password",Snackbar.LENGTH_LONG)
                    .show();*/
            return;
        }
        if (edtPassword.getText().toString().length()<6)
        {
           /* Snackbar.make(v,"Enter Password Too Short",Snackbar.LENGTH_LONG)
                    .show();*/
            Toast.makeText(getApplicationContext(),"Enter Password Too Short",Toast.LENGTH_LONG).show();
            return;
        }
        //login
        auth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>()
                {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        onAuthSuccess(auth.getCurrentUser());
                       /* if (auth.getCurrentUser().isEmailVerified()){

                            onAuthSuccess(authResult.getUser());
                        }else {
                            Toast.makeText(login_class.this,"Please verify Your Email ..!",
                                    Toast.LENGTH_LONG).show();
                        }*/
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(login_class.this,"Failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void onAuthSuccess(FirebaseUser user)
    {

        if (user != null) {
            DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("userType");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);

                    if(value.equals("Admin")) {
                        startActivity(new Intent(login_class.this, Admin__menu.class));
                        Toast.makeText(login_class.this, "You're Logged in as Admin", Toast.LENGTH_SHORT).show();
                        finish();
                    } if(value.equals("User")) {
                        if (auth.getCurrentUser().isEmailVerified()){
                            startActivity(new Intent(login_class.this, User_Menu.class));
                            Toast.makeText(login_class.this, "You're Logged in as User", Toast.LENGTH_SHORT).show();


                            finish();

                        }else {
                            Toast.makeText(login_class.this,"Please verify Your Email ..!",
                                    Toast.LENGTH_LONG).show();
                        }

                       /* startActivity(new Intent(login_class.this, User_Menu.class));
                        Toast.makeText(login_class.this, "You're Logged in as User", Toast.LENGTH_SHORT).show();
                        finish();*/
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(login_class.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
