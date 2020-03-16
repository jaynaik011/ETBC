package com.example.etbc.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.etbc.HIstory;
import com.example.etbc.MainActivity;
import com.example.etbc.Profile;
import com.example.etbc.R;
import com.example.etbc.login_class;

public class Admin__menu extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__menu);
        GridLayout mainGrid;
        mainGrid= findViewById(R.id.mainGrid_admin);
        setSingleEvent(mainGrid);
    }
    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(finalI == 0){
                        Intent intent = new Intent(Admin__menu.this,Ticket_History_Admin.class);
                        startActivity(intent);
                    }
                    else if(finalI == 1){
                        Intent intent = new Intent(Admin__menu.this, UserProfile_admin.class);
                        startActivity(intent);
                    }
                    else if(finalI == 2){
                        Intent intent = new Intent(Admin__menu.this, Profile.class);
                        startActivity(intent);
                    }
                    else if(finalI ==3){
                        Intent intent = new Intent(Admin__menu.this, login_class.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(Admin__menu.this,"Please set activity for this card",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
