package com.example.etbc.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.etbc.HIstory;
import com.example.etbc.Model.TicketBooked;
import com.example.etbc.Model.Ticketlist;
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

public class Ticket_History_Admin extends AppCompatActivity {
    ListView BookTicket;
    DatabaseReference dbTicket;
    List<TicketBooked> ticketBookedList;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket__history__admin);
        BookTicket= findViewById(R.id.ticketlist_admin);
        dbTicket= FirebaseDatabase.getInstance().getReference("All Ticket");
        ticketBookedList=new ArrayList<>();
        dbTicket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ticketBookedList.clear();
                for(DataSnapshot TicketSnap:dataSnapshot.getChildren()){
                    TicketBooked  ticketlist=TicketSnap.getValue(TicketBooked.class);
                    ticketBookedList.add(ticketlist);
                }
               Ticket_list_admin adapter = new Ticket_list_admin(Ticket_History_Admin.this,ticketBookedList);
                BookTicket.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
