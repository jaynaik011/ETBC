package com.example.etbc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class User_Menu extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GridLayout mainGrid;
        mainGrid= findViewById(R.id.mainGrid);

        setSingleEvent(mainGrid);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    }
    private void setSingleEvent(final GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(finalI == 0){
                        mainGrid.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                IntentIntegrator integrator = new IntentIntegrator(User_Menu.this);
                                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                                integrator.setPrompt("Scan");
                                integrator.setCameraId(0);
                                integrator.setOrientationLocked(true);
                                integrator.setBeepEnabled(false);
                                integrator.setBarcodeImageEnabled(false);
                                integrator.initiateScan();

                            }
                        });

                        /*Intent intent = new Intent(User_Menu.this,User_Menu.class);
                        startActivity(intent);*/
                    }
                    else if(finalI == 1){
                        Intent intent = new Intent(User_Menu.this,BookTicket.class);
                        startActivity(intent);
                    }
                    else if(finalI == 2){
                        Intent intent = new Intent(User_Menu.this,HIstory.class);
                        startActivity(intent);
                    }
                    else if(finalI == 3){
                        Intent intent = new Intent(User_Menu.this,Profile.class);
                        startActivity(intent);
                    }
                    else if(finalI == 4){
                        Intent intent = new Intent(User_Menu.this,User_Menu.class);
                        startActivity(intent);
                    }
                    else if(finalI == 5){
                       // Paper.book().destroy();

                        Intent intent = new Intent(User_Menu.this, login_class.class);
                       // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else if(finalI == 6){

                    }
                    else{
                        Toast.makeText(User_Menu.this,"Please set activity for this card",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    String station =obj.getString("Station");
                    Intent intent = new Intent(User_Menu.this,QrBooked.class);
                    intent.putExtra("Station",station);
                    startActivity(intent);
                    //Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    /*Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();*/
                    Toast.makeText(this, "Wrong Qr Code Scanned",Toast.LENGTH_SHORT).show();
                }

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
