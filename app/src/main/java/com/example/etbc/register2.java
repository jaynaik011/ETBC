package com.example.etbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.etbc.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register2 extends AppCompatActivity {

    String gender = "";
    private ProgressBar Bar;
    public  static String uid="userId";
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference users=db.getReference("Users");

    Button signup,register;
    RadioGroup group;
    RadioButton rb;
    String usertype="User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        group = findViewById(R.id.RadioGender);
        final EditText edtEmail=findViewById(R.id.etEmail);
        final EditText edtPassword=findViewById(R.id.etPassword);
        final EditText edtPhone=findViewById(R.id.etPhone);
        final EditText edtname=findViewById(R.id.etName);
        final EditText edtbdate=findViewById(R.id.etbdate);
        final Button signup=findViewById(R.id.btn_signup);
        //final EditText edtgender=register_layout.findViewById(R.id.etGender);
        final RadioGroup rg= findViewById(R.id.RadioGender);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtEmail.getText().toString()))
                {
                    Snackbar.make(v,"Please Enter Email address",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(edtPassword.getText().toString()))
                {
                    Snackbar.make(v,"Please Enter Password",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                if (edtPassword.getText().toString().length()<6)
                {
                    Snackbar.make(v,"Enter Password Too short",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(edtname.getText().toString()))
                {
                    Snackbar.make(v,"Please Enter Name",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(edtPhone.getText().toString()))
                {
                    Snackbar.make(v,"Please Enter Phone Number",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
             if (TextUtils.isEmpty(edtbdate.getText().toString()))
                {
                    Snackbar.make(v,"Please Enter Date OF Birth",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                if(group.getCheckedRadioButtonId()!=-1){

                    int selectedId = rg.getCheckedRadioButtonId();
                    rb = findViewById(selectedId);
                    gender = rb.getText().toString();
                }else {
                    Toast.makeText(getApplicationContext(), "Please Select Gender", Toast.LENGTH_SHORT).show();
                }



                        auth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        //save user to db
                                        User user= new User();
                                        user.setEmail(edtEmail.getText().toString());
                                        user.setPassword(edtPassword.getText().toString());
                                        user.setName(edtname.getText().toString());
                                        user.setPhone(edtPhone.getText().toString());
                                        user.setGender(gender);
                                        user.setBdate(edtbdate.getText().toString());
                                        user.setUserType("User");
                                        //use email to key
                                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>()
                                                {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Intent intent=new Intent(register2.this,login_class.class);
                                                        startActivity(intent);
                                                        auth.getCurrentUser().sendEmailVerification()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful())
                                                                {
                                                                    Toast.makeText(register2.this,"Register Successfully .Please Check Email To verification",Toast.LENGTH_SHORT).show();
                                                                    Intent intent=new Intent(register2.this,login_class.class);
                                                                    startActivity(intent);
                                                                }
                                                                else
                                                                    {
                                                                    Toast.makeText(register2.this,task.getException().getMessage(),Toast.LENGTH_SHORT)
                                                                            .show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(register2.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                        ;


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(register2.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });



                // register new user


            }

        });
    }
}
