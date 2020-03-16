package com.example.etbc.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.etbc.Model.User;
import com.example.etbc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfile_admin extends AppCompatActivity {
    DatabaseReference dbuserProfile;
    ListView userProfile;
    List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_admin);
        userProfile= findViewById(R.id.profile_list_admin);
        dbuserProfile= FirebaseDatabase.getInstance().getReference("Users");

        userList=new ArrayList<>();
        dbuserProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot userSnap:dataSnapshot.getChildren()){
                    User user=userSnap.getValue(User.class);
                    userList.add(user);
                }
               User_list_Admin adapter = new User_list_Admin(UserProfile_admin.this,userList);
                userProfile.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
