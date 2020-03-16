package com.example.etbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.etbc.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION=1;
    private static final int REQUEST_CAMERA=2;
    private static final int REQUEST_INTERNET=3;


    public  static String uid="userId";
    Button btnSignIn,btnRegister;

    RelativeLayout rootLayout;

    FirebaseAuth auth;
    FirebaseDatabase db;

    DatabaseReference users;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ADD PERMISSION


        //save user details


        //init firebase

        //init view
        btnRegister= findViewById(R.id.btnRegister);
        btnSignIn= findViewById(R.id.btnSignIn);
        rootLayout= findViewById(R.id.rootLayout);
        //event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*showRegisterDialog();*/
                Intent intent=new Intent(MainActivity.this,register2.class);
                startActivity(intent);


            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,login_class.class);
                startActivity(intent);



            }
        });
    }


}
