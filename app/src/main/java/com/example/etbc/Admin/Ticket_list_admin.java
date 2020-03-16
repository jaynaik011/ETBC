package com.example.etbc.Admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.etbc.Model.TicketBooked;
import com.example.etbc.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

public class Ticket_list_admin extends ArrayAdapter {
    private Activity context;
    private List<TicketBooked> ticketList;
    public Ticket_list_admin(Activity context, List<TicketBooked> ticketList) {
        super(context, R.layout.ticket_list_admin, ticketList);
        this.context = context;
        this.ticketList = ticketList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        @SuppressLint("ViewHolder") View listViewItem=inflater.inflate(R.layout.ticket_list_admin,null,true);

        CardView card= listViewItem.findViewById(R.id.li_card);
        TextView uname= listViewItem.findViewById(R.id.li_name);
        TextView tid= listViewItem.findViewById(R.id.li_tid);
        TextView journey= listViewItem.findViewById(R.id.li_journey);
        TextView jtime= listViewItem.findViewById(R.id.li_time);
        TextView price= listViewItem.findViewById(R.id.li_price);
        TextView status= listViewItem.findViewById(R.id.li_status);
        TextView count= listViewItem.findViewById(R.id.count);


        //data getting
        final TicketBooked ticket=ticketList.get(position);
        uname.setText("User Name:"+ticket.getUname());
        tid.setText("TicketId:--"+ticket.getId());
        journey.setText("Source:-"+ticket.getSource()+"\n "+"Destination:-"+ticket.getDest());
        jtime.setText("Book Time:-"+ticket.getStart()+"\n "+"Till Vaild:-"+ticket.getEnd());


        if(ticket.getIs_retn().compareTo("Yes")==0)
        {

            String sta="<b>Status:- </b>"+"<font color='#E22013'>"+ticket.getIs_retn()+"<font>";
            status.setText(Html.fromHtml(sta));
        }
        else if(ticket.getIs_retn().compareTo("No")==0)
        {

            String sta="<b>Status:- </b>"+"<font color='#E22013'>"+ticket.getIs_retn()+"<font>";
            status.setText(Html.fromHtml(sta));
        }

        String amount=String.valueOf(ticket.getAmt());
        String pcount=String.valueOf(ticket.getCount());
        price.setText("Amount:--"+amount);
        count.setText("NO OF Passenger:--"+pcount);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ticket.getIs_retn().compareTo("Yes")==0)
                {
                    if(checkvalid(ticket.getEnd())) {

                    }
                    else
                    {
                        Snackbar.make(v,"The Ticket is Expired!",Snackbar.LENGTH_SHORT).show();
                    }
                }
                else if(ticket.getIs_retn().compareTo("No")==0) {
                    if(checkvalid(ticket.getStart())) {

                    }
                    else
                    {
                        Snackbar.make(v,"The Ticket is Expired!",Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return listViewItem;


    }
    public Boolean checkvalid(String date)
    {
        String[] diff =date.split(" ");
        String[] dt =diff[0].split("/");
        String[] tm =diff[1].split(":");

        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.YEAR,Integer.parseInt(dt[0]));
        cal.set(Calendar.MONTH,Integer.parseInt(dt[1])-1);
        cal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dt[2]));
        cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(tm[0]));
        cal.set(Calendar.MINUTE,Integer.parseInt(tm[1]));

        Calendar now=Calendar.getInstance();
        return cal.getTime().after(now.getTime());
    }
}
