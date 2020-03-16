package com.example.etbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.etbc.Model.TicketBooked;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QrBooked extends AppCompatActivity {
    private static final int REQUEST_CAMERA=1;
    double lat,longt;

    //card details
    String card="",uid,Email,uname;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date d;
    double ticdist,m,n,m1,n1;

    Spinner dest,passenger,journeyType;
    TextView amt,reason ,source;
    Button bkTickt;

    int passengercount=1,totalamt;
    String status="Single",sourceselet,destselet;
    String[] count=new String[]{"1","2","3","4","5","6"};
    String[] Type=new String[]{"Single","Return"};
    String d_start,d_end,d_return,is_return;
    String[] stations=new String[]{"Select Station","virar","Nallasopara","Vasai Road","Naigaon",
            "Bhayander","Mira Road","Dahisar","Borivali","Kandivali","Malad",
            "Goregaon", "Ram Mandir","Jogeshwari","Andheri","Vile Parle"/*,"Santacruz ","khar Road",
            "Bandra","Mahim","Matunga Road","Dadar","Parbhadevi","lower Parel","Mahalaxmi",
            "Mumbai Central", "Grand Road","Charni Road","Marin line","Churchgate"*/};
    final HashMap<String, List<Double>> hmLang = new HashMap<>();
    //data base connection
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseBookTicket,dbProfile;
    DatabaseReference databaseBookTicket1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_booked);
        // User name
        //user name and other details
        dbProfile = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        dbProfile.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists()|| dataSnapshot!=null)
                {

                    Email = dataSnapshot.child("email").getValue().toString();
                    uname = dataSnapshot.child("name").getValue().toString();
                }
                else {
                    Toast.makeText(QrBooked.this,"Error to fetch User Name",Toast.LENGTH_SHORT
                    ).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //hash map values
        hmLang.put("virar", Arrays.asList(19.4550, 72.8119));
        hmLang.put("Nallasopara", Arrays.asList(19.4176, 72.8189));
        hmLang.put("Vasai Road", Arrays.asList(19.3826, 72.8321));
        hmLang.put("Naigaon", Arrays.asList(19.3514, 72.8464));
        hmLang.put("Bhayander", Arrays.asList(19.3113, 72.8562));
        hmLang.put("Mira Road", Arrays.asList(19.2809, 72.8560));
        hmLang.put("Dahisar", Arrays.asList(19.2501, 72.8593));
        hmLang.put("Borivali", Arrays.asList(19.2291, 72.8574));
        hmLang.put("Kandivali", Arrays.asList(19.2024, 72.8517));
        hmLang.put("Malad", Arrays.asList(19.1870, 72.8489));
        hmLang.put("Goregaon", Arrays.asList(19.1646, 72.8493));
        hmLang.put("Ram Mandir", Arrays.asList(19.1511,72.8501));
        hmLang.put("Jogeshwari", Arrays.asList(19.1361, 72.8488));
        hmLang.put("Andheri", Arrays.asList(19.1198, 72.8465));
        hmLang.put("Vile Parle", Arrays.asList(19.0996, 72.8440));
        uid=user.getUid();
        /*Toast.makeText(this,user.getUid(),Toast.LENGTH_LONG).show();*/
        databaseBookTicket= FirebaseDatabase.getInstance().getReference("User Booked Ticket").child(user.getUid());
        databaseBookTicket1=FirebaseDatabase.getInstance().getReference("All Ticket");
        journeyType= findViewById(R.id.type);
        source= findViewById(R.id.qrSource);
        passenger= findViewById(R.id.passenger);
        dest= findViewById(R.id.destination);
        amt= findViewById(R.id.amount);
        reason= findViewById(R.id.reason);
        bkTickt= findViewById(R.id.BKBookBtn);
        //Source Data
        Intent i= getIntent();
        if( i!=null) {
            String StName = i.getStringExtra("Station");
            source.setText(String.valueOf(StName));
          //  Toast.makeText(this, StName, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "scan wrong qr", Toast.LENGTH_LONG).show();
        }

        //spinner station data
        final ArrayAdapter<String> adapt= new ArrayAdapter<>(QrBooked.this, R.layout.support_simple_spinner_dropdown_item, stations);
        adapt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dest.setAdapter(adapt);
        //spinner no of passenger
        ArrayAdapter<String> adapt1= new ArrayAdapter<>(QrBooked.this, R.layout.support_simple_spinner_dropdown_item, count);
        adapt1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        passenger.setAdapter(adapt1);
        //spinner for type of journey
        ArrayAdapter<String> adapt2= new ArrayAdapter<>(QrBooked.this, R.layout.support_simple_spinner_dropdown_item, Type);
        adapt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        journeyType.setAdapter(adapt2);

        dest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                destselet=dest.getSelectedItem().toString();
                calulateprice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        passenger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                passengercount=position+1;
                calulateprice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                passengercount=1;
            }
        });

        journeyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    status="Single";
                    calulateprice();
                }else if(position==1){
                    status="Return";
                    calulateprice();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                status="Single";
                calulateprice();

            }
        });

        bkTickt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(source.getText().toString().compareTo(dest.getSelectedItem().toString())==0)
                {
                    Toast.makeText(QrBooked.this,"Source and Destination cannot be Same!'",Toast.LENGTH_LONG).show();
                }
                else
                {
                    calculatedate();
                }
            }
        });

    }
    public void calulateprice() {

            final Iterator<Map.Entry<String,List<Double>>> itr = hmLang.entrySet().iterator();
            while (itr.hasNext()){
                Map.Entry<String, List<Double>> entry = itr.next();
                if (destselet.equals(entry.getKey())){
                    List<Double> stacode=entry.getValue();
                    m=stacode.get(0);
                    n=stacode.get(1);
                }
                if(source.equals(entry.getKey())) {
                    List<Double> stacode = entry.getValue();
                    m1 = stacode.get(0);
                    n1 = stacode.get(1);
                }
                ticdist = distance(m, n,m1,n1);
            }
            if(source.getText().toString().compareTo
                    (dest.getSelectedItem().toString())==0)
            {
                amt.setText("");
            }
            else
            {
                String price="";
                if(ticdist<=10)
                {
                    price="5";
                }
                else if(ticdist<=30)
                {
                    price="10";
                }
                else if(ticdist<=55)
                {
                    price="15";
                }
                else if(ticdist>55)
                {
                    price="20";
                }
           /* else if(ticdist<=25)
            {
                price="25";
            }
            else if(ticdist<=30)
            {
                price="30";
            }
            else if(ticdist<=35)
            {
                price="35";
            }
            else if(ticdist>35)
            {
                price="35";
            }*/
                int cal=Integer.parseInt(price);
                totalamt=cal*passengercount;

                if(status.compareTo("Return")==0)
                {
                    totalamt *=2;
                }
                amt.setText(String.valueOf(totalamt));
                //  Toast.makeText(BookTicket.this,String.valueOf(totalamt),Toast.LENGTH_LONG).show();
            }

    }
    public void calculatedate() {
        d=new Date();
        Calendar cals=Calendar.getInstance();
        cals.setTime(d);
        cals.add(Calendar.MINUTE,30);
        String start=sdf.format(cals.getTime());

        Calendar cal=Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.HOUR,4);
        cal.add(Calendar.MINUTE,30);
        String end=sdf.format(cal.getTime());

        String retn="",isreturn="No";
        if(status.compareTo("Return")==0)
        {
            cals.add(Calendar.DAY_OF_MONTH,1);
            retn=sdf.format(cals.getTime());
            isreturn="Yes";
        }

        d_start=start;
        d_end=end;
        d_return=retn;
        is_return=isreturn;

        paydialog();

    }

    public void paydialog() {
        final Dialog dialog=new Dialog(QrBooked.this);
        //d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.payment_dailog);
        //ImageView close= (ImageView) d.findViewById(R.id.pclose);
        String s="Amount:"+amt.getText();
        TextView amount= dialog.findViewById(R.id.pamt);
        //amount.setText(""+s);
        amount.setText(s);

        final EditText cardno,mm,yy,cvv;
        cardno= dialog.findViewById(R.id.pcard);
        mm= dialog.findViewById(R.id.pmm);
        yy= dialog.findViewById(R.id.pyy);
        cvv= dialog.findViewById(R.id.pcvv);
        Button pay= dialog.findViewById(R.id.ppay);
        RadioGroup rg= dialog.findViewById(R.id.prg);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.debit)
                {
                    card="debit";
                }
                else if(checkedId==R.id.credit)
                {
                    card="credit";
                }
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(card.compareTo("")!=0) {
                    if (cardno.getText().toString().compareTo("") != 0) {
                        if (cardno.getText().toString().length() == 16) {
                            if (mm.getText().toString().compareTo("") != 0) {
                                int m = Integer.parseInt(mm.getText().toString());
                                if (m >= 1 && m <= 12) {
                                    if (yy.getText().toString().compareTo("") != 0) {
                                        if (checkyear(v, yy.getText().toString(), mm.getText().toString())) {
                                            if (cvv.getText().toString().compareTo("") != 0) {
                                                if (cvv.getText().toString().length() == 3) {
                                                    book();
                                                    dialog.cancel();
                                                } else {
                                                    Snackbar.make(v, "The Length of CVV Should be 3", Snackbar.LENGTH_SHORT).show();
                                                    cvv.requestFocus();
                                                }
                                            } else {
                                                Snackbar.make(v, "Enter CVV", Snackbar.LENGTH_SHORT).show();
                                                cvv.requestFocus();
                                            }
                                        }
                                    } else {
                                        Snackbar.make(v, "Enter Year || YYYY", Snackbar.LENGTH_SHORT).show();
                                        yy.requestFocus();
                                    }
                                } else {
                                    Snackbar.make(v, "Invalid Month", Snackbar.LENGTH_SHORT).show();
                                    mm.requestFocus();
                                }
                            } else {
                                Snackbar.make(v, "Enter Month || MM", Snackbar.LENGTH_SHORT).show();
                                mm.requestFocus();
                            }
                        } else {
                            Snackbar.make(v, "The Length of Card No should be 16", Snackbar.LENGTH_SHORT).show();
                            cardno.requestFocus();
                        }
                    } else {
                        Snackbar.make(v, "Enter Card No", Snackbar.LENGTH_SHORT).show();
                        cardno.requestFocus();
                    }
                }
                else
                {
                    Snackbar.make(v, "Select a Payment Method", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void   book() {
        String ticketID=databaseBookTicket.push().getKey();
        TicketBooked bookticket = new TicketBooked(ticketID,
                source.getText().toString(),
                dest.getSelectedItem().toString(),
                passengercount,
                totalamt,
                d_start,
                d_end,
                is_return,
                d_return,uid,Email,uname
        );
        databaseBookTicket.child(ticketID).setValue(bookticket);
        databaseBookTicket1.child(ticketID).setValue(bookticket);
        Toast.makeText(this,"Ticket Booked",Toast.LENGTH_LONG).show();

    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lon1);
        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lon2);
        double distanceInMeters = loc1.distanceTo(loc2);
        return distanceInMeters/1000;

    }

    public Boolean checkyear(View v,String year,String month)
    {
        Boolean res=true;
        if(year.length()==4) {
            Calendar exp = Calendar.getInstance();
            exp.set(Calendar.DAY_OF_MONTH,1);
            exp.set(Calendar.MONTH, Integer.parseInt(month)-1);
            exp.set(Calendar.YEAR, Integer.parseInt(year));

            Calendar now=Calendar.getInstance();
            if(exp.before(now))
            {
                Snackbar.make(v,"Your Card is Expired",Snackbar.LENGTH_SHORT).show();
                res=false;
                return false;
            }
        }
        else
        {
            Snackbar.make(v,"Invalid Year Format || YYYY",Snackbar.LENGTH_SHORT).show();
            res=false;
            return false;
        }
        return res;
    }

}
