package com.example.etbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    DatabaseReference dbProfile;
    EditText name, phone, email;
    TextView dob, gender;
    Button update, delete;
    private String checker = "";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
  //  DatabaseReference db=FirebaseDatabase.getInstance().getReference("Users").child("user");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.settings_full_name);
        phone = findViewById(R.id.settings_phone_number);
        email = findViewById(R.id.settings_Emailaddress);
        dob = findViewById(R.id.settings_DOB);
        gender = findViewById(R.id.settings_gender);
        update = findViewById(R.id.update_account_settings_btn);
        delete = findViewById(R.id.delete_account_settings_btn);

        dbProfile = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        dbProfile.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists()|| dataSnapshot!=null)
                {

                    String Email = dataSnapshot.child("email").getValue().toString();
                    String uname = dataSnapshot.child("name").getValue().toString();
                    String no = dataSnapshot.child("phone").getValue().toString();
                    String Gender = dataSnapshot.child("gender").getValue().toString();
                    String date = dataSnapshot.child("bdate").getValue().toString();

                    name.setText(uname);
                    email.setText(Email);
                    phone.setText(no);
                    dob.setText(date);
                    gender.setText(Gender);
                }
                else {
                    Toast.makeText(Profile.this,"User Profile Load Failed",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked"))
                {
                   userInfoSaved();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                    builder.setCancelable(false);
                    builder.setMessage("Do you want to Update Profile?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateOnlyUserInfo();
                        }
                    });
                    builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user select "No", just cancel this dialog and continue with app
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteOnlyUserInfo();
                    }
                });
                builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
            }


        });
    }
     public void updateOnlyUserInfo()
    {
        dbProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("name").setValue(name.getText().toString());
                dataSnapshot.getRef().child("email").setValue(email.getText().toString());
                dataSnapshot.getRef().child("phone").setValue(phone.getText().toString());
                Toast.makeText(Profile.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void deleteOnlyUserInfo()
    {
        dbProfile.removeValue();
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override

            public void onComplete(@NonNull Task<Void> task)
            {

                if(task.isSuccessful()){
                    Toast.makeText(Profile.this,"Account deleted",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Profile.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {

                    Toast.makeText(Profile.this,task.getException().getMessage(),
                    Toast.LENGTH_SHORT).show();
                }
             }
        });


    }
    private void userInfoSaved()
    {
        if (TextUtils.isEmpty(name.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone.getText().toString()))
        {
            Toast.makeText(this, "Phone Number is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email.getText().toString()))
        {
            Toast.makeText(this, "Email is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(dob.getText().toString()))
        {
            Toast.makeText(this, "Date Of Birth is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(gender.getText().toString()))
        {
            Toast.makeText(this, "Gender is mandatory.", Toast.LENGTH_SHORT).show();
        }
    }
}






