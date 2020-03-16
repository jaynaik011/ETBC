package com.example.etbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class password extends AppCompatActivity {
    EditText email;
    Button send;
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        email= findViewById(R.id.etEmail_Pass);
        send= findViewById(R.id.btn_sdlink);
        auth = FirebaseAuth.getInstance();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Email address",Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(password.this, email.getText().toString(),Toast.LENGTH_SHORT).show();
              auth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(password.this,"Password Reset Send To Your Email..! ",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(password.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });

    }
}
